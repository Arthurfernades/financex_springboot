package br.com.financias.trabalho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastosDTO {

    private double valor;

    private String categoria;

    private String descricao;

    private String loginUser;

}