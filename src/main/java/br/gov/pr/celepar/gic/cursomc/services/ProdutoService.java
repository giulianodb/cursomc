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
import br.gov.pr.celepar.gic.cursomc.domain.Produto;
import br.gov.pr.celepar.gic.cursomc.repositories.CategoriaRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.ProdutoRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.DataIntegrityException;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		
		Optional<Produto> produto = repo.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public void cadastrar(Produto produto) {
		repo.save(produto);
	
	}
	
	public List<Produto> findAll() {
		return repo.findAll();
	}
	
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page,Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome,categorias,pageRequest);
	}
	

}
