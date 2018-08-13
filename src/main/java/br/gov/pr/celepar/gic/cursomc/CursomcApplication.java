package br.gov.pr.celepar.gic.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.gov.pr.celepar.gic.cursomc.domain.Categoria;
import br.gov.pr.celepar.gic.cursomc.domain.Cidade;
import br.gov.pr.celepar.gic.cursomc.domain.Cliente;
import br.gov.pr.celepar.gic.cursomc.domain.Endereco;
import br.gov.pr.celepar.gic.cursomc.domain.Estado;
import br.gov.pr.celepar.gic.cursomc.domain.ItemPedido;
import br.gov.pr.celepar.gic.cursomc.domain.Pagamento;
import br.gov.pr.celepar.gic.cursomc.domain.PagamentoComBoleto;
import br.gov.pr.celepar.gic.cursomc.domain.PagamentoComCartao;
import br.gov.pr.celepar.gic.cursomc.domain.Pedido;
import br.gov.pr.celepar.gic.cursomc.domain.Produto;
import br.gov.pr.celepar.gic.cursomc.domain.enums.EstadoPagamento;
import br.gov.pr.celepar.gic.cursomc.domain.enums.TipoCliente;
import br.gov.pr.celepar.gic.cursomc.repositories.CategoriaRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.CidadeRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.ClienteRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.EnderecoRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.EstadoRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.ItemPedidoRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.PagamentoRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.PedidoRepository;
import br.gov.pr.celepar.gic.cursomc.repositories.ProdutoRepository;
import br.gov.pr.celepar.gic.cursomc.services.CategoriaService;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		Categoria cat3 = new Categoria(null,"Cama mesa e banho");
		Categoria cat4 = new Categoria(null,"Eletrônicos");
		Categoria cat5 = new Categoria(null,"Jardinagem");
		Categoria cat6 = new Categoria(null,"Decoração");
		Categoria cat7 = new Categoria(null,"Perfumaria");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		Produto p4 = new Produto(null,"Mesa de escritório",300.00);
		Produto p5 = new Produto(null,"Toalha",50.00);
		Produto p6 = new Produto(null,"Colcha",200.00);
		Produto p7 = new Produto(null,"TV True color",1200.00);
		Produto p8 = new Produto(null,"Roçadeira",800.00);
		Produto p9 = new Produto(null,"Abajour",100.00);
		Produto p10 = new Produto(null,"Pendente",180.00);
		Produto p11 = new Produto(null,"Shampóo",90.00);
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia",est1);
		Cidade cid2 = new Cidade(null, "São Paulo",est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		
		cat3.getProdutos().addAll(Arrays.asList(p5,p6));
		cat4.getProdutos().addAll(Arrays.asList(p1,p2,p3,p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9,p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2,cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		
		categoriaService.cadastrar(cat1);
		categoriaService.cadastrar(cat2);
		categoriaService.cadastrar(cat3);
		categoriaService.cadastrar(cat4);
		categoriaService.cadastrar(cat5);
		categoriaService.cadastrar(cat6);
		categoriaService.cadastrar(cat7);
		
//		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p3,p2,p3,p1,p2,p3,p1,p2,p3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(cid1,cid2,cid3));
		
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","14984781238",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("54465","5646"));
		
		
		Endereco end1 = new Endereco(null,"Rua flores","500","Apt 3", "jaridm","545",cli1,cid1);
		Endereco end2 = new Endereco(null,"Avenida mat os","34","sala 33", "centro","3565464",cli1,cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1,end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1,end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedi1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido pedi2 = new Pedido(null, sdf.parse("10/10/2017 12:32"), cli1, end2);
		
		
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,pedi1,6);
		pedi1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto( null,EstadoPagamento.PENDENTE,pedi2,sdf.parse("20/10/2017 00:00"), null);
		pedi2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(pedi1,pedi2));
		
		pedidoRepository.saveAll(Arrays.asList(pedi1,pedi2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(pedi1, p1, 0.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(pedi1, p3, 0.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(pedi2, p2, 100.0, 1, 800.0);
		
		pedi1.getItens().addAll(Arrays.asList(ip1,ip2));
		pedi2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip3,ip3));
		// 
		
	}
}
