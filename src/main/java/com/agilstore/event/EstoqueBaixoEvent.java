package com.agilstore.event;

import com.agilstore.entity.Produto;
import org.springframework.context.ApplicationEvent;

public class EstoqueBaixoEvent extends ApplicationEvent {
    private final Produto produto;

    public EstoqueBaixoEvent(Object source, Produto produto) {
        super(source);
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }
}
