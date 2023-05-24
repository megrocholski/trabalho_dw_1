package dw.trabalho01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dw.trabalho01.model.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {

	List<Jogador> findByNome(String nome);
	
}
