# exo-sonar-rules

## Description
This project contains custom Java rules for eXo SonarQube.  
* It checks that the CSRF protection is activated for WebUI listeners associated to PROCESS phase.
* It checks that invocations of JCR getRepositoryXXX() are multi-tenant compliant (no invocation of RepositoryService.getDefaultRepository() or RepositoryService.getRepository(name))

## Pre-requirements
* SonarQube 5.0.1
* Sonar Java Plugin 3.2 installed

## Usage
1. Build a jar file using
```maven
mvn clean package
```
2. Copy the jar file in the /extensions/plugin folder of your SonarQube installation
3. Restart SonarQube
