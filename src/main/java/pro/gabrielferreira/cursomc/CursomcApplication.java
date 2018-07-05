package pro.gabrielferreira.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.domain.Produto;
import pro.gabrielferreira.cursomc.repositories.CategoriaRepository;
import pro.gabrielferreira.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	/* Classe princial do spring boot */
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired 
	ProdutoRepository produtoRepository;
	
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
		
		//persiste no banco de dados
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
	}
}
