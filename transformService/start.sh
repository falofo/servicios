mvn clean package
docker build -t transform .
docker run -d -p 9090:8080 --name transform transform
