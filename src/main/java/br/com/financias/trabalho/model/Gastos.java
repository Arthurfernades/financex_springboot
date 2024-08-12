package br.com.financias.trabalho.model;

import br.com.financias.trabalho.model.enums.ECategoriaGastos;
import br.com.financias.trabalho.model.usuarios.Cliente;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gastos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double valor;

    @Enumerated(EnumType.STRING)
    private ECategoriaGastos eCategoriaGastos;

    @Nullable
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
