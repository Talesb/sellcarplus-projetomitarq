package br.com.sellcarplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sellcarplus.model.negocio.Caminhao;
import br.com.sellcarplus.model.negocio.Carro;
import br.com.sellcarplus.model.negocio.Moto;
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
	
	@RequestMapping("/caminhoes")
	public List<Veiculo> obterListaCaminhao() {
		return  veiculoService.findAllCaminhoes();
	}
	
	@RequestMapping("/motos")
	public List<Veiculo> obterListaMoto() {
		return veiculoService.findAllMotos();
	}
	
	@RequestMapping("/carros")
	public List<Veiculo> obterListaCarro() {
		return veiculoService.findAllCarros();
	}
	

	@RequestMapping("{id}")
	public Veiculo obterPorId(@PathVariable int id) {
		return veiculoService.findById(id);
	}

	@DeleteMapping("{id}")
	public void excluir(@PathVariable int id) {
		veiculoService.delete(id);
	}

	@PostMapping("/carro")
	public void salvar(@RequestBody Carro carro) {
		veiculoService.save(carro);
	}

	@PostMapping("/moto")
	public void salvar(@RequestBody Moto moto) {
		veiculoService.save(moto);
	}

	@PostMapping("/caminhao")
	public void salvar(@RequestBody Caminhao caminhao) {
		veiculoService.save(caminhao);
	}

}
