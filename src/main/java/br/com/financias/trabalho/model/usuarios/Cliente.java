package br.com.financias.trabalho.model.usuarios;

import br.com.financias.trabalho.model.Gastos;
import br.com.financias.trabalho.model.Mensagem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends UsuarioBase {

    private double saldo;

    private double receita;

    private double meta;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Gastos> gastos;

    @ManyToOne
    @JoinColumn(name = "moderador_id")
    private Moderador moderador;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Mensagem> mensagens;

}
