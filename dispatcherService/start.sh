mvn clean package -U
docker build -t dispatcher:latest .
docker run -d -v /tmp:/tmp -p 7777:7777  --name dispatcher dispatcher