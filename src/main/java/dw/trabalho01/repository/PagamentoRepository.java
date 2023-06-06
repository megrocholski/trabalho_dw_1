package dw.trabalho01.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dw.trabalho01.model.Jogador;
import dw.trabalho01.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
	@Query(value = "SELECT * FROM pagamento WHERE cod_jogador =?1", nativeQuery = true)
	List<Pagamento> findPagamentosByJogador(long jogador_cod_jogador);
}
