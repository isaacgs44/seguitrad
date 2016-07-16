package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import lib.JIntegerTextField;
import dominio.Paso;
import dominio.Tramite;
import excepcion.TramiteException;

public class PanelPasos extends JPanel implements ActionListener, ItemListener {
	private static final long serialVersionUID = -3747753566803238367L;

	private JTextField nombrePaso;
	private JRadioButton radioBotonPropio;
	private JRadioButton radioBotonOtro;
	private JTextField otro;
	private JRadioButton radioBotonFechaLimiteSi;
	private JRadioButton radioBotonFechaLimiteNo;
	private JRadioButton radioBotonPlantillaSi;
	private JRadioButton radioBotonPlantillaNo;
	private JTextField plantilla;
	private JRadioButton radioBotonDocumentoSi;
	private JRadioButton radioBotonDocumentoNo;
	private JRadioButton radioBotonCambioEstadoSi;
	private JRadioButton radioBotonCambioEstadoNo;
	private JComboBox<String> cambioEstado;
	private JRadioButton radioBotonSecuenciaSi;
	private JRadioButton radioBotonSecuenciaNo;
	private JComboBox<Paso> secuencia;
	private JRadioButton radioBotonObligatorioSi;
	private JRadioButton radioBotonObligatorioNo;
	private JRadioButton radioBotonRepeticionSi;
	private JRadioButton radioBotonRepeticionNo;
	private JRadioButton radioBotonVeces;
	private JRadioButton radioBotonIndefinido;
	private JIntegerTextField repeticion;

	private JButton agregarBoton;
	private JButton arribaBoton;
	private JButton abajoBoton;
	private JButton quitarBoton;
	private JButton modificarBoton;
	private JButton seleccionarPlantillaBoton;

	private PanelTablaPasos panelTablaPasos;

	private Tramite tramite;
	private Tramite tramiteAnterior;
	private boolean hayTramitesEspecificos;

	private transient ArrayList<Paso> pasosRelacionados;
	
	public PanelPasos(Tramite tramite) {
		this (tramite, null, false);
	}
	
	public PanelPasos(Tramite tramite, Tramite tramiteAnterior, boolean hayTramitesEspecificos) {
		setLayout(null);
		this.tramite = tramite;
		this.tramiteAnterior = tramiteAnterior;
		this.hayTramitesEspecificos = hayTramitesEspecificos;

		JLabel etiquetaPasos = new JLabel("PASOS");
		etiquetaPasos.setBounds(435, 1, 150, 50);
		etiquetaPasos.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(etiquetaPasos);

		JLabel etiquetaPaso = new JLabel("Paso");
		etiquetaPaso.setBounds(40, 10, 150, 50);
		etiquetaPaso.setFont(new Font("Tahoma", Font.PLAIN, 20)); 
		add(etiquetaPaso);

		JLabel etiquetaNombre = new JLabel("Nombre: ");
		etiquetaNombre.setBounds(60, 40, 150, 50);
		add(etiquetaNombre);
		nombrePaso = new JTextField(20);
		nombrePaso.setBounds(210, 50, 200, 25);
		add(nombrePaso);

		JLabel etiquetaResponsable = new JLabel("Responsable: ");
		etiquetaResponsable.setBounds(60, 70, 150, 50);
		add(etiquetaResponsable);

		radioBotonPropio = new JRadioButton("Propio");
		radioBotonPropio.setBounds(210, 80, 90, 25);
		radioBotonPropio.addItemListener(this);
		add(radioBotonPropio);
		radioBotonOtro = new JRadioButton("Otro", false);
		radioBotonOtro.setBounds(315, 80, 50, 25);
		radioBotonOtro.addItemListener(this);
		add(radioBotonOtro);
		ButtonGroup grupo1 = new ButtonGroup();
		grupo1.add(radioBotonPropio);
		grupo1.add(radioBotonOtro);

		otro = new JTextField(20);
		otro.setBounds(400, 80, 200, 25);
		add(otro);

		JLabel etiquetaFechaLimite = new JLabel("Fecha Límite: ");
		etiquetaFechaLimite.setBounds(60, 100, 150, 50);
		add(etiquetaFechaLimite);

		radioBotonFechaLimiteSi = new JRadioButton("Sí");
		radioBotonFechaLimiteSi.setBounds(210, 100, 50, 55);
		add(radioBotonFechaLimiteSi);
		radioBotonFechaLimiteNo = new JRadioButton("No");
		radioBotonFechaLimiteNo.setBounds(315, 100,50, 55);
		add(radioBotonFechaLimiteNo);
		ButtonGroup grupo2 = new ButtonGroup();
		grupo2.add(radioBotonFechaLimiteSi);
		grupo2.add(radioBotonFechaLimiteNo);

		JLabel etiquetaPlantilla = new JLabel("Plantilla: ");
		etiquetaPlantilla.setBounds(60, 140, 150, 50);
		add(etiquetaPlantilla);

		radioBotonPlantillaSi = new JRadioButton("Sí");
		radioBotonPlantillaSi.setBounds(210, 155, 50, 25);
		radioBotonPlantillaSi.addItemListener(this);
		add(radioBotonPlantillaSi);

		plantilla = new JTextField(20);
		plantilla.setEditable(false);
		plantilla.setBounds(265, 155, 200, 25);
		add(plantilla);
		
		seleccionarPlantillaBoton = new JButton("");
		seleccionarPlantillaBoton.setBounds(480, 143, 40, 35);
		seleccionarPlantillaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/folder.png")));
		seleccionarPlantillaBoton.addActionListener(this);
		add(seleccionarPlantillaBoton);
		
		radioBotonPlantillaNo = new JRadioButton("No");
		radioBotonPlantillaNo.setBounds(580, 155, 50, 25);
		radioBotonPlantillaNo.addItemListener(this);
		add(radioBotonPlantillaNo);
		ButtonGroup grupo3 = new ButtonGroup();
		grupo3.add(radioBotonPlantillaSi);
		grupo3.add(radioBotonPlantillaNo);

		JLabel etiquetaDocumentoEspecifico = new JLabel("Documento específico: ");
		etiquetaDocumentoEspecifico.setBounds(60, 185, 150, 50);
		add(etiquetaDocumentoEspecifico);

		radioBotonDocumentoSi = new JRadioButton("Sí");
		radioBotonDocumentoSi.setBounds(210, 185, 50, 55);
		add(radioBotonDocumentoSi);
		radioBotonDocumentoNo = new JRadioButton("No");
		radioBotonDocumentoNo.setBounds(315, 185, 50, 55);
		add(radioBotonDocumentoNo);
		ButtonGroup grupo4 = new ButtonGroup();
		grupo4.add(radioBotonDocumentoSi);
		grupo4.add(radioBotonDocumentoNo);

		JLabel etiquetaCambioEstado = new JLabel("Cambio de estado: ");
		etiquetaCambioEstado.setBounds(60, 225, 150, 50);
		add(etiquetaCambioEstado);

		radioBotonCambioEstadoSi = new JRadioButton("Sí");
		radioBotonCambioEstadoSi.setBounds(210, 240, 50, 25);
		radioBotonCambioEstadoSi.addItemListener(this);
		add(radioBotonCambioEstadoSi);

		cambioEstado = new JComboBox<String>();
		cambioEstado.setBounds(265, 240, 200, 25);
		add(cambioEstado);

		radioBotonCambioEstadoNo = new JRadioButton("No");
		radioBotonCambioEstadoNo.setBounds(480, 240, 50, 25);
		radioBotonCambioEstadoNo.addItemListener(this);
		add(radioBotonCambioEstadoNo);
		ButtonGroup grupo5 = new ButtonGroup();
		grupo5.add(radioBotonCambioEstadoSi);
		grupo5.add(radioBotonCambioEstadoNo);

		JLabel etiquetaSecuencia = new JLabel("Secuencia: ");
		etiquetaSecuencia.setBounds(60, 260, 150, 50);
		add(etiquetaSecuencia);

		radioBotonSecuenciaSi = new JRadioButton("Sí");
		radioBotonSecuenciaSi.setBounds(210, 275, 50, 25);
		radioBotonSecuenciaSi.addItemListener(this);
		add(radioBotonSecuenciaSi);

		secuencia = new JComboBox<Paso>();
		secuencia.setBounds(265, 275, 200, 25);
		add(secuencia);

		radioBotonSecuenciaNo = new JRadioButton("No");
		radioBotonSecuenciaNo.setBounds(480, 275, 50, 25);
		radioBotonSecuenciaNo.addItemListener(this);
		add(radioBotonSecuenciaNo);
		ButtonGroup grupo6 = new ButtonGroup();
		grupo6.add(radioBotonSecuenciaSi);
		grupo6.add(radioBotonSecuenciaNo);

		JLabel etiquetaObligatorio = new JLabel("Obligatorio: ");
		etiquetaObligatorio.setBounds(60, 300, 150, 50);
		add(etiquetaObligatorio);

		radioBotonObligatorioSi = new JRadioButton("Sí");
		radioBotonObligatorioSi.setBounds(210, 310, 50, 25);
		add(radioBotonObligatorioSi);
		radioBotonObligatorioNo = new JRadioButton("No");
		radioBotonObligatorioNo.setBounds(315, 310, 50, 25);
		add(radioBotonObligatorioNo);
		ButtonGroup grupo7 = new ButtonGroup();
		grupo7.add(radioBotonObligatorioSi);
		grupo7.add(radioBotonObligatorioNo);

		JLabel etiquetaRepeticion = new JLabel("Repetición: ");
		etiquetaRepeticion.setBounds(60, 335, 150, 50);
		add(etiquetaRepeticion);

		radioBotonRepeticionSi = new JRadioButton("Sí");
		radioBotonRepeticionSi.addItemListener(this);
		radioBotonRepeticionSi.setBounds(210, 350, 50, 25);
		add(radioBotonRepeticionSi);

		radioBotonVeces = new JRadioButton("");
		radioBotonVeces.setBounds(315, 350, 20, 25);
		radioBotonVeces.addItemListener(this);
		add(radioBotonVeces);

		repeticion = new JIntegerTextField(2);
		repeticion.setBounds(345, 350, 50, 25);
		add(repeticion);

		JLabel etiquetaVeces = new JLabel("Veces ");
		etiquetaVeces.setBounds(400, 335, 150, 50);
		add(etiquetaVeces);

		radioBotonIndefinido = new JRadioButton("Indefinida");
		radioBotonIndefinido.setBounds(480, 350, 100, 25);
		radioBotonIndefinido.addItemListener(this);
		add(radioBotonIndefinido);

		radioBotonRepeticionNo = new JRadioButton("No");
		radioBotonRepeticionNo.addItemListener(this);
		radioBotonRepeticionNo.setBounds(580, 350, 50, 25);
		add(radioBotonRepeticionNo);

		ButtonGroup grupo8 = new ButtonGroup();
		grupo8.add(radioBotonRepeticionSi);
		grupo8.add(radioBotonRepeticionNo);
		ButtonGroup grupo9 = new ButtonGroup();
		grupo9.add(radioBotonIndefinido);
		grupo9.add(radioBotonVeces);

		agregarBoton = new JButton("Agregar");
		agregarBoton.setBounds(780, 340, 130, 35);
		agregarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/agregar.png")));
		agregarBoton.addActionListener(this);
		add(agregarBoton);

		JLabel etiquetaPaso2 = new JLabel("Pasos");
		etiquetaPaso2.setBounds(435, 380, 150, 50);
		etiquetaPaso2.setFont(new Font("Tahoma", Font.PLAIN, 20)); 
		add(etiquetaPaso2);

		arribaBoton = new JButton("");
		arribaBoton.setBounds(930, 430, 50, 30);
		arribaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/arriba.png")));
		arribaBoton.addActionListener(this);
		add(arribaBoton);

		abajoBoton = new JButton("");
		abajoBoton.setBounds(930, 470, 50, 30);
		abajoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/abajo.png")));
		abajoBoton.addActionListener(this);
		add(abajoBoton);

		quitarBoton = new JButton("");
		quitarBoton.setBounds(930, 510, 50, 30);
		quitarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/eliminar.png")));
		quitarBoton.addActionListener(this);
		add(quitarBoton);
	
		modificarBoton = new JButton("");
		modificarBoton.setBounds(930, 550, 50, 30);
		modificarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
		modificarBoton.addActionListener(this);
		add(modificarBoton); 

		panelTablaPasos = new PanelTablaPasos();
		panelTablaPasos.setBounds(10, 430, 900, 150);
		add(panelTablaPasos);

		inicializar();
		
		setVisible(true);
	}

	private void inicializar() {
		radioBotonPropio.setSelected(true);
		otro.setEnabled(false);
		radioBotonFechaLimiteNo.setSelected(true);
		radioBotonPlantillaNo.setSelected(true);
		plantilla.setEnabled(false);
		seleccionarPlantillaBoton.setEnabled(false);
		radioBotonDocumentoSi.setSelected(true);
		radioBotonCambioEstadoNo.setSelected(true);
		cambioEstado.setEnabled(false);
		radioBotonSecuenciaNo.setSelected(true);
		secuencia.setEnabled(false);
		radioBotonObligatorioSi.setSelected(true);
		radioBotonRepeticionNo.setSelected(true);		
		radioBotonVeces.setEnabled(false);
		repeticion.setEnabled(false);
		radioBotonIndefinido.setEnabled(false);
		if (tramiteAnterior != null) {
			// mostrar y transferir datos ya guardados
			for (Paso p: tramiteAnterior.getPasos()) {
				tramite.getPasos().add(p);
				secuencia.addItem(p);
			}
			panelTablaPasos.modificarTabla(tramite.getPasos());
			if (hayTramitesEspecificos) {
				// Sólo se permiten pasos opcionales
				radioBotonObligatorioNo.setSelected(true);
				radioBotonObligatorioSi.setEnabled(false);
				radioBotonObligatorioNo.setEnabled(false);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getSource().equals(radioBotonPropio)) {
			otro.setEnabled(false);
		} else if (arg0.getSource().equals(radioBotonOtro)) {
			otro.setEnabled(true);
		} else if (arg0.getSource().equals(radioBotonPlantillaSi)) {
			seleccionarPlantillaBoton.setEnabled(true); 
			plantilla.setEnabled(true); 
		} else if (arg0.getSource().equals(radioBotonPlantillaNo)) {
			seleccionarPlantillaBoton.setEnabled(false); 
			plantilla.setEnabled(false); 
		} else if (arg0.getSource().equals(radioBotonCambioEstadoSi)) {
			cambioEstado.setEnabled(true); 
		} else if (arg0.getSource().equals(radioBotonCambioEstadoNo)) {
			cambioEstado.setEnabled(false); 
		} else if (arg0.getSource().equals(radioBotonSecuenciaSi)) {
			secuencia.setEnabled(true); 
		} else if (arg0.getSource().equals(radioBotonSecuenciaNo)) {
			secuencia.setEnabled(false); 
		} else if (arg0.getSource().equals(radioBotonRepeticionNo)) {
			radioBotonVeces.setEnabled(false);
			repeticion.setEnabled(false);
			radioBotonIndefinido.setEnabled(false);
		} else if (arg0.getSource().equals(radioBotonRepeticionSi)) {
			radioBotonVeces.setEnabled(true);
			radioBotonIndefinido.setEnabled(true);
			radioBotonVeces.setSelected(true);
			repeticion.setEnabled(true);
		} else if (arg0.getSource().equals(radioBotonVeces)) {
			repeticion.setEnabled(true);
		} else if (arg0.getSource().equals(radioBotonIndefinido)) {
			repeticion.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(agregarBoton)) {
			agregar();
		} else if (e.getSource().equals(arribaBoton)) {
			moverArriba();
		} else if (e.getSource().equals(abajoBoton)) {
			moverAbajo();
		} else if (e.getSource().equals(quitarBoton)) {
			quitar();
		} else if (e.getSource().equals(modificarBoton)) {
			modificarPaso();
		} else if (e.getSource().equals(seleccionarPlantillaBoton)) {
			seleccionar();
		}
	}

	private void agregar() {
		Paso paso = new Paso();
		try {
			// tomar los datos escritos
			paso.setNombrePaso(nombrePaso.getText());
			if (radioBotonOtro.isSelected()) {
				paso.setResponsable(otro.getText());
			} else {
				paso.setResponsable("Propio");
			}			
			if (radioBotonPlantillaSi.isSelected()) {
				paso.setPlantilla(plantilla.getText());
				paso.setConPlantilla(true);
			} else {
				paso.setPlantilla("");
				paso.setConPlantilla(false);
			}
			paso.setConDocumento(radioBotonDocumentoSi.isSelected());
			if (radioBotonCambioEstadoSi.isSelected()) {
				paso.setEstado((String) cambioEstado.getSelectedItem());
				paso.setConCambioEstado(true);
			} else {
				paso.setEstado("");
				paso.setConCambioEstado(false);
			}
			if(radioBotonSecuenciaSi.isSelected()){
				paso.setSecuencia((Paso) secuencia.getSelectedItem());
			} else {
				paso.setSecuencia(null);
			}
			paso.setObligatorio(radioBotonObligatorioSi.isSelected());
			if (radioBotonRepeticionSi.isSelected()) {
				if (radioBotonIndefinido.isSelected()) {
					paso.setRepeticion(0);
				} else {
					paso.setRepeticion(repeticion.getValue());
				}
				paso.setConRepeticion(true);
			} else {
				paso.setRepeticion(1);
				paso.setConRepeticion(false);
			}
			paso.setConFechaLimite(radioBotonFechaLimiteSi.isSelected());
			// agregar el paso en la lista y tabla
			tramite.agregarPaso(paso);
			panelTablaPasos.agregarFila(paso);
			secuencia.addItem(paso);
			// si viene de modificar un paso con secuencia, reestablecer la secuencia
			if (pasosRelacionados != null) {
				for (Paso p : pasosRelacionados) {
					p.setSecuencia(paso);
				}
				pasosRelacionados = null;
				panelTablaPasos.modificarTabla(tramite.getPasos());
			}
			limpiar();
		} catch (TramiteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private void limpiar() {
		nombrePaso.setText("");
		otro.setText("");
		plantilla.setText("");
		repeticion.setValue(2);
	}

	private void moverArriba() {
		// tomar la posición seleccionada
		int posicionInicial = panelTablaPasos.obtenerFilaSeleccionada();
		// validar que haya algo seleccionado
		if (posicionInicial < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de pasos", 
					"Error al quitar el paso", JOptionPane.ERROR_MESSAGE);
		} else {
			// mover el paso una posición arriba
			int posicionFinal = tramite.moverPaso(posicionInicial, -1);
			panelTablaPasos.moverFila(posicionInicial, posicionFinal);
		}
	}

	private void moverAbajo() {
		// tomar la posición seleccionada
		int posicionInicial = panelTablaPasos.obtenerFilaSeleccionada();
		// validar que haya algo seleccionado
		if (posicionInicial < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de pasos", 
					"Error al quitar el paso", JOptionPane.ERROR_MESSAGE);
		} else {
			// mover el paso una posición abajo
			int posicionFinal = tramite.moverPaso(posicionInicial, +1);
			panelTablaPasos.moverFila(posicionInicial, posicionFinal);
		}
	}

	private void quitar() {
		// tomar la posición seleccionada
		int posicion = panelTablaPasos.obtenerFilaSeleccionada();	
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de pasos", 
					"Error al quitar paso", JOptionPane.ERROR_MESSAGE);
		} else {
			// quitar el paso
			try {
				tramite.quitarPaso(posicion, hayTramitesEspecificos);
				panelTablaPasos.quitarFila(posicion);
				secuencia.removeItemAt(posicion);
			} catch (TramiteException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void modificarPaso() {
		// tomar la posición seleccionada
		int posicion = panelTablaPasos.obtenerFilaSeleccionada();	
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de pasos", 
					"Error al modificar paso", JOptionPane.ERROR_MESSAGE);
		} else {
			int opcion = JOptionPane.showConfirmDialog(this, "Al modificar el paso se borrará de la lista,\ndebe recordar agregarlo de nuevo o se perderán los datos.\n¿Desea continuar?", 
					"Modificar paso", JOptionPane.YES_NO_OPTION);
			if (opcion != JOptionPane.YES_OPTION) {
				return;
			}
			// mostrar los datos del paso
			Paso paso = tramite.getPasos().get(posicion);
			// borrar el paso
			try {
				tramite.quitarPaso(posicion, hayTramitesEspecificos);
				panelTablaPasos.quitarFila(posicion);
				secuencia.removeItem(paso);
			} catch (TramiteException e) {
				// el paso está como secuencia de otro paso
				int op = JOptionPane.showConfirmDialog(this, "Existen pasos que tienen secuencia con el paso seleccionado.\nDebe agregar nuevamente el paso para que no se pierda la secuencia.\n¿Desea continuar?", 
						"Modificar paso", JOptionPane.YES_NO_OPTION);
				if (op != JOptionPane.YES_OPTION) {
					return;
				}
				// borrar las secuencias con el paso y almacenar temporalmente los pasos
				pasosRelacionados = new ArrayList<Paso>();
				for (Paso p : tramite.getPasos()) {
					if (p.getSecuencia() != null && p.getSecuencia().equals(paso)) {
						p.setSecuencia(null);
						pasosRelacionados.add(p);
					}
				}
				try {
					tramite.quitarPaso(posicion, hayTramitesEspecificos);
					panelTablaPasos.quitarFila(posicion);
					secuencia.removeItem(paso);
				} catch (TramiteException e1) {
					System.out.print("entra");
					//En teoría no debe haber nunca una exception
				}
			}
			// mostrar los datos del paso
			nombrePaso.setText(paso.getNombrePaso());
			if (paso.getResponsable().compareToIgnoreCase("Propio") == 0) {
				radioBotonPropio.setSelected(true); 
			} else {
				radioBotonOtro.setSelected(true);
				otro.setText(paso.getResponsable());
			}
			if (paso.isConPlantilla()) {
				radioBotonPlantillaSi.setSelected(true);
				plantilla.setText(paso.getPlantilla());
			} else {
				radioBotonPlantillaNo.setSelected(true);
				plantilla.setText("");
			}
			if (paso.isConDocumento()) {
				radioBotonDocumentoSi.setSelected(true);
			} else {
				radioBotonDocumentoNo.setSelected(true);
			}
			if (paso.isConCambioEstado()) {
				radioBotonCambioEstadoSi.setSelected(true);
				cambioEstado.setSelectedItem(paso.getEstado());
			} else {
				radioBotonCambioEstadoNo.setSelected(true);
			}
			if (paso.getSecuencia() != null) {
				radioBotonSecuenciaSi.setSelected(true);
				secuencia.setSelectedItem(paso.getSecuencia());
			} else {
				radioBotonSecuenciaNo.setSelected(true);
			}
			if (paso.isObligatorio()) {
				radioBotonObligatorioSi.setSelected(true); 
			} else {
				radioBotonObligatorioNo.setSelected(true);
			}
			if (paso.isConRepeticion()) {
				radioBotonRepeticionSi.setSelected(true);
				if (paso.getRepeticion() == 0) {
					radioBotonIndefinido.setSelected(true);
				} else {
					radioBotonVeces.setSelected(true);
					repeticion.setValue(paso.getRepeticion());
				}
			} else {
				radioBotonRepeticionNo.setSelected(true);
				repeticion.setValue(1);
			}
			if (paso.isConFechaLimite()) {
				radioBotonFechaLimiteSi.setSelected(true);
			} else {
				radioBotonFechaLimiteNo.setSelected(true);
			}
		}
	}
	
	private void seleccionar() {
		FileDialog fd = new FileDialog(new Frame(), "Seleccionar plantilla ", FileDialog.LOAD); 
		if (plantilla.getText().isEmpty()) {
			fd.setDirectory(System.getProperty("user.dir"));
			fd.setFile("*.pdf; *.doc; *.docx");
		} else {
			fd.setFile(plantilla.getText());
		}
		fd.setVisible(true);
		if (fd.getFile() == null) {
			plantilla.setText("");
		} else { 
			plantilla.setText(fd.getDirectory() + fd.getFile());
		}		
	}
	
	public void setEstados(String[] estados) {
		// insertar los estados en el combo
		cambioEstado.removeAllItems();
		for (String estado: estados) {
			cambioEstado.addItem(estado);
		}
	}
	public void ocultarComponentes() {
		modificarBoton.setVisible(false);
		
	}

}
