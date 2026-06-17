package com.devsu.project.msvc_clientes.messaging;

import com.devsu.project.msvc_clientes.domain.ClientEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishClientCreated(ClientEntity client) {
        log.info("Publishing client created event for clientId: {}", client.getClientId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                client
        );
    }

    public void publishClientUpdated(ClientEntity client) {
        log.info("Publishing client updated event for clientId: {}", client.getClientId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                client
        );
    }

    public void publishClientDeleted(Long clientId) {
        log.info("Publishing client deleted event for id: {}", clientId);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                clientId
        );
    }
}
