package co.edu.javeriana.aes.modtaller.controller;

import co.edu.javeriana.aes.modtaller.model.FacturaMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/TransformService")
public class TransformController {

    @PostMapping("/transformarMsg")
    ResponseEntity<?> transformarMsg(@RequestBody FacturaMsg factura) {

        String newMsg = factura.getMsg();
        try {
            newMsg = newMsg.replace("idFactura", factura.getIdFactura());
            newMsg = newMsg.replace("totalFactura", factura.getTotalFactura());
            Map<String,String> mapRep = new HashMap<>();
            mapRep.put("mensaje",newMsg);
            return new ResponseEntity<>(mapRep, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error transformando mensaje", HttpStatus.BAD_REQUEST);
        }

    }
}
