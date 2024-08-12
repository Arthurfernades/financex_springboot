package br.com.financias.trabalho.service.dto;

import br.com.financias.trabalho.model.dto.ModeradorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeradorResponseDTO {

    private ModeradorDTO moderadorDTO;

    private String mensagem;

    private String pagina;
}
