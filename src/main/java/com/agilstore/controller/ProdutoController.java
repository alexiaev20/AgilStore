package com.agilstore.controller;

import com.agilstore.dto.ProdutoDTO;
import com.agilstore.entity.Produto;
import com.agilstore.service.ProdutoService;
import com.agilstore.specification.ProdutoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    private ProdutoDTO converteParaDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setCategoria(produto.getCategoria());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());
        
        dto.add(linkTo(methodOn(ProdutoController.class).buscarProdutoPorId(produto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(ProdutoController.class).listarProdutosPaginados(null, null, null, 0, 10, "nome", "asc")).withRel("todos-produtos"));
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionarProduto(@jakarta.validation.Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto salvo = produtoService.adicionarProduto(produtoDTO);
        return converteParaDTO(salvo);
    }
    
    @GetMapping("/paginado")
    public Page<ProdutoDTO> listarProdutosPaginados(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precoMin,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(defaultValue = "nome") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direcao) {
            
        Sort sort = "desc".equalsIgnoreCase(direcao) ? Sort.by(ordenarPor).descending() : Sort.by(ordenarPor).ascending();
        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        
        Specification<Produto> spec = Specification.where(ProdutoSpecification.nomeContem(nome))
                .and(ProdutoSpecification.categoriaContem(categoria))
                .and(ProdutoSpecification.precoMaiorQue(precoMin));
                
        Page<Produto> produtos = produtoService.listarProdutosComFiltro(spec, pageable);
        return produtos.map(this::converteParaDTO);
    }

    @GetMapping("/{id}")
    public ProdutoDTO buscarProdutoPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        return converteParaDTO(produto);
    }

    @PutMapping("/{id}")
    public ProdutoDTO atualizarProduto(@PathVariable Long id, @jakarta.validation.Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = produtoService.atualizarProduto(id, produtoDTO);
        return converteParaDTO(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
    }
}
