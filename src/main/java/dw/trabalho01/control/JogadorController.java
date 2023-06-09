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

	// rota para buscar todos os jogadores
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

	// rota para criar um jogador
	@PostMapping("/jogador")
	public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jo) {
		try {
			if (jo.getNome() != "" && jo.getEmail() != "" && jo.getDataNasc() != null) {
				Jogador j = rep.save(new Jogador(jo.getNome(), jo.getEmail(), jo.getDataNasc()));
				return new ResponseEntity<>(j, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// rota para buscar um jogador pelo id
	@GetMapping("/jogador/{id}")
	public ResponseEntity<Jogador> getJogadorById(@PathVariable("id") long id) {
		Optional<Jogador> data = rep.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// rota para atualizar os dados de um jogador
	@PutMapping("/jogador/{id}")
	public ResponseEntity<Jogador> updateJogador(@PathVariable("id") long id, @RequestBody Jogador jo) {

		Optional<Jogador> data = rep.findById(id);

		if (data.isPresent()) {
			Jogador j = data.get();
			if (jo.getNome() != "") {
				j.setNome(jo.getNome());
			}
			if (jo.getEmail() != "") {
				j.setEmail(jo.getEmail());
			}
			if (jo.getDataNasc() != null) {
				j.setDataNasc(jo.getDataNasc());
			}

			return new ResponseEntity<>(rep.save(j), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	//rota para deletar um jogador de acordo com seu id
	@DeleteMapping("/jogador/{id}")
	public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("id") long id) {
		try {
			rep.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//rota para deletar todos os jogadores
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
