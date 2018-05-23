cd dispatcherService
mvn clean package -U
cd ../routingService
mvn clean package -U
cd ../transformService
mvn clean package -U
cd ../pagosService
mvn clean package -U
docker-compose up -d --build
