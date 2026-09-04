package com.agilstore.repository;

import com.agilstore.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByCategoriaContainingIgnoreCase(String categoria);
    List<Produto> findAllByOrderByNome();
    List<Produto> findAllByOrderByQuantidadeDesc();
    List<Produto> findAllByOrderByPrecoAsc();
}
