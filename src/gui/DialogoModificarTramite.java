package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import dominio.Tramite;
import excepcion.BaseDatosException;
import excepcion.TramiteException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogoModificarTramite extends JDialog implements ActionListener {
	private static final long serialVersionUID = 5100254499659550133L;
	
	private JButton cancelarBoton;
	private JButton siguienteBoton;
	private JButton anteriorBoton;
	private JButton aceptarBoton;

	private PanelDatosGenerales panelDatosGenerales;
	private PanelDatosEspecificos panelDatosEspecificos;
	private PanelPasos panelPasos;

	private int regActual = 0;
	private Tramite tramiteAnterior;
	private Tramite tramite;
	private VentanaPrincipal ventanaPrincipal;
	
	public DialogoModificarTramite(VentanaPrincipal ventanaPrincipal) {	
		super(ventanaPrincipal, "Modificar Trámite", true);
		setLayout(null);
		this.ventanaPrincipal = ventanaPrincipal;
		tramiteAnterior = ventanaPrincipal.getLista().getTramite();
		
		anteriorBoton = new JButton("Anterior");
		anteriorBoton.setBounds(95, 610, 150, 40);
		anteriorBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/anterior.png")));
		add(anteriorBoton);
		anteriorBoton.addActionListener(this);

		siguienteBoton = new JButton("Siguiente");
		siguienteBoton.setBounds(435, 610, 150, 40);
		siguienteBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/siguiente.png")));
		add(siguienteBoton);
		siguienteBoton.addActionListener(this);

		aceptarBoton = new JButton("Aceptar");
		aceptarBoton.setBounds(435, 610, 150, 40);
		aceptarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		add(aceptarBoton);
		aceptarBoton.addActionListener(this);

		cancelarBoton = new JButton("Cancelar");
		cancelarBoton.setBounds(775, 610, 150, 40);
		cancelarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		add(cancelarBoton);
		cancelarBoton.addActionListener(this);

		tramite = new Tramite();
		panelDatosGenerales = new PanelDatosGenerales(tramite, tramiteAnterior);
		panelDatosGenerales.setBounds(10, 10, 990, 590);
		panelDatosEspecificos = new PanelDatosEspecificos(tramite, tramiteAnterior, !ventanaPrincipal.getLista().getListaTramitesEsp().isEmpty());
		panelDatosEspecificos.setBounds(10, 10, 990, 590);
		panelPasos = new PanelPasos(tramite, tramiteAnterior, !ventanaPrincipal.getLista().getListaTramitesEsp().isEmpty());
		panelPasos.setBounds(10, 10, 990, 590);
		add(panelPasos);
		add(panelDatosEspecificos);
		add(panelDatosGenerales);

		inicializar();
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cancelar();
			}
		});
		setSize(1020,700);
		setLocationRelativeTo(ventanaPrincipal);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * 
	 */
	private void inicializar() {
		panelDatosEspecificos.setVisible(false);
		panelPasos.setVisible(false);
		aceptarBoton.setVisible(false);
		anteriorBoton.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cancelarBoton)) {
			cancelar();
		} else if (e.getSource().equals(siguienteBoton)) {
			siguiente();
		} else if (e.getSource().equals(anteriorBoton)) {
			anterior();
		} else if (e.getSource().equals(aceptarBoton)) {
			aceptar();
		}
	}

	/**
	 * 
	 */
	private void aceptar() {
		try {
			JOptionPane.showMessageDialog(this, "El sistema se está preparando para modificar el trámite."
					+ "\nEsta operación puede durar algunos segundos "
					+ "\ndependiendo de la cantidad y tamaño de las plantillas seleccionadas", 
					"Modificar trámite", JOptionPane.INFORMATION_MESSAGE);
			ventanaPrincipal.getLista().setTramite(tramite);
			//modificar base de datos
			ventanaPrincipal.getLista().modificarTramite();
			JOptionPane.showMessageDialog(this, "Trámite modificado con éxito"
					+ "\n\nTrámite: " + tramite.getNombreTramite() 
					+ "\nDepartamento o área: " + tramite.getDepartamento() 
					+ "\n" + tramite.getDescripcion(), "Modificar trámite", 
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} catch (BaseDatosException e1) {
			ventanaPrincipal.getLista().setTramite(tramiteAnterior);
			JOptionPane.showMessageDialog(this, e1.getMessage(), e1.getTitulo(), JOptionPane.ERROR_MESSAGE);
		} catch (TramiteException e2) {
			ventanaPrincipal.getLista().setTramite(tramiteAnterior);
			JOptionPane.showMessageDialog(this, e2.getMessage(), e2.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 
	 */
	private void anterior() {
		if (regActual == 2) {
			aceptarBoton.setVisible(false);
			anteriorBoton.setVisible(true);
			siguienteBoton.setVisible(true);
			panelDatosGenerales.setVisible(false);
			panelPasos.setVisible(false);
			panelDatosEspecificos.setVisible(true);
			regActual--;
		} else if (regActual == 1) {
			siguienteBoton.setVisible(true);
			aceptarBoton.setVisible(false);
			anteriorBoton.setVisible(false);
			panelDatosEspecificos.setVisible(false);
			panelPasos.setVisible(false);
			panelDatosGenerales.setVisible(true);
			regActual--; 
		}
	}

	/**
	 * 
	 */
	private void siguiente() {
		if (regActual == 0) {
			if (panelDatosGenerales.validar()) {
				String[] estados = panelDatosGenerales.getEstados(); 
				aceptarBoton.setVisible(false);
				anteriorBoton.setVisible(true);
				panelDatosGenerales.setVisible(false);
				panelPasos.setVisible(false);
				panelDatosEspecificos.setEstados(estados);
				panelDatosEspecificos.setVisible(true);
				regActual++;				
			}
		} else if (regActual == 1) {
			String[] estados = panelDatosGenerales.getEstados(); 
			siguienteBoton.setVisible(false);
			aceptarBoton.setVisible(true);
			anteriorBoton.setVisible(true);
			panelDatosGenerales.setVisible(false);
			panelDatosEspecificos.setVisible(false);
			panelPasos.setEstados(estados);
			panelPasos.setVisible(true);
			regActual++; 
		}
	}

	/**
	 * 
	 */
	private void cancelar() {
		Object[] opciones = {"Sí", "No"};
		int respuesta = JOptionPane.showOptionDialog(this, 
				"Si cancela se perderán todos los datos escritos. ¿Está seguro que desea cancelar?", 
				"Cancelar registro", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
				null, opciones, opciones[1]);
		if (respuesta == 0) {
			dispose();
		}
	}
}
