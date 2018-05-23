mvn clean package -U
docker build -t routing:latest .
docker run -d -v /tmp:/tmp -p 9090:9090  --name routing routing