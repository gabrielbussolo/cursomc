package pro.gabrielferreira.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.domain.Cidade;
import pro.gabrielferreira.cursomc.domain.Cliente;
import pro.gabrielferreira.cursomc.domain.Endereco;
import pro.gabrielferreira.cursomc.domain.Estado;
import pro.gabrielferreira.cursomc.domain.Pagamento;
import pro.gabrielferreira.cursomc.domain.PagamentoComBoleto;
import pro.gabrielferreira.cursomc.domain.PagamentoComCartao;
import pro.gabrielferreira.cursomc.domain.Pedido;
import pro.gabrielferreira.cursomc.domain.Produto;
import pro.gabrielferreira.cursomc.domain.enums.EstadoPagamento;
import pro.gabrielferreira.cursomc.domain.enums.TipoCliente;
import pro.gabrielferreira.cursomc.repositories.CategoriaRepository;
import pro.gabrielferreira.cursomc.repositories.CidadeRepository;
import pro.gabrielferreira.cursomc.repositories.ClienteRepository;
import pro.gabrielferreira.cursomc.repositories.EnderecoRepository;
import pro.gabrielferreira.cursomc.repositories.EstadoRepository;
import pro.gabrielferreira.cursomc.repositories.PagamentoRepository;
import pro.gabrielferreira.cursomc.repositories.PedidoRepository;
import pro.gabrielferreira.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	/* Classe princial do spring boot */
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired 
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	//Gambiarra para instanciar classes e jogar no banco h2
	@Override
	public void run(String... args) throws Exception {
		
		//cria categorias
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		//cria produtos
		Produto p1 = new Produto(null, "Computador", 2000.);
		Produto p2 = new Produto(null, "Impressora", 800.);
		Produto p3 = new Produto(null, "Mouse", 80.);
		
		//associa PRODUTOS A CATEGORIA
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		//associa CATEGORIAS A PRODUTOS
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//associa CIO
		
		//persiste no banco de dados
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		//cria estados
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		//cria cidades
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		//estado tem que conhecer a cidade dele
		est1.getCidades().addAll(Arrays.asList(c1));
		est1.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "96838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1 ,c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2 ,sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
	}
}
