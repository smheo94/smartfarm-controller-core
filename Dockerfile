FROM       tomcat:8-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
RUN apk add tzdata
RUN ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" >  /etc/timezone
RUN export TZ=Asia/Seoul
#RUN apk add dnsmasq
#RUN echo "address=/iptime.org/127.0.0.1" >  /etc/dnsmasq.conf
ARG WAR_FILE
ADD target/${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war
ADD docker/*.jar /usr/local/tomcat/lib/
#RUN dnsmasq -k &
EXPOSE 8080
