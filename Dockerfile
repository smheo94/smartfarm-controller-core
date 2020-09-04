FROM       tomcat:8-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
RUN apk add tzdata
RUN ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" >  /etc/timezone
RUN export TZ=Asia/Seoul
ADD docker/.sf.k.d /etc/.sf.k
ADD docker/.sf.k.z /home/gsm/.sf.k
ADD docker/.sf.k.a /usr/local/etc/.sf.k
ADD DB/update.20200715.md /var/file/
ADD DB/update.20200806.md /var/file/
ADD DB/update.20200824.md /var/file/

RUN sed -i "s,</Host>,<Valve className=\"org.apache.catalina.valves.ErrorReportValve\" showReport=\"false\" showServerInfo=\"false\" errorCode.400=\"/common/error.jsp\" /></Host>,g" /usr/local/tomcat/conf/server.xml
#redirectPort=\"8443\" />.redirectPort=\"8443\"  URIEncoding=\"UTF-8\" relaxedQueryChars='[]\|{}^&#x5c;&#x60;&quot;&lt;&gt;' />.g" /usr/local/tomcat/conf/server.xml
RUN echo "tomcat.util.http.parser.HttpParser.requestTargetAllow=|{}"  >> /usr/local/tomcat/conf/catalina.properties
ARG WAR_FILE
ADD target/${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war
ADD docker/*.jar /usr/local/tomcat/lib/
EXPOSE 8080
