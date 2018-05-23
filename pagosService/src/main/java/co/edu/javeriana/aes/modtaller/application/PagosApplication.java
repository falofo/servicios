package co.edu.javeriana.aes.modtaller.application;

import co.edu.javeriana.aes.modtaller.builder.PagosRouteBuilder;
import org.apache.camel.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"co.edu.javeriana.aes.modtaller.builder","co.edu.javeriana.aes.modtaller.model"})
public class PagosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagosApplication.class, args);
    }

}
