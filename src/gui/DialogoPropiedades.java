package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import dominio.Tramite;

import java.awt.event.ActionListener;

public class DialogoPropiedades extends JDialog implements ActionListener {
	private static final long serialVersionUID = -3986982284316512540L;
	
	private JLabel etiquetaTitulo;
	private JButton cerrarBoton;
	private JButton modificarTramiteBoton;
	private JButton siguienteBoton;
	private JButton anteriorBoton;
	
	private PanelDatosGenerales panelDatosGenerales;
	private PanelTablaDatosEspecificos panelTablaDatosEspecificos;
	private PanelTablaPasos panelTablaPasos;

	private int regActual = 0;
	private VentanaPrincipal ventanaPrincipal;
	private Tramite tramite;
	
	public DialogoPropiedades(VentanaPrincipal ventanaPrincipal) {	
		super(ventanaPrincipal, "Propiedades", true);
		this.ventanaPrincipal = ventanaPrincipal; 
		this.tramite = ventanaPrincipal.getLista().getTramite();
		setLayout(null);
				
		anteriorBoton = new JButton("Anterior");
		anteriorBoton.setBounds(74, 610, 150, 40);
		anteriorBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/anterior.png")));
		add(anteriorBoton);
		anteriorBoton.addActionListener(this);
		
		modificarTramiteBoton = new JButton("Modificar trámite");
		modificarTramiteBoton.setBounds(298, 610, 200, 40);
		modificarTramiteBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
		add(modificarTramiteBoton);
		modificarTramiteBoton.addActionListener(this);
		
		siguienteBoton = new JButton("Siguiente");
		siguienteBoton.setBounds(572, 610, 150, 40);
		siguienteBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/siguiente.png")));
		add(siguienteBoton);
		siguienteBoton.addActionListener(this);
		
		cerrarBoton = new JButton("Cerrar");
		cerrarBoton.setBounds(796, 610, 150, 40);
		cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		add(cerrarBoton);
		cerrarBoton.addActionListener(this);
		
		etiquetaTitulo = new JLabel("DATOS ESPECÍFICOS");
		etiquetaTitulo.setBounds(445, 11, 250, 50);
		etiquetaTitulo.setFont(new Font("Tahoma", Font.PLAIN, 24)); 
		add(etiquetaTitulo);
		
		panelDatosGenerales = new PanelDatosGenerales(null, tramite);
		panelDatosGenerales.ocultarComponentes();
		panelDatosGenerales.setBounds(10, 10, 990, 590);
		panelTablaDatosEspecificos = new PanelTablaDatosEspecificos();
		panelTablaDatosEspecificos.modificarTabla(tramite.getCampos());
		panelTablaDatosEspecificos.deshabilitarTabla();
		panelTablaDatosEspecificos.setBounds(10, 60, 990, 540);
		panelTablaPasos = new PanelTablaPasos();
		panelTablaPasos.modificarTabla(tramite.getPasos());
		panelTablaPasos.deshabilitarTabla();
		panelTablaPasos.setBounds(10, 60, 990, 540);
		add(panelTablaPasos);
		add(panelTablaDatosEspecificos);
		add(panelDatosGenerales);
		
		inicializar();
		
		setSize(1020, 700);
		setLocationRelativeTo(ventanaPrincipal);
		setResizable(false);
		setVisible(true);
	}
	
	private void inicializar() {
		panelDatosGenerales.setVisible(true);
		panelTablaDatosEspecificos.setVisible(false);
		panelTablaPasos.setVisible(false);
		etiquetaTitulo.setVisible(false);
		anteriorBoton.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cerrarBoton)) {
			dispose();
		} else if (e.getSource().equals(siguienteBoton)) {
			siguiente();
		} else if (e.getSource().equals(anteriorBoton)) {
			anterior();
		} else if (e.getSource().equals(modificarTramiteBoton)) {
			modificarTramite();
		}
	}

	/**
	 * 
	 */
	private void modificarTramite() {
		dispose();
		new DialogoModificarTramite(ventanaPrincipal);
	}
	
	/**
	 * 
	 */
	private void anterior() {
		if (regActual == 2) {
			anteriorBoton.setVisible(true);
			siguienteBoton.setVisible(true);
			panelDatosGenerales.setVisible(false);
			panelTablaPasos.setVisible(false);
			panelTablaDatosEspecificos.setVisible(true);
			etiquetaTitulo.setText("DATOS ESPECÍFICOS");
			etiquetaTitulo.setVisible(true);
			regActual--;
		} else if (regActual == 1) {
			siguienteBoton.setVisible(true);
			anteriorBoton.setVisible(false);
			panelTablaDatosEspecificos.setVisible(false);
			panelTablaPasos.setVisible(false);
			panelDatosGenerales.setVisible(true);
			etiquetaTitulo.setVisible(false);
			regActual--; 
		}
	}

	/**
	 * 
	 */
	private void siguiente() {
		if (regActual == 0) {
			anteriorBoton.setVisible(true);
			siguienteBoton.setVisible(true);
			panelDatosGenerales.setVisible(false);
			panelTablaPasos.setVisible(false);
			panelTablaDatosEspecificos.setVisible(true);
			etiquetaTitulo.setText("DATOS ESPECÍFICOS");
			etiquetaTitulo.setVisible(true);
			regActual++;				
		} else if (regActual == 1) {
			siguienteBoton.setVisible(false);
			anteriorBoton.setVisible(true);
			panelDatosGenerales.setVisible(false);
			panelTablaDatosEspecificos.setVisible(false);
			panelTablaPasos.setVisible(true);
			etiquetaTitulo.setText("PASOS");
			etiquetaTitulo.setVisible(true);
			regActual++; 
		}
	}
}
