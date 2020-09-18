package com.app.colas.healthy.rest;

import com.app.colas.healthy.entidades.Mensaje;
import com.app.colas.healthy.jms.JmsProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class ServicioRestJMS {
    @Autowired
    private JmsProducer jmsProducer;

    @PostMapping("/healthy-enviar")
    public Mensaje enviar(@RequestBody Mensaje mensaje){
        //Convierto el Auto en formato JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try {
            json = mapper.writeValueAsString(mensaje);
            jmsProducer.send(json);
        } catch (Exception e) {
            mensaje.setRespuesta("Fallo no enviado");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Intentelo denuevo");
        }
        return mensaje;
    }

}
