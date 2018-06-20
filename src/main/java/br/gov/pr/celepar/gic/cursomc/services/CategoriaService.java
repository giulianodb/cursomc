package br.gov.pr.celepar.gic.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.Categoria;
import br.gov.pr.celepar.gic.cursomc.repositories.CategoriaRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		
		
//		Optional<Categoria> categoria = repo.findById(id);
		
//		return categoria.orElse(null);
		
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		
		
	}
	
	public void cadastrar(Categoria categoria) {
		repo.save(categoria);
		
	}
}
