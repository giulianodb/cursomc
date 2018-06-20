package br.gov.pr.celepar.gic.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.gov.pr.celepar.gic.cursomc.domain.Categoria;
import br.gov.pr.celepar.gic.cursomc.services.CategoriaService;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaService categoriaService;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		categoriaService.cadastrar(cat1);
		categoriaService.cadastrar(cat2);
		
	}
}
