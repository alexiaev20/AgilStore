package com.agilstore.scheduler;

import com.agilstore.entity.Produto;
import com.agilstore.event.EstoqueBaixoEvent;
import com.agilstore.repository.ProdutoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstoqueScheduler {
    private final ProdutoRepository produtoRepository;
    private final ApplicationEventPublisher eventPublisher;

    public EstoqueScheduler(ProdutoRepository produtoRepository, ApplicationEventPublisher eventPublisher) {
        this.produtoRepository = produtoRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(cron = "0 0 * * * *") // Executa de hora em hora
    public void verificarEstoqueBaixo() {
        List<Produto> todos = produtoRepository.findAll();
        for (Produto p : todos) {
            if (p.getQuantidade() < 5) {
                eventPublisher.publishEvent(new EstoqueBaixoEvent(this, p));
            }
        }
    }
}
