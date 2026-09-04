package com.agilstore.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EstoqueEventListener {
    private static final Logger logger = LoggerFactory.getLogger(EstoqueEventListener.class);

    @Async
    @EventListener
    public void handleEstoqueBaixoEvent(EstoqueBaixoEvent event) {
        // Simulando envio de mensagem para um Broker (Kafka/RabbitMQ) ou E-mail
        logger.warn("ALERTA ASSÍNCRONO: O produto '{}' (ID: {}) atingiu o nível crítico de estoque! Quantidade atual: {}",
                event.getProduto().getNome(),
                event.getProduto().getId(),
                event.getProduto().getQuantidade());
    }
}
