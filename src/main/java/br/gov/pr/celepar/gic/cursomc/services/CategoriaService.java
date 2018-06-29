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
import br.gov.pr.celepar.gic.cursomc.repositories.CategoriaRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.DataIntegrityException;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		
		
//		Optional<Categoria> categoria = repo.findById(id);
		
//		return categoria.orElse(null);
		
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		
		
	}
	
	public void cadastrar(Categoria categoria) {
		repo.save(categoria);
	
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		repo.save(categoria);
		
		return categoria;
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repo.save(categoria);
	}
	
	public void delete(Integer idCategoria) {
		find(idCategoria);
		
		try {
			
			repo.deleteById(idCategoria);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityException("Não é possível excluir categorias com produtos.");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	
	public Page<Categoria> findPage(Integer page,Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
	}
}
