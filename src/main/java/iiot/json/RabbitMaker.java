package iiot.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iiot.pojos.Data;
import iiot.pojos.Maths;
import iiot.service.DataService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


// Al final he metido el producer y el consumer en la misma clase porque he visto que no habia incompatibilidades.
@Component
public class RabbitMaker implements CommandLineRunner {

    public static final String EXCHANGE_NAME = "messaging.exchange";
    public static final String ROUTING_KEY = "routing_key";
    @Autowired
    private DataService dataService;
    @Autowired
    private RabbitTemplate template;

    public RabbitMaker() {
    }

    @RabbitListener(queues = "iiot.messaging.queue")
    @SendTo("")
    public String receiveGeneric(String message) throws UnsupportedEncodingException, JsonProcessingException {
        // Se coge el mensaje qye llega y se va transformando hasta la clase que queremos, en este caso Data.
        byte[] bytes = message.getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = new String(bytes, "UTF-8");
        Data data = objectMapper.readValue(json, Data.class);
        data.setId(dataService.list().size()+1);
        System.out.println(data.toString());
        // Se inserta el dato en la base de datos.
        dataService.insert(data);
        // Hago que espere para que no se sature el sistema.
        makeSlow();
        // Creo la clase de operaciones y la transformo en JSON para guardarla en la base de datos.
        Maths math = dataService.createMaths();
        String mathsJson = objectMapper.writeValueAsString(math);
        Maths jsonMaths = objectMapper.readValue(mathsJson, Maths.class);
        System.out.println(jsonMaths.toString());
        // Se guarda en la base de datos.
        dataService.insertMaths(jsonMaths);
        return data.toString();
    }
    // Hacer 3 segundos mas lento.
    private void makeSlow() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // Hacer 6 segundos mas lento.
    private void makeSlowSender() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Metodo que implementa la clase CommandLineRunner, uso el while para que esté activado continuamente.
    @Override
    public void run(String... args) throws Exception {
        boolean send = true;
        while (send == true) {
            ObjectMapper objectMapper = new ObjectMapper();
            Data data = dataService.createData();
            // Creamos el json de los datos dados.
            String json = objectMapper.writeValueAsString(data);
            // Enviamos el mensaje al TopicExhange con la routingKey.
            // Los exchanges interactuan con las queues.
            template.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, json);
            makeSlowSender();
        }
    }

}
