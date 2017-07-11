/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.team.git;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.RebaseCommand.Operation;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.FS;
import org.jabylon.common.team.TeamProvider;
import org.jabylon.common.team.TeamProviderException;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.DiffKind;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.PropertyFileDiff;
import org.jabylon.properties.Workspace;
import org.jabylon.team.git.util.ProgressMonitorWrapper;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Component(enabled=true,immediate=true)
@Service
public class GitTeamProvider implements TeamProvider {
	
	@Property(value="Git")
	private static String KEY_KIND = TeamProvider.KEY_KIND;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GitTeamProvider.class);

    private Repository createRepository(ProjectVersion project) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        File gitDir = new File(project.absoluteFilePath().path());
        Repository repository = builder.setGitDir(new File(gitDir,".git")).build();
        return repository;
    }

    @Override
    public Collection<PropertyFileDiff> update(ProjectVersion project, IProgressMonitor monitor)
        throws TeamProviderException {
    	
        SubMonitor subMon = SubMonitor.convert(monitor,100);
        List<PropertyFileDiff> updatedFiles = new ArrayList<PropertyFileDiff>();
        String branchName = getBranchName(project);
        try {
            Repository repository = createRepository(project);
            Git git = Git.wrap(repository);
            FetchCommand fetchCommand = git.fetch();
            URI uri = project.getParent().getRepositoryURI();
            if(uri!=null)
            	fetchCommand.setRemote(stripUserInfo(uri).toString());
            String refspecString = "refs/heads/{0}:refs/remotes/origin/{0}";
            refspecString = MessageFormat.format(refspecString, branchName);
            RefSpec spec = new RefSpec(refspecString);
            fetchCommand.setRefSpecs(spec);
            subMon.subTask("Fetching from remote");
            if(!"https".equals(uri.scheme()) && ! "http".equals(uri.scheme()))
            	fetchCommand.setTransportConfigCallback(createTransportConfigCallback(project.getParent()));
            fetchCommand.setCredentialsProvider(createCredentialsProvider(project.getParent()));
            fetchCommand.setProgressMonitor(new ProgressMonitorWrapper(subMon.newChild(80)));
            fetchCommand.call();
            ObjectId remoteHead = repository.resolve("refs/remotes/origin/"+branchName+"^{tree}");
            
            DiffCommand diff = git.diff();
            subMon.subTask("Caculating Diff");
            diff.setProgressMonitor(new ProgressMonitorWrapper(subMon.newChild(20)));
            diff.setOldTree(new FileTreeIterator(repository));
            CanonicalTreeParser p = new CanonicalTreeParser();
            ObjectReader reader = repository.newObjectReader();
            try {
                p.reset(reader, remoteHead);
            } finally {
                reader.release();
            }
            diff.setNewTree(p);
            checkCanceled(subMon);
            List<DiffEntry> diffs = diff.call();
            for (DiffEntry diffEntry : diffs) {
            	checkCanceled(subMon);
                updatedFiles.add(convertDiffEntry(diffEntry));
                LOGGER.trace(diffEntry.toString());
            }
            if(!updatedFiles.isEmpty())
            {
            	checkCanceled(subMon);
            	//no more cancel after this point
                ObjectId lastCommitID = repository.resolve("refs/remotes/origin/"+branchName+"^{commit}");
                LOGGER.info("Merging remote commit {} to {}/{}", new Object[]{lastCommitID,branchName,project.getParent().getName()});
                //TODO: use rebase here?
                if(isRebase(project))
                {
                	RebaseCommand rebase = git.rebase();
                	rebase.setUpstream("refs/remotes/origin/"+branchName);
                	RebaseResult result = rebase.call();
                	if(result.getStatus().isSuccessful())
                	{
                		LOGGER.info("Rebase finished: {}",result.getStatus());
                	}
                	else
                	{
                		LOGGER.error("Rebase of {} failed. Attempting abort",project.relativePath());
                		rebase = git.rebase();
                		rebase.setOperation(Operation.ABORT);
                		result = rebase.call();
                		LOGGER.error("Abort finished with {}",result.getStatus());
                	}
                }
                else
                {
                	MergeCommand merge = git.merge();
                	merge.include(lastCommitID);
                	MergeResult mergeResult = merge.call();
                	
                	LOGGER.info("Merge finished: {}",mergeResult.getMergeStatus());                	
                }
            }
            else
            	LOGGER.info("Update finished successfully. Nothing to merge, already up to date");
        } catch (JGitInternalException e) {
            throw new TeamProviderException(e);
        } catch (InvalidRemoteException e) {
            throw new TeamProviderException(e);
        } catch (GitAPIException e) {
            throw new TeamProviderException(e);
        } catch (AmbiguousObjectException e) {
            throw new TeamProviderException(e);
        } catch (IOException e) {
            throw new TeamProviderException(e);
        }
        finally{
            monitor.done();
        }
        return updatedFiles;
    }

    private void checkCanceled(IProgressMonitor monitor) {
    	if(monitor.isCanceled())
    		throw new OperationCanceledException();
		
	}

	private PropertyFileDiff convertDiffEntry(DiffEntry diffEntry) {
        PropertyFileDiff diff = PropertiesFactory.eINSTANCE.createPropertyFileDiff();
        diff.setOldPath(diffEntry.getOldPath());
        diff.setNewPath(diffEntry.getNewPath());
        DiffKind kind = DiffKind.MODIFY;
        switch(diffEntry.getChangeType())
        {
            case ADD:
                kind = DiffKind.ADD;
                break;
            case COPY:
                kind = DiffKind.COPY;
                break;
            case DELETE:
                kind = DiffKind.REMOVE;
                break;
            case MODIFY:
                kind = DiffKind.MODIFY;
                break;
            case RENAME:
                kind = DiffKind.MOVE;
                break;	
        }
        diff.setKind(kind);
        return diff;
    }

    @Override
    public Collection<PropertyFileDiff> update(PropertyFileDescriptor descriptor, IProgressMonitor monitor)
        throws TeamProviderException {

        //TODO check if it needs to be implemented
        return null;
    }

    @Override
    public void checkout(ProjectVersion project, IProgressMonitor monitor) throws TeamProviderException {
        try {
            SubMonitor subMon = SubMonitor.convert(monitor, 100);
            subMon.setTaskName("Checking out");
            subMon.worked(20);
            File repoDir = new File(project.absoluteFilePath().path());
            CloneCommand clone = Git.cloneRepository();
            clone.setBare(false);
            clone.setNoCheckout(false);
            String branchName = getBranchName(project);
            // if(!"master".equals(project.getName()))
            clone.setBranch("refs/heads/" + branchName);
            // clone.setCloneAllBranches(true);
            clone.setBranchesToClone(Collections.singletonList("refs/heads/" + branchName));

            clone.setDirectory(repoDir);

            URI uri = project.getParent().getRepositoryURI();
            if(!"https".equals(uri.scheme()) && ! "http".equals(uri.scheme()))
            	clone.setTransportConfigCallback(createTransportConfigCallback(project.getParent()));
            clone.setCredentialsProvider(createCredentialsProvider(project.getParent()));
            clone.setURI(stripUserInfo(uri).toString());
            clone.setProgressMonitor(new ProgressMonitorWrapper(subMon.newChild(70)));
            
            clone.call();
            subMon.done();
            if (monitor != null)
                monitor.done();
        } catch (TransportException e) {
            throw new TeamProviderException(e);
        } catch (InvalidRemoteException e) {
            throw new TeamProviderException(e);
        } catch (GitAPIException e) {
            throw new TeamProviderException(e);
        }
    }

	@Override
    public void commit(ProjectVersion project, IProgressMonitor monitor) throws TeamProviderException {
        try {
            Repository repository = createRepository(project);
            SubMonitor subMon = SubMonitor.convert(monitor, "Commit", 100);
            Git git = new Git(repository);
            // AddCommand addCommand = git.add();
            List<String> changedFiles = addNewFiles(git, subMon.newChild(30));
            if (!changedFiles.isEmpty())
            {
            	checkCanceled(subMon);
            	CommitCommand commit = git.commit();
                Preferences node = PreferencesUtil.scopeFor(project.getParent());
                String username = node.get(GitConstants.KEY_USERNAME, "Jabylon");
                String email = node.get(GitConstants.KEY_EMAIL, "jabylon@example.org");
                String message = node.get(GitConstants.KEY_MESSAGE, "Auto Sync-up by Jabylon");
                boolean insertChangeId = node.getBoolean(GitConstants.KEY_INSERT_CHANGE_ID, false);
                commit.setAuthor(username, email);
                commit.setCommitter(username, email);
                commit.setInsertChangeId(insertChangeId);
                commit.setMessage(message);
                for (String path : changedFiles) {
                	checkCanceled(subMon);
                    commit.setOnly(path);
                    
                }
                commit.call();	
                subMon.worked(10);
            }
            else
            {
            	LOGGER.info("No changed files, skipping commit phase");
            }
            checkCanceled(subMon);
            PushCommand push = git.push();
            URI uri = project.getParent().getRepositoryURI();
            if(uri!=null)
            	push.setRemote(stripUserInfo(uri).toString());
            push.setProgressMonitor(new ProgressMonitorWrapper(subMon.newChild(60)));
            if(!"https".equals(uri.scheme()) && ! "http".equals(uri.scheme()))
            	push.setTransportConfigCallback(createTransportConfigCallback(project.getParent()));
            push.setCredentialsProvider(createCredentialsProvider(project.getParent()));
            
            RefSpec spec = createRefSpec(project);
            push.setRefSpecs(spec);
            
            Iterable<PushResult> result = push.call();
            for (PushResult r : result) {           
            	for(RemoteRefUpdate rru : r.getRemoteUpdates()) {
            		if(rru.getStatus() != RemoteRefUpdate.Status.OK && rru.getStatus() != RemoteRefUpdate.Status.UP_TO_DATE) {
            			String error = "Push failed: "+rru.getStatus();
            			LOGGER.error(error);
            			throw new TeamProviderException(error);
            		}
            	}
            }
            
            Ref ref = repository.getRef(getBranchName(project));
            if(ref!=null)
            {
            	LOGGER.info("Successfully pushed {} to {}",ref.getObjectId(),project.getParent().getRepositoryURI());
            }
        } catch (NoHeadException e) {
            throw new TeamProviderException(e);
        } catch (NoMessageException e) {
            throw new TeamProviderException(e);
        } catch (ConcurrentRefUpdateException e) {
            throw new TeamProviderException(e);
        } catch (JGitInternalException e) {
            throw new TeamProviderException(e);
        } catch (WrongRepositoryStateException e) {
            throw new TeamProviderException(e);
        } catch (InvalidRemoteException e) {
            throw new TeamProviderException(e);
        } catch (IOException e) {
            throw new TeamProviderException(e);
        } catch (GitAPIException e) {
            throw new TeamProviderException(e);
        } finally {
        	if(monitor!=null)
        		monitor.done();
        }
    }

    private RefSpec createRefSpec(ProjectVersion version) {
        Preferences node = PreferencesUtil.scopeFor(version);
        String refSpecString = node.get(GitConstants.KEY_PUSH_REFSPEC, GitConstants.DEFAULT_PUSH_REFSPEC);
        if(refSpecString.isEmpty())
        	refSpecString = GitConstants.DEFAULT_PUSH_REFSPEC;
        refSpecString = MessageFormat.format(refSpecString, getBranchName(version));
		return new RefSpec(refSpecString);
	}
    
    private boolean isRebase(ProjectVersion version) {
        Preferences node = PreferencesUtil.scopeFor(version);
        return node.getBoolean(GitConstants.KEY_REBASE, GitConstants.DEFAULT_REBASE);
	}  
    
    private String getBranchName(ProjectVersion version) {
    	Preferences node = PreferencesUtil.scopeFor(version);
    	return node.get(GitConstants.KEY_BRANCH_NAME, version.getName());
    }

	private List<String> addNewFiles(Git git, IProgressMonitor monitor) throws IOException, GitAPIException {
    	monitor.beginTask("Creating Diff", 100);
        DiffCommand diffCommand = git.diff();
        
        AddCommand addCommand = git.add();
        List<String> changedFiles = new ArrayList<String>();
        List<String> newFiles = new ArrayList<String>();

        List<DiffEntry> result = diffCommand.call();
        //TODO: delete won't work
        for (DiffEntry diffEntry : result) {
        	checkCanceled(monitor);
            if(diffEntry.getChangeType()==ChangeType.ADD)
            {
                addCommand.addFilepattern(diffEntry.getNewPath());
                newFiles.add(diffEntry.getNewPath());
                monitor.subTask(diffEntry.getNewPath());
            }
            else if(diffEntry.getChangeType()==ChangeType.MODIFY)
            {
            	monitor.subTask(diffEntry.getOldPath());
                changedFiles.add(diffEntry.getOldPath());
            }
            monitor.worked(0);
        }
        if(!newFiles.isEmpty())
            addCommand.call();

        changedFiles.addAll(newFiles);
        monitor.done();
        return changedFiles;
    }

    @Override
    public void commit(PropertyFileDescriptor descriptor, IProgressMonitor monitor) throws TeamProviderException {

        // TODO Auto-generated method stub
    }
    
	@Override
	public Collection<PropertyFileDiff> reset(ProjectVersion project, IProgressMonitor monitor) throws TeamProviderException {
		
		List<PropertyFileDiff> updatedFiles = new ArrayList<PropertyFileDiff>();
        try {
			Repository repository = createRepository(project);
			SubMonitor subMon = SubMonitor.convert(monitor, "Reset", 100);
			Git git = new Git(repository);
			subMon.subTask("Calculating Diff");
			String branchName = getBranchName(project);
			DiffCommand diffCommand = git.diff();
			diffCommand.setProgressMonitor(new ProgressMonitorWrapper(subMon.newChild(30)));
			diffCommand.setOldTree(prepareTreeParser(repository, "refs/remotes/origin/"+branchName));
			diffCommand.setNewTree(null);
			List<DiffEntry> diffs = diffCommand.call();
			for (DiffEntry diffEntry : diffs) {
	        	checkCanceled(monitor);
	        	PropertyFileDiff fileDiff = createDiff(diffEntry, monitor);
	        	revertDiff(fileDiff);
	            updatedFiles.add(fileDiff);
			}
			
			subMon.subTask("Executing Reset");
			ResetCommand reset = git.reset();
			reset.setMode(ResetType.HARD);
			reset.setRef("refs/remotes/origin/"+branchName);
			reset.call();
			
			
			CleanCommand clean = git.clean();
			clean.setCleanDirectories(true);
			Set<String> call = clean.call();
			LOGGER.info("cleaned "+call);
			
		} catch (IOException e) {
			LOGGER.error("reset failed",e);
			throw new TeamProviderException(e);
		} catch (GitAPIException e) {
			LOGGER.error("reset failed",e);
			throw new TeamProviderException(e);
		}
        return updatedFiles;
	}
	
	private PropertyFileDiff createDiff(DiffEntry diffEntry, IProgressMonitor monitor) {
		PropertyFileDiff fileDiff = PropertiesFactory.eINSTANCE.createPropertyFileDiff();
		switch (diffEntry.getChangeType()) {
		case ADD:
			monitor.subTask(diffEntry.getNewPath());
        	fileDiff.setKind(DiffKind.ADD);
        	fileDiff.setNewPath(diffEntry.getNewPath());
			return fileDiff;
		case COPY:
			monitor.subTask(diffEntry.getNewPath());
        	fileDiff.setKind(DiffKind.COPY);
        	fileDiff.setNewPath(diffEntry.getNewPath());
        	fileDiff.setOldPath(diffEntry.getOldPath());
			return fileDiff;
		case DELETE:
			monitor.subTask(diffEntry.getOldPath());
        	fileDiff.setKind(DiffKind.REMOVE);
        	fileDiff.setOldPath(diffEntry.getOldPath());
			return fileDiff;
		case MODIFY:
			monitor.subTask(diffEntry.getNewPath());
        	fileDiff.setKind(DiffKind.MODIFY);
        	fileDiff.setOldPath(diffEntry.getOldPath());
        	fileDiff.setNewPath(diffEntry.getNewPath());
			return fileDiff;			
		case RENAME:
			monitor.subTask(diffEntry.getNewPath());
        	fileDiff.setKind(DiffKind.MOVE);
        	fileDiff.setOldPath(diffEntry.getOldPath());
        	fileDiff.setNewPath(diffEntry.getOldPath());
			return fileDiff;			
		default:
			break;
		}
		return fileDiff;
	}

	/**
	 * inverts the direction of a diff
	 * @param diff
	 */
	private void revertDiff(PropertyFileDiff diff) {
		String path1 = diff.getNewPath();
		String path2 = diff.getOldPath();
		diff.setOldPath(path1);
		diff.setNewPath(path2);
		switch (diff.getKind()) {
		case REMOVE:
			diff.setKind(DiffKind.ADD);
			break;
		case ADD:
			diff.setKind(DiffKind.REMOVE);
			break;
		default:
			break;
		}
	}
	
	private static AbstractTreeIterator prepareTreeParser(Repository repository, String ref) throws IOException, MissingObjectException,
			IncorrectObjectTypeException {
		// from the commit we can build the tree which allows us to construct
		// the TreeParser
		Ref head = repository.getRef(ref);
		RevWalk walk = new RevWalk(repository);
		RevCommit commit = walk.parseCommit(head.getObjectId());
		RevTree tree = walk.parseTree(commit.getTree().getId());

		CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
		ObjectReader oldReader = repository.newObjectReader();
		try {
			oldTreeParser.reset(oldReader, tree.getId());
		} finally {
			oldReader.release();
		}
		return oldTreeParser;
	}

    public static void main(String[] args) throws IOException, JGitInternalException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, JSchException {
        Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
        workspace.setRoot(URI.createFileURI(new File("target/test").getAbsolutePath()));
        Project project = PropertiesFactory.eINSTANCE.createProject();
        project.setName("jabylon3");
        workspace.getChildren().add(project);

        ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
        version.setName("master");
        project.getChildren().add(version);

        GitTeamProvider provider = new GitTeamProvider();
        provider.commit(version, null);
    }

    private CredentialsProvider createCredentialsProvider(Project project)
    {
        Preferences node = PreferencesUtil.scopeFor(project);
        String username = node.get(GitConstants.KEY_USERNAME, "");
        String password = node.get(GitConstants.KEY_PASSWORD, "");
        return new UsernamePasswordCredentialsProvider(username, password) {
        	@Override
        	public boolean supports(CredentialItem... items) {
        		for (CredentialItem credentialItem : items) {
					if (credentialItem instanceof CredentialItem.YesNoType) {
						return true;
					}
					else
						return super.supports(credentialItem);
				}
        		return super.supports(items);
        	}
        	
        	@Override
        	public boolean get(URIish uri, CredentialItem... items) throws UnsupportedCredentialItem {
        		List<CredentialItem> remaining = new ArrayList<CredentialItem>(Arrays.asList(items));
        		for (CredentialItem item : items) {
        			if (item instanceof CredentialItem.YesNoType) {
						//unknown host warning
        				remaining.remove(item);
						CredentialItem.YesNoType yesNo = (CredentialItem.YesNoType) item;
						yesNo.setValue(true);											
					}
				}
        		return super.get(uri, remaining.toArray(new CredentialItem[0]));
        	}
        };
    }

	private TransportConfigCallback createTransportConfigCallback(Project project) {

        Preferences node = PreferencesUtil.scopeFor(project);
        final String username = node.get(GitConstants.KEY_USERNAME, "");
        final String password = node.get(GitConstants.KEY_PASSWORD, "");
        final String privateKey = node.get(GitConstants.KEY_PRIVATE_KEY, null);

		final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {

			@Override
			protected void configure(Host host, Session session) {

				// do nothing
			}

			protected JSch createDefaultJSch(FS fs) throws JSchException {
				JSch defaultJSch = super.createDefaultJSch(fs);
				if(privateKey!=null) {
					byte[] passphrase = null;
					if(password!=null && !password.trim().isEmpty())
						passphrase = password.getBytes(StandardCharsets.UTF_8);
					defaultJSch.addIdentity(username, privateKey.getBytes(), null, passphrase);
				}
				return defaultJSch;
			}
		};
		return new TransportConfigCallback() {

			@Override
			public void configure(Transport transport) {
				SshTransport sshTransport = (SshTransport) transport;
				sshTransport.setSshSessionFactory(sshSessionFactory);
			}
		};
	}
    
    private URI stripUserInfo(URI uri)
    {
        if(uri.userInfo()!=null && uri.userInfo().length()>0)
        {
            String userInfo = uri.userInfo();
            URI strippedUri = URI.createHierarchicalURI(uri.scheme(), uri.authority().replace(userInfo+"@", ""), uri.device(), uri.segments(), uri.query(), uri.fragment());
            return strippedUri;
        }
        return uri;
    }
}
