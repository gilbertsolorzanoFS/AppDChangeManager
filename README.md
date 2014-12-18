AppDChangeManager
===========

The AppDChangeManager solution allows users to select auto-discovery rules, custom-match
rules and health-rule so that they can be transfered from on AppDynamics Application to
another AppDynamics application. This will help AppDynamics Administrators by reducing
the work associated with updating configurations. The administrator just need to give
users accesses to an AppDynamics application sandbox or any non-production AppDynamics
application so that they can create health-rule, update auto-discovery rule and/or
health-rules. The user then uses the Web UI, to create a change and add specific items
to be transfered.

Requirements:
------------
Requires ant to build the package
Requires Apache Derby (Version 10.11.1.1 needed libraries provided)
Requires Application server, tested with Tomcat server
Requires Google's Gson utility, 

Building:
--------
1. Fork the Repo to local machine
2. Run ant -f AppD_build.xml

Configuration:
-------------
--Tomcat
    Port Changes: Update tomcat's server.xml change the port from 8080 to 9580 to insure
	there isn't a port collision
    JAVA_OPTS Changes: Derby db requires that the JVM know about its installation directory
    HEAP Changes: Update Java HEAP to be max 256MB, add more if needed
    ROOT Index.jsp Change: Update Tomcat's root index.jsp to send the user to the application 

Usage:
-----



For support please email: gilbert.solorzano@appdynamics.com
