This is a WIP JavaEE site using JPA for persistence. Subject to history rewrite. You've been warned

###Install instructions

1. Download Java 7 sdk
1. [Download Eclipse JEE kepler](https://glassfish.java.net/download.html) - I downloaded eclipse-jee-kepler-SR1-win32.zip. No install needed.
2. [Download glassfish 4](https://glassfish.java.net/download.html) - I downloaded glassfish-4.0.zip. No install needed.
3. [Download MySql](http://dev.mysql.com/downloads/windows/installer/) - I downloaded mysql-installer-community-5.6.16.0.msi - contains the server and the workbench. Install it.
5. Unzip eclipse and run it. Create a Java-7 execution environment and assign it the JDK7 you downloaded.
6. In eclipse download the adaptor for glassfish (needs restart) and create a glassfish runtime
6. Drop `mysql-connector-java-5.1.28-bin.jar` (from C:\Program files\MySQL\Connector J 5.1.xx)  into GLASSFISH_INSTALL/glassfish/lib
4. Clone this project and build the db using the hw1_db_1.sql
5. Start glassfish, go to the glassfish admin console and add a pool and resource named jdbc/dvd_store_pool_1_resource (specified in persistence.xml) as described [here](http://utumno.github.io/blog/2014/02/02/glassfish-connection-pools/)
9. Import the project into the workspace, go to its properties > Dynamic Web module and change the runtime to point to your glassfish runtime
