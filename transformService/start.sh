mvn clean package -U
docker build -t transform:latest .
docker run -d -v /tmp:/tmp -p 8888:8888  --name transform transform
