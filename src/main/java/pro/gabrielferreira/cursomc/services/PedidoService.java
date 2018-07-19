package pro.gabrielferreira.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pro.gabrielferreira.cursomc.domain.ItemPedido;
import pro.gabrielferreira.cursomc.domain.PagamentoComBoleto;
import pro.gabrielferreira.cursomc.domain.Pedido;
import pro.gabrielferreira.cursomc.domain.enums.EstadoPagamento;
import pro.gabrielferreira.cursomc.repositories.ItemPedidoRepository;
import pro.gabrielferreira.cursomc.repositories.PagamentoRepository;
import pro.gabrielferreira.cursomc.repositories.PedidoRepository;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

//aqui é onde ficam as regras de negocio
@Service
public class PedidoService {

	@Autowired // instancia automaticamente a dependencia
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	// ou seja, essa é uma "regra" que quando der um buscar, vai retornar um item
	// por id
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		// reparar a separacao das responsabilidades, por exemplo aqui instanciei um
		// repo ali em cima, e uso o metodo dele de buscar no banco por id, cada classe
		// com sua responsabilidade
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null); // garanto que o id dele é nulo, pra deixar o repo dar o id
		obj.setInstante(new Date()); // pego o instante que o pedido esta sendo gerado
		// antes nao setava o cliente inteiro no pedido, apens o id, agora seta.
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj); // seto o pedido atual no pagamento
		if (obj.getPagamento() instanceof PagamentoComBoleto) { // se o pagamento for do tipo Pagamento com boleto dou
																// uma data de vencimento pra ele
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		pedidoRepository.save(obj); // persisto o pedido
		pagamentoRepository.save(obj.getPagamento()); // persisto o pagamento
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			// antes nao setava o produto inteiro, agora seta.
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens()); // persisto os itenspedido

		emailService.sendOrderConfirmationHtmlEmail(obj); // envia o email com html

		return obj; // retorno o obj (normalmente pro resources)
	}
}
