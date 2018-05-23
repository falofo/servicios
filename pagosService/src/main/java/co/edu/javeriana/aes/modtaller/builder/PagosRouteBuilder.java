package co.edu.javeriana.aes.modtaller.builder;

import co.edu.javeriana.aes.modtaller.model.Contract;
import co.edu.javeriana.aes.modtaller.model.FacturaMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.camel.model.rest.RestParamType.path;
import static org.apache.camel.model.rest.RestParamType.body;


@Component
public class PagosRouteBuilder extends RouteBuilder {

    public void configure() throws Exception {

        JacksonDataFormat formatContract = new JacksonDataFormat(Contract.class);
        JacksonDataFormat formatFactura = new JacksonDataFormat(FacturaMsg.class);

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                // turn on swagger api-doc
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "User API")
                .apiProperty("api.version", "1.0.0");

        rest("/users").description("User REST service")
                .consumes("application/json")
                .produces("application/json")

                .post("/{id}").description("Consultar contrato").type(String.class)
                .param().name("id").type(path).description("ID del contrato").dataType("string").endParam()
                .param().name("body").type(body).description("The user to update").endParam()
                .to("direct:searchContract");

        from("direct:searchContract").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().getHeaders().remove("CamelHttpPath");
                System.out.println(exchange.getIn().getHeader("id"));
            }
        }).recipientList(simple("http://172.16.124.1:9090/RoutingService/getContractInfo/${header.id}?bridgeEndpoint=true"))
                .unmarshal(formatContract)
        .to("direct:transform");

        from("direct:transform")
                .process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().getHeaders().remove("CamelHttpPath");
                FacturaMsg factura = new FacturaMsg();
                Contract contract = (Contract) exchange.getIn().getBody();
                exchange.getOut().setHeader("contratoDis", contract);
                factura.setMsg(StringEscapeUtils.escapeHtml4(contract.getEsquema()));
                factura.setIdFactura(exchange.getIn().getHeader("id").toString());
                factura.setTotalFactura("");
                exchange.getOut().setBody(factura);
            }
        }).marshal().json(JsonLibrary.Jackson).log("${body}")
                .recipientList(simple("http://172.16.124.1:8888/TransformService/transformarMsg?bridgeEndpoint=true"))
                .unmarshal().json(JsonLibrary.Jackson)
        .to("direct:dispatcher");

        from("direct:dispatcher").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().getHeaders().remove("CamelHttpPath");
                Contract contract = (Contract) exchange.getIn().getHeader("contratoDis");
                Map<String,String> newSqm = (Map<String, String>) exchange.getIn().getBody();
                contract.setEsquema(newSqm.get("mensaje"));
                exchange.getOut().setBody(contract);
                System.out.println(exchange.getIn().getHeader("id"));
            }
        }).marshal().json(JsonLibrary.Jackson).log("${body}")
                .recipientList(simple("http://172.16.124.1:7777/DispatcherService/pagarFactura?bridgeEndpoint=true"))
                .convertBodyTo(String.class);

    }
}
