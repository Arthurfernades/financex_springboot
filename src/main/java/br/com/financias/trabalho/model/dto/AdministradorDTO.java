package br.com.financias.trabalho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorDTO {

    private String nome;

    private String login;

    private String email;

    private String senha;

    private String tipo = "Administrador";

}
