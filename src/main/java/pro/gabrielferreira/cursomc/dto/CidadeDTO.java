package pro.gabrielferreira.cursomc.dto;

import java.io.Serializable;

import pro.gabrielferreira.cursomc.domain.Cidade;

public class CidadeDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nome;
	
	public CidadeDTO() {}
	public CidadeDTO(Cidade cidade) {
		this.nome = cidade.getNome();
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
