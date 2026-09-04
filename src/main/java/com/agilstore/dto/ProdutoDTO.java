package com.agilstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> {
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa")
    private int quantidade;

    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private double preco;
    private String imagemUrl;
}
