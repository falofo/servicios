FROM java:8
VOLUME /tmp
ADD target/pagos-service-1.0-SNAPSHOT.jar pagos.jar
RUN bash -c 'touch /pagos.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /pagos.jar"]
