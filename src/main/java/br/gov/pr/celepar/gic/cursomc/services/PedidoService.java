package br.gov.pr.celepar.gic.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.ItemPedido;
import br.gov.pr.celepar.gic.cursomc.domain.Pagamento;
import br.gov.pr.celepar.gic.cursomc.domain.PagamentoComBoleto;
import br.gov.pr.celepar.gic.cursomc.domain.PagamentoComCartao;
import br.gov.pr.celepar.gic.cursomc.domain.Pedido;
import br.gov.pr.celepar.gic.cursomc.domain.enums.EstadoPagamento;
import br.gov.pr.celepar.gic.cursomc.dto.PedidoDTO;
import br.gov.pr.celepar.gic.cursomc.repositories.ItemPedidoRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.PagamentoRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.PedidoRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido buscar(Integer id) {
		
		
//		Optional<Pedido> pedido = repo.findById(id);
		
//		return pedido.orElse(null);
		
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
		
	}
	
	public void cadastrar(Pedido pedido) {
		repo.save(pedido);
	}
	
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
			
			//se for boleto define 7 dias para pagamento do boleto
			boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
		}
		
		pedido = repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0d);
			ip.setPreco(produtoService.find(ip.getProduto().getCodigo()).getPreco() );
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		return pedido;
		
	}
	
	public Pedido fromDTO(PedidoDTO pedidoDTO) {
		return new Pedido();
	}
}
