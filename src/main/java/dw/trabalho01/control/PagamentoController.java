package dw.trabalho01.control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dw.trabalho01.model.Jogador;
import dw.trabalho01.model.Pagamento;
import dw.trabalho01.repository.JogadorRepository;
import dw.trabalho01.repository.PagamentoRepository;

@RestController
@RequestMapping("/api")
public class PagamentoController {
	@Autowired
	PagamentoRepository rep;
	@Autowired
	JogadorRepository jRepository;

	// rota para buscar todos os pagamentos
	@GetMapping("/pagamento")
	public ResponseEntity<List<Pagamento>> getAllPagamentos() {
		try {
			List<Pagamento> lj = new ArrayList<Pagamento>();

			// if (jogador == null) {
			rep.findAll().forEach(lj::add);
			// } else {
			// Optional<Jogador> jg = jRepository.findById(jogador);
			// if (jg.isPresent()) {
			// Jogador jog = new Jogador(jg.get().getNome(), jg.get().getEmail(),
			// jg.get().getDataNasc());
			// rep.findPagamentosByJogador(jog).forEach(lj::add);
			// }
			// }

			if (lj.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(lj, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// rota para buscar pagamentos de um determinado jogador
	@GetMapping("/pagamento/jogador/{id}")
	public ResponseEntity<List<Pagamento>> getPagamentosByJogador(@PathVariable("id") long id) {
		try {
			List<Pagamento> lj = new ArrayList<Pagamento>();

			Optional<Jogador> jg = jRepository.findById(id);
			if (jg.isPresent()) {
				Jogador jog = new Jogador(jg.get().getNome(), jg.get().getEmail(), jg.get().getDataNasc());
				rep.findPagamentosByJogador(id).forEach(lj::add);
			}

			if (lj.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(lj, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// rota para criar um novo pagamento
	@PostMapping("/pagamento/{id}")
	public ResponseEntity<Pagamento> createPagamento(@PathVariable("id") long id, @RequestBody Pagamento pg) {
		try {
			// JogadorController jogController = new JogadorController();
			// ResponseEntity<Jogador> j = jogController.getJogadorById(id);

			Optional<Jogador> data = jRepository.findById(id);
			if (data.isPresent()) {
				Jogador jg = data.get();
				if (pg.getAno() > 0 && pg.getMes() > 0 && pg.getMes() <= 12 && pg.getValor() > 0) {
					Pagamento p = rep.save(new Pagamento(pg.getAno(), pg.getMes(), pg.getValor(), jg));

					return new ResponseEntity<>(p, HttpStatus.CREATED);
				}
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// rota para buscar pagamento de acordo com o id
	@GetMapping("/pagamento/{id}")
	public ResponseEntity<Pagamento> getPagamentoById(@PathVariable("id") long id) {
		Optional<Pagamento> data = rep.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// rota para atualizar dados de um pagamento
	@PutMapping("/pagamento/{id}/{id_jogador}")
	public ResponseEntity<Pagamento> updatePagamento(@PathVariable("id") long id, @RequestBody Pagamento pg,
			@PathVariable("id_jogador") long id_jogador) {

		Optional<Pagamento> data = rep.findById(id);

		if (data.isPresent()) {
			Optional<Jogador> data2 = jRepository.findById(id_jogador);

			if (data2.isPresent()) {
				Pagamento p = data.get();
				if (pg.getAno() > 0) {
					p.setAno(pg.getAno());
				}
				if (pg.getMes() > 0 && pg.getMes() <= 12) {
					p.setMes(pg.getMes());
				}
				if (pg.getValor() > 0) {
					p.setValor(pg.getValor());
				}
				p.setJogador(data2.get());

				return new ResponseEntity<>(rep.save(p), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// rota para deletar um pagamento de acordo com o id
	@DeleteMapping("pagamento/{id}")
	public ResponseEntity<HttpStatus> deletePagamento(@PathVariable("id") long id) {
		try {
			rep.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// rota para deletar todos os pagamentos
	@DeleteMapping("/pagamento")
	public ResponseEntity<HttpStatus> deleteAllPagamento() {
		try {
			rep.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
