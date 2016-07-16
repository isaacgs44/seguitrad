package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import dominio.Consulta;

public class DialogoAbrirConsulta extends JDialog implements ActionListener {
	private static final long serialVersionUID = 4065086045034063576L;
 
	private JList<Consulta> consultas;
	private DefaultListModel<Consulta> modeloConsultas;
	private JScrollPane scrollConsultas;
	private JButton cerrarBoton;
	private JButton abrirBoton;
	
	private VentanaPrincipal ventanaPrincipal;

	public DialogoAbrirConsulta(VentanaPrincipal ventanaPrincipal) {	
		super(ventanaPrincipal, "Abrir consulta", true);
		setLayout(null);
		this.ventanaPrincipal = ventanaPrincipal;

		JLabel etiquetaNombreSolicitante = new JLabel("Consultas guardadas: ");
		etiquetaNombreSolicitante.setBounds(150, 10, 200, 50);
		etiquetaNombreSolicitante.setFont(new Font("Tahoma", Font.PLAIN, 20)); 
		add(etiquetaNombreSolicitante);

		modeloConsultas = new DefaultListModel<Consulta>();
		consultas = new JList<Consulta>(modeloConsultas);
		consultas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		add(consultas);
		scrollConsultas = new JScrollPane();
		scrollConsultas.setBounds(45, 60, 400, 280);
		scrollConsultas.setViewportView(consultas);
		add(scrollConsultas);

		abrirBoton = new JButton("Abrir");
		abrirBoton.addActionListener(this);
		abrirBoton.setBounds(86, 370, 120, 35);
		abrirBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/abrir.png")));
		add(abrirBoton);

		cerrarBoton = new JButton("Cerrar");
		cerrarBoton.addActionListener(this);
		cerrarBoton.setBounds(292, 370, 120, 35);
		cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
		add(cerrarBoton);

		inicializar();
		
		setSize(500, 450);
		setLocationRelativeTo(ventanaPrincipal);
		setResizable(false);
		setVisible(true);
	}
	
	private void inicializar() {
		// llenar la lista de consultas
		for (Consulta consulta: ventanaPrincipal.getLista().getListaConsultas()) {
			modeloConsultas.addElement(consulta);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cerrarBoton)) {
			dispose();
		} else if (e.getSource().equals(abrirBoton)) {
			abrir();
		}
	}

	/**
	 * 
	 */
	private void abrir() {
		// verificar que haya algo seleccionado
		if (consultas.getSelectedIndex() != -1) {
			Consulta consulta = consultas.getSelectedValue();
			dispose();
			new DialogoNuevaConsulta(ventanaPrincipal, consulta);
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de consultas", 
					"Error al abrir consulta", JOptionPane.ERROR_MESSAGE);
		}
	}
}