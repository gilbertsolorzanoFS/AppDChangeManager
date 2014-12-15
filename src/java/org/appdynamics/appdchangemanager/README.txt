
java -cp "lib/*" -Dderby.system.home=/opt/Apache/tomcat/webapps/db -Dij.protocol=jdbc:derby: -jar lib/derbyrun.jar ij

CONNECT 'jdbc:derby://localhost:1527/appd_chdb;create=true;user=appd;password=appDPassOne';

The idea is to have a login page where the user logs in, this has to use SSL 

--Update server.xml
Port 9580 NonSSL
Port 9543 SSL


DB Name: appd_chdb
User Name: appd
Password: appD-S3cr3t1
Password: appDPassOne
v2MUKQBoeyWvsUON0fGGTdG6khNBvqOt




--Configuration page
  Configure connection info
  Configure controller info

--- add to JAVA_OPTS="${JAVA_OPTS} -Dderby.system.home=-Dderby.system.home=/opt/Apache/tomcat/dbs"

""" derby.properties
derby.stream.error.logSeverityLevel=0
derby.database.fullAccessUsers=appd
derby.database.defaultConnectionMode=fullAccess
derby.connection.requireAuthentication=false
derby.user.appd=appDPassOne
derby.user.user1=appDPassOne
derby.authentication.provider=builtin
"""

Download bootstrap
http://getbootstrap.com/getting-started/#download version 3.3.0


Download jquery
http://jquery.com/download/ 1.11.1

html5shiv.js
respond.js

Derby: web.xml
        <servlet-name> derbynet </servlet-name>
	<servlet-class> org.apache.derby.drda.NetServlet </servlet-class>
	<load-on-startup>1</load-on-startup>


Rename and add new <TOMCAT>/webapps/ROOT/index.jsp , to redirect.

<% response.sendRedirect(response.encodeRedirectURL(request.getContextPath() +
        "/AppDChangeManager")); %>



HTTP variables
srcConnId
destConnId
srcILCon (controller)
destILCon (controller)
srcILApp (application)
destILApp (application)
srcILTier (tier)
destILTier (tier)