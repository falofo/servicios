mvn clean package
docker build -t dispatcher .
docker run -d -p 9090:8080 --name dispatcher dispatcher
