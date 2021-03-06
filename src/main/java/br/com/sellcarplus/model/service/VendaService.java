package br.com.sellcarplus.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sellcarplus.model.dto.VendaDTO;
import br.com.sellcarplus.model.negocio.Cliente;
import br.com.sellcarplus.model.negocio.Veiculo;
import br.com.sellcarplus.model.negocio.Venda;
import br.com.sellcarplus.model.repository.IVendaRepository;

@Service
public class VendaService {

	@Autowired
	private IVendaRepository vendaRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private VeiculoService veiculoService;

	public List<Venda> findAll() {
		return (List<Venda>) this.vendaRepository.findAll();
	}

	public Venda findById(long id) {
		return this.vendaRepository.findById(id).get();
	}

	public void delete(long id) {
		this.vendaRepository.deleteById(id);
	}

	public void save(VendaDTO vendaDTO) throws Exception {

		if (vendaDTO.getClienteId() == 0) {
			throw new Exception("É necessário informar um Cliente id");
		}

		if (vendaDTO.getVeiculoIds().isEmpty()) {
			throw new Exception("É necessário informar um id de um veículo");
		}

		Cliente clienteVenda = this.clienteService.findById(vendaDTO.getClienteId());

		if (clienteVenda == null) {
			throw new Exception("Cliente não encontrado");
		}

		Venda novaVenda = Venda.fromDTO(vendaDTO);
		List<Veiculo> veiculos = new ArrayList<Veiculo>();

		vendaDTO.getVeiculoIds().forEach(id -> {
			Veiculo veiculoEncontrado = this.veiculoService.findById(id);
			if (veiculoEncontrado != null) {
				novaVenda.setValorTotal(veiculoEncontrado.getPreco() + novaVenda.getValorTotal());
				veiculos.add(veiculoEncontrado);
			}
		});

		if (veiculos.isEmpty()) {
			throw new Exception("Não foi encontrado nenhum veículo com os ids informados");

		}

		novaVenda.setCliente(clienteVenda);
		novaVenda.setVeiculos(veiculos);

		if (novaVenda.getPorcentagemDesconto() > 0 && novaVenda.getValorTotal() > 0) {
			double valorTotalComDesconto = (novaVenda.getValorTotal()
					- ((novaVenda.getValorTotal() * novaVenda.getPorcentagemDesconto()) / 100));

			novaVenda.setValorTotal(valorTotalComDesconto);
		}

		this.vendaRepository.save(novaVenda);

		veiculos.forEach(veiculo -> {
			veiculo.setVenda(novaVenda);
			veiculo.setVendido(true);
			this.veiculoService.save(veiculo);
		});

	}

}
