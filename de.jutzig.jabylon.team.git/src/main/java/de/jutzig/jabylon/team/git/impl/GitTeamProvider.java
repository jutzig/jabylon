package de.jutzig.jabylon.team.git.impl;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.jcraft.jsch.UserInfo;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.team.git.impl.util.ProgressMonitorWrapper;
import de.jutzig.jabylon.ui.team.TeamProvider;

public class GitTeamProvider implements TeamProvider {



	private Repository createRepository(ProjectVersion project) throws IOException {
		//orig
//		FileRepositoryBuilder builder = new FileRepositoryBuilder();
//		Repository repository = builder.setGitDir(PATH).readEnvironment().findGitDir().build();
//
//		Git git = new Git(repository);              
//		CloneCommand clone = git.cloneRepository();
//		clone.setBare(false);
//		clone.setCloneAllBranches(true);
//		clone.setDirectory(PATH).setURI(url);
//		UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(login, password);                
//		clone.setCredentialsProvider(user);
//		clone.call();   
		
		
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		File gitDir = new File(project.absolutPath().toFileString());
		gitDir.getParentFile().mkdirs();
		Repository repository = builder.setGitDir(new File(gitDir,".git")).build();

		
		return null;
	}

	
	
	public static void main(String[] args) throws IOException, JGitInternalException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException {
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI(new File("target/test").getAbsolutePath()));
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setName("graphiti");
		workspace.getProjects().add(project);
		
		ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
		version.setBranch("master");
		project.setMaster(version);
		
		GitTeamProvider provider = new GitTeamProvider();
//		provider.checkout(version);
		
		
		////
//		FileRepositoryBuilder builder = new FileRepositoryBuilder();
//		builder.setGitDir(new File("target/test/graphiti/master/.git"));
//		builder.setWorkTree(new File("target/test/graphiti/master"));
//		FileRepository fileRepository = builder.build();
//		Git git = new Git(fileRepository);
//		CheckoutCommand checkout = git.checkout();
//		checkout.setName("master");
//		checkout.call();
//		System.out.println(fileRepository.getWorkTree());
		
		
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
		File repoDir = new File(project.absolutPath().toFileString());
		CloneCommand clone = Git.cloneRepository();
		clone.setBare(false);
		clone.setNoCheckout(false);
		clone.setCloneAllBranches(true);
		clone.setDirectory(repoDir);
//		clone.setBranch(project.getBranch());
		
		URI uri = project.getProject().getRepositoryURI();
		if(uri.userInfo()!=null)
		{
			String userInfo = uri.userInfo();
			String[] values = userInfo.split(":");
			UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(values[0], values[1]);                
			clone.setCredentialsProvider(user);
			uri = URI.createHierarchicalURI(uri.scheme(), uri.authority().replace("@"+userInfo, ""), uri.device(), uri.segments(), uri.query(), uri.fragment());
		}
		clone.setURI(uri.toString());
		clone.setProgressMonitor(new ProgressMonitorWrapper(subMon.newChild(80)));

		Git git = clone.call();  
		CheckoutCommand checkout = git.checkout();
		checkout.setName(project.getBranch());
		try {
			checkout.call();
		} catch (JGitInternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RefAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RefNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRefNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subMon.done();
		if(monitor!=null)
			monitor.done();
		
	}

	@Override
	public void commit(ProjectVersion project, IProgressMonitor monitor)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(PropertyFileDescriptor descriptor,
			IProgressMonitor monitor) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
