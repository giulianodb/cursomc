package br.gov.pr.celepar.gic.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pr.celepar.gic.cursomc.domain.Produto;
import br.gov.pr.celepar.gic.cursomc.dto.ProdutoDTO;
import br.gov.pr.celepar.gic.cursomc.resources.utils.URL;
import br.gov.pr.celepar.gic.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Produto produto = produtoService.find(id);
		
		
		return ResponseEntity.ok(produto);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> 
		findPage(@RequestParam(value="nome",defaultValue="") String nome,
				@RequestParam(value="categorias",defaultValue="0") String categorias,
				@RequestParam(value="page",defaultValue="0") Integer page,
				@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage,
				@RequestParam(value="orderBy",defaultValue="nome") String orderBy,
				@RequestParam(value="direction",defaultValue="ASC") String direction) {
		
		String nomeDecoded = URL.decodeParam(nome);
		
		Page<Produto> produtos = produtoService. search(nomeDecoded,URL.decodeIntList(categorias), page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> produtosDTO = produtos.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(produtosDTO);
	}
	
	
}
  