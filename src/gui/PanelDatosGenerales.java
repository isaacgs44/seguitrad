package gui;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import dominio.Campo;
import dominio.Paso;
import dominio.Tramite;
import excepcion.TramiteException;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author mily
 *
 */
public class PanelDatosGenerales extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5528794973425115085L;
	
	private JTextField nombreTramite;
	private JLabel etiquetaEstado;
	private JTextField estado;
	private JTextField deptoArea;
	private JTextArea textDescripcion;
	private JTextField nombreArchivo;
	private JList<String> listaEstados;
	private DefaultListModel<String> modeloListaEstados;
	private JSpinner spinnerDias;
	
	private JButton agregarBoton;
	private JButton arribaBoton;
	private JButton abajoBoton;
	private JButton quitarBoton;
	
	private Tramite tramite;
	private Tramite tramiteAnterior;

	public PanelDatosGenerales(Tramite tramite) {
		this (tramite, null);
	}
	
	public PanelDatosGenerales(Tramite tramite, Tramite tramiteAnterior) {
		setLayout(null);
		this.tramite = tramite;
		this.tramiteAnterior = tramiteAnterior;
		
		JLabel etiquetaDatosGenerales = new JLabel("DATOS GENERALES"); 
		etiquetaDatosGenerales.setBounds(435, 1, 250, 50);
		etiquetaDatosGenerales.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(etiquetaDatosGenerales);

		JLabel etiquetaObligatorioNombreTramite = new JLabel ("*"); 
		etiquetaObligatorioNombreTramite.setFont(new Font("Tahoma", Font.PLAIN, 20));
		etiquetaObligatorioNombreTramite.setForeground(Color.red);
		etiquetaObligatorioNombreTramite.setBounds(45, 42,20, 50);
		add(etiquetaObligatorioNombreTramite);
		JLabel etiquetaNombreTramite = new JLabel("Nombre del trámite: ");
		etiquetaNombreTramite.setBounds(60, 40, 150, 50);
		add(etiquetaNombreTramite);
		nombreTramite = new JTextField(20);
		nombreTramite.setBounds(210, 50, 200, 25);
		add(nombreTramite);

		etiquetaEstado = new JLabel("Estado: ");
		etiquetaEstado.setBounds(465, 40, 90, 50);
		add(etiquetaEstado);
		estado = new JTextField(20);
		estado.setBounds(555, 50, 200, 25);
		add(estado);

		agregarBoton = new JButton("Agregar");
		agregarBoton.setBounds(810, 45, 150, 35);
		agregarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/agregar.png")));
		agregarBoton.addActionListener(this);
		add(agregarBoton);
		
		JLabel etiquetaObligatorioDepartamento = new JLabel ("*"); 
		etiquetaObligatorioDepartamento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		etiquetaObligatorioDepartamento.setForeground(Color.red);
		etiquetaObligatorioDepartamento.setBounds(45,120,20, 50);
		add(etiquetaObligatorioDepartamento);
		JLabel etiquetaDeptoArea = new JLabel("Departamento o área: ");
		etiquetaDeptoArea.setBounds(60, 120, 150, 50);
		add(etiquetaDeptoArea);
		deptoArea = new JTextField(20);
		deptoArea.setBounds(210, 130, 200, 25);
		add(deptoArea);
		
		JLabel etiquetaObligatorio = new JLabel ("*"); 
		etiquetaObligatorio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		etiquetaObligatorio.setForeground(Color.red);
		etiquetaObligatorio.setBounds(450,130,20, 50);
		add(etiquetaObligatorio);
		JLabel etiquetaEstados = new JLabel("Estados: ");
		etiquetaEstados.setBounds(465, 130, 150, 50);
		add(etiquetaEstados);
		modeloListaEstados = new DefaultListModel<String>();
		listaEstados = new JList<String>(modeloListaEstados);
		listaEstados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(listaEstados);
		JScrollPane scrollLista = new JScrollPane();
		scrollLista.setBounds(465, 200, 290, 160);
		scrollLista.setViewportView(listaEstados);
		add(scrollLista);
		JLabel etiquetaAvisoEstados = new JLabel ("Mínimo debe de haber dos estados"); 
		etiquetaAvisoEstados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		etiquetaAvisoEstados.setForeground(Color.red);
		etiquetaAvisoEstados.setBounds(465,350,300, 50);
		add(etiquetaAvisoEstados);

		JLabel etiquetaDescripcion = new JLabel("Descripción: ");
		etiquetaDescripcion.setBounds(60, 200, 150, 50);
		add(etiquetaDescripcion);
		textDescripcion = new JTextArea();
		textDescripcion.setLineWrap(true);
		JScrollPane scrollDescripcion = new JScrollPane(textDescripcion);
		scrollDescripcion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDescripcion.setBounds(210, 200, 200, 100);
		add(scrollDescripcion);
		
		JLabel etiquetaObligatorioNombreArchivo = new JLabel ("*"); 
		etiquetaObligatorioNombreArchivo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		etiquetaObligatorioNombreArchivo.setForeground(Color.red);
		etiquetaObligatorioNombreArchivo.setBounds(45,330,20, 50);
		add(etiquetaObligatorioNombreArchivo);
		JLabel etiquetaNombreArchivo = new JLabel("Nombre del archivo: ");
		etiquetaNombreArchivo.setBounds(60, 330, 150, 50);
		add(etiquetaNombreArchivo);
		nombreArchivo = new JTextField(20);
		nombreArchivo.setBounds(210, 345, 200, 25);
		add(nombreArchivo);
		
		JLabel etiquetaObligatorioDiasAlerta = new JLabel ("*"); 
		etiquetaObligatorioDiasAlerta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		etiquetaObligatorioDiasAlerta.setForeground(Color.red);
		etiquetaObligatorioDiasAlerta.setBounds(45,400,20, 50);
		add(etiquetaObligatorioDiasAlerta);
		JLabel etiquetaDias = new JLabel("# días para alerta: ");
		etiquetaDias.setBounds(60, 400, 200, 50);
		add(etiquetaDias);
		spinnerDias = new JSpinner();
		spinnerDias.setBounds(210, 415, 45, 25);
		add(spinnerDias);
		
		JLabel etiquetaAvisoDiasAlerta = new JLabel ("Los campos marcados con * son obligatorios"); 
		etiquetaAvisoDiasAlerta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		etiquetaAvisoDiasAlerta.setForeground(Color.red);
		etiquetaAvisoDiasAlerta.setBounds(60,450,300, 50);
		add(etiquetaAvisoDiasAlerta);

		arribaBoton = new JButton("");
		arribaBoton.setBounds(810, 220, 50, 30);
		arribaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/arriba.png")));
		arribaBoton.addActionListener(this);
		add(arribaBoton);

		abajoBoton = new JButton("");
		abajoBoton.setBounds(810, 260, 50, 30);
		abajoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/abajo.png")));
		abajoBoton.addActionListener(this);
		add(abajoBoton);

		quitarBoton = new JButton("");
		quitarBoton.setBounds(810,300, 50, 30);
		quitarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/eliminar.png")));
		quitarBoton.addActionListener(this);
		add(quitarBoton);
		
		inicializar();

		setVisible(true);
	}
	
	private void inicializar() {
		spinnerDias.setModel(new SpinnerNumberModel(1, 1, 90, 1));
		if (tramiteAnterior == null) {
			// insertar estados predefinidos
			modeloListaEstados.addElement("Aceptado");
			modeloListaEstados.addElement("Cancelado");
			modeloListaEstados.addElement("Terminado");
			modeloListaEstados.addElement("En trámite");
			modeloListaEstados.addElement("En proceso");
		} else {
			// mostrar datos ya guardados
			nombreTramite.setText(tramiteAnterior.getNombreTramite());
			deptoArea.setText(tramiteAnterior.getDepartamento());
			textDescripcion.setText(tramiteAnterior.getDescripcion());
			nombreArchivo.setText(tramiteAnterior.getNombreArchivo());
			spinnerDias.setValue(tramiteAnterior.getDiasAlerta());
			Campo c = tramiteAnterior.buscarCampo("Estado");
			for (String edo: c.getValorDefectoOpciones()) {
				modeloListaEstados.addElement(edo);
			}
			// No se permitirá cambiar el nombre del archivo
			nombreArchivo.setEditable(false);
		}
	}
	
	public void ocultarComponentes() {
		abajoBoton.setVisible(false);
		arribaBoton.setVisible(false);
		quitarBoton.setVisible(false);
		etiquetaEstado.setVisible(false);
		estado.setVisible(false);
		agregarBoton.setVisible(false);
		nombreTramite.setEditable(false);
		deptoArea.setEditable(false);
		textDescripcion.setEditable(false);
		nombreArchivo.setEditable(false);
		spinnerDias.setEnabled(false);
		listaEstados.setEnabled(false);
	}

	public String[] getEstados() {
		String[] estados = new String[modeloListaEstados.size()];
		// obtener los estados de la lista y guardarlos en el arreglo
		for (int i = 0; i < estados.length; i++) {
			estados[i] = modeloListaEstados.get(i);
		}
		return estados;
	}
	
	public boolean validar() {
		try {
			tramite.setNombreTramite(nombreTramite.getText());
			tramite.setDepartamento(deptoArea.getText());
			tramite.setDescripcion(textDescripcion.getText());
			tramite.setNombreArchivo(nombreArchivo.getText());
			nombreArchivo.setText(tramite.getNombreArchivo());
			tramite.setDiasAlerta((int) spinnerDias.getValue());
			if (modeloListaEstados.getSize() >= 2) {
				return true;
			} else {
				return false;
			}
		} catch (TramiteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
			return false;
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
		}
	}

	private void agregar() {
		// tomar el estado escrito, ponerlo en minúsculas con mayúscula inicial
		String cadena = estado.getText().trim();
		if (!cadena.isEmpty()) {
			String inicial = cadena.substring(0, 1);
			cadena = cadena.substring(1);
			cadena = inicial.toUpperCase() + cadena.toLowerCase();
		}
		// validar que no esté vacío
		if (cadena.compareTo("") == 0) {
			JOptionPane.showMessageDialog(null, "estado vacío");
			// validar que no tenga el carácter de separación
		} else if (cadena.indexOf('/') != -1) {
			JOptionPane.showMessageDialog(null, "El sistema no permite el uso del carácter /, "
					+ "\nya que generaría problemas internos. Favor de reemplazarlo.");
			// validar que no esté ya en la lista
		} else if (modeloListaEstados.contains(cadena)){
			JOptionPane.showMessageDialog(null, "El estado ya existe ");
		} else {
			// agregar en la lista
			modeloListaEstados.addElement(cadena);
			estado.setText("");
		}
	}

	private void moverArriba() {
		int posicion;
		String cadenaEstado;
		// tomar la posición seleccionada
		posicion = listaEstados.getSelectedIndex();
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(null, " Debe seleccionar un elemento de la lista  ");
		} else if (posicion == 0) {
			// si es el primero, moverlo al final
			cadenaEstado = modeloListaEstados.getElementAt(posicion);
			modeloListaEstados.remove(posicion);
			modeloListaEstados.addElement(cadenaEstado);
			listaEstados.setSelectedIndex(modeloListaEstados.getSize() - 1);
		} else {
			// mover el estado una posición arriba
			cadenaEstado = modeloListaEstados.getElementAt(posicion);
			modeloListaEstados.remove(posicion);
			modeloListaEstados.insertElementAt(cadenaEstado, posicion - 1);
			listaEstados.setSelectedIndex(posicion - 1);
		}
	}

	private void moverAbajo() {
		int posicion;
		String cadenaEstado;
		// tomar la posición seleccionada
		posicion = listaEstados.getSelectedIndex();
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(null, " Debe seleccionar un elemento de la lista  ");
		} else if (posicion == (modeloListaEstados.getSize() - 1)) {
			// si es el último, moverlo al inicio
			cadenaEstado = modeloListaEstados.getElementAt(posicion);
			 modeloListaEstados.remove(posicion);
			 modeloListaEstados.insertElementAt(cadenaEstado, 0);
			 listaEstados.setSelectedIndex(0);
		} else {
			// mover el estado una posición abajo
			cadenaEstado = modeloListaEstados.getElementAt(posicion);
	        modeloListaEstados.remove(posicion);
	        modeloListaEstados.insertElementAt(cadenaEstado, posicion + 1);
	        listaEstados.setSelectedIndex(posicion + 1);
		}
	}

	private void quitar() {
		int posicion;
		// tomar la posición seleccionada
		posicion = listaEstados.getSelectedIndex();
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista");
			// validar que no queden menos de dos estados
		} else if (modeloListaEstados.getSize() > 2) {
			// validar que no esté siendo usado el estado en un paso
			String edo = modeloListaEstados.get(posicion);
			boolean esUsado = false;
			for (Paso p: tramite.getPasos()) {
				if (p.getEstado().compareToIgnoreCase(edo) == 0) {
					esUsado = true;
					break;
				}
			}
			if (!esUsado) {
				// quitar el estado
				modeloListaEstados.remove(posicion);
			} else {
				JOptionPane.showMessageDialog(null, "El estado está siendo usado en un paso");
			}
		} else {
			JOptionPane.showMessageDialog(null, "No se deben tener menos de dos estados");
		}
	}
	
}
