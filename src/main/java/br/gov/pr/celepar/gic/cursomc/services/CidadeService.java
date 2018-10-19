package br.gov.pr.celepar.gic.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.Cidade;
import br.gov.pr.celepar.gic.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findCidadesPorEstado(Integer estadoId) {
		return repo.findCidades(estadoId); 
	}
	
//	public Estado findOne (String nome) {
//		return repo.findByNome(nome);
//	}
	

}
