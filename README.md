AppDChangeManager
===========

The AppDChangeManager solution allows users to select auto-discovery rules, custom-match
rules and health-rule so that they can be transfered from one AppDynamics application to
another AppDynamics application. This will help AppDynamics Administrators by reducing
the work associated with updating configurations. The administrator just need to give
users accesses to an AppDynamics application sandbox or any non-production AppDynamics
application so that they can create their health-rules, update auto-discovery rules and/or
health-rules. The user then uses the Web UI, to create a change and add specific requests
for items to be transfered. The WebUI Administrators can then approve the change and 
execute the change. If they find a problem with the change the can also reject the change.
Once the WebUI Administrator executes a change, the appropriate REST calls are made to
export the items from the source controller. The exported XML is then written to a file
as a backup. The solution will also attempt to export the item from the destination and 
save the XML into a file. Then the XML output from the source is used to in a REST call
to post to the destination controller. Once the change operation is complete an AppDynamics'
Event is posted to the destination controller listing the changes that took place.

Requirements:
------------
Requires ant to build the package
Requires Apache Derby (Version 10.11.1.1 needed libraries provided)
Requires Application server, tested with Tomcat server
Requires Google's Gson utility (provided)

Building:
--------
1. Fork the Repo to local machine
2. Run ant -f AppD_build.xml

Configuration:
-------------
<h3>Apache Derby</h3><br>
    <p>Data Directory: Create directory called 'dbs' for database files, make sure tomcat user <br>
	has write access to the directory. For example: <CATALINE_HOME>/dbs</p>
    <p>Db Properties: Create file in the 'dbs' directory called derby.properties with the <br>
	following entries:</p>
```java
derby.stream.error.logSeverityLevel=0
derby.database.fullAccessUsers=appd
derby.database.defaultConnectionMode=fullAccess
derby.connection.requireAuthentication=false
derby.user.appd=appDPassOne
derby.user.user1=appDPassOne
derby.authentication.provider=builtin
```
<h3>Tomcat</h3><br>
<p>Port Changes: Update tomcat's server.xml, change the port from 8080 to 9580 and <br>
	8443 to 9543 to insure there isn't a port collision</p>
<p>JAVA_OPTS Changes: Derby db requires that the JVM know about its installation directory<br>
	make sure to add:<br></p>
<p>JAVA_OPTS="${JAVA_OPTS} -Dderby.system.home=-Dderby.system.home=<CATALINA_HOME>/dbs"</p>
<p>HEAP Changes: Update Java HEAP to be max 256MB, add more if needed</p>
<p>ROOT Index.jsp Change: Update Tomcat's root index.jsp to send the user to the application <br>
	application root '/AppDChangeManager'</p>


```
<% response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/AppDChangeManager")); %>
```

<p>Derby WAR: Added the 'derby.war' file into webapps directory<br></p>
<p>Derby web.xml: Update the web.xml to add 'load-on-startup' options, as shown<br></p>

```java
<servlet-name> derbynet </servlet-name>
<servlet-class> org.apache.derby.drda.NetServlet </servlet-class>
<load-on-startup>1</load-on-startup>
```

<p>AppDChangeManager WAR: Copy the AppDChangeManager WAR file into the webapps directory.<br></p>
<p>Start Tomcat: Start Tomcat to uncompress the WAR file, don't access the application<br></p>
<p>Derby Jars: Copy the derby jar files into the Tomcat's lib directory: <br>
*derbyclient.jar<br>
*derby.jar<br>
*derbynet.jar<br>
</p>
<p>Restart Tomcat: Restart tomcat to insure it has the DERBY libraries loaded<br></p>
  



Usage:
-----



For support please email: gilbert.solorzano@appdynamics.com
