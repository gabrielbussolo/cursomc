package pro.gabrielferreira.cursomc.resources.exceptions;

import java.io.Serializable;
//classe basica "domain", apenas para instanciar
public class StandardError implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer status; // status http
	private String msg; // mensagem de erro
	private Long timeStamp; // time do erro

	public StandardError(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
