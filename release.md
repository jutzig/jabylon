# Release Instructions

Make sure settings.xml contains these entries

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
			<password>password</password>
		</server>
		
Release with
   
    mvn -DpreparationGoals="clean install" -DreleaseVersion=1.3.0 -DdevelopmentVersion=1.3.1-SNAPSHOT release:prepare release:perform
    		
