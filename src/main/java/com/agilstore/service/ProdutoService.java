package com.agilstore.service;

import com.agilstore.dto.ProdutoDTO;
import com.agilstore.entity.Produto;
import com.agilstore.repository.ProdutoRepository;
import com.agilstore.entity.MovimentacaoEstoque;
import com.agilstore.repository.MovimentacaoEstoqueRepository;
import com.agilstore.event.EstoqueBaixoEvent;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;


@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProdutoService(ProdutoRepository produtoRepository, MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, ApplicationEventPublisher eventPublisher) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.eventPublisher = eventPublisher;
    }


    
    private void registrarMovimentacao(Produto produto, int quantidade, String tipo) {
        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setProduto(produto);
        mov.setQuantidade(quantidade);
        mov.setTipoMovimentacao(tipo);
        movimentacaoEstoqueRepository.save(mov);
    }

    @CacheEvict(value = "produtos", allEntries = true)
    public Produto adicionarProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setPreco(produtoDTO.getPreco());
        Produto salvo = produtoRepository.save(produto);
        registrarMovimentacao(salvo, salvo.getQuantidade(), "ENTRADA");
        return salvo;
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

    @CacheEvict(value = "produtos", allEntries = true)
    public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = buscarProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        int qtdeAntiga = produto.getQuantidade();
        if (produtoDTO.getNome() != null) produto.setNome(produtoDTO.getNome());
        if (produtoDTO.getCategoria() != null) produto.setCategoria(produtoDTO.getCategoria());
        if (produtoDTO.getQuantidade() != 0) produto.setQuantidade(produtoDTO.getQuantidade());
        if (produtoDTO.getPreco() != 0) produto.setPreco(produtoDTO.getPreco());
        Produto salvo = produtoRepository.save(produto);
        registrarMovimentacao(salvo, salvo.getQuantidade(), "ENTRADA");
        return salvo;
    }

    @CacheEvict(value = "produtos", allEntries = true)
    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    
    @Cacheable(value = "produtos", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Produto> listarProdutosComFiltro(Specification<Produto> spec, Pageable pageable) {
        return produtoRepository.findAll(spec, pageable);
    }

    public Page<Produto> listarProdutosPaginados(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }
}
