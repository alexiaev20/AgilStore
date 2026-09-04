package com.agilstore.specification;

import com.agilstore.entity.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification {
    public static Specification<Produto> nomeContem(String nome) {
        return (root, query, criteriaBuilder) ->
                nome == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Produto> categoriaContem(String categoria) {
        return (root, query, criteriaBuilder) ->
                categoria == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("categoria")), "%" + categoria.toLowerCase() + "%");
    }
    
    public static Specification<Produto> precoMaiorQue(Double precoMin) {
        return (root, query, criteriaBuilder) ->
                precoMin == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), precoMin);
    }
}
