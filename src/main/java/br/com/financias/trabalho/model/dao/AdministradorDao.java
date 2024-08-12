package br.com.financias.trabalho.model.dao;

import br.com.financias.trabalho.model.usuarios.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorDao extends JpaRepository<Administrador,  Integer> {

    public Administrador findByEmail(String email);

    public Administrador findByLogin(String login);

}
