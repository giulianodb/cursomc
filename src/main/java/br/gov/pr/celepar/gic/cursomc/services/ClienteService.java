package br.gov.pr.celepar.gic.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.gov.pr.celepar.gic.cursomc.domain.Cidade;
import br.gov.pr.celepar.gic.cursomc.domain.Cliente;
import br.gov.pr.celepar.gic.cursomc.domain.Endereco;
import br.gov.pr.celepar.gic.cursomc.domain.enums.Perfil;
import br.gov.pr.celepar.gic.cursomc.domain.enums.TipoCliente;
import br.gov.pr.celepar.gic.cursomc.dto.ClienteDTO;
import br.gov.pr.celepar.gic.cursomc.dto.ClienteNewDTO;
import br.gov.pr.celepar.gic.cursomc.repositories.CidadeRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.ClienteRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.EnderecoRepository;
import br.gov.pr.celepar.gic.cursomc.security.UserSS;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.AuthorizationException;
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
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	
	public Cliente buscar(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasHole(Perfil.ADMIN) && !user.getId().equals(id)) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasHole(Perfil.ADMIN) && !user.getId().equals(id)) {
			throw new AuthorizationException("Acesso negado");
		}
		
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
		
		return new Cliente( clienteDTO.getId(),clienteDTO.getNome(), clienteDTO.getEmail(),null,null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cidade cid = cidadeRepository.findById(clienteNewDTO.getCidadeId()).orElse(null);
		
		String senha = pe.encode(clienteNewDTO.getSenha());
		
		Cliente cliente = new Cliente( null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(),clienteNewDTO.getCpfOuCnpj(),TipoCliente.toEnum(clienteNewDTO.getTipo()),senha);		
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
	
	
	public URI uploadProfilePicture (MultipartFile multipartFile) {
	
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"),fileName,"image");
		 
	}
}
