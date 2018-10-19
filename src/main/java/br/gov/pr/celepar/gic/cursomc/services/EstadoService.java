package br.gov.pr.celepar.gic.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.Estado;
import br.gov.pr.celepar.gic.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome(); 
	}
	
	public Estado findOne (String nome) {
		return repo.findByNome(nome);
	}
	

}
