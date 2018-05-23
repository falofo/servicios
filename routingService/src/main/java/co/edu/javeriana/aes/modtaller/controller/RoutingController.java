package co.edu.javeriana.aes.modtaller.controller;

import co.edu.javeriana.aes.modtaller.model.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

@RestController
@RequestMapping("/RoutingService")
public class RoutingController {

    @PostMapping("/getContractInfo/{idFactura}")
    ResponseEntity<?> getContractInfo(@PathVariable String idFactura) {
        Properties prop = new Properties();
        InputStream input = null;

        try{
            try{
                Long numero=Long.valueOf(idFactura);
            }
            catch(Exception e){
                return new ResponseEntity<>("El numeroFactura debe ser de tipo numerico", HttpStatus.BAD_REQUEST);
            }
            if(idFactura.length()<4){
                return new ResponseEntity<>("El numeroFactura debe ser mayor a 4 digitos", HttpStatus.BAD_REQUEST);
            }
            final String idServicio=idFactura.substring(0,3);
            final ClassLoader loader=Thread.currentThread().getContextClassLoader();
            input = loader.getResourceAsStream("contract.properties");
            prop.load(input);
            final String conf=prop.getProperty(idServicio);
            final String[] parteConf=conf.split("\\|");
            Contract resp = new Contract();
            resp.setEndPoint(parteConf[0]);
            resp.setEsquema(parteConf[1]);
            resp.setTipoServicio(parteConf[2]);
            resp.setIdServicio(parteConf[3]);
            resp.setRutaContrato(parteConf[4]);
            resp.setWsdlJson(parteConf[5]);
            resp.setHeadBody(parteConf[6]);
            resp.setRespExt(parteConf[7]);

            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error transformando mensaje", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/addContract/{idFactura}")
    ResponseEntity<?> setContract(@PathVariable String idFactura,@RequestBody Contract contrato) {
        Properties prop = new Properties();

        try{
            final File propsFile = new File("contract.properties");
            StringBuffer contratoSB = new StringBuffer();
            contratoSB.append(validarDatos(contrato.getEndPoint()));
            contratoSB.append("|");
            contratoSB.append(validarDatos(contrato.getEsquema()));
            contratoSB.append("|");
            contratoSB.append(validarDatos(contrato.getTipoServicio()));
            contratoSB.append("|");
            contratoSB.append(validarDatos(contrato.getIdServicio()));
            contratoSB.append("|");
            contratoSB.append(validarDatos(contrato.getRutaContrato()));
            contratoSB.append("|");
            contratoSB.append(validarDatos(contrato.getWsdlJson()));
            contratoSB.append("|");
            contratoSB.append(validarDatos(contrato.getHeadBody()));
            contratoSB.append("|");
            contratoSB.append(validarDatos(contrato.getRespExt()));
            prop.load(new FileInputStream(propsFile));
            prop.setProperty(idFactura, contratoSB.toString());
            prop.save(new FileOutputStream(propsFile), "");

            return new ResponseEntity<>("Contrato Guardado exitosamente", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error transformando mensaje", HttpStatus.BAD_REQUEST);
        }

    }

    private String validarDatos(String dato){
        if(dato.length()>0){
            return dato;
        }else{
            return " ";
        }
    }
}
