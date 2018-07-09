package pro.gabrielferreira.cursomc.domain.enums;
//enum simples, porem aqui tem algumas coisas diferentes que preciso estudar melhor.
public enum TipoCliente {
	
	//seto o enum normalmente
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	//percorro a lista de enum para devolver o enum correspondente.
	public static TipoCliente toEnum(Integer cod) { //declaro static para nao precisar instanciar a classe, apenas usar o metodo
		if(cod == null) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
