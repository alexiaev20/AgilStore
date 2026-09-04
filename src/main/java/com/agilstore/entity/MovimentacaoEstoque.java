package com.agilstore.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MovimentacaoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private String tipoMovimentacao; // ENTRADA ou SAIDA

    @Column(nullable = false)
    private int quantidade;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dataMovimentacao;
}
