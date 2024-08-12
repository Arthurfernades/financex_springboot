package br.com.financias.trabalho.model.dao;

import br.com.financias.trabalho.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemDao extends JpaRepository<Mensagem, Integer> {
}
