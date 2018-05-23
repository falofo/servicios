FROM java:8
VOLUME /tmp
ADD target/transform-REST-service-1.0-SNAPSHOT.jar transform.jar
RUN bash -c 'touch /transform.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /transform.jar"]
