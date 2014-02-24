package tcc.cozinha.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Pedidos;
import tcc.cozinha.listeners.TelaCozinhaListener;

public class TelaPrincipalCozinha extends JFrame {

	private static final long serialVersionUID = -2829835213153042386L;

	private JPanel panelLista;
	private JTable tabelaPedidos;
	private DefaultTableModel tableModel;
	private ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();
	private ArrayList<TelaCozinhaListener> telaCozinhaListeners = new ArrayList<TelaCozinhaListener>();
	
	public TelaPrincipalCozinha() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		iniciar();
		criarComponentes();
		pack();
		setVisible(true);
	}

	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout("insets 0"));
//		setAlwaysOnTop(true);		
	}

	private void criarComponentes() {
		getTabelaPedidos();
	}

	private DefaultTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new DefaultTableModel(null, new String[] {
					"Hora", "Produto", "Quantidade", "Mesa", "Nota" }) {

				private static final long serialVersionUID = -1475015549982259479L;

				public boolean isCellEditable(int row, int col) {
					return false;
				}

			};
		}
		return tableModel;
	}

	public JPanel getPanelLista() {
		if(panelLista == null){
			panelLista = new JPanel(new MigLayout());
		}
		return panelLista;
	}
	
	public JTable getTabelaPedidos() {
		if(tabelaPedidos == null){
			tabelaPedidos = new JTable();
			JScrollPane scrollPane = new JScrollPane();
			tabelaPedidos.setModel(getTableModel());
			tabelaPedidos.setModel(getTableModel());
			tabelaPedidos.getColumnModel().setColumnSelectionAllowed(false);
			tabelaPedidos.getColumnModel().getColumn(0).setPreferredWidth((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.1));
			tabelaPedidos.getColumnModel().getColumn(1).setPreferredWidth((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.6));
			tabelaPedidos.getColumnModel().getColumn(2).setPreferredWidth((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.1));
			tabelaPedidos.getColumnModel().getColumn(3).setPreferredWidth((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.1));
			tabelaPedidos.getColumnModel().getColumn(4).setPreferredWidth((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.1));
			tabelaPedidos.setRowHeight(30);
			tabelaPedidos.setFont(new Font("Calibri", 2, 20));
			tabelaPedidos.setForeground(Color.white);
			tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaPedidos.setGridColor(new Color(52, 17, 17, 255));
			tabelaPedidos.setBackground(new Color(73, 33, 33, 255));
			tabelaPedidos.setSelectionBackground(new Color(151, 119, 119, 255));
			tabelaPedidos.setMinimumSize(new Dimension(527,497));
			JTableHeader cabecalho = tabelaPedidos.getTableHeader();  
			cabecalho.setFont(new Font("Calibri", 0, 15));
			cabecalho.setForeground(Color.white);
			cabecalho.setBackground(new Color(99, 64, 64, 255));
			cabecalho.setEnabled(false);
			cabecalho.setOpaque(false);
			scrollPane.setViewportView(tabelaPedidos);
			scrollPane.getViewport().setBackground(new Color(73, 33, 33, 255));
			scrollPane.setPreferredSize(tabelaPedidos.getPreferredScrollableViewportSize());
			scrollPane.setMinimumSize(tabelaPedidos.getMinimumSize());
			scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 17, 17, 255), 1));
			tabelaPedidos.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent arg0) {
					for (TelaCozinhaListener listener : telaCozinhaListeners) {
						listener.tabelaPedidoAction(pedidos.get(tabelaPedidos.getSelectedRow()));
					}
				}
			});
			scrollPane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			scrollPane.setViewportView(tabelaPedidos);
			getPanelLista().add(scrollPane, "dock north");
			add(getPanelLista());
		}
		return tabelaPedidos;
	}
	
	public void setPedido(Pedidos pedido){
		this.pedidos.add(pedido);
		addItemLista(pedido);
	}
	
	private void addItemLista(Pedidos pedido) {
			String[] linha = new String[] { ""+pedido.getDtAber().getHours()+":"+pedido.getDtAber().getMinutes()+":"+pedido.getDtAber().getSeconds(),
					pedido.getItem().getNome(),
					Integer.toString(pedido.getQuantidade()),
					pedido.getMesa().getMesa(),
					pedido.getStatuspedido().getStatus()};
			getTableModel().addRow(linha);
	}
	
	public void alterarStatus(Pedidos pedido){
		int i = 0;
		for (Pedidos pedido1 : pedidos) {
			if(pedido1.getIdpedido().equals(pedido.getIdpedido())){
				System.out.println(pedido.getStatuspedido().getStatus());
				break;
			}
			i++;
		}
		getTabelaPedidos().setValueAt(pedido.getStatuspedido().getStatus(), i, 4);
	}
	
	public void addListener(TelaCozinhaListener listener){
		this.telaCozinhaListeners.add(listener);
	}

//	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
//		new TelaPrincipalCozinha(new PedidosDAO().findAll());
//	}

}
