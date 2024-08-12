package br.com.financias.trabalho.model.usuarios;

import br.com.financias.trabalho.model.Mensagem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public class Moderador extends UsuarioBase{

    @OneToMany(mappedBy = "moderador", cascade = CascadeType.ALL)
    private List<Cliente> listaClientes;

    @OneToMany(mappedBy = "moderador", cascade = CascadeType.ALL)
    private List<Mensagem> mensagens;

}
