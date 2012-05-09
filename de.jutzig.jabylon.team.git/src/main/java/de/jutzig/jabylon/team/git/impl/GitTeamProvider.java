package de.jutzig.jabylon.team.git.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.team.git.impl.util.ProgressMonitorWrapper;
import de.jutzig.jabylon.ui.team.TeamProvider;
import de.jutzig.jabylon.ui.util.PreferencesUtil;

public class GitTeamProvider implements TeamProvider {



	private Repository createRepository(ProjectVersion project) throws IOException {
   
			
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		File gitDir = new File(project.absolutPath().toFileString());
		Repository repository = builder.setGitDir(new File(gitDir,".git")).build();
		return repository;
	}

	
	

	@Override
	public Iterable<File> update(ProjectVersion project,
			IProgressMonitor monitor) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<File> update(PropertyFileDescriptor descriptor,
			IProgressMonitor monitor) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkout(ProjectVersion project, IProgressMonitor monitor)
			throws IOException {
		SubMonitor subMon = SubMonitor.convert(monitor,100);
		subMon.setTaskName("Checking out");
		subMon.worked(20);
		File repoDir = new File(project.absolutPath().toFileString());
		CloneCommand clone = Git.cloneRepository();
		clone.setBare(false);
		clone.setNoCheckout(false);
		clone.setCloneAllBranches(true);
		clone.setDirectory(repoDir);

		URI uri = project.getProject().getRepositoryURI();

		clone.setCredentialsProvider(createCredentialsProvider(project.getProject()));
		clone.setURI(stripUserInfo(uri).toString());
		clone.setProgressMonitor(new ProgressMonitorWrapper(subMon.newChild(70)));

		clone.call();  
		subMon.done();
		if(monitor!=null)
			monitor.done();
		
	}

	@Override
	public void commit(ProjectVersion project, IProgressMonitor monitor)
			throws IOException {
		Repository repository = createRepository(project);
		Git git = new Git(repository);
//		AddCommand addCommand = git.add();
		List<String> changedFiles = addNewFiles(git);
		if(changedFiles.isEmpty())
			return;
		CommitCommand commit = git.commit();
		commit.setAuthor("Jabylon", "jabylon@example.org");
		commit.setMessage("Auto sync up by Jabylon");
//		commit.setOnly(only)
		try {
			commit.call();
			PushCommand push = git.push();
			push.setCredentialsProvider(createCredentialsProvider(project.getProject()));
			push.setPushAll();
			push.call();
			
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConcurrentRefUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JGitInternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongRepositoryStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private List<String> addNewFiles(Git git) throws IOException {
		DiffCommand diffCommand = git.diff();
		AddCommand addCommand = git.add();
		List<String> changedFiles = new ArrayList<String>();
		List<String> newFiles = new ArrayList<String>();
		try {
			diffCommand.setOutputStream(System.out);
			List<DiffEntry> result = diffCommand.call();
			for (DiffEntry diffEntry : result) {
				if(diffEntry.getChangeType()==ChangeType.ADD)
				{
					addCommand.addFilepattern(diffEntry.getNewPath());
					newFiles.add(diffEntry.getNewPath());
				}
				else if(diffEntry.getChangeType()==ChangeType.MODIFY)
				{
					changedFiles.add(diffEntry.getOldPath());
				}
			}
			if(!newFiles.isEmpty())
				addCommand.call();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changedFiles.addAll(newFiles);
		return changedFiles;
		
	}




	@Override
	public void commit(PropertyFileDescriptor descriptor,
			IProgressMonitor monitor) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) throws IOException, JGitInternalException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException {
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI(new File("target/test").getAbsolutePath()));
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setName("jabylon3");
		workspace.getProjects().add(project);
		
		ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
		version.setBranch("master");
		project.setMaster(version);
		
		GitTeamProvider provider = new GitTeamProvider();
		provider.commit(version, null);
		
		
	}

	private CredentialsProvider createCredentialsProvider(Project project)
	{
		Preferences node = PreferencesUtil.scopeFor(project);
		String username = node.get(GitConstants.KEY_USERNAME, "");
		String password = node.get(GitConstants.KEY_PASSWORD, "");
		return new UsernamePasswordCredentialsProvider(username, password);
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
