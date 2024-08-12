package br.com.financias.trabalho.model.dao;

import br.com.financias.trabalho.model.usuarios.UsuarioBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioBaseDao extends JpaRepository<UsuarioBase, Integer> {
}
