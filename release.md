# Release Instructions

 * In the main pom, set the properties

    	<release.version>[new-version]</release.version>
		<release.tag>v[new-version]</release.tag>
		
 * Update `jabylon/src/site/markdown/releaseNotes.md` with the release notes for the new version
 * Push the changes
 * Generate a GH-Access Token https://github.com/settings/tokens
 * Make sure settings.xml contains these entries

		<server>
			<id>jabylon</id>
			<username>username</username>
			<password>password</password>
			<filePermissions>664</filePermissions>
			<directoryPermissions>775</directoryPermissions>
		</server>
		<server>
			<id>github</id>
			<username>githubuser</username>
			<privateKey>[token]</privateKey>
		</server>
		
 * Set JAVA_HOME to a JDK, e.g.
  
    export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/
  		
 + Release with
   
    mvn -DpreparationGoals="clean install" -DreleaseVersion=1.4.2 -DdevelopmentVersion=1.4.3-SNAPSHOT release:prepare release:perform
    		
