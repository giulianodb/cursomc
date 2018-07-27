package br.gov.pr.celepar.gic.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pr.celepar.gic.cursomc.domain.Cidade;
import br.gov.pr.celepar.gic.cursomc.domain.Cliente;
import br.gov.pr.celepar.gic.cursomc.domain.Endereco;
import br.gov.pr.celepar.gic.cursomc.domain.enums.TipoCliente;
import br.gov.pr.celepar.gic.cursomc.dto.ClienteDTO;
import br.gov.pr.celepar.gic.cursomc.dto.ClienteNewDTO;
import br.gov.pr.celepar.gic.cursomc.repositories.CidadeRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.ClienteRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.EnderecoRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.DataIntegrityException;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	
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
//		Cidade cid = new Cidade(clienteDTO.getCidadeId);
		
		return new Cliente( clienteDTO.getId(),clienteDTO.getNome(), clienteDTO.getEmail(),null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cidade cid = cidadeRepository.findById(clienteNewDTO.getCidadeId()).orElse(null);
		
		Cliente cliente = new Cliente( null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(),clienteNewDTO.getCpfOuCnpj(),TipoCliente.toEnum(clienteNewDTO.getTipo()));		
		Endereco endereco = new Endereco(null,clienteNewDTO.getLogradouro(),clienteNewDTO.getNumero(),clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(),clienteNewDTO.getCep(), cliente,cid);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		
		if(clienteNewDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		
		if(clienteNewDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		
		
		return cliente;
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		
		return cliente;
	}
	
	private void updateData(Cliente clienteBanco, Cliente clienteAnterado) {
		clienteBanco.setEmail(clienteAnterado.getEmail());
		clienteBanco.setNome(clienteAnterado.getNome());
	}

}
