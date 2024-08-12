package br.com.financias.trabalho.model.dao;

import br.com.financias.trabalho.model.usuarios.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDao extends JpaRepository<Cliente, Integer> {

    public Cliente findByEmail(String email);

    public Cliente findByLogin(String login);

}
