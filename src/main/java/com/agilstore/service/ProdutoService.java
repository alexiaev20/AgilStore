package com.agilstore.service;

import com.agilstore.dto.ProdutoDTO;
import com.agilstore.entity.Produto;
import com.agilstore.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto adicionarProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setPreco(produtoDTO.getPreco());
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> buscarProdutoPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Produto> buscarProdutoPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = buscarProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        if (produtoDTO.getNome() != null) produto.setNome(produtoDTO.getNome());
        if (produtoDTO.getCategoria() != null) produto.setCategoria(produtoDTO.getCategoria());
        if (produtoDTO.getQuantidade() != 0) produto.setQuantidade(produtoDTO.getQuantidade());
        if (produtoDTO.getPreco() != 0) produto.setPreco(produtoDTO.getPreco());
        return produtoRepository.save(produto);
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public Page<Produto> listarProdutosPaginados(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }
}
