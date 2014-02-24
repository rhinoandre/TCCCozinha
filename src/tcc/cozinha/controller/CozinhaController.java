package tcc.cozinha.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import tcc.balcao.interfaces.Acoes;
import tcc.balcao.interfaces.StatusMesas;
import tcc.balcao.interfaces.StatusPedidos;
import tcc.balcao.model.TransferObject;
import tcc.balcao.model.entity.Pedidos;
import tcc.cozinha.controller.threads.EnviarSolicitacoes;
import tcc.cozinha.listeners.StatusPedidoListeners;
import tcc.cozinha.listeners.TelaCozinhaAdapter;
import tcc.cozinha.listeners.TelaCozinhaListener;
import tcc.cozinha.view.TelaPrincipalCozinha;
import tcc.cozinha.view.TelaStatusPedido;

public class CozinhaController {
	private TelaPrincipalCozinha telaCozinha;
	private TelaStatusPedido telaStatusPedido;
	
	private EnviarSolicitacoes enviarSolicitacoes;
	
	public CozinhaController() {
		try {
			
			telaCozinha = new TelaPrincipalCozinha();
			telaCozinha.addListener(new TelaCozinhaAdapter() {
				
				@Override
				public void tabelaPedidoAction(Pedidos pedido) {
					if(telaStatusPedido != null){
						telaStatusPedido.dispose();
						telaStatusPedido = null;
					}
					
					try {
						telaStatusPedido = new TelaStatusPedido(pedido);
						telaStatusPedido.addListener(new StatusPedidoListeners() {
							
							@Override
							public void fecharButtonAction() {
								telaStatusPedido.dispose();
							}
							
							@Override
							public void entregueButtonAction(Pedidos pedido) {
								try {
									pedido.setStatuspedido(StatusPedidos.FINALIZADO);
									enviarSolicitacoes.alterarStatus(pedido);
									telaCozinha.alterarStatus(pedido);
									telaStatusPedido.dispose();
//									TODO: Tenho que remover o item da lista.
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							
							@Override
							public void canceladoButtonAction(Pedidos pedido) {
								try {
									pedido.setStatuspedido(StatusPedidos.CANCELADO);
									enviarSolicitacoes.alterarStatus(pedido);
									telaCozinha.alterarStatus(pedido);
									telaStatusPedido.dispose();
//									TODO: Tenho que remover o item da lista.
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void emExecucaoAction(Pedidos pedido) {
								try {
									pedido.setStatuspedido(StatusPedidos.EM_EXECUCAO);
									enviarSolicitacoes.alterarStatus(pedido);
									telaCozinha.alterarStatus(pedido);
									telaStatusPedido.dispose();
//									TODO: Tenho que remover o item da lista.
								} catch (IOException e) {
									e.printStackTrace();
								}
								
							}
						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
				}

			});

			enviarSolicitacoes = new EnviarSolicitacoes(new TelaCozinhaAdapter(){
				
				@Override
				public void novoPedido(ArrayList<Pedidos> pedidos) {
					for (Pedidos pedido : pedidos) {
						telaCozinha.setPedido(pedido);
					}
				}
				public void cancelarPedido(Pedidos pedido) {
					telaCozinha.alterarStatus(pedido);
					
				}
			});
			new Thread(enviarSolicitacoes).start();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
