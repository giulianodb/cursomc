package br.gov.pr.celepar.gic.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.Categoria;
import br.gov.pr.celepar.gic.cursomc.domain.Cliente;
import br.gov.pr.celepar.gic.cursomc.domain.Cliente;
import br.gov.pr.celepar.gic.cursomc.dto.ClienteDTO;
import br.gov.pr.celepar.gic.cursomc.repositories.ClienteRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.DataIntegrityException;
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
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
		
	}
	
	public void cadastrar(Cliente cliente) {
		repo.save(cliente);
		
	}
	
	public Cliente update(Cliente clienteAlterado) {
		Cliente clienteBanco = find(clienteAlterado.getId());
		updateData(clienteBanco,clienteAlterado);
		return repo.save(clienteBanco);
	}

	public void delete(Integer idCliente) {
		find(idCliente);
		
		try {
			repo.deleteById(idCliente);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityException("Não é possível excluir clientes com pedidos.");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	
	public Page<Cliente> findPage(Integer page,Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente( clienteDTO.getId(),clienteDTO.getNome(), clienteDTO.getEmail(),null,null);
	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		repo.save(cliente);
		
		return cliente;
	}
	
	private void updateData(Cliente clienteBanco, Cliente clienteAnterado) {
		clienteBanco.setEmail(clienteAnterado.getEmail());
		clienteBanco.setNome(clienteAnterado.getNome());
	}

}
