package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.toedter.calendar.JDateChooser;

import dominio.Paso;
import dominio.PasoEspecifico;
import dominio.TramiteEspecifico;

/**
 * <p>
 * Muestra todos los pasos de un tr�mite espec�fico que sean obligatorios y con
 * fecha l�mite.
 * </p>
 * <p>
 * El panel contiene dos etiquetas; la primera etiqueta con nombre del paso y la
 * segunda etiqueta que indica que es con fecha l�mite. Dos radio bot�n que
 * sirven para habilitar el campo fecha. Y el campo fecha que sirve para asignar
 * la fecha al paso. Si el tr�mite espec�fico es nuevo, el campo fecha est�
 * inhabilitado y el radio bot�n es marcado en <strong>No</strong>. Si el
 * tr�mite se va a modificar, se muestra la fecha que tiene asignado el paso
 * espec�fico.
 * </p>
 * 
 * @author jesus
 *
 */
public class PanelDetallesPaso extends JPanel {
	private static final long serialVersionUID = -4237362003682833331L;
	/**
	 * Panel donde se visualizan los pasos de un tr�mite espec�fico.
	 */
	private JPanel panelContenedor;
	/**
	 * Guarda las fechas de los pasos espec�ficos que tengan fecha l�mite y
	 * sean obligatorios.
	 */
	private JDateChooser[] fecha;
	/**
	 * Guarda los componentes de tipo fecha de los pasos espec�ficos que tengan
	 * fecha l�mite y sean obligatorios.
	 */
	private ArrayList<JDateChooser> componentes;
	/**
	 * Guarda temporalmente todos los pasos de un tr�mite.
	 */
	private ArrayList<Paso> pasos;
	//REPLACE
	/**
	 * Guarda el tr�mite que incluye los pasos espec�ficos obligatorios y con fecha l�mite.
	 */
	private TramiteEspecifico tramiteEspecifico;

	/**
	 * Constructor que es utilizado cuando un tr�mite especifico es nuevo, los
	 * radios bot�n est�n marcados en <Strong>No</strong>, y los campos fecha
	 * est�n inhabilitados. Los valores de las fechas son nulas.
	 * 
	 * @param pasos
	 *            Pasos que contiene un tr�mite.
	 */
	public PanelDetallesPaso(ArrayList<Paso> pasos) {
		this(pasos, null);
	}

	//REPLACE
	/**
	 * Constructor que es utilizado cuando el tr�mite espec�fico se va a
	 * modificar, muestra los valores que se le asign� a cada paso espec�fico.
	 * Manda a llamar al m�todo <code>inicializar</code>.
	 * 
	 * 
	 * @param pasos
	 *            pasos que contiene un tr�mite.
	 * @param tramiteEspecifico
	 *            tr�mite espec�fico para obtener los pasos espec�ficos.
	 * 
	 * @see #inicializar()
	 */
	public PanelDetallesPaso(ArrayList<Paso> pasos,
			TramiteEspecifico tramiteEspecifico) {
		this.pasos = pasos;
		this.tramiteEspecifico = tramiteEspecifico;
		inicializar();
	}
	
	/**
	 * Se recorren todos los pasos del tr�mite, si el paso tiene fecha l�mite y
	 * es obligatorio, se guarda el componente de tipo fecha.
	 * <p>
	 * Si el tr�mite espec�fico es nuevo, las fechas se muestran como nulas, y
	 * se marca el radito bot�n <strong>No</strong>. Si el tr�mite espec�fico se
	 * va a modificar, se muestran los pasos espec�ficos y los valores que
	 * contienen cada paso espec�fico.
	 * </p>
	 */
	private void inicializar() {
		int numeroPasosAMostrar = 0;
		for (Paso p : pasos) {
			if (p.isObligatorio() && p.isConFechaLimite()) {
				numeroPasosAMostrar++;
			}
		}
		JRadioButton radioBotonSi[] = new JRadioButton[numeroPasosAMostrar];
		JRadioButton radioBotonNo[] = new JRadioButton[numeroPasosAMostrar];
		fecha = new JDateChooser[numeroPasosAMostrar];
		ButtonGroup  grupoRadioButton; 
		int index = 0;
		componentes = new ArrayList<JDateChooser>();
		this.setLayout(new GridLayout(numeroPasosAMostrar, 1));
		for (Paso p : pasos) {
			if (p.isObligatorio() && p.isConFechaLimite()) {
				panelContenedor = new JPanel();
				panelContenedor.setLayout(new FlowLayout(FlowLayout.LEFT));
				imprimirLabels(p.getNombrePaso());
				imprimirLabels("Fecha l�mite");
				radioBotonSi[index] = new JRadioButton("S�");
				radioBotonSi[index].setName(Integer.toString(index));
				radioBotonSi[index].setPreferredSize(new Dimension(60, 30));
				radioBotonNo[index] = new JRadioButton("No");
				radioBotonNo[index].setName(Integer.toString(index));
				radioBotonNo[index].setPreferredSize(new Dimension(60, 30));
				grupoRadioButton = new ButtonGroup();
				grupoRadioButton.add(radioBotonSi[index]);
				grupoRadioButton.add(radioBotonNo[index]);
				fecha[index] = new JDateChooser();
				fecha[index].setPreferredSize(new Dimension(250, 30));
				fecha[index].setEnabled(false);
				radioBotonSi[index].addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent evt) {
						habilitarFecha(evt);
					}
				});
				radioBotonNo[index].addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent evt) {
						inhabilitarFecha(evt);
					}
				});
				//REPLACE
				if (tramiteEspecifico == null || tramiteEspecifico.buscarPasoEspecifico(p.getNombrePaso(), p.getRepeticion()) == -1) {//REPLACE
					radioBotonNo[index].setSelected(true);
					fecha[index].setDate(null);
				} else {
					//FALTA repetici�n de pasos
					int posicion = tramiteEspecifico.buscarPasoEspecifico(p.getNombrePaso(), p.getRepeticion());
					PasoEspecifico paso = tramiteEspecifico.obtenerPasoEspecifico(posicion);
					if (paso.getFechaLimite() == null) {
						radioBotonNo[index].setSelected(true);
						fecha[index].setDate(null);
					} else {
						radioBotonSi[index].setSelected(true);
						fecha[index].setDate(paso.getFechaLimite());
					}
					//Desactiva la modificaci�n si el paso ya fue realizado
					if (paso.isRealizado()) {
						radioBotonSi[index].setEnabled(false);
						fecha[index].setEnabled(false);
						radioBotonNo[index].setEnabled(false);
					}
				}
				panelContenedor.add(radioBotonSi[index]);
				panelContenedor.add(fecha[index]);
				panelContenedor.add(radioBotonNo[index]);
				componentes.add(fecha[index]);
				this.add(panelContenedor);
				index++;
			}
		}
	}

	/**
	 * Devuelve los componentes de un paso espec�fico.
	 * 
	 * @return Lista de componentes de un paso espec�fico.
	 */
	public ArrayList<JDateChooser> getComponentes() {
		return componentes;
	}

	/**
	 * Agrega la etiqueta con el nombre del paso que se le asigna como par�metro.
	 * 
	 * @param nombrePaso
	 *            Nombre del paso con que se guard� en el tr�mite.
	 */
	private void imprimirLabels(String nombrePaso) {
		JLabel nuevoLabel = new JLabel(nombrePaso);
		nuevoLabel.setPreferredSize(new Dimension(150, 30));
		panelContenedor.add(nuevoLabel);
	}

	/**
	 * <p>
	 * Agrega un evento a cada paso espec�fico para poder habilitar el campo de
	 * la fecha.
	 * </p>
	 * Al habilitar el campo fecha se muestra la fecha del sistema. </p>
	 * 
	 * @param evt
	 *            evento que indica si se presiona el radio bot�n
	 *            <strong>S�</strong>.
	 */
	private void habilitarFecha(ItemEvent evt) {
		JRadioButton fuente = (JRadioButton)evt.getSource();
		int index = Integer.parseInt(fuente.getName());
		fecha[index].setEnabled(true);
		fecha[index].setDate(new Date());
	}

	/**
	 * Agrega un evento a cada paso espec�fico para poder inhabilitar el campo de
	 * la fecha. Se asigna el valor de la fecha como nula.
	 * 
	 * @param evt
	 *            Evento que indica si se presiona el radio bot�n
	 *            <Strong>No</strong>.
	 */
	private void inhabilitarFecha(ItemEvent evt){
		JRadioButton evento = (JRadioButton) evt.getSource();
		int index = Integer.parseInt(evento.getName());
		fecha[index].setEnabled(false);
		fecha[index].setDate(null);
	}
}
