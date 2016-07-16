package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DialogoTramitesInconclusos extends JDialog implements ActionListener {
	private static final long serialVersionUID = -2383166086666096334L;

	private JButton detallesBoton;
	private JButton cerrarBoton;
	private JTable tableTramitesInconclusos;

	private VentanaPrincipal ventanaPrincipal;

	public DialogoTramitesInconclusos(VentanaPrincipal ventanaPrincipal) {
		super(ventanaPrincipal, "Trámites inconclusos", true);
		setLayout(null);
		this.ventanaPrincipal = ventanaPrincipal;

		Object[][] data = {
				{"", "", "", "", ""}};
		String[] columnNames = {"Nombre Solicitante", "Título", "Estado",
				"Paso a concluir", "Responsable"};
		tableTramitesInconclusos = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(tableTramitesInconclusos);
		scrollPane.setBounds(43, 60, 650, 200);
		add(scrollPane);

		detallesBoton = new JButton("Detalles");
		detallesBoton.setBounds(736, 80, 120, 35);
		detallesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/detalles.png")));
		detallesBoton.addActionListener(this);
		add(detallesBoton);

		cerrarBoton = new JButton("Cerrar");
		cerrarBoton.setBounds(390, 300, 120, 35);
		cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
		cerrarBoton.addActionListener(this);
		add(cerrarBoton);

		setSize(900, 380);
		setLocationRelativeTo(ventanaPrincipal);
		setResizable(false);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cerrarBoton)) {
			dispose();
		} else if (e.getSource().equals(detallesBoton)) {
			new DialogoSeguimiento(ventanaPrincipal, null);
		}
	}
}
