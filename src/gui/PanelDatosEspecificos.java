package gui;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import lib.JIntegerTextField;
import dominio.Campo;
import dominio.Tipo;
import dominio.Tramite;
import excepcion.TramiteException;

public class PanelDatosEspecificos extends JPanel implements ActionListener {
	private static final long serialVersionUID = -3353935489561706446L;
	//hola
	private JTextField campoNombre;
	private JComboBox<Tipo> comboTipo;
	private JLabel etiquetaValorDefecto;
	private JTextField valorDefectoTexto;
	private JIntegerTextField valorDefectoNumero;
	private JRadioButton valorDefectoFechaSi;
	private JRadioButton valorDefectoFechaNo;
	private JTextField valorDefectoTextoList;
	private JList<String> valorDefectoOpciones;
	private DefaultListModel<String> modeloListaOpciones;
	private JScrollPane valorDefectoOpcionesScrollList;
	
	private JRadioButton radioBotonSi;
	private JRadioButton radioBotonNo;
	
	private JButton añadirOpcionesBoton;
	private JButton arribaOpcionesBoton;
	private JButton abajoOpcionesBoton;
	private JButton quitarOpcionesBoton;
		
	private JButton arribaCampoBoton;
	private JButton abajoCampoBoton;
	private JButton quitarCampoBoton;		
	private JButton agregarCampoBoton;
	private JButton modificarCampoBoton;
		
	private PanelTablaDatosEspecificos panelTablaDatosEspecificos;
	private Tramite tramite;
	private Tramite tramiteAnterior;
	private boolean hayTramitesEspecificos;
	
	public PanelDatosEspecificos(Tramite tramite) {
		this (tramite, null, false);
	}
	
	public PanelDatosEspecificos(Tramite tramite, Tramite tramiteAnterior, boolean hayTramitesEspecificos) {
		setLayout(null);
		this.tramite = tramite;
		this.tramiteAnterior = tramiteAnterior;
		this.hayTramitesEspecificos = hayTramitesEspecificos;

		JLabel etiquetaDatosEspecificos = new JLabel("DATOS ESPECÍFICOS");
		etiquetaDatosEspecificos.setBounds(435, 1, 250, 50);
		etiquetaDatosEspecificos.setFont(new Font("Tahoma", Font.PLAIN, 24)); 
		add(etiquetaDatosEspecificos);
		
		JLabel etiquetaCompo= new JLabel("Campo");
		etiquetaCompo.setBounds(40,10,150,50);
		etiquetaCompo.setFont(new Font("Tahoma", Font.PLAIN, 20)); 
		add(etiquetaCompo);
			
		JLabel etiquetaCampoNombre = new JLabel("Nombre: ");
		etiquetaCampoNombre.setBounds(60, 40, 150, 50);
		add(etiquetaCampoNombre);
		campoNombre = new JTextField(20);
		campoNombre.setBounds(210, 50, 200, 25);
		add(campoNombre);

		JLabel etiquetaTipo = new JLabel("Tipo: ");
		etiquetaTipo.setBounds(60, 110, 150, 50);
		add(etiquetaTipo);
		comboTipo = new JComboBox<Tipo>(Tipo.values());
		comboTipo.setBounds(210, 120, 200, 25);
		add(comboTipo);

		etiquetaValorDefecto = new JLabel("Valor por defecto: ");
		etiquetaValorDefecto.setBounds(60, 150, 150, 50);
		add(etiquetaValorDefecto);
		valorDefectoTexto = new JTextField();
		valorDefectoTexto.setBounds(210, 165, 200, 25);
		add(valorDefectoTexto);
		valorDefectoNumero = new JIntegerTextField(0);
		valorDefectoNumero.setBounds(210, 165, 200, 25);
		add(valorDefectoNumero);
		valorDefectoFechaSi = new JRadioButton("Fecha actual", false);
		valorDefectoFechaSi.setBounds(210, 165, 100, 25);
		add(valorDefectoFechaSi);
		valorDefectoFechaNo = new JRadioButton("Sin fecha", true);
		valorDefectoFechaNo.setBounds(310, 165, 100, 25);
		add(valorDefectoFechaNo);
		ButtonGroup grupo1 = new ButtonGroup();
		grupo1.add(valorDefectoFechaSi);
		grupo1.add(valorDefectoFechaNo);		
		valorDefectoTextoList = new JTextField();
		valorDefectoTextoList.setBounds(210, 165, 200, 25);
		add(valorDefectoTextoList);
		añadirOpcionesBoton = new JButton("Añadir");
		añadirOpcionesBoton.setBounds(440, 160, 120, 35);
		añadirOpcionesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/añadir.png")));
		añadirOpcionesBoton.addActionListener(this);
		add(añadirOpcionesBoton);
		modeloListaOpciones = new DefaultListModel<String>();
		valorDefectoOpciones = new JList<String>(modeloListaOpciones);
		valorDefectoOpciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		valorDefectoOpcionesScrollList = new JScrollPane(valorDefectoOpciones);
		valorDefectoOpcionesScrollList.setBounds(210, 210, 200, 120);
		add(valorDefectoOpcionesScrollList);
		
		arribaOpcionesBoton = new JButton("");
		arribaOpcionesBoton.setBounds(440, 210, 50, 30);
		arribaOpcionesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/arriba.png")));
		arribaOpcionesBoton.addActionListener(this);
		add(arribaOpcionesBoton);
		abajoOpcionesBoton = new JButton("");
		abajoOpcionesBoton.setBounds(440, 250, 50, 30);
		abajoOpcionesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/abajo.png")));
		abajoOpcionesBoton.addActionListener(this);
		add(abajoOpcionesBoton);
		quitarOpcionesBoton = new JButton("");
		quitarOpcionesBoton.setBounds(440, 290, 50, 30);
		quitarOpcionesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/eliminar.png")));
		quitarOpcionesBoton.addActionListener(this);
		add(quitarOpcionesBoton); 

		agregarCampoBoton = new JButton("Agregar");
		agregarCampoBoton.setBounds(770, 290, 130, 35);
		agregarCampoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/agregar.png")));
		agregarCampoBoton.addActionListener(this);
		add(agregarCampoBoton);

		JLabel etiquetaObligatorio = new JLabel("Obligatorio: ");
		etiquetaObligatorio.setBounds(60, 80, 150, 35);
		add(etiquetaObligatorio);

		radioBotonSi = new JRadioButton("Sí", true);
		radioBotonSi.setBounds(210, 80, 55, 35);
		add(radioBotonSi);
		radioBotonNo = new JRadioButton("No", false);
		radioBotonNo.setBounds(340, 80, 55, 35);
		add(radioBotonNo);
		ButtonGroup grupo2 = new ButtonGroup();
		grupo2.add(radioBotonSi);
		grupo2.add(radioBotonNo);

		JLabel etiquetaCampos = new JLabel("Campos");
		etiquetaCampos.setBounds(435, 320, 150, 50);
		etiquetaCampos.setFont(new Font("Tahoma", Font.PLAIN, 20)); 
		add(etiquetaCampos);

		arribaCampoBoton = new JButton("");
		arribaCampoBoton.setBounds(850,380, 50, 30);
		arribaCampoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/arriba.png")));
		arribaCampoBoton.addActionListener(this);
		add(arribaCampoBoton);

		abajoCampoBoton = new JButton("");
		abajoCampoBoton.setBounds(850, 430, 50, 30);
		abajoCampoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/abajo.png")));
		abajoCampoBoton.addActionListener(this);
		add(abajoCampoBoton);
		
		quitarCampoBoton = new JButton("");
		quitarCampoBoton.setBounds(850, 480, 50, 30);
		quitarCampoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/eliminar.png")));
		quitarCampoBoton.addActionListener(this);
		add(quitarCampoBoton);
		
		modificarCampoBoton = new JButton("");
		modificarCampoBoton.setBounds(850, 530, 50, 30);
		modificarCampoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
		modificarCampoBoton.addActionListener(this);
		add(modificarCampoBoton); 

		panelTablaDatosEspecificos = new PanelTablaDatosEspecificos();
		panelTablaDatosEspecificos.setBounds(10, 370, 820, 200);
		add(panelTablaDatosEspecificos);

		inicializar();
		
		setVisible(true);			
	}

	private void inicializar() {
		comboTipo.addActionListener(this);
		valorDefectoTexto.setVisible(true);
		valorDefectoNumero.setVisible(false);
		valorDefectoFechaSi.setVisible(false);
		valorDefectoFechaNo.setVisible(false);
		valorDefectoOpcionesScrollList.setVisible(false);
		valorDefectoTextoList.setVisible(false);
		añadirOpcionesBoton.setVisible(false);
		arribaOpcionesBoton.setVisible(false);
		abajoOpcionesBoton.setVisible(false);
		quitarOpcionesBoton.setVisible(false);
		
		if (tramiteAnterior == null) {
			// insertar datos predefinidos
			try {
				Campo c1 = new Campo("Nombre del solicitante", 0, Tipo.TEXTO, "", true);
				Campo c2 = new Campo("Título", 1, Tipo.TEXTO, "", true);
				Campo c3 = new Campo("Fecha de inicio", 2, Tipo.FECHA, "Fecha actual", true);
				Campo c4 = new Campo("Fecha de fin", 3, Tipo.FECHA, "", true);
				Campo c5 = new Campo("Estado", 4, Tipo.OPCEXCL, "", true);
				tramite.getCampos().add(c1);
				tramite.getCampos().add(c2);
				tramite.getCampos().add(c3);
				tramite.getCampos().add(c4);
				tramite.getCampos().add(c5);
				Collections.sort(tramite.getCampos());
				// Mostrar en la tabla
				panelTablaDatosEspecificos.modificarTabla(tramite.getCampos());
			} catch (TramiteException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error en Datos específicos", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			// mostrar y transferir datos ya guardados
			for (Campo c: tramiteAnterior.getCampos()) {
				tramite.getCampos().add(c);
			}
			panelTablaDatosEspecificos.modificarTabla(tramite.getCampos());
			if (hayTramitesEspecificos) {
				// Sólo se permiten campos opcionales
				radioBotonNo.setSelected(true);
				radioBotonSi.setEnabled(false);
				radioBotonNo.setEnabled(false);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(agregarCampoBoton)) {
			agregarCampo();
		} else if (e.getSource().equals(arribaCampoBoton)) {
			moverArribaCampo();
		} else if (e.getSource().equals(abajoCampoBoton)) {
			moverAbajoCampo();
		} else if (e.getSource().equals(quitarCampoBoton)) {
			quitarCampo();
		} else if (e.getSource().equals(modificarCampoBoton)) {
			modificarCampo();
		} else if (e.getSource().equals(comboTipo)) {
			cambiarTipo();
		} else if (e.getSource().equals(añadirOpcionesBoton)) {
			agregarOpciones();
		} else if (e.getSource().equals(arribaOpcionesBoton)) {
			moverArribaOpciones();
		} else if (e.getSource().equals(abajoOpcionesBoton)) {
			moverAbajoOpciones();
		} else if (e.getSource().equals(quitarOpcionesBoton)) {
			quitarOpciones();
		}
	}
	
	private String[] getOpciones() {
		String[] opciones = new String[modeloListaOpciones.size()];
		// obtener las opciones de la lista y guardarlas en el arreglo
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = modeloListaOpciones.get(i);
		}
		return opciones;
	}

	private void agregarCampo() {
		Campo campo = new Campo();
		try {
			// tomar los datos escritos
			campo.setNombreCampo(campoNombre.getText());
			campo.setObligatorio(radioBotonSi.isSelected());
			campo.setTipo((Tipo) comboTipo.getSelectedItem());
			switch ((Tipo) comboTipo.getSelectedItem()) {
			case FECHA:
				if (valorDefectoFechaSi.isSelected()) {
					campo.setValorDefecto("Fecha actual");
				} else {
					campo.setValorDefecto("");
				}
				break;
			case NUMERO:
				campo.setValorDefecto(valorDefectoNumero.getText());
				break;
			case OPCEXCL:
			case OPCMULT:
				campo.setValorDefectoOpciones(getOpciones());
				break;
			case TEXTO:
				campo.setValorDefecto(valorDefectoTexto.getText());
				break;  
			}
			// agregar el campo en la lista y tabla
			tramite.agregarCampo(campo);
			panelTablaDatosEspecificos.agregarFila(campo);
			limpiar();
		} catch (TramiteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error en Datos específicos", JOptionPane.ERROR_MESSAGE);
		}   
	}

	private void limpiar() {
		campoNombre.setText("");
		radioBotonSi.setSelected(true);
		valorDefectoNumero.setValue(0);
		modeloListaOpciones.clear();
		valorDefectoTexto.setText("");
	}

	private void moverArribaCampo() {
		// tomar la posición seleccionada
		int posicionInicial = panelTablaDatosEspecificos.obtenerFilaSeleccionada();
		// validar que haya algo seleccionado
		if (posicionInicial < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de campos", 
					"Error al mover campo", JOptionPane.ERROR_MESSAGE);
		} else {
			// mover el campo una posición arriba en la tabla
			int posicionFinal = tramite.moverCampo(posicionInicial, -1);
			panelTablaDatosEspecificos.moverFila(posicionInicial, posicionFinal);
		}
	}

	private void moverAbajoCampo() {
		// tomar la posición seleccionada
		int posicionInicial = panelTablaDatosEspecificos.obtenerFilaSeleccionada();
		// validar que haya algo seleccionado
		if (posicionInicial < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de campos", 
					"Error al mover el campo", JOptionPane.ERROR_MESSAGE);
		} else {
			// mover el campo una posición abajo en la tabla
			int posicionFinal = tramite.moverCampo(posicionInicial, +1);
			panelTablaDatosEspecificos.moverFila(posicionInicial, posicionFinal);
		}
	}

	private void quitarCampo() {
		// tomar la posición seleccionada
		int posicion = panelTablaDatosEspecificos.obtenerFilaSeleccionada();	
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de campos", 
					"Error al quitar campo", JOptionPane.ERROR_MESSAGE);
		} else {
			// quitar el campo
			try {
				tramite.quitarCampo(posicion, hayTramitesEspecificos);
				panelTablaDatosEspecificos.quitarFila(posicion);
			} catch (TramiteException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error en Datos específicos", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void modificarCampo() {
		// tomar la posición seleccionada
		int posicion = panelTablaDatosEspecificos.obtenerFilaSeleccionada();	
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fila de la tabla de campos", 
					"Error al modificar campo", JOptionPane.ERROR_MESSAGE);
		} else {
			int opcion = JOptionPane.showConfirmDialog(this, "Al modificar el campo se borrará de la lista,\ndebe recordar agregarlo de nuevo o se perderán los datos.\n¿Desea continuar?", 
					"Modificar campo", JOptionPane.YES_NO_OPTION);
			if (opcion != JOptionPane.YES_OPTION) {
				return;
			}
			// mostrar los datos del campo
			Campo campo = tramite.getCampos().get(posicion);
			// borrar el campo
			try {
				tramite.quitarCampo(posicion, hayTramitesEspecificos);
				panelTablaDatosEspecificos.quitarFila(posicion);
			} catch (TramiteException e) {
				JOptionPane.showMessageDialog(this, "El campo seleccionado no puede ser modificado.\nEs un campo predefinido.", 
						"Error al modificar campo", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// mostrar los datos del campo
			campoNombre.setText(campo.getNombreCampo());
			if (campo.isObligatorio()) {
				radioBotonSi.setSelected(true); 
			} else {
				radioBotonNo.setSelected(true);
			}
			comboTipo.setSelectedItem(campo.getTipo());
			switch (campo.getTipo()) {
			case FECHA:
				if (campo.getValorDefecto().isEmpty()) {
					valorDefectoFechaNo.setSelected(true);
				} else {
					valorDefectoFechaSi.setSelected(true);
				}
				break;
			case NUMERO:
				valorDefectoNumero.setText(campo.getValorDefecto());
				break;
			case OPCEXCL:
			case OPCMULT:
				String[] valores = campo.getValorDefectoOpciones();
				modeloListaOpciones.clear();
				for (String valor : valores) {
					modeloListaOpciones.addElement(valor);
				}
				break;
			case TEXTO:
				valorDefectoTexto.setText(campo.getValorDefecto());
				break;
			}
		}
	}
	
	private void cambiarTipo() {
		if (comboTipo.getSelectedItem() == null) {
			return;
		}
		Tipo t = (Tipo)comboTipo.getSelectedItem();
		switch (t) {
		case FECHA:
			etiquetaValorDefecto.setText("Valor por defecto: ");
			valorDefectoTexto.setVisible(false);
			valorDefectoNumero.setVisible(false);
			valorDefectoFechaSi.setVisible(true);
			valorDefectoFechaNo.setVisible(true);
			valorDefectoFechaNo.setSelected(true);
			valorDefectoOpcionesScrollList.setVisible(false);
			valorDefectoTextoList.setVisible(false);
			añadirOpcionesBoton.setVisible(false);
			arribaOpcionesBoton.setVisible(false);
			abajoOpcionesBoton.setVisible(false);
			quitarOpcionesBoton.setVisible(false);
			break;
		case NUMERO:
			etiquetaValorDefecto.setText("Valor por defecto: ");
			valorDefectoTexto.setVisible(false);
			valorDefectoNumero.setVisible(true);
			valorDefectoNumero.setValue(0);
			valorDefectoFechaSi.setVisible(false);
			valorDefectoFechaNo.setVisible(false);
			valorDefectoOpcionesScrollList.setVisible(false);
			valorDefectoTextoList.setVisible(false);
			añadirOpcionesBoton.setVisible(false);
			arribaOpcionesBoton.setVisible(false);
			abajoOpcionesBoton.setVisible(false);
			quitarOpcionesBoton.setVisible(false);
			break;
		case OPCEXCL:
			etiquetaValorDefecto.setText("Posibles valores: ");
			valorDefectoTexto.setVisible(false);
			valorDefectoNumero.setVisible(false);
			valorDefectoFechaSi.setVisible(false);
			valorDefectoFechaNo.setVisible(false);
			valorDefectoOpcionesScrollList.setVisible(true);
			valorDefectoTextoList.setVisible(true);
			añadirOpcionesBoton.setVisible(true);
			arribaOpcionesBoton.setVisible(true);
			abajoOpcionesBoton.setVisible(true);
			quitarOpcionesBoton.setVisible(true);
			valorDefectoTextoList.setText("");
			break;
		case OPCMULT:
			etiquetaValorDefecto.setText("Posibles valores: ");
			valorDefectoTexto.setVisible(false);
			valorDefectoNumero.setVisible(false);
			valorDefectoFechaSi.setVisible(false);
			valorDefectoFechaNo.setVisible(false);
			valorDefectoOpcionesScrollList.setVisible(true);
			valorDefectoTextoList.setVisible(true);
			añadirOpcionesBoton.setVisible(true);
			arribaOpcionesBoton.setVisible(true);
			abajoOpcionesBoton.setVisible(true);
			quitarOpcionesBoton.setVisible(true);
			valorDefectoTextoList.setText("");
			break;
		case TEXTO:
			etiquetaValorDefecto.setText("Valor por defecto: ");
			valorDefectoTexto.setVisible(true);
			valorDefectoTexto.setText("");
			valorDefectoNumero.setVisible(false);
			valorDefectoFechaSi.setVisible(false);
			valorDefectoFechaNo.setVisible(false);
			valorDefectoOpcionesScrollList.setVisible(false);
			valorDefectoTextoList.setVisible(false);
			añadirOpcionesBoton.setVisible(false);
			arribaOpcionesBoton.setVisible(false);
			abajoOpcionesBoton.setVisible(false);
			quitarOpcionesBoton.setVisible(false);
			break;
		}
	}

	private void agregarOpciones() {
		// tomar la opción escrita, ponerla en minúsculas con mayúscula inicial
		String opcion = valorDefectoTextoList.getText().trim();
		if (!opcion.isEmpty()) {
			String inicial = opcion.substring(0, 1);
			opcion = opcion.substring(1);
			opcion = inicial.toUpperCase() + opcion.toLowerCase();
		}
		// validar que no esté vacía
		if (opcion.compareTo("") == 0) {
			JOptionPane.showMessageDialog(null, "opción vacía");
			// validar que no tenga el carácter de separación
		} else if (opcion.indexOf('/') != -1) {
			JOptionPane.showMessageDialog(null, "El sistema no permite el uso del carácter /, "
					+ "\nya que generaría problemas internos. Favor de reemplazarlo.");
			// validar que no esté ya en la lista
		} else if (modeloListaOpciones.contains(opcion)) {
			JOptionPane.showMessageDialog(null, "La opción ya existe");
		} else {
			// agregar en la lista
			modeloListaOpciones.addElement(opcion);
			valorDefectoTextoList.setText("");
		}
	}

	private void moverArribaOpciones() {
		int posicion;
		String cadenaOpcion;
		// tomar la posición seleccionada
		posicion = valorDefectoOpciones.getSelectedIndex();
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista");
		} else if (posicion == 0) {
			// si es la primera, moverla al final
			cadenaOpcion = modeloListaOpciones.getElementAt(posicion);
			modeloListaOpciones.remove(posicion);
			modeloListaOpciones.addElement(cadenaOpcion);
		} else {
			// mover la opción una posición arriba
			cadenaOpcion = modeloListaOpciones.getElementAt(posicion);
			modeloListaOpciones.remove(posicion);
			modeloListaOpciones.insertElementAt(cadenaOpcion, posicion - 1);
		}
	}

	private void moverAbajoOpciones() {
		int posicion;
		String cadenaOpcion;
		// tomar la posición seleccionada
		posicion = valorDefectoOpciones.getSelectedIndex();
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista");
		} else if (posicion == (modeloListaOpciones.getSize() - 1)) {
			// si es la última, moverla al inicio
			cadenaOpcion = modeloListaOpciones.getElementAt(posicion);
			modeloListaOpciones.remove(posicion);
			modeloListaOpciones.insertElementAt(cadenaOpcion, 0);
		} else {
			// mover la opción una posición abajo
			cadenaOpcion = modeloListaOpciones.getElementAt(posicion);
			modeloListaOpciones.remove(posicion);
			modeloListaOpciones.insertElementAt(cadenaOpcion, posicion + 1);
		}
	}

	private void quitarOpciones() {
		int posicion;
		// tomar la posición seleccionada
		posicion = valorDefectoOpciones.getSelectedIndex();
		// validar que haya algo seleccionado
		if (posicion < 0) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista");
		} else {
			modeloListaOpciones.remove(posicion);
		}
	}
	
	public void setEstados(String[] estados) {
		// buscar el Campo "Estado" e insertarle los nuevos valores
		try {
			Campo c = tramite.buscarCampo("Estado");
			c.setValorDefectoOpciones(estados);
			panelTablaDatosEspecificos.modificarTabla(tramite.getCampos());
		} catch (TramiteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error en Datos específicos", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void ocultarComponentes() {
		modificarCampoBoton.setVisible(false);
		
	}
}
