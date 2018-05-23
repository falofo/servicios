FROM java:8
VOLUME /tmp
ADD target/routing-REST-Service-1.0-SNAPSHOT.jar routing.jar
RUN bash -c 'touch /routing.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /routing.jar"]
