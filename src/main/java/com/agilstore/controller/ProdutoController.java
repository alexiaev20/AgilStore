package com.agilstore.controller;

import com.agilstore.dto.ProdutoDTO;
import com.agilstore.entity.Produto;
import com.agilstore.service.ProdutoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Adiciona um novo produto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionarProduto(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.adicionarProduto(produtoDTO);
    }

    // Lista todos os produtos 
    @GetMapping
    public List<Produto> listarProdutos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String ordenarPor) {
        
        // Filtra os produtos pela categoria, se o parâmetro 'categoria' for fornecido
        List<Produto> produtos = (categoria != null) ? produtoService.buscarProdutoPorCategoria(categoria) : produtoService.listarProdutos();
        
        // Ordena os produtos conforme o parâmetro 'ordenarPor'
        if ("nome".equalsIgnoreCase(ordenarPor)) {
            produtos.sort(Comparator.comparing(Produto::getNome));
        } else if ("quantidade".equalsIgnoreCase(ordenarPor)) {
            produtos.sort(Comparator.comparingInt(Produto::getQuantidade).reversed());
        } else if ("preco".equalsIgnoreCase(ordenarPor)) {
            produtos.sort(Comparator.comparingDouble(Produto::getPreco));
        }

        return produtos;
    }
    
    @GetMapping("/paginado")
    public Page<Produto> listarProdutosPaginados(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(defaultValue = "nome") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direcao) {
        Sort sort = "desc".equalsIgnoreCase(direcao) ? Sort.by(ordenarPor).descending() : Sort.by(ordenarPor).ascending();
        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        return produtoService.listarProdutosPaginados(pageable);
    }

  


    // Busca produto por ID
    @GetMapping("/{id}")
    public Produto buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
    }

    // Atualiza um produto
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        return produtoService.atualizarProduto(id, produtoDTO);
    }

    // Exclui um produto
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
    }
}
