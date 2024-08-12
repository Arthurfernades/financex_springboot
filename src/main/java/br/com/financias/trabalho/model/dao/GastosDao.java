package br.com.financias.trabalho.model.dao;

import br.com.financias.trabalho.model.Gastos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastosDao extends JpaRepository<Gastos, Integer> {
}
