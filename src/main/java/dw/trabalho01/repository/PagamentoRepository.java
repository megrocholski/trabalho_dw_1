package dw.trabalho01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dw.trabalho01.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
