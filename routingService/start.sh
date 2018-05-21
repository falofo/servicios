mvn clean package
docker build -t routing .
docker run -d -p 9090:8080 --name routing routing
