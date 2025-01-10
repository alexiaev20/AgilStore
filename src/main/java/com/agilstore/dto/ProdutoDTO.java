package com.agilstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa um produto para criação ou atualização")
public class ProdutoDTO {

    @Schema(description = "Nome do produto", example = "notebook")
    private String nome;

    @Schema(description = "Categoria do produto", example = "eletronico")
    private String categoria;

    @Schema(description = "Quantidade do produto no estoque", example = "1")
    private int quantidade;

    @Schema(description = "Preço do produto", example = "800.00")
    private double preco;

    // Construtor
    public ProdutoDTO(String nome, String categoria, int quantidade, double preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.preco = preco;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

    // Getters e setters gerados automaticamente por @Data (Lombok)
}
