package com.agilstore.repository;

import com.agilstore.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaContainingIgnoreCase(String categoria);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findAllByOrderByNome();
    List<Produto> findAllByOrderByPrecoAsc();
    List<Produto> findAllByOrderByQuantidadeDesc();
}
