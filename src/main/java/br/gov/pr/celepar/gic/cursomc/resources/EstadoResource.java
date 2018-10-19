package br.gov.pr.celepar.gic.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pr.celepar.gic.cursomc.domain.Cidade;
import br.gov.pr.celepar.gic.cursomc.domain.Estado;
import br.gov.pr.celepar.gic.cursomc.dto.CidadeDTO;
import br.gov.pr.celepar.gic.cursomc.dto.EstadoDTO;
import br.gov.pr.celepar.gic.cursomc.services.CidadeService;
import br.gov.pr.celepar.gic.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		
		List<Estado> listEstado = estadoService.findAll();
		
		List<EstadoDTO> listEstadoDTO = listEstado.stream().map(obj -> new EstadoDTO(obj.getId(), obj.getNome())).collect(Collectors.toList()); 
		
		return ResponseEntity.ok().body(listEstadoDTO);
	}
	
	@RequestMapping(value="{nome}",method=RequestMethod.GET)
	public ResponseEntity<EstadoDTO> findPorNome(@PathVariable(value="nome") String nome) {
		
		Estado estado = estadoService.findOne(nome);
		
		EstadoDTO estadoDTO = new EstadoDTO(estado.getId(), estado.getNome());
		
		return ResponseEntity.ok().body(estadoDTO);
	}
	
	
	@RequestMapping(value="/{estado_id}/cidades",method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> buscarCidades(@PathVariable(value="estado_id") Integer estadoId) {
		
		List<Cidade> listCidade = cidadeService.findCidadesPorEstado(estadoId);
		
		List<CidadeDTO> listCidadeDTO = listCidade.stream().map(obj -> new CidadeDTO(obj.getId(),obj.getNome())).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listCidadeDTO);
	}
}
  