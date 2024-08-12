package br.com.financias.trabalho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteListaDTO {

    private String nome;

    private double receita;

    private double gastosTotais;

    private double meta;

    private int id;
}
