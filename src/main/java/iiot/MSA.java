package iiot;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

// Establecemos que es una app spring y que usa mongo y rabbit.
@SpringBootApplication
@EnableRabbit
@EnableMongoRepositories
public class MSA {

    public static void main(String[] args) {
        SpringApplication.run(MSA.class, args);
    }
}