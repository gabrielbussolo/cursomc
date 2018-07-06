package pro.gabrielferreira.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

//Essa classe é basicamente inteira JPA
@Entity
public class Categoria implements Serializable { 
	//serializar a classe serve para tirar ela de binario, e colocar no banco, ou servir ela como json
	//https://pt.stackoverflow.com/questions/88270/qual-a-finalidade-da-interface-serializable
	private static final long serialVersionUID = 1L;
	
	//unica coisa que criei nessa classe foi os atributos e o relacionamento, o resto é tudo codigo gerado pelo eclipse
	
	//@id diz que o proximo atributo é id, @generatedvalue atribui uma sequencia de valores automaticamente
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	//tratando referencia ciclica (nao entendi, pesquisar mais)
	@JsonManagedReference
	@ManyToMany(mappedBy="categorias") //indico que este é o lado dominado.
	private List<Produto> produtos = new ArrayList<>(); //por ser many to many, crio uma lista para receber os produtos de determinada categoria
	
	public Categoria() {}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
