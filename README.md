### Resource development management system

### Required software:
- JDK 1.8 or above
- Apache Maven 3.3.9 or above
- H2 database last stable version

### Quick start for overview
 mvn -f front-end/pom.xml jetty:run
 
### Quick start for developing
 - Build the root project: mvn clean install
 - Install Intellij Idea Jetty runner plugin
 - Edit configurations > Add new Configuration > Jetty runner
 - Specify absolute path to front-end/src/main/webapp folder
 - Specify absolute path to front-end/target/classes folder
 - Ok > Press Play, enjoy
 
### Build and deploy for production

- open H2 database and run script:

  CREATE USER IF NOT EXISTS user PASSWORD 12345 ADMIN;
  CREATE SCHEMA IF NOT EXISTS rd_ms_db AUTHORIZATION user;

- than you should achieve .war file to deploy it to container
  (run 'mvn clean install' in directory **\rd-management-system)
- .war will be in directory **\rd-management-system\front-end\target\
- to deploy move .war file to directory webapps of Tomcat
-------------------------------------
- use next properties to enter H2 database via H2 console:
  url=jdbc:h2:tcp://localhost/~/rd_ms_db;
  username=user
  password=12345
