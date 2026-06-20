package com.devsu.project.msvc_cuentas.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@Profile("!test")
public class ClientEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeClientEvent(Object message) {
        try {
            if (message instanceof Map) {
                Map<?, ?> clientData = (Map<?, ?>) message;
                log.info("Client event received - clientId: {}, name: {}",
                        clientData.get("clientId"),
                        clientData.get("name"));
            } else if (message instanceof Long) {
                log.info("Client deleted event received - id: {}", message);
            } else {
                log.info("Client event received: {}", message);
            }
        } catch (Exception e) {
            log.error("Error processing client event: {}", e.getMessage());
        }
    }
}
