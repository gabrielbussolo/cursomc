package pro.gabrielferreira.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//a chave dessa classe é composta por duas chaves, pois ela faz uma ligacao entre pedido e produto
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	private Double desconto;
	private Integer quantidade;
	private Double preco;

	public ItemPedido() {
	}

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido); //como eu instanciei a classe em cima, atribuo a ela o pedido que recebi por parametro.
		id.setProduto(produto); // mesma coisa que o de cima.
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	// a palavra get faz o json reconhecer e retornar junto
	// só retorna subtotal
	public double getSubTotal() {
		return (preco - desconto) * quantidade;
	}

	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}

	// criado o set de pedido e produto
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	public Produto getProduto() {
		return id.getProduto();
	}

	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	// to string pra jogar no console e formatar futuro email;
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", Qte: ");
		builder.append(getQuantidade());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(getPreco()));
		builder.append(", Subtotal: ");
		builder.append(nf.format(getSubTotal()));
		builder.append("\n");
		return builder.toString();
	}

}
