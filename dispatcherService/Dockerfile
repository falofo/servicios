FROM java:8
VOLUME /tmp
ADD target/dispatcher-REST-service-1.0-SNAPSHOT.jar dispatcher.jar
RUN bash -c 'touch /dispatcher.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /dispatcher.jar"]
