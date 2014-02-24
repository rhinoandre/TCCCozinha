package tcc.cozinha.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Pedidos;
import tcc.cozinha.listeners.StatusPedidoListeners;

public class TelaStatusPedido extends JFrame {

	private static final long serialVersionUID = -2380974144458111385L;
	
	private JPanel principalPanel, botoesPanel, nomeProdutoPanel;


	private JButton entregueButton, canceladoButton, fecharButton, emExecucaoButton;
	private JLabel produtoLabel;

	private Pedidos pedido;
	private ArrayList<StatusPedidoListeners> statusPedidoListeners = new ArrayList<StatusPedidoListeners>();



	public TelaStatusPedido(Pedidos pedido) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.pedido = pedido;
		iniciar();
		criarComponentes();
	
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new MigLayout("insets 0"));
		setAlwaysOnTop(true);
		setUndecorated(true);
		setResizable(false);
		setTitle("Mudar status");	
	}
	
	private void criarComponentes() {
		getNomeProdutoPanel().add(getProdutoLabel(), "align center");
		getBotoesPanel().add(getEntregueButton());
		getBotoesPanel().add(getEmExecucaoButton());
		getBotoesPanel().add(getCanceladoButton());
		getBotoesPanel().add(getFecharButton());
		
		getPrincipalPanel().add(getNomeProdutoPanel(), "wrap");
		getPrincipalPanel().add(getBotoesPanel());
		add(getPrincipalPanel());
	}
	
	private JPanel getPrincipalPanel() {
		if(principalPanel == null){
			principalPanel = new JPanel(new MigLayout());
			principalPanel.setBackground(new Color(73, 33, 33, 255));
			principalPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		}
		return principalPanel;
	}
	
	private JPanel getBotoesPanel() {
		if(botoesPanel == null){
			botoesPanel = new JPanel(new MigLayout());
			botoesPanel.setOpaque(false);
		}
		return botoesPanel;
	}
	
	
	private JPanel getNomeProdutoPanel() {
		if(nomeProdutoPanel == null){
			nomeProdutoPanel = new JPanel(new MigLayout());
			nomeProdutoPanel.setOpaque(false);
		}
		return nomeProdutoPanel;
	}
	
	private JButton getEmExecucaoButton() {
		if(emExecucaoButton == null){
			emExecucaoButton = new JButton(new ImageIcon("imgs\\imgEmExecucao.png"));
			emExecucaoButton.setPressedIcon(new ImageIcon("imgs\\imgEmExecucaoPressed.png"));
			configurarBotoes(emExecucaoButton);
			emExecucaoButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (StatusPedidoListeners listener : statusPedidoListeners) {
						listener.emExecucaoAction(pedido);
					}
				}
			});
		}
		return emExecucaoButton;
	}


	private JButton getEntregueButton() {
		if(entregueButton == null){
			entregueButton = new JButton(new ImageIcon("imgs\\imgEntregue.png"));
			entregueButton.setPressedIcon(new ImageIcon("imgs\\imgEntreguePressed.png"));
			configurarBotoes(entregueButton);
			entregueButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (StatusPedidoListeners listener : statusPedidoListeners) {
						listener.entregueButtonAction(pedido);
					}
				}
			});
		}
		return entregueButton;
	}
	
	private JButton getCanceladoButton() {
		if(canceladoButton == null){
			canceladoButton = new JButton(new ImageIcon("imgs\\imgCancelado.png"));
			canceladoButton.setPressedIcon(new ImageIcon("imgs\\imgCanceladoPressed.png"));
			configurarBotoes(canceladoButton);
			canceladoButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (StatusPedidoListeners listener : statusPedidoListeners) {
						listener.canceladoButtonAction(pedido);
					}
				}
			});
		}
		return canceladoButton;
	}
	
	private JButton getFecharButton() {
		if(fecharButton == null){
			fecharButton = new JButton(new ImageIcon("imgs\\imgFechar.png"));
			fecharButton.setPressedIcon(new ImageIcon("imgs\\imgFecharPressed.png"));
			configurarBotoes(fecharButton);
			fecharButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (StatusPedidoListeners listener : statusPedidoListeners) {
						listener.fecharButtonAction();
					}
				}
			});
		}
		return fecharButton;
	}
	
	private JLabel getProdutoLabel() {
		if(produtoLabel == null){
			produtoLabel = new JLabel(pedido.getMesa().getMesa() + " - " + pedido.getItem().getNome());
			produtoLabel.setForeground(Color.white);
			produtoLabel.setFont(new Font("Calibri", 1, 15));
		}
		return produtoLabel;
	}
	
	private void configurarBotoes(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(-5, -5, -5, -5));
	}
	
	public void addListener(StatusPedidoListeners listener){
		statusPedidoListeners.add(listener);
	}
}
