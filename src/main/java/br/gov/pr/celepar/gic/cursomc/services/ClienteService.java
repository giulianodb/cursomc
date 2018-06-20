package br.gov.pr.celepar.gic.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.Cliente;
import br.gov.pr.celepar.gic.cursomc.repositories.ClienteRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}
	
	public void cadastrar(Cliente cliente) {
		repo.save(cliente);
		
	}
}
