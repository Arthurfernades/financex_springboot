package br.com.financias.trabalho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensagemDTO {

    private int clienteId;

    public String mensage;
}
