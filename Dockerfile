FROM       tomcat:8-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
RUN apk add tzdata
RUN ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" >  /etc/timezone
ARG WAR_FILE
ADD target/${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war
ADD docker/*.jar /usr/local/tomcat/lib/

EXPOSE 8080
