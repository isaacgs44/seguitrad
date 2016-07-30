package gui;

import dominio.Paso;
import dominio.PasoEspecifico;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Muestra los pasos específicos que han sido realizados.
 *
 * @author Cresencio
 */
public class PanelPasoRealizado extends JPanel implements ActionListener {

    /**
     * Número de versión de serialización de la clase.
     */
    private static final long serialVersionUID = -8692794042074788858L;
    /**
     * Arreglo que almacena etiquetas con el nombre de los pasos realizados.
     */
    private JLabel[] nombrePaso;
    /**
     * Arreglo que almacena etiquetas con la fecha de realización de los pasos.
     */
    private JLabel[] fechaRealizacion;
    /**
     * Arreglo que almacena un checkbox por cada paso realizado.
     */
    private JCheckBox[] checkDocumento;
    /**
     * Arreglo que almacena botones creados por cada paso realizado.
     */
    private JButton[] mostrarDocBoton;
    /**
     * Arreglo que almacena botones creados por cada paso realizado.
     */
    private JButton[] verPlantillaBoton;
    /**
     * Lista que almacena únicamente los pasos realizados.
     */
    private ArrayList<PasoEspecifico> pasosRealizados;
    /**
     * Se utiliza para obtener datos del trámite.
     */
    private VentanaPrincipal ventanaPrincipal;
    /**
     * Lista que almacena los pasos específicos en general.
     */
    private ArrayList<PasoEspecifico> pasosEspecificos;
    /**
     * Arreglo que almacena el posible nombre del boton
     * <code>verPlantillaBoton</code> para un paso.
     */
    private String nombreBotonPlantilla[];
    /**
     * Arreglo que almacena el nombre de un paso con plantilla.
     */
    private String nombrePaso_Plantilla[];
    /**
     * Arreglo que almacena el posible nombre del boton <code>verDocBoton</code>
     * para un paso.
     */
    private String nombreBotonDocumento[];
    /**
     * Arreglo que almacena el nombre de un paso con documento.
     */
    private String nombrePaso_Documento[];

    /**
     * Inicializar el panel con los pasos realizados.
     * <p>
     * Este constructor itera la lista de pasos específicos y agrega los pasos
     * realizados a la lista <code>pasosRealizados</code> con los que se
     * construye el panel.
     * </p>
     *
     * @param pasosEspecificos Lista de pasos específicos del trámite.
     * @param ventanaPrincipal Ventana principal sobre la que se creará el panel
     * de pasos realizados.
     */
    public PanelPasoRealizado(ArrayList<PasoEspecifico> pasosEspecificos, VentanaPrincipal ventanaPrincipal) {
        int i = 0;
        this.pasosEspecificos = pasosEspecificos;
        pasosRealizados = new ArrayList<>();
        this.ventanaPrincipal = ventanaPrincipal;
        for (PasoEspecifico pe : pasosEspecificos) {
            if (pe.isRealizado()) {
                pasosRealizados.add(pe);
                i++;
            }
        }
        nombreBtnPlantilla(ventanaPrincipal);
        nombreBtnDocumento(ventanaPrincipal);
        crearPanel(i);
    }

    /**
     * Crea el panel de pasos a partir de la lista de pasos realizados. Itera la
     * lista de pasos realizados y los agrega al panel contenedor.
     *
     * @param n contiene el número espacios que requiere la lista de pasos para
     * ser mostrada en el panel (número de pasos realizados).
     */
    private void crearPanel(int n) {
        this.setSize(-1, 30 * n);
        this.setLayout(new GridLayout(n, 1));
        nombrePaso = new JLabel[n];
        fechaRealizacion = new JLabel[n];
        checkDocumento = new JCheckBox[n];
        mostrarDocBoton = new JButton[n];
        verPlantillaBoton = new JButton[n];
        JPanel panelContenedor;
        ButtonGroup grupo = new ButtonGroup();

        int i = 0;
        for (PasoEspecifico realizado : pasosRealizados) {
            panelContenedor = new JPanel();
            panelContenedor.setLayout(new FlowLayout(FlowLayout.LEFT));
            nombrePaso[i] = new JLabel(realizado.getNombrePaso());
            nombrePaso[i].setPreferredSize(new Dimension(250, 30));
            panelContenedor.add(nombrePaso[i]);
            Date d = realizado.getFechaRealizacion();
            DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
            fechaRealizacion[i] = new JLabel(formato.format(d));
            fechaRealizacion[i].setPreferredSize(new Dimension(100, 30));
            panelContenedor.add(fechaRealizacion[i]);

            String nombreBtnPlantilla = getNombreBtnPlantilla(realizado);
            verPlantillaBoton[i] = new JButton(nombreBtnPlantilla);
            verPlantillaBoton[i].setActionCommand(nombreBtnPlantilla);
            verPlantillaBoton[i].setName(String.valueOf(i));
            verPlantillaBoton[i].addActionListener(this);
            verPlantillaBoton[i].setPreferredSize(new Dimension(130, 30));
            panelContenedor.add(verPlantillaBoton[i]);

            String nombreBtnDocumento = getNombreBtnDocumento(realizado);
            mostrarDocBoton[i] = new JButton(nombreBtnDocumento);
            mostrarDocBoton[i].setActionCommand(nombreBtnDocumento);
            mostrarDocBoton[i].setName(String.valueOf(i));
            mostrarDocBoton[i].addActionListener(this);
            mostrarDocBoton[i].setPreferredSize(new Dimension(130, 30));
            panelContenedor.add(mostrarDocBoton[i]);

            checkDocumento[i] = new JCheckBox("" + (i + 1));
            checkDocumento[i].setPreferredSize(new Dimension(20, 30));
            checkDocumento[0].setSelected(true);
            grupo.add(checkDocumento[i]);
            panelContenedor.add(checkDocumento[i]);
            this.add(panelContenedor);
            i++;
        }
    }

    /**
     * Devuelve el nombre del boton documento.
     * <p>
     * Permite obtener el nombre que tendra el boton mostrarDocBoton del panel
     * de pasos realizado ya que el nombre puede variar; Con documento(cuando el
     * paso tiene asiciado un documento), Vacio(cuando el paso puede tener
     * documento pero aún no se le asocia ninguno), Mostrar documento(Cuando el
     * paso tiene cargado un documento específico) y Sin documento(cuando el
     * paso no puede llevar documento).
     * </p>
     *
     * @param realizado Contiene el paso para el que se está haciendo la
     * comprobación.
     * @return El nombre que tendrá el boton.
     */
    private String getNombreBtnDocumento(PasoEspecifico realizado) {
        String nombreBtnDocumento = "";
        for (int j = 0; j < nombrePaso_Documento.length; j++) {
            if (realizado.getNombrePaso().contains(nombrePaso_Documento[j]) && nombreBotonDocumento[j].equals("Con_documento")) {
                if (realizado.getDocumento() == null || realizado.getDocumento().equals("")) {
                    nombreBtnDocumento = "Vacío";
                }
                if (realizado.getDocumento() != null && !"".equals(realizado.getDocumento())) {
                    nombreBtnDocumento = "Mostrar documento";
                }
            }
            if (realizado.getNombrePaso().contains(nombrePaso_Documento[j]) && nombreBotonDocumento[j].equals("Sin_documento")) {
                nombreBtnDocumento = "Sin documento";
            }
        }
        return nombreBtnDocumento;
    }

    /**
     * Devuelve el nombre del boton plantilla.
     * <p>
     * Permite obtener el nombre que tendra el boton verPlantillaBoton del panel
     * de pasos realizado ya que el nombre puede variar; Ver plantilla(cuando se
     * encuentra cargada una plantilla), Vacio(cuando el paso tiene plantilla
     * pero no una plantilla cargada) y Sin plantilla(cuando al hacer el trámite
     * se determinó que el paso no tendría plantilla.
     * </p>
     * <p>
     * Para obtener el nombre se itera un arreglo que contiene las posibilidades
     * del nombre del boton para el paso seleccionado.
     * </p>
     *
     * @param realizado Contiene el paso para el que se está haciendo la
     * comprobación.
     * @return El nombre que tendrá el boton.
     */
    private String getNombreBtnPlantilla(PasoEspecifico realizado) {
        String nombreBtnPlantilla = "";
        for (int j = 0; j < nombrePaso_Plantilla.length; j++) {
            if (realizado.getNombrePaso().contains(nombrePaso_Plantilla[j])) {
                nombreBtnPlantilla = nombreBotonPlantilla[j];
            }
        }
        return nombreBtnPlantilla;
    }

    /**
     * Método que escucha los eventos de los botones de
     * <code>PanelPasoRealizado</code>.
     * <p>
     * Este metodo ejecuta las acciones/eventos que se hayan establecido para un
     * determinado boton.
     * </p>
     *
     * @param arg0 Almacena las acciones que el boton invoque cuando el usuario
     * interactúa con algún boton.
     */
    @Override
    public void actionPerformed(ActionEvent arg0
    ) {
        if (arg0.getActionCommand().equalsIgnoreCase("Mostrar documento")) {
            mostrarDocumento(arg0);
        }
        if (arg0.getActionCommand().equalsIgnoreCase("Ver plantilla")) {
            JButton boton = (JButton) arg0.getSource();
            int valor = Integer.parseInt(boton.getName());
            verPlantilla(obtenerPlantillaPasosRealizados(valor));
        }
    }

    /**
     * Muestra el documento específico para al paso realizado seleccionado.
     *
     * @param arg0 Contiene el evento a realizar.
     */
    private void mostrarDocumento(ActionEvent arg0) {
        JButton boton = (JButton) arg0.getSource();
        int valor = Integer.parseInt(boton.getName());
        if (boton.getText().equals("Mostrar documento")) {
            if (obtenerPasoSeleccionado(valor).getDocumento() != null && !"".equals(obtenerPasoSeleccionado(valor).getDocumento())) {
                File path = new File(obtenerPasoSeleccionado(valor).getDocumento());
                try {
                    Desktop.getDesktop().open(path);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error leyendo archivo", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay documento",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Devuelve la posición del paso específico realizado que se haya
     * seleccionado.
     *
     * @return Posición del paso específico. .
     */
    public int posicionMarcada() {
        int posicion = -1;
        for (int i = 0; i < checkDocumento.length; i++) {
            if (checkDocumento[i].isSelected()) {
                posicion = i;
            }
        }

        return posicion;
    }

    /**
     * Devuelve el nombre del boton ver documento. Mediante la posición del
     * boton en el panel de pasos realizados.
     *
     * @param indice Contiene la posición del paso realizado seleccionado.
     * @return Nombre del boton. .
     */
    public String obtenerNombreBotonDocumento(int indice) {
        String nombreBoton = "";
        if (mostrarDocBoton[indice].getText().equals("Sin documento")) {
            nombreBoton = mostrarDocBoton[indice].getText();
        }
        return nombreBoton;
    }

    /**
     * Devuelve la ruta de la plantilla para el paso seleccionado.
     * <p>
     * Este método itera la lista de pasos específicos realizados y con la
     * posición del paso seleccionado se filtra la ruta de la plantilla, en caso
     * de no tener el método no retorna nada.
     * </p>
     *
     * @param indice Posición del paso seleccionado.
     * @return ruta de la plantilla en caso de que tuviera. .
     */
    public String obtenerPlantillaPasosRealizados(int indice) {
        int numPasosRealizados = 0;
        String plantilla = null;
        for (PasoEspecifico pe : pasosEspecificos) {
            if (pe.isRealizado()) {
                numPasosRealizados++;
            }
        }
        String[] todo_pasos = new String[numPasosRealizados];
        int i = 0;
        for (PasoEspecifico pe : pasosEspecificos) {
            if (pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            if (todo_pasos[indice].contains(p.getNombrePaso())) {
                plantilla = p.getPlantilla();
            }
        }
        return plantilla;
    }

    /**
     * Abre la plantilla establecida para un paso.
     *
     * @param ruta Contiene el directorio donde se almacena la plantilla para el
     * paso seleccionado.
     */
    public void verPlantilla(String ruta) {
        File path = new File(ruta);
        try {
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error abriendo la plantilla",
                    "Ver plantilla", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para llenar arreglos con los nombres de los pasos con plantilla.
     * <p>
     * Los arreglos llenados se utilizan para determinar el nombre que tendrán
     * los botones de ver documento y ver plantilla en el panel de pasos
     * realizados.
     * </p>
     *
     * @param ventanaPrincipal permite obtener los datos totales del trámite. .
     */
    private void nombreBtnPlantilla(VentanaPrincipal ventanaPrincipal) {
        int i = 0;
        nombreBotonPlantilla = new String[ventanaPrincipal.getLista().getTramite().getPasos().size()];
        nombrePaso_Plantilla = new String[ventanaPrincipal.getLista().getTramite().getPasos().size()];
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            if (p.isConPlantilla() == false) {
                nombreBotonPlantilla[i] = "Sin plantilla";
                nombrePaso_Plantilla[i] = p.getNombrePaso();
            }
            if (!"".equals(p.getPlantilla()) && p.isConPlantilla()) {
                nombreBotonPlantilla[i] = "Ver plantilla";
                nombrePaso_Plantilla[i] = p.getNombrePaso();
            }
            if (p.isConPlantilla() && p.getPlantilla().equals("")) {
                nombreBotonPlantilla[i] = "Vacio";
                nombrePaso_Plantilla[i] = p.getNombrePaso();
            }
            i++;
        }
    }

    /**
     * Devuelve el paso específico seleccionado de la lista de pasos realizados.
     * <p>
     * Este método itera la lista de pasos realizados y busca coincidencias con
     * la posición recibida, cuando la encuentra retorna el paso que se haya
     * seleccionado.
     * </p>
     *
     * @param indice Contiene la posición del paso seleccionado de la lista de
     * pasos realizados.
     * @return Objeto PasoEspecifico que haya sido seleccionado.
     */
    private PasoEspecifico obtenerPasoSeleccionado(int indice) {
        int i = 0;
        for (PasoEspecifico pe : pasosEspecificos) {
            if (pe.isRealizado()) {
                if (i == indice) {
                    return pe;
                }
                i++;
            }
        }
        return null;
    }

    /**
     * Método para llenar arreglos con los nombres de los pasos con documento.
     * <p>
     * Los arreglos llenados se utilizan para determinar el nombre que tendrá el
     * boton verDocButon para un paso específico del panel de pasos realizados.
     * </p>
     *
     * @param ventanaPrincipal permite obtener los datos totales del trámite. .
     */
    private void nombreBtnDocumento(VentanaPrincipal ventanaPrincipal) {
        int i = 0;
        nombreBotonDocumento = new String[ventanaPrincipal.getLista().getTramite().getPasos().size()];
        nombrePaso_Documento = new String[ventanaPrincipal.getLista().getTramite().getPasos().size()];
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            if (p.isConDocumento()) {
                nombreBotonDocumento[i] = "Con_documento";
                nombrePaso_Documento[i] = p.getNombrePaso();
            } else {
                nombreBotonDocumento[i] = "Sin_documento";
                nombrePaso_Documento[i] = p.getNombrePaso();
            }
            i++;
        }
    }
}
