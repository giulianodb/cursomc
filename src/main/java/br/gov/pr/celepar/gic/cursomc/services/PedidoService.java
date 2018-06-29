package br.gov.pr.celepar.gic.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.Pedido;
import br.gov.pr.celepar.gic.cursomc.repositories.PedidoRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
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
}
