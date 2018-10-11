FROM       tomcat:8-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
ARG WAR_FILE
ADD target/${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war
ADD docker/*.jar /usr/local/tomcat/lib/

EXPOSE 8080
