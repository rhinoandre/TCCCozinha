package tcc.cozinha.listeners;

import java.util.ArrayList;

import tcc.balcao.model.entity.Pedidos;

public interface TelaCozinhaListener {
	public void novoPedido(ArrayList<Pedidos> pedidos);
	public void tabelaPedidoAction(Pedidos pedido);
	public void cancelarPedido(Pedidos pedido);
}
