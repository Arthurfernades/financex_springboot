package br.com.financias.trabalho.model.dao;

import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.model.usuarios.Moderador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeradorDao extends JpaRepository<Moderador,  Integer> {

    public Moderador findByEmail(String email);

    public Moderador findByLogin(String login);

//    @Query("SELECT c FROM Cliente c WHERE c.moderador.id = :id")
//    List<Cliente> findClientesById(@Param("id") int id);
}
