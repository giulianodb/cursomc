package br.gov.pr.celepar.gic.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pr.celepar.gic.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
	@Transactional(readOnly=true)
	public Estado findByNome(String nome);
	
}
