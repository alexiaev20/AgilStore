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
import com.agilstore.service.report.RelatorioPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final RelatorioPdfService relatorioPdfService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
        this.relatorioPdfService = relatorioPdfService;
    }

    private ProdutoDTO converteParaDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setCategoria(produto.getCategoria());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());
        dto.setImagemUrl(produto.getImagemUrl());
        
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
    
    @GetMapping(value = "/relatorio", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> baixarRelatorio() {
        try {
            byte[] pdf = relatorioPdfService.gerarRelatorioInventario();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "inventario.pdf");
            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<ProdutoDTO> uploadImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String dir = "uploads/";
            Files.createDirectories(Paths.get(dir));
            Path path = Paths.get(dir + id + "_" + file.getOriginalFilename());
            Files.write(path, file.getBytes());
            
            ProdutoDTO update = new ProdutoDTO();
            update.setImagemUrl(path.toString());
            return ResponseEntity.ok(atualizarProduto(id, update));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
    }
}
