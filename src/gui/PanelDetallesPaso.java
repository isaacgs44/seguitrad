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

/**
 * <p>
 * Muestra todos los pasos de un trámite específico que sean obligatorios y con
 * fecha límite.
 * </p>
 * <p>
 * El panel contiene dos etiquetas; la primera etiqueta con nombre del paso y la
 * segunda etiqueta que indica que es con fecha límite. Dos radio botón que
 * sirven para habilitar el campo fecha. Y el campo fecha que sirve para asignar
 * la fecha al paso. Si el trámite específico es nuevo, el campo fecha está
 * inhabilitado y el radio botón es marcado en <strong>No</strong>. Si el
 * trámite se va a modificar, se muestra la fecha que tiene asignado el paso
 * específico.
 * </p>
 *
 * @author jesus
 *
 */
public class PanelDetallesPaso extends JPanel {

    /**
     * Versión de serialización de clase.
     */
    private static final long serialVersionUID = -4237362003682833331L;
    /**
     * Panel donde se visualizan los pasos de un trámite específico.
     */
    private JPanel panelContenedor;
    /**
     * Guarda las fechas de los pasos específicos que tengan fecha límite y sean
     * obligatorios.
     */
    private JDateChooser[] fecha;
    /**
     * Guarda los componentes de tipo fecha de los pasos específicos que tengan
     * fecha límite y sean obligatorios.
     */
    private ArrayList<JDateChooser> componentes;
    /**
     * Guarda temporalmente todos los pasos de un trámite.
     */
    private ArrayList<Paso> pasos;
    /**
     * Guarda los pasos específicos de un trámite con fecha límite y
     * obligatorios.
     */
    private ArrayList<PasoEspecifico> pasosEspecificos;

    /**
     * Constructor que es utilizado cuando un trámite especifico es nuevo, los
     * radios botón están marcados en <Strong>No</strong>, y los campos fecha
     * están inhabilitados. Los valores de las fechas son nulas.
     *
     * @param pasos Pasos que contiene un trámite.
     */
    public PanelDetallesPaso(ArrayList<Paso> pasos) {
        this(pasos, null);
    }

    /**
     * Constructor que es utilizado cuando el trámite específico se va a
     * modificar, muestra los valores que se le asignó a cada paso específico.
     * Manda a llamar al método <code>inicializar</code>.
     *
     *
     * @param pasos pasos que contiene un trámite.
     * @param pasosEspecificos pasos específicos que contiene un trámite
     * específico.
     *
     * @see #inicializar()
     */
    public PanelDetallesPaso(ArrayList<Paso> pasos,
            ArrayList<PasoEspecifico> pasosEspecificos) {
        this.pasos = pasos;
        this.pasosEspecificos = pasosEspecificos;
        inicializar();
    }

    /**
     * Se recorren todos los pasos del trámite, si el paso tiene fecha límite y
     * es obligatorio, se guarda el componente de tipo fecha.
     * <p>
     * Si el trámite específico es nuevo, las fechas se muestran como nulas, y
     * se marca el radito botón <strong>No</strong>. Si el trámite específico se
     * va a modificar, se muestran los pasos específicos y los valores que
     * contienen cada paso específico.
     * </p>
     */
    private void inicializar() {
        int numeroPasosAMostrar = 0;
        for (Paso p : pasos) {
            //Comprueba que un paso tenga repeticion
            if (p.isObligatorio() && p.isConFechaLimite() && p.getRepeticion() > 1) {
                for (int i = 0; i < p.getRepeticion(); i++) {
                    numeroPasosAMostrar++;
                }
            }
            //Comprueba que un paso sin repeticion no sea indefinido
            if (p.isObligatorio() && p.isConFechaLimite() && p.getRepeticion() == 1) {
                numeroPasosAMostrar++;
            }
        }
        JRadioButton radioBotonSi[] = new JRadioButton[numeroPasosAMostrar];
        JRadioButton radioBotonNo[] = new JRadioButton[numeroPasosAMostrar];
        fecha = new JDateChooser[numeroPasosAMostrar];
        ButtonGroup grupoRadioButton;
        int index = 0;
        componentes = new ArrayList<JDateChooser>();
        this.setLayout(new GridLayout(numeroPasosAMostrar, 1));
        for (Paso p : pasos) {

            if (p.isObligatorio() && p.isConFechaLimite() && p.getRepeticion() >= 1) {
                for (int i = 1; i <= p.getRepeticion(); i++) {
                    panelContenedor = new JPanel();
                    panelContenedor.setLayout(new FlowLayout(FlowLayout.LEFT));
                    String nombrePaso = (i == 1) ? p.getNombrePaso() : p.getNombrePaso() + " " + i;
                    imprimirLabels(nombrePaso);
                    imprimirLabels("Fecha límite");
                    radioBotonSi[index] = new JRadioButton("Sí");
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
                    if (pasosEspecificos == null) {
                        radioBotonNo[index].setSelected(true);
                        fecha[index].setDate(null);
                    } else if (pasosEspecificos.get(p.getNumPaso()).getFechaLimite() == null) {
                        radioBotonNo[index].setSelected(true);
                        fecha[index].setDate(null);
                    } else {
                        radioBotonSi[index].setSelected(true);
                        fecha[index].setDate(pasosEspecificos.get(p.getNumPaso()).getFechaLimite());
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
    }

    /**
     * Devuelve los componentes de un paso específico.
     *
     * @return Lista de componentes de un paso específico.
     */
    public ArrayList<JDateChooser> getComponentes() {
        return componentes;
    }

    /**
     * Agrega la etiqueta con el nombre del paso que se le asigna como
     * parámetro.
     *
     * @param nombrePaso Nombre del paso con que se guardó en el trámite.
     */
    private void imprimirLabels(String nombrePaso) {
        JLabel nuevoLabel = new JLabel(nombrePaso);
        nuevoLabel.setPreferredSize(new Dimension(150, 30));
        panelContenedor.add(nuevoLabel);
    }

    /**
     * <p>
     * Agrega un evento a cada paso específico para poder habilitar el campo de
     * la fecha.
     * </p>
     * Al habilitar el campo fecha se muestra la fecha del sistema. </p>
     *
     * @param evt evento que indica si se presiona el radio botón
     * <strong>Sí</strong>.
     */
    private void habilitarFecha(ItemEvent evt) {
        JRadioButton fuente = (JRadioButton) evt.getSource();
        int index = Integer.parseInt(fuente.getName());
        fecha[index].setEnabled(true);
        fecha[index].setDate(new Date());
    }

    /**
     * Agrega un evento a cada paso específico para poder inhabilitar el campo
     * de la fecha. Se asigna el valor de la fecha como nula.
     *
     * @param evt Evento que indica si se presiona el radio botón
     * <Strong>No</strong>.
     */
    private void inhabilitarFecha(ItemEvent evt) {
        JRadioButton evento = (JRadioButton) evt.getSource();
        int index = Integer.parseInt(evento.getName());
        fecha[index].setEnabled(false);
        fecha[index].setDate(null);
    }
}
