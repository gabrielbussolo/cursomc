package pro.gabrielferreira.cursomc.dto;

import java.io.Serializable;

import pro.gabrielferreira.cursomc.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String estado;

	public EstadoDTO() {}
	public EstadoDTO(Estado estado) {
		this.estado = estado.getNome();
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
