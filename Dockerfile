FROM tomcat:9
ADD ./target/library-control-system.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]