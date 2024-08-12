package br.com.financias.trabalho.service.dto;

import br.com.financias.trabalho.model.Gastos;
import br.com.financias.trabalho.model.dto.GastosDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastosResponseDTO {

    private GastosDTO gastos;
    private Gastos gastos2;
    private String mensagem;
    private String pagina;


}
