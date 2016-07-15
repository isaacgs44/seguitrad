package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dominio.TramiteEspecifico;

/**
 * <p>
 * Clase que contiene los métodos necesarios para realizar la búsqueda de
 * trámites específicos.
 * </p>
 * <p>
 * El panel contiene dos checkbox, dos campos de textos, una tabla y un botón de
 * buscar. Los checkbox tienen la función de indicar si el trámite específico
 * que se busca es por nombre del solicitante o por título. Si los dos
 * checkbox están marcados, el trámite específico que se busca debe coincidir
 * en ambos campos, si ninguno de los checkbox están marcados se realiza una
 * búsqueda general de trámites específicos. En los campos de texto se puede
 * ingresar el nombre del solicitante o título que se busca.
 * </p>
 * Los resultados se muestran dentro de una tabla.
 * 
 * @author jesus
 *
 */
public class PanelBuscar extends JPanel implements ActionListener {
	private static final long serialVersionUID = 650316241219391095L;
	/**
	 * Checbox que indica si el campo de texto <strong>nombre</strong> está
	 * habilitado para realizar la búsqueda.
	 */
	private JCheckBox checkNombre;
	/**
	 * Checbox que indica si el campo de texto <strong>título</strong> está
	 * habilitado para realizar la búsqueda.
	 */
	private JCheckBox checkTitulo;
	/**
	 * Campo de texto donde se ingresa el nombre del solicitante que se va
	 * a buscar en los trámites.
	 */
	private JTextField nombre;
	/**
	 * Campo de texto donde se ingresa el título que se va a buscar en
	 * los trámites.
	 */
	private JTextField titulo;
	/**
	 * Botón que sirve para empezar la búsqueda dentro de la lista de
	 * trámites.
	 */
	private JButton buscarBoton;
	/**
	 * Tabla donde se muestra el resultado de la búsqueda.
	 */
	private JTable tablaBuscar;
	/**
	 * Modelo que sirve para guardar los valores que contiene la tabla.
	 */
	private DefaultTableModel modelo;
	/**
	 *Nombre de las columnas que se muestran arriba de la tabla.
	 */
	private final String[] columnNames = { "Nombre", "Título",
			"Fecha de inicio", "Fecha de fin", "Estado" };
	/**
	 * Guarda temporalmente todos los trámites específicos.
	 * 
	 * @see #PanelBuscar(ArrayList)
	 */
	private ArrayList<TramiteEspecifico> listaTramites;
	/**
	 * Guarda todos los trámites específicos que se encuentran en la
	 * búsqueda.
	 * 
	 * @see #valoresTramite(TramiteEspecifico)
	 */
	private ArrayList<TramiteEspecifico> tramitesEncontrados;

	/**
	 * Inicializa todos los componentes que se visualizan en el panel.
	 * 
	 * @param listaTramites
	 *            Todos los trámites específicos que hay en el trámite actual.
	 */
	public PanelBuscar(ArrayList<TramiteEspecifico> listaTramites) {
		setLayout(null);
		this.listaTramites = listaTramites;
		checkNombre = new JCheckBox("Nombre");
		checkNombre.setBounds(100, 20, 100, 30);
		add(checkNombre);

		nombre = new JTextField();
		nombre.setBounds(220, 20, 150, 25);
		add(nombre);

		checkTitulo = new JCheckBox("Título");
		checkTitulo.setBounds(100, 60, 100, 30);
		add(checkTitulo);

		titulo = new JTextField();
		titulo.setBounds(220, 60, 150, 25);
		add(titulo);

		buscarBoton = new JButton("Buscar");
		buscarBoton.setBounds(400, 37, 120, 35);
		buscarBoton.setIcon(new ImageIcon(getClass().getResource(
				"/imagenes/buscar.png")));
		buscarBoton.addActionListener(this);
		add(buscarBoton);

		JLabel etiquetaTitulo = new JLabel(
				"Seleccione un registro para hacer la acción");
		etiquetaTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		etiquetaTitulo.setBounds(250, 82, 400, 50);
		add(etiquetaTitulo);

		Object[][] data = {};
		modelo = new DefaultTableModel(data, columnNames);
		tablaBuscar = new JTable(modelo);
		JScrollPane scrollPane = new JScrollPane(tablaBuscar);
		scrollPane.setBounds(95, 130, 650, 200);
		add(scrollPane);
		setVisible(true);
                buscar("", "");
	}

	/**
	 * <p>
	 * Método que escucha los eventos que se ejecutan dentro de la ventana.
	 * </p>
	 * <p>
	 * Si el botón que se presiona es el de buscar, se asigna los valores
	 * correspondientes para realizar la búsqueda.
	 * Se manda a llamar el método buscar.
	 * </p>
	 * 
	 * @param e
	 *            Indica qué evento se va a realizar.
	 * @see #buscar(String, String)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(buscarBoton)) {
			String nombre = "";
			String titulo = "";
			if (checkNombre.isSelected() == true) {
				nombre = this.nombre.getText();
			}
			if (checkTitulo.isSelected() == true) {
				titulo = this.titulo.getText();
			}
			buscar(nombre, titulo);
		}
	}

	/**
	 * Busca trámites específicos dependiendo del nombre del solicitante y
	 * título.
	 * <p>
	 * Si no se seleccionó ninguno de los checkbox, los parámetros (cadena de
	 * caracteres) que recibe el método están vacíos. Se realiza una búsqueda
	 * general de todos los trámites específicos y se muestra el resultado de la
	 * búsqueda en la tabla.
	 * </p>
	 * <p>
	 * Si los dos checkbox están seleccionados, ninguno de los parámetros que
	 * recibe el método están vacíos. Se realiza una búsqueda en la lista de
	 * trámites específicos y se guardan los trámites que coincidan o que
	 * contengan ambos parámetros.
	 * </p>
	 * <p>
	 * Si alguna de los checbox están seleccionado, un parámetro que recibe el
	 * método no está vacío. Se realiza la búsqueda dependiendo del checbox
	 * seleccionado dentro de la lista de trámites, y se guardan los trámites,
	 * que coincidan o que contengan el parámetro no vacío.
	 * </p>
	 * Se muestra la tabla con el resultado de la búsqueda.
	 * 
	 * @param buscarNombre
	 *            Palabra con la que se va  a buscar el nombre del solicitante.
	 * @param buscarTitulo
	 *            Palabra con la que se va a buscar el título.
	 * @see #valoresTramite(TramiteEspecifico)
	 */
	public void buscar(String buscarNombre, String buscarTitulo) {
		int posicion;
		String valorNombre;
		String valorTitulo;
		// Se va guardando los valores de los tramites encontrados
		ArrayList<String[]> datosTabla = new ArrayList<String[]>();
		Object[][] data = null;
		tramitesEncontrados = new ArrayList<TramiteEspecifico>();
		for (TramiteEspecifico t : listaTramites) {
			if (buscarNombre.equals("") && buscarTitulo.equals("")) {
				datosTabla.add(valoresTramite(t));
			} else if (!buscarNombre.equals("") && !buscarTitulo.equals("")) {
				posicion = t.buscarCampo("Nombre del solicitante");
				valorNombre = t.obtenerValores(posicion)[0];
				posicion = t.buscarCampo("Título");
				valorTitulo = t.obtenerValores(posicion)[0];
				if (valorNombre.indexOf(buscarNombre) > -1
						&& valorTitulo.indexOf(buscarTitulo) > -1) {
					datosTabla.add(valoresTramite(t));
				}
			} else {
				if (!buscarNombre.equals("")) {
					posicion = t.buscarCampo("Nombre del solicitante");
					valorNombre = t.obtenerValores(posicion)[0];
					if (valorNombre.indexOf(buscarNombre) > -1) {
						datosTabla.add(valoresTramite(t));
					}
				} else if (!buscarTitulo.equals("")) {
					posicion = t.buscarCampo("Título");
					valorTitulo = t.obtenerValores(posicion)[0];
					if (valorTitulo.indexOf(buscarTitulo) > -1) {
						datosTabla.add(valoresTramite(t));
					}
				}
			}
		}
		data = new Object[datosTabla.size()][5];
		for (int i = 0; i < datosTabla.size(); i++) {
			data[i] = datosTabla.get(i);
		}
		modelo = new DefaultTableModel(data, columnNames);
		tablaBuscar.setModel(modelo);
	}

	/**
	 * Obtiene los valores del trámite especifico que se le pasa como parámetro.
	 * 
	 * @param t
	 *            Trámite especifico del cual se van a obtener sus valores.
	 * @return Arreglo con los valores del trámite específico.
	 */
	private String[] valoresTramite(TramiteEspecifico t) {
		int index;
		// guarda en un arreglo los valores de los datos especificos;
		String[] valores = new String[5];
		index = t.buscarCampo("Nombre del solicitante");
		valores[0] = t.obtenerValores(index)[0];
		index = t.buscarCampo("Título");
		valores[1] = t.obtenerValores(index)[0];
		index = t.buscarCampo("Fecha de inicio");
                valores[2] = t.obtenerValores(index)[0];
		index = t.buscarCampo("Fecha de fin");
                valores[3] = t.obtenerValores(index)[0];
		index = t.buscarCampo("Estado");
		valores[4] = t.obtenerValores(index)[0];
		tramitesEncontrados.add(t);
		return valores;
	}

	/**
	 * Retorna el trámite seleccionado.
	 * 
	 * @return El trámite seleccionado.
	 */
	public TramiteEspecifico getTramiteSeleccionado() {
		TramiteEspecifico tramiteSeleccionado = tramitesEncontrados
				.get(obtenerFilaSeleccionada());
		return tramiteSeleccionado;
	}

	/**
	 * Obtiene el n&uacute;mero de fila seleccionada.
	 * <p>
	 * Si hay varias filas seleccionadas o si no hay ninguna, regresar&aacute;
	 * -1; si hay una seleccionada, regresar&aacute; la posici&oacute;n.
	 * 
	 * @return La fila seleccionada o -1 si hay selecci&oacute;n m&uacute;ltiple
	 *         o no hay selecci&oacute;n.
	 */
	public int obtenerFilaSeleccionada() {
		int numFilas = tablaBuscar.getSelectedRowCount();
		return (numFilas <= 1) ? tablaBuscar.getSelectedRow() : -1;
	}

	/**
	 * Elimina de la tabla la fila con el valor del parámetro que recibe
	 * el método.
	 * 
	 * @param fila
	 *            fila seleccionada
	 */
	public void eliminarFilaSeleccionada(int fila) {
		modelo.removeRow(fila);
	}
}
