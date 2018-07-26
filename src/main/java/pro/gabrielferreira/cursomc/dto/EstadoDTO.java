package pro.gabrielferreira.cursomc.dto;

import java.io.Serializable;

import pro.gabrielferreira.cursomc.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;

	public EstadoDTO() {}
	public EstadoDTO(Estado estado) {
		this.setId(estado.getId());
		this.nome = estado.getNome();
	}
	
	public String getEstado() {
		return nome;
	}

	public void setEstado(String estado) {
		this.nome = estado;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
