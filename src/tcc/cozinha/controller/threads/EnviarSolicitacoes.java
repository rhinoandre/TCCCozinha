package tcc.cozinha.controller.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import tcc.balcao.interfaces.Acoes;
import tcc.balcao.interfaces.StatusPedidos;
import tcc.balcao.model.TransferObject;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;
import tcc.balcao.model.entity.Statuspedido;
import tcc.cozinha.controller.DescobrirServidor;
import tcc.cozinha.listeners.TelaCozinhaListener;

@SuppressWarnings({ "unchecked" })
public class EnviarSolicitacoes implements Runnable {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private InetAddress endServidor;
	private TelaCozinhaListener telaCozinhaListener;
	private TransferObject to;
	private Boolean parar = false;

	public EnviarSolicitacoes(TelaCozinhaListener telaCozinhaListener)
			throws IOException, ClassNotFoundException {
		this.telaCozinhaListener = telaCozinhaListener;

		endServidor = DescobrirServidor.getInstance().descobrirServidor();
		socket = new Socket(endServidor, 1238);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			TransferObject obj = new TransferObject(
					Acoes.INICIAR_CONEXAO_COZINHA, "Cozinha");
			enviarObject(obj);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (!parar) {
			Object object;
			try {
				object = ois.readObject();

				if (object instanceof TransferObject) {
					to = (TransferObject) object;
					verificarOpcoes(to.getOpcao());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void verificarOpcoes(int opcao) {
		switch (opcao) {
		case Acoes.INICIAR_CONEXAO_COZINHA:
			if (to.getObj() instanceof ArrayList<?>) {
				ArrayList<Pedidos> pedidos = (ArrayList<Pedidos>) to.getObj();
				telaCozinhaListener.novoPedido(pedidos);
			} else {
				TransferObject toError = (TransferObject) to.getObj();
				// TODO: Chamar tela de erro
			}
			break;
		case Acoes.BUSCAR_PEDIDOS:
			if (to.getObj() instanceof ArrayList<?>) {
				telaCozinhaListener.novoPedido((ArrayList<Pedidos>) to.getObj());
			}
			break;
			
		case Acoes.NOVO_PEDIDO:
			if (to.getObj() instanceof ArrayList<?>) {
				telaCozinhaListener.novoPedido((ArrayList<Pedidos>) to.getObj());
			}
			break;
		case Acoes.CANCELAR_PEDIDO:
			if (to.getObj() instanceof Pedidos) {
				Pedidos pedido = (Pedidos) to.getObj();
				telaCozinhaListener.cancelarPedido(pedido);
			}
			break;
			
		case Acoes.FECHAR_CONEXAO:
			parar = true;
			break;
			
		default:
			break;
		}
	}

	public void enviarObject(Object obj) throws IOException {
		oos.writeObject(obj);
	}
	
	public void alterarStatus(Pedidos pedido) throws IOException {
			enviarObject(new TransferObject(Acoes.ALTERAR_STATUS, pedido));
	}

	public void fecharConexao(Mesa mesa) throws IOException,
			ClassNotFoundException {
		enviarObject(new TransferObject(Acoes.FECHAR_CONEXAO, "Cozinha"));
	}

	public void buscarPedidos() throws IOException,
			ClassNotFoundException {
		TransferObject obj = new TransferObject(Acoes.BUSCAR_PEDIDOS,
				new Date());
		enviarObject(obj);

	}
}