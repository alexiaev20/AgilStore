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

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionarProduto(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.adicionarProduto(produtoDTO);
    }

    @GetMapping
    public List<Produto> listarProdutos(@RequestParam(required = false) String categoria) {
        if (categoria != null) {
            return produtoService.buscarProdutoPorCategoria(categoria);
        }
        return produtoService.listarProdutos();
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

    @GetMapping("/{id}")
    public Produto buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
    }

    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        return produtoService.atualizarProduto(id, produtoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
    }
}
