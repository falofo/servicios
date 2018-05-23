mvn clean package -U
docker build -t pagos:latest .
docker run -d -v /tmp:/tmp -p 8080:8080  --name pagos pagos
