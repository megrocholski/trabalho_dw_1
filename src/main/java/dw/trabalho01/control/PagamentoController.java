package dw.trabalho01.control;

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

	@GetMapping("/pagamento")
	public ResponseEntity<List<Pagamento>> getAllPagamentos() {
		try {
			List<Pagamento> lj = new ArrayList<Pagamento>();

			rep.findAll().forEach(lj::add);

			if (lj.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(lj, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/pagamento/{id}")
	public ResponseEntity<Pagamento> createPagamento(@PathVariable("id") long id, @RequestBody Pagamento pg) {
		try {
			// JogadorController jogController = new JogadorController();
			// ResponseEntity<Jogador> j = jogController.getJogadorById(id);

			Optional<Jogador> data = jRepository.findById(id);
			if (data.isPresent()) {
				Jogador jg = data.get();
				Pagamento p = rep.save(new Pagamento(pg.getAno(), pg.getMes(), pg.getValor(), jg));
				return new ResponseEntity<>(p, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/pagamento/{id}")
	public ResponseEntity<Pagamento> getPagamentoById(@PathVariable("id") long id) {
		Optional<Pagamento> data = rep.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/pagamento/{id}")
	public ResponseEntity<Pagamento> updatePagamento(@PathVariable("id") long id, @RequestBody Pagamento pg) {
		System.out.println("Alterar");

		Optional<Pagamento> data = rep.findById(id);

		if (data.isPresent()) {
			Pagamento p = data.get();
			p.setAno(pg.getAno());
			p.setMes(pg.getMes());
			p.setValor(pg.getValor());
			// p.setJogador(pg.getJogador());

			return new ResponseEntity<>(rep.save(p), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("pagamento/{id}")
	public ResponseEntity<HttpStatus> deletePagamento(@PathVariable("id") long id) {
		try {
			rep.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

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
