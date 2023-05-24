package dw.trabalho01.control;

import java.io.Console;
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

@RestController
@RequestMapping("/api")
public class JogadorController {

	@Autowired
	JogadorRepository rep;

	@GetMapping("/jogador")
	public ResponseEntity<List<Jogador>> getAllJogadores(@RequestParam(required = false) String nome) {
		try {
			List<Jogador> lj = new ArrayList<Jogador>();

			if (nome == null) {
				rep.findAll().forEach(lj::add);
			} else {
				rep.findByNome(nome).forEach(lj::add);
			}

			if (lj.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(lj, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/jogador")
	public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jo) {
		try {
			Jogador j = rep.save(new Jogador(jo.getNome(), jo.getEmail(), jo.getDataNasc()));

			return new ResponseEntity<>(j, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @PostMapping("/jogador/pagamento/{id}")
	// public ResponseEntity<Jogador> createPagamentoJogador(@PathVariable("id") long id, @RequestBody Pagamento pg) {
	// 	try {
	// 		Optional<Jogador> data = rep.findById(id);
	// 		Jogador j = new Jogador();

	// 		if (data.isPresent()) {
	// 			PagamentoController pgController = new PagamentoController();

	// 			ResponseEntity<Pagamento> p = pgController.createPagamento(pg);
				
	// 			Pagamento pa = p.getBody();

	// 			j = data.get();
	// 			// List<Pagamento> pgs = new ArrayList<Pagamento>();
	// 			// while(j.getPagamentos().){

	// 			// }
	// 			// pgs.add(pg);
	// 			// j.setPagamentos(pgs);
	// 			j.getPagamentos().add(pa);

				
	// 		} else {
	// 			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 		}
	// 		return new ResponseEntity<>(j, HttpStatus.CREATED);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}
	// }

	@GetMapping("/jogador/{id}")
	public ResponseEntity<Jogador> getJogadorById(@PathVariable("id") long id) {
		Optional<Jogador> data = rep.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/jogador/{id}")
	public ResponseEntity<Jogador> updateJogador(@PathVariable("id") long id, @RequestBody Jogador jo) {
		System.out.println("Alterar");

		Optional<Jogador> data = rep.findById(id);

		if (data.isPresent()) {
			Jogador j = data.get();
			j.setNome(jo.getNome());
			j.setEmail(jo.getEmail());
			j.setDataNasc(jo.getDataNasc());

			return new ResponseEntity<>(rep.save(j), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/jogador/{id}")
	public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("id") long id) {
		try {
			rep.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/jogador")
	public ResponseEntity<HttpStatus> deleteAllJogador() {
		try {
			rep.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
