package co.edu.javeriana.aes.modtaller.controller;


import co.edu.javeriana.aes.modtaller.model.Contract;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/DispatcherService")
public class DispatcherController {

    @PostMapping("/pagarFactura")
    ResponseEntity<?> pagar(@RequestBody Contract contrato) {
        String content;
        contrato.setEsquema(StringEscapeUtils.unescapeHtml4(contrato.getEsquema()));
        try{
            if (contrato.getTipoServicio().equals("1")) {
                HttpClient httpclient = HttpClients.createDefault();

                StringEntity strEntity = new StringEntity(contrato.getEsquema(), "text/xml", "UTF-8");
                HttpPost post = new HttpPost(contrato.getEndPoint());
                post.setHeader("SOAPAction", contrato.getIdServicio());
                post.setEntity(strEntity);

                // Execute request
                HttpResponse response = httpclient.execute(post);
                HttpEntity respEntity = response.getEntity();
                content = EntityUtils.toString(respEntity);
            }else{
                URL url = new URL(contrato.getEndPoint());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod(contrato.getIdServicio());
                conn.setRequestProperty("Content-Type", "application/json");

                String input = contrato.getEsquema();

                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                content = br.toString();

                conn.disconnect();
            }

            if (content.contains(contrato.getRespExt())) {
                return new ResponseEntity<>(contrato.getRespExt(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error transformando mensaje", HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>("Error transformando mensaje", HttpStatus.BAD_REQUEST);
        }

    }

    /*public static  void main(String[] args){
        try {
            // Get target URL
            HttpClient httpclient = HttpClients.createDefault();

            StringEntity strEntity = new StringEntity("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sch=\"http://www.servicios.co/pagos/schemas\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <sch:Pago>\n" +
                    "         <sch:referenciaFactura>\n" +
                    "            <sch:referenciaFactura>111</sch:referenciaFactura>\n" +
                    "         </sch:referenciaFactura>\n" +
                    "         <sch:totalPagar>1111</sch:totalPagar>\n" +
                    "      </sch:Pago>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>", "text/xml", "UTF-8");
            HttpPost post = new HttpPost("http://localhost:7070/w1-soap-svr/PagosServiceService");
            post.setHeader("SOAPAction","pagar");
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();
            String content = EntityUtils.toString(respEntity);
            System.out.println(content);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
