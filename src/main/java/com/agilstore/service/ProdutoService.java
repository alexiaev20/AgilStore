package com.agilstore.service;

import com.agilstore.dto.ProdutoDTO;
import com.agilstore.entity.Produto;
import com.agilstore.repository.ProdutoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private static final String DATA_FILE = "produtos.json";

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Adiciona um novo produto
    public Produto adicionarProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setPreco(produtoDTO.getPreco());
        Produto salvo = produtoRepository.save(produto);
        salvarDadosEmArquivo();
        return salvo;
    }

    // Lista todos os produtos
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // Busca produto por ID
    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // Busca produtos pelo nome
    public List<Produto> buscarProdutoPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Busca produtos por categoria
    public List<Produto> buscarProdutoPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }


    // Atualiza um produto
    public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = buscarProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        if (produtoDTO.getNome() != null) produto.setNome(produtoDTO.getNome());
        if (produtoDTO.getCategoria() != null) produto.setCategoria(produtoDTO.getCategoria());
        if (produtoDTO.getQuantidade() != 0) produto.setQuantidade(produtoDTO.getQuantidade());
        if (produtoDTO.getPreco() != 0) produto.setPreco(produtoDTO.getPreco());
        Produto atualizado = produtoRepository.save(produto);
        salvarDadosEmArquivo();
        return atualizado;
    }

    // Exclui um produto
    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
        salvarDadosEmArquivo();
    }

    // Salva os dados em um arquivo JSON
    private void salvarDadosEmArquivo() {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            List<Produto> produtos = produtoRepository.findAll();
            writer.write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(produtos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ordena produtos por nome
    public List<Produto> listarProdutosOrdenadosPorNome() {
        return produtoRepository.findAllByOrderByNome();
    }

    // Ordena produtos por quantidade
    public List<Produto> listarProdutosOrdenadosPorQuantidade() {
        return produtoRepository.findAllByOrderByQuantidadeDesc();
    }

    // Ordena produtos por preço
    public List<Produto> listarProdutosOrdenadosPorPreco() {
        return produtoRepository.findAllByOrderByPrecoAsc();
    }
    
    private void verificarEstoqueBaixo(Produto produto) {
        if (produto.getQuantidade() < 5) {
            System.out.println("Alerta: Produto com estoque baixo: " + produto.getNome());
        }
    }

	public Page<Produto> listarProdutosPaginados(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}