# Composición de Servicios con Apache Camel – Spring Boot – Docker

### Introducción
Este es un ejemplo que muestra cómo realizar una composición de servicios con Apache Camel. Para el ejemplo se simula una integración de cuatro servicios con el propósito de realizar pagos de facturas, capaz de realizar integración de nuevos agentes de pago a través de un contrato.

El ejemplo se compone de cuatro servicios:
1. PagosService: recibe una petición para pagar una factura, se encarga de orquestar los demás servicios.
2. RoutingService: consulta el contrato de servicio (destino) de un archivo properties, además de guardar nuevos contratos.
3. TransformService: transforma los datos de entrada a la mensajería esperada por los agentes de pago.
4. DispatcherService: realiza la el consumo de los servicios de pago externos.

### Prerrequisitos
* [Java 1.8](https://www.java.com)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)

### Build - Run
Para iniciar y compilar el servicio ejecute el archivo

	start.sh
  
Para detener el servicio ejecute el archivo

	stop.sn
