package tcc.cozinha.listeners;

import tcc.balcao.model.entity.Pedidos;

public interface StatusPedidoListeners {
	public void entregueButtonAction(Pedidos pedidos);
	public void canceladoButtonAction(Pedidos pedidos);
	public void fecharButtonAction();
	public void emExecucaoAction(Pedidos pedido);
}
