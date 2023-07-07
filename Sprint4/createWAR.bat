cd "./Test-Framework"
jar cvf temp.war *
copy "temp.war" "C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/temp.war"
del temp.war