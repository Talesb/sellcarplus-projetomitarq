package br.com.sellcarplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sellcarplus.model.negocio.Veiculo;
import br.com.sellcarplus.model.service.VeiculoService;


@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController {

	@Autowired
	private VeiculoService veiculoService;

	@GetMapping
	public List<Veiculo> obterLista() {
		return veiculoService.findAll();
	}

	@RequestMapping("{id}")
	public Veiculo obterPorId(@PathVariable int id) {
		return veiculoService.findById(id);
	}

	@DeleteMapping("{id}")
	public void excluir(int id) {
		veiculoService.delete(id);
	}

	@PostMapping
	public void salvar(Veiculo Veiculo) {
		veiculoService.save(Veiculo);
	}
}
