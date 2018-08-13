package br.gov.pr.celepar.gic.cursomc.dto;

import java.io.Serializable;

import br.gov.pr.celepar.gic.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private String nome;
	private Double preco;
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO (Produto obj) {
		this.codigo = obj.getCodigo();
		this.nome = obj.getNome();
		this.preco = obj.getPreco();
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
