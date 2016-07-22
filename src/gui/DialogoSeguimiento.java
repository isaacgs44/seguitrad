package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.*;

import dominio.Paso;
import dominio.PasoEspecifico;
import dominio.TramiteEspecifico;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.BorderUIResource;
import lib.UtileriasArchivo;

/**
 * Lleva el control de seguimiento de un trámite.
 * <p>
 * Muestra un dialogo con todos los datos de un trámite específico. Sirve para
 * llevar el control de seguimiento de los pasos que el tramitante va
 * realizando.
 * </p>
 * <p>
 * El dialogo muestra tres paneles para dividir por secciones; en la primer
 * sección se encuentran los detalles del trámite, en la segunda los pasos
 * realizados y en la tercer sección los pasos que aún no se realizan.
 * </p>
 *
 */
public class DialogoSeguimiento extends JDialog implements ActionListener {

    private static final long serialVersionUID = -7800314788050081571L;
    /**
     * Almacena el nombre del tramitante contenido en el registro del trámite
     * específico.
     */
    private JTextField nombreSolicitante;
    /**
     * Contiene el título del trámite.
     */
    private JTextField Titulo;
    /**
     * Almacena la fecha en la que se inició el trámite especifico.
     */
    private JTextField fechaInicio;
    /**
     * Etiqueta del campo que contiene la fecha fin.
     */
    private JLabel etiquetaFechaFin;
    /**
     * Muestra la fecha en la que el trámite finalizará.
     */
    private JTextField fechaFin;
    /**
     * Contiene el estado del trámite, y no es editable. El estado cambia
     * dependiendo de los pasos que se vayan realizando.
     */
    private JTextField estado;
    /**
     * El boton muestra un dialogo donde se pueden apreciar los detalles del
     * trámite. El dialogo detalles muestra datos no editables del trámite.
     */
    private JButton detallesBoton;
    /**
     * Inicia el dialogo modificar registro.
     *
     * @see
     * DialogoModificarRegistro#DialogoModificarRegistro(gui.VentanaPrincipal,
     * dominio.TramiteEspecifico) Al iniciar el dialogo se toma como
     * seleccionado el trámite actual.
     */
    private JButton modificarBoton;
    /**
     * Inicia el dialogo eliminar registro. Toma como seleccionado el trámite
     * actual, y en caso de se tenga avance con los pasos obligatorios manda una
     * advertencia.
     *
     * @see
     * DialogoEliminarRegistro#DialogoEliminarRegistro(gui.VentanaPrincipal)
     */
    private JButton eliminarBoton;
    /**
     * Sirve para abrir el documento de un paso realizado que esté seleccionado
     * en caso de que tenga. En la segunda sección del dialogo de seguimiento se
     * muestra una tabla con los pasos realizados, a un lado aparecen los
     * botones para realizar las acciones que estén disponibles para el paso.
     */
    private JButton verDocBoton;
    /**
     * Boton para cargar documento. Carga un documento especifico a un paso
     * seleccionado.
     */
    private JButton cargarDocBoton;
    /**
     * Boton para quitar el documento asosiado a un trámite realizado.
     */
    private JButton quitarDocBoton;
    /**
     * Boton para eliminar un paso seleccionado.
     *
     * @see #eliminarPasoRealizado(int)
     * @see #getPasoSeleccionadoRealizado(int)
     * @see #getSecuenciaPasosEliminar(int)
     */
    private JButton eliminarPasoBoton;
    /**
     * Contiene los nombres de todos los pasos sin realizar. En esta lista están
     * almacenados todos los pasos que no han sido realizados.
     */
    private JList<String> descripcion;
    private DefaultListModel<String> modeloDescripcion;
    /**
     * Scroll para desplazarse entre pasos no realizados. Si la lista de pasos
     * supera el número admitido en la interfaz se añade un scroll.
     */
    private JScrollPane scrollDescripcion;
    /**
     * Boton para editar la fecha de realización de un paso y habilitar la
     * opcion de cargar un documento.
     */
    private JButton editarBoton;
    /**
     * Almacena la ruta del documento cargado.
     */
    private JTextField datoEspecifico;
    /**
     * Abre una ventana para seleccionar un archivo específico. Si el paso que
     * se está editando tiene documento específico, se habilita el boton para
     * cargar el archivo.
     */
    private JButton carpetaBoton;
    /**
     * Abre el documento establecido como plantilla del paso.
     */
    private JButton verPlantillaBoton;
    /**
     * Guarda los cambios realziados en memoria y regresa al dialogo buscar
     * segumiento.
     */
    private JButton aceptarBoton;
    /**
     * Descarta los cambios hechos en el dialogo seguimiento y regresa al
     * dialogo buscar seguimiento.
     */
    private JButton cancelarBoton;
    /**
     * Despliega un calendario que permite elegir la fecha en que se realizó un
     * paso, por defecto tiene la fecha actual.
     */
    private JDateChooser fechaChooser;
    /**
     * Añade un paso a la lista de pasos realizados.
     */
    private JButton agregarBoton;
    /**
     * @see PanelPasoRealizado
     */
    private PanelPasoRealizado panelPasoRealizado;
    /**
     * Contiene los pasos realizados. Sirve para desplazarse sobre los pasos que
     * ya fueron realizados.
     */
    private JScrollPane scroll;
    /**
     * @see VentanaPrincipal#getLista()
     */
    private VentanaPrincipal ventanaPrincipal;
    /**
     * @see TramiteEspecifico#getPasosEspecificos()
     */
    private TramiteEspecifico tramiteEspecifico;
    /**
     * Almacena una copia temporal de los pasos modificados.
     */
    private ArrayList<PasoEspecifico> pasosModificados;
    /**
     * @see Paso#Paso()
     */
    private PasoEspecifico paso;
    private JLabel etiquetaNombre;
    private JPanel panelContenedor;
    private JList lista;

    /**
     * Constructor que recibe el tramite seleccionado y los datos que se
     * obtuvieron al abrir la base de datos. Este constructor se encarga de
     * llenas la lista de pasosModificados que es con la que se trabaja hasta
     * que el usuario presione el boto aceptar.
     *
     * @param ventanaPrincipal Permite obtener todos los datos referentes al
     * trámite actual que se encuentren almacenados en la base de datos.
     * @param tramiteEspecifico Tramite especifico seleccionado.
     * @author Isaac
     */
    public DialogoSeguimiento(VentanaPrincipal ventanaPrincipal, TramiteEspecifico tramiteEspecifico) {
        super(ventanaPrincipal, "Seguimiento", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        this.tramiteEspecifico = tramiteEspecifico;
        pasosModificados = new ArrayList<>();
        paso = new PasoEspecifico();
        for (PasoEspecifico p : tramiteEspecifico.getPasosEspecificos()) {
            PasoEspecifico p2 = new PasoEspecifico();
            p2.setNombrePaso(p.getNombrePaso());
            p2.setNumPaso(p.getNumPaso());
            p2.setDocumento(p.getDocumento());
            p2.setFechaLimite(p.getFechaLimite());
            p2.setFechaRealizacion(p.getFechaRealizacion());
            p2.setRealizado(p.isRealizado());
            p2.setRepeticion(p.getRepeticion());
            System.out.println("Repeticiones antes de almacenar en la copia = " + p.getRepeticion());
            pasosModificados.add(p2);
        }
        for (PasoEspecifico pasoEspecifico : tramiteEspecifico.getPasosEspecificos()) {
            System.out.println(pasoEspecifico.getRepeticion());
        }
        initComponents(tramiteEspecifico);
    }

    public void initComponents(TramiteEspecifico tramiteEspecifico) {
        JLabel etiquetaNombreSolicitante = new JLabel("Nombre del solicitante : ");
        etiquetaNombreSolicitante.setBounds(60, 5, 150, 50);
        add(etiquetaNombreSolicitante);
        nombreSolicitante = new JTextField(20);
        nombreSolicitante.setText(tramiteEspecifico.getValores().get(0)[0]);
        nombreSolicitante.setBounds(210, 15, 530, 25);
        add(nombreSolicitante);

        JLabel etiquetaTitulo = new JLabel("Título : ");
        etiquetaTitulo.setBounds(60, 45, 150, 50);
        add(etiquetaTitulo);
        Titulo = new JTextField(20);
        Titulo.setText(tramiteEspecifico.getValores().get(1)[0]);
        Titulo.setBounds(210, 55, 530, 25);
        add(Titulo);

        JLabel etiquetaFechaInicio = new JLabel("Fecha inicio : ");
        etiquetaFechaInicio.setBounds(60, 85, 150, 50);
        add(etiquetaFechaInicio);
        fechaInicio = new JTextField(20);
        fechaInicio.setText(tramiteEspecifico.getValores().get(2)[0]);
        fechaInicio.setBounds(210, 95, 200, 25);
        add(fechaInicio);

        etiquetaFechaFin = new JLabel("Fecha fin : ");
        etiquetaFechaFin.setBounds(430, 85, 150, 50);
        add(etiquetaFechaFin);
        fechaFin = new JTextField(20);
        fechaFin.setText(tramiteEspecifico.getValores().get(3)[0]);
        fechaFin.setBounds(540, 95, 200, 25);
        add(fechaFin);

        JLabel etiquetaEstado = new JLabel("Estado: ");
        etiquetaEstado.setBounds(60, 125, 150, 50);
        add(etiquetaEstado);
        estado = new JTextField();
        estado.setText(tramiteEspecifico.getValores().get(4)[0]);
        estado.setBounds(210, 135, 200, 25);
        add(estado);

        detallesBoton = new JButton("Detalles");
        detallesBoton.setBounds(800, 30, 150, 30);
        detallesBoton.addActionListener(this);
        detallesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/detalles.png")));
        add(detallesBoton);

        modificarBoton = new JButton("Modificar registro");
        modificarBoton.setBounds(800, 70, 200, 30);
        modificarBoton.addActionListener(this);
        modificarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
        add(modificarBoton);

        eliminarBoton = new JButton("Eliminar registro");
        eliminarBoton.setBounds(800, 110, 200, 30);
        eliminarBoton.addActionListener(this);
        eliminarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/eliminarRegistro.png")));
        add(eliminarBoton);

        JLabel etiquetaPasosRealizados = new JLabel("Pasos realizados : ");
        etiquetaPasosRealizados.setBounds(120, 155, 200, 50);
        etiquetaPasosRealizados.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(etiquetaPasosRealizados);

        JLabel etiquetaNombrePanel = new JLabel("Nombre del paso");
        etiquetaNombrePanel.setBounds(50, 180, 150, 50);
        add(etiquetaNombrePanel);

        JLabel etiquetaFecha = new JLabel("Fecha realizacion");
        etiquetaFecha.setBounds(300, 180, 150, 50);
        add(etiquetaFecha);

        JLabel etiquetaPlantilla = new JLabel("Plantilla");
        etiquetaPlantilla.setBounds(440, 180, 150, 50);
        add(etiquetaPlantilla);

        JLabel etiquetaDocumentoEspecificoPanel = new JLabel("Documento especifico");
        etiquetaDocumentoEspecificoPanel.setBounds(550, 180, 150, 50);
        add(etiquetaDocumentoEspecificoPanel);

        verDocBoton = new JButton("Ver documento      ");
        verDocBoton.setBounds(800, 230, 200, 30);
        verDocBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/verDoc.png")));
        verDocBoton.addActionListener(this);
        add(verDocBoton);

        cargarDocBoton = new JButton("Cargar documento");
        cargarDocBoton.setBounds(800, 270, 200, 30);
        cargarDocBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cargarDoc.png")));
        cargarDocBoton.addActionListener(this);
        add(cargarDocBoton);

        quitarDocBoton = new JButton("Quitar documento ");
        quitarDocBoton.setBounds(800, 310, 200, 30);
        quitarDocBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/quitarDoc.png")));
        quitarDocBoton.addActionListener(this);
        add(quitarDocBoton);

        eliminarPasoBoton = new JButton("Eliminar paso       ");
        eliminarPasoBoton.setBounds(800, 350, 200, 30);
        eliminarPasoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/eliminarPaso.png")));
        eliminarPasoBoton.addActionListener(this);
        add(eliminarPasoBoton);

        JLabel etiquetaPasosDisponibles = new JLabel("Pasos disponibles : ");
        etiquetaPasosDisponibles.setBounds(120, 420, 200, 50);
        etiquetaPasosDisponibles.setFont(new java.awt.Font("Tahoma", 0, 20));
        add(etiquetaPasosDisponibles);

        modeloDescripcion = new DefaultListModel<String>();
        descripcion = new JList<String>(modeloDescripcion);
        descripcion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(descripcion);

        add(creaListaPasosSinRealizar());

        editarBoton = new JButton("Editar");
        editarBoton.setBounds(400, 500, 150, 30);
        editarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/editar.png")));
        editarBoton.addActionListener(this);
        add(editarBoton);

        etiquetaNombre = new JLabel("Nombre: ");
        etiquetaNombre.setBounds(630, 435, 530, 50);
        add(etiquetaNombre);

        JLabel etiquetaFechaRealizacion = new JLabel("Fecha realizacion: ");
        etiquetaFechaRealizacion.setBounds(630, 465, 150, 50);
        add(etiquetaFechaRealizacion);
        fechaChooser = new JDateChooser(new Date());
        fechaChooser.setBounds(630, 500, 100, 30);
        add(fechaChooser);
        agregarBoton = new JButton("Agregar");
        agregarBoton.setBounds(800, 500, 130, 30);
        agregarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/agregar.png")));
        agregarBoton.addActionListener(this);
        add(agregarBoton);

        JLabel etiquetaDocumentoEspecifico = new JLabel("Documento especifico: ");
        etiquetaDocumentoEspecifico.setBounds(630, 520, 150, 50);
        add(etiquetaDocumentoEspecifico);
        datoEspecifico = new JTextField(20);
        datoEspecifico.setEditable(false);
        datoEspecifico.setBounds(630, 560, 200, 25);
        add(datoEspecifico);

        carpetaBoton = new JButton("");
        carpetaBoton.setBounds(850, 550, 40, 35);
        carpetaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/folder.png")));
        carpetaBoton.addActionListener(this);
        add(carpetaBoton);

        verPlantillaBoton = new JButton("ver plantilla");
        verPlantillaBoton.setBounds(630, 590, 150, 30);
        verPlantillaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/verPlantilla.png")));
        verPlantillaBoton.addActionListener(this);
        add(verPlantillaBoton);

        aceptarBoton = new JButton("Aceptar");
        aceptarBoton.setBounds(240, 645, 150, 35);
        aceptarBoton.addActionListener(this);
        aceptarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
        add(aceptarBoton);

        cancelarBoton = new JButton("Cancelar");
        cancelarBoton.setBounds(630, 645, 150, 35);
        cancelarBoton.addActionListener(this);
        cancelarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
        add(cancelarBoton);

        panelPasoRealizado = new PanelPasoRealizado(pasosModificados, ventanaPrincipal);
        scroll = new JScrollPane(panelPasoRealizado);
        scroll.setBounds(50, 220, 700, 200);
        add(scroll);

        nombreSolicitante.setEditable(false);
        Titulo.setEditable(false);
        fechaInicio.setEditable(false);
        fechaFin.setEditable(false);
        estado.setEditable(false);

        ocultarElementos();
        setSize(1040, 730);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    public void mostrarElementos() {
        fechaChooser.setEnabled(true);
        agregarBoton.setEnabled(true);
        datoEspecifico.setEnabled(true);
        carpetaBoton.setEnabled(true);
        verPlantillaBoton.setEnabled(true);
    }

    public void ocultarElementos() {
        fechaChooser.setEnabled(false);
        agregarBoton.setEnabled(false);
        datoEspecifico.setEnabled(false);
        carpetaBoton.setEnabled(false);
        verPlantillaBoton.setEnabled(false);
        datoEspecifico.setText("");
        etiquetaNombre.setText("Nombre: ");
    }

    public void verPlantilla(String ruta) {
        try {
            File path = new File(ruta);
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se ha cargado una plantilla al paso",
                    "Ver plantilla", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelarBoton) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "Esta seguro que desea salir?", "Confirmación",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_NO_OPTION) {
                dispose();
            }
        }
        if (e.getSource() == detallesBoton) {
            new DialogoDetalles(ventanaPrincipal, tramiteEspecifico);
        }
        if (e.getSource() == modificarBoton) {
            new DialogoModificarRegistro(ventanaPrincipal, tramiteEspecifico);
        }
        if (e.getSource() == eliminarBoton) {
            new DialogoEliminarRegistro(ventanaPrincipal);
        }
        if (e.getSource() == aceptarBoton) {
            tramiteEspecifico.setPasosEspecificos(pasosModificados);
            dispose();
        }
        if (e.getSource() == editarBoton) {
            int[] selectedIndices = lista.getSelectedIndices();
            if (selectedIndices.length != 0) {
                if (!"puedeRealizarse".equals(getSecuenciaPasos(selectedIndices[0])) && getSecuenciaPasos(selectedIndices[0]) != null) {
                    JOptionPane.showMessageDialog(this, "El paso tiene seriación, antes debe realizar : \n " + getSecuenciaPasos(selectedIndices[0]),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (getSecuenciaPasos(selectedIndices[0]).equals("puedeRealizarse")) {
                    mostrarElementos();
                    etiquetaNombre.setText("Nombre : " + paso.nombrePasoSeleccionado(selectedIndices[0], pasosModificados));
                    datoEspecifico.setText("");
                    datoEspecifico.setEditable(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un paso especifico",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == agregarBoton) {
            try {
                int[] selectedIndices = lista.getSelectedIndices();
                int posicion = selectedIndices[0];
                if (!"".equals(datoEspecifico.getText()) && datoEspecifico.getText() != null) {
                    guardarDocumentoEspecifico(datoEspecifico.getText(), posicion);
                }
                realizarPaso(posicion);
                resetPanel();
            } catch (ArrayIndexOutOfBoundsException Ex) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un paso especifico de la lista",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == verPlantillaBoton) {
            int[] selectedIndices = lista.getSelectedIndices();
            int posicion = selectedIndices[0];
            if (obtenerPlantilla(posicion).equals("Sin plantilla")) {
                verPlantillaBoton.setEnabled(false);
            } else {
                verPlantilla(obtenerPlantilla(posicion));
            }
        }
        if (e.getSource() == carpetaBoton) {
            int[] selectedIndices = lista.getSelectedIndices();
            int posicion = selectedIndices[0];
            if (isconDocumento(posicion)) {
                datoEspecifico.setEditable(true);
                seleccionarDocumentoEspecifico();
            } else {
                datoEspecifico.setEditable(false);
            }
        }
        if (e.getSource() == eliminarPasoBoton) {
            if (panelPasoRealizado.indiceCheck() != -1) {
                if (getSecuenciaPasosEliminar(panelPasoRealizado.indiceCheck())) {
                    JOptionPane.showMessageDialog(this, "Este paso tiene pasos seriados que ya han sido realizados",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int respuesta = JOptionPane.showConfirmDialog(this,
                            "Se perderá la fecha de realización del paso\n"
                            + "y la documentación asociada a éste,\n¿Desea eliminarlo?", "Advertencia",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        eliminarPasoRealizado(panelPasoRealizado.indiceCheck());
                        resetPanel();
                        ocultarElementos();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe existir al menos un paso para poder eliminar",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == verDocBoton) {
            if (panelPasoRealizado.indiceCheck() != -1) {
                int indiceS = panelPasoRealizado.indiceCheck();
                if (getPasoSeleccionadoRealizado(indiceS).getDocumento() != null && !"".equals(getPasoSeleccionadoRealizado(indiceS).getDocumento())) {
                    File path = new File(getPasoSeleccionadoRealizado(indiceS).getDocumento());
                    try {
                        Desktop.getDesktop().open(path);
                    } catch (IOException ex) {
                        Logger.getLogger(DialogoSeguimiento.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No hay documento",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == cargarDocBoton) {
            cargarDocumento();
        }

        if (e.getSource() == quitarDocBoton) {
            if (panelPasoRealizado.indiceCheck() != -1) {
                int indiceS = panelPasoRealizado.indiceCheck();
                if (getPasoSeleccionadoRealizado(indiceS).getDocumento() != null && !getPasoSeleccionadoRealizado(indiceS).getDocumento().equals("")) {
                    int respuesta = JOptionPane.showConfirmDialog(this,
                            "Se eliminará el documento asociado este paso,\n¿Desea continuar?", "Advertencia",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        File path = new File(getPasoSeleccionadoRealizado(indiceS).getDocumento());
                        path.delete();
                        getPasoSeleccionadoRealizado(indiceS).setDocumento(null);
                        JOptionPane.showMessageDialog(this, "Documento eliminado",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        resetPanel();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No hay documento",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún paso",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Método para eliminar un paso realizado. Éste método elimina un paso
     * realizado y coloca el tramite al último estado de algún paso que se
     * encuentre realizado y que tenga cambio de estado, en caso de eliminar
     * todos los pasos el método coloca el estado con su valor por defecto.
     *
     * @param indice Recibe la posición del elemento a eliminar
     * @see #getPasoSeleccionadoRealizado(int)
     * @see TramiteEspecifico#modificarCampo(dominio.Campo, java.lang.String[])
     * @author Cresencio
     */
    public void eliminarPasoRealizado(int indice) {
        PasoEspecifico pEliminar = getPasoSeleccionadoRealizado(indice);
        //Si el paso contaba con documentación, la documentación también se elimina.
        if (getPasoSeleccionadoRealizado(indice).getDocumento() != null && !"".equals(getPasoSeleccionadoRealizado(indice).getDocumento())) {
            File path = new File(getPasoSeleccionadoRealizado(indice).getDocumento());
            path.delete();
        }
        if (pEliminar.getRepeticion() != 0) {
            pEliminar.setRealizado(false);
        } else {
            pasosModificados.remove(pEliminar);
        }
        //Determinar el cambio de estado del tramite en caso de que el paso eliminado tuviera cambio
        //Si se eliminan todos los pasos, se obtendrá el valor por defecto
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            for (PasoEspecifico pe : pasosModificados) {
                if (pe.isRealizado() && pe.getNombrePaso().contains(p.getNombrePaso()) && p.isConCambioEstado()) {
                    int posicion = tramiteEspecifico.buscarCampo("Estado");
                    String[] val = new String[1];
                    val[0] = p.getEstado();
                    tramiteEspecifico.modificarCampo(tramiteEspecifico.obtenerCampo(posicion), val);
                }
                int contador = 0;
                for (PasoEspecifico pe1 : pasosModificados) {
                    if (!pe1.isRealizado()) {
                        contador++;
                    }
                }
                if (contador == pasosModificados.size()) {
                    String[] opciones;
                    int posicion = tramiteEspecifico.buscarCampo("Estado");
                    StringTokenizer tokens = new StringTokenizer(tramiteEspecifico.getCampos().get(posicion).getValorDefecto(), "/");
                    opciones = new String[tokens.countTokens()];
                    int indice2 = 0;
                    while (tokens.hasMoreTokens()) {
                        opciones[indice2] = tokens.nextToken().trim();
                        indice2++;
                    }
                    tramiteEspecifico.modificarCampo(tramiteEspecifico.obtenerCampo(posicion), opciones);
                }
            }
        }
    }

    public String obtenerPlantilla(int indice) {
        int numPasosFaltantes = 0;
        String plantilla = null;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                numPasosFaltantes++;
            }
        }
        String[] todo_pasos = new String[numPasosFaltantes];
        int i = 0;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            if (todo_pasos[indice].contains(p.getNombrePaso()) && p.isConPlantilla()) {
                plantilla = p.getPlantilla();
            }
            if (todo_pasos[indice].contains(p.getNombrePaso()) && p.isConPlantilla() == false) {
                plantilla = "Sin plantilla";
            }
        }
        return plantilla;
    }

    public boolean isconDocumento(int indice) {
        int numPasosFaltantes = 0;
        boolean conDocumento = false;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                numPasosFaltantes++;
            }
        }
        String[] todo_pasos = new String[numPasosFaltantes];
        int i = 0;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            if (todo_pasos[indice].contains(p.getNombrePaso()) && p.isConDocumento()) {
                conDocumento = p.isConDocumento();
            }
            if (todo_pasos[indice].contains(p.getNombrePaso()) && p.isConDocumento() == false) {
                conDocumento = p.isConDocumento();
            }
        }
        return conDocumento;
    }

    private void seleccionarDocumentoEspecifico() {
        FileDialog fd = new FileDialog(new Frame(), "Seleccionar documento", FileDialog.LOAD);
        if (datoEspecifico.getText().isEmpty()) {
            fd.setDirectory(System.getProperty("user.dir"));
            fd.setFile("*.pdf; *.doc; *.docx");
        } else {
            fd.setFile(datoEspecifico.getText());
        }
        fd.setVisible(true);
        if (fd.getFile() == null) {
            datoEspecifico.setText("");
        } else {
            datoEspecifico.setText(fd.getDirectory() + fd.getFile());
        }
        datoEspecifico.setEnabled(false);
    }

    private void cargarDocumento() {
        if (panelPasoRealizado.indiceCheck() != -1 && !panelPasoRealizado.obtenerNombreBotonDocumento(panelPasoRealizado.indiceCheck()).equals("Sin documento")) {
            int indiceS = panelPasoRealizado.indiceCheck();
            if (getPasoSeleccionadoRealizado(indiceS).getDocumento() == null || "".equals(getPasoSeleccionadoRealizado(indiceS).getDocumento())) {
                System.out.println("Indice seleccionado: " + panelPasoRealizado.indiceCheck());
                FileDialog fd = new FileDialog(new Frame(), "Seleccionar documento ", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir"));
                fd.setFile("*.pdf; *.doc; *.docx");
                fd.setVisible(true);
                if (fd.getFile() != null) {
                    JOptionPane.showMessageDialog(this, "El sistema comenzará a cargar el documento."
                            + "\n Es necesario esperar un momento",
                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    String ruta = fd.getDirectory() + fd.getFile();
                    String extension = ruta.substring(ruta.lastIndexOf('.'));
                    String tramitante = tramiteEspecifico.obtenerValores(0)[0];
                    String rutaNueva;

                    rutaNueva = this.ventanaPrincipal.getLista().getBd().getDirectorio() + "doc_" + tramitante + "_" + getPasoSeleccionadoRealizado(indiceS).getNombrePaso() + extension;
                    System.out.println("Ruta nueva " + rutaNueva);
                    int i = 2;
                    while (true) {
                        File archivo = new File(rutaNueva);
                        if (archivo.exists()) {
                            rutaNueva = this.ventanaPrincipal.getLista().getBd().getDirectorio() + "doc_" + tramitante + "_" + i + getPasoSeleccionadoRealizado(indiceS).getNombrePaso() + extension;

                        } else {
                            break;
                        }
                        i++;
                    }

                    UtileriasArchivo.copiarArchivo(ruta, rutaNueva);
                    getPasoSeleccionadoRealizado(indiceS).setDocumento(rutaNueva);
                    JOptionPane.showMessageDialog(this, "Documento cargado exitosamente",
                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    resetPanel();
                }
            } else {
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "Ya existe un documento, ¿Desea Reemplazarlo?", "Advertencia",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    System.out.println("Indice seleccionado: " + panelPasoRealizado.indiceCheck());
                    FileDialog fd = new FileDialog(new Frame(), "Seleccionar documento ", FileDialog.LOAD);
                    fd.setDirectory(System.getProperty("user.dir"));
                    fd.setFile("*.pdf; *.doc; *.docx");
                    fd.setVisible(true);
                    if (fd.getFile() != null) {
                        JOptionPane.showMessageDialog(this, "El sistema comenzará a cargar el documento."
                                + "\n Es necesario esperar un momento",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        String ruta = fd.getDirectory() + fd.getFile();
                        String extension = ruta.substring(ruta.lastIndexOf('.'));
                        String tramitante = tramiteEspecifico.obtenerValores(0)[0];
                        String rutaNueva;
                        rutaNueva = this.ventanaPrincipal.getLista().getBd().getDirectorio() + "doc_" + tramitante + "_" + getPasoSeleccionadoRealizado(indiceS).getNombrePaso() + extension;
                        UtileriasArchivo.copiarArchivo(ruta, rutaNueva);
                        getPasoSeleccionadoRealizado(indiceS).setDocumento(rutaNueva);
                        JOptionPane.showMessageDialog(this, "Documento cargado exitosamente",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        resetPanel();
                    } else if (respuesta == JOptionPane.NO_OPTION) {
                        dispose();
                    }
                }
            }
        }
    }

    private void guardarDocumentoEspecifico(String ruta, int indice) {
        int numPasosFaltantes = 0;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                numPasosFaltantes++;
            }
        }
        String[] todo_pasos = new String[numPasosFaltantes];
        int i = 0;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        JOptionPane.showMessageDialog(this, "El sistema comenzará a cargar el documento."
                + "\n Es necesario esperar un momento",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
        String extension = ruta.substring(ruta.lastIndexOf('.'));
        String tramitante = tramiteEspecifico.obtenerValores(0)[0];
        String rutaNueva;
        rutaNueva = this.ventanaPrincipal.getLista().getBd().getDirectorio() + "doc_" + tramitante + "_" + todo_pasos[indice] + extension;
        System.out.println("Ruta nueva " + rutaNueva);
        UtileriasArchivo.copiarArchivo(ruta, rutaNueva);
        getPasoSeleccionadoSinRealizar(indice).setDocumento(rutaNueva);
        JOptionPane.showMessageDialog(this, "Documento cargado exitosamente",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    private PasoEspecifico getPasoSeleccionadoRealizado(int indice) {
        int i = 0;
        for (PasoEspecifico pe : pasosModificados) {
            if (pe.isRealizado()) {
                if (i == indice) {
                    return pe;
                }
                i++;
            }
        }
        return null;
    }

    private PasoEspecifico getPasoSeleccionadoSinRealizar(int indice) {
        int i = 0;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                if (i == indice) {
                    return pe;
                }
                i++;
            }
        }
        return null;
    }

    public void resetPanel() {
        remove(scroll); // eliminamos paneles
        remove(scrollDescripcion);
        add(creaListaPasosSinRealizar());

        panelPasoRealizado = new PanelPasoRealizado(pasosModificados, ventanaPrincipal);
        scroll = new JScrollPane(panelPasoRealizado);
        scroll.setBounds(50, 220, 700, 200);
        add(scroll); //añadimos los nuevos paneles
        add(scrollDescripcion);

        scroll.revalidate(); // restablece panel basado en la nueva lista de componentes
        scrollDescripcion.revalidate();

        scroll.repaint();   // pinta el panel
        scrollDescripcion.repaint();
    }

    /**
     * Esta función sirve para saber que pasos de los que no han sido realizados
     * son seriados y para cambiar el estado de un trámite en caso de que la
     * realización del paso tenga cambio de estado.
     *
     * @param indice Indica la posición del paso a realizar y se ocupa para
     * obtener la secuencia dentro del método.
     * @return El nombre del paso requerido en caso de tener secuencia, o la
     * autorización de realización.
     * @author Cresencio
     */
    private String getSecuenciaPasos(int indice) {
        String secuencia = null;
        int i = 0;
        int j = 0;
        int k = 0;
        String nombrePasos[] = new String[ventanaPrincipal.getLista().getTramite().getPasos().size()];
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            nombrePasos[i] = p.getNombrePaso();
            i++;
        }
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                j++;
            }
        }
        String nombrePasosSinRealizar[] = new String[j];
        /*
        Se almacena en  arreglos el nombre de los pasos de la lista de Pasos del trámite y 
        el nombre de los pasos especificos que no han sido realizados para compararlos, ya que
        cada una de las listas tiene distintos atributos que se consultaran y que son necesarios 
        para validar la secuencia de un paso.
         */
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                nombrePasosSinRealizar[k] = pe.getNombrePaso();
                k++;
            }
        }
        //Se itera la lista de pasos especificos del trámite actual para realizar las validaciones
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                for (String nombrePaso : nombrePasos) {
                    /*Dentro de la iteración de la lista de pasos especificos se valida que los pasos disponibles en la vista
                  no hayan sido realizados, y también que coincidan con el que se seleccionó al momento de presionar el boton
                  editar.
                     */
                    if (pe.getNombrePaso().contains(nombrePaso) && nombrePasosSinRealizar[indice].equals(pe.getNombrePaso())) {
                        //Si la condicion anterior se cumple, lo siguiente es verificar que el paso tenga secuencia
                        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
                            if (p.getSecuencia() != null && pe.getNombrePaso().contains(p.getNombrePaso())
                                    && pe.getNombrePaso().contains(nombrePasosSinRealizar[indice])) {
                                String pasoAnterior = p.getSecuencia().getNombrePaso();
                                for (PasoEspecifico pe1 : pasosModificados) {
                                    if (pe1.getNombrePaso().contains(pasoAnterior)) {
                                        //Se verifica que el paso requerido se encuentre realizado
                                        if (pe1.isRealizado()) {
                                            secuencia = "puedeRealizarse";
                                            //Si el paso tiene cambio de estado en el trámite, se lleva a cabo.
                                            if (pe.getNombrePaso().contains(p.getNombrePaso()) && p.isConCambioEstado()) {
                                                int posicion = tramiteEspecifico.buscarCampo("Estado");
                                                String[] val = new String[1];
                                                val[0] = p.getEstado();
                                                tramiteEspecifico.modificarCampo(tramiteEspecifico.obtenerCampo(posicion), val);
                                            }
                                        }
                                        if (pe1.isRealizado() == false) {
                                            secuencia = pasoAnterior;
                                        }
                                    }
                                }
                            }
                            //Si no tiene secuencia entonces retorna la instrucción que permitirá su realización
                            if (p.getSecuencia() == null && pe.getNombrePaso().contains(p.getNombrePaso()) && pe.getNombrePaso().contains(nombrePasosSinRealizar[indice])) {
                                secuencia = "puedeRealizarse";
                                if (pe.getNombrePaso().contains(p.getNombrePaso()) && p.isConCambioEstado()) {
                                    int posicion = tramiteEspecifico.buscarCampo("Estado");
                                    String[] val = new String[1];
                                    val[0] = p.getEstado();
                                    tramiteEspecifico.modificarCampo(tramiteEspecifico.obtenerCampo(posicion), val);
                                }
                            }
                        }
                    }
                }
            }
        }
        return secuencia;
    }

    /**
     *
     * @param indice recibe el numero de elemento seleccionado para eliminarse
     * de la lista de pasos realizados
     * @return Verdadero o falso dependiendo de la seriación de pasos, si el
     * paso tiene pasos seriados realizados retorna falso y no se realiza la
     * eliminación.
     * @author Cresencio
     */
    public boolean getSecuenciaPasosEliminar(int indice) {
        /* La cadena secuencia, almacena el paso seleccionado y se utiliza para verificar
           si el paso elegido es seriado y verificar si además los pasos seriados han sido realizados.
         */
        String secuencia = getPasoSeleccionadoRealizado(indice).getNombrePaso();
        boolean isPasoSeriado = false;
        for (PasoEspecifico pe : pasosModificados) {
            //Se comprueba que en cada paso realizado, no tenga como secuencia el paso que se desea eliminar.
            if (pe.isRealizado()) {
                for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
                    if (pe.getNombrePaso().contains(p.getNombrePaso()) && p.getSecuencia() != null) {
                        if (secuencia.contains(p.getSecuencia().getNombrePaso())) {
                            isPasoSeriado = true;
                        }
                    }
                }
            }
        }
        return isPasoSeriado;
    }

    /**
     * Método que realiza un paso si ha pasado todas las validaciones de
     * seriación.
     *
     * @param indice Recibe el número del paso que se realizará.
     * @author Cresencio
     */
    public void realizarPaso(int indice) {
        Date d = new Date(fechaChooser.getDate().getTime());
        PasoEspecifico pe = getPasoSeleccionadoSinRealizar(indice);
        System.out.println("Repeticiones del paso " + pe.getNombrePaso() + " Numero de veces " + pe.getRepeticion());
        if (pe.getRepeticion() > 0) {
            pe.setRealizado(true);
            pe.setFechaRealizacion(d);
            pe.setDocumento(datoEspecifico.getText());
        }
        //Si el paso especifico es indefinido, se crea un objeto de tipo PasoEspecifico para añadirlo
        //a pasos realizados para manejarlo de forma independiente y evitar sobreescritura.
        if (pe.getRepeticion() == 0) {
            PasoEspecifico pNuevo = new PasoEspecifico();
            pNuevo.setNombrePaso(pe.getNombrePaso());
            pNuevo.setNumPaso(pe.getNumPaso());
            pNuevo.setRealizado(true);
            pNuevo.setDocumento(datoEspecifico.getText());
            pNuevo.setFechaRealizacion(d);
            pNuevo.setFechaLimite(null);
            pNuevo.setRepeticion(0);
            pasosModificados.add(pNuevo);
        }
        ocultarElementos();
    }

    private JScrollPane creaListaPasosSinRealizar() {
        panelContenedor = new JPanel();
        int numPasos = 0;
        panelContenedor.setLayout(new GridLayout(numPasos, 2, 3, 3));
        panelContenedor.setBounds(115, 480, 250, 140);
        //Dependiendo del numero de pasos sin realizar, el contador incrementa.
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                numPasos++;
            }
        }
        String todo_pasos[] = new String[numPasos];
        int i = 0;
        for (PasoEspecifico pe : pasosModificados) {
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        //Se crea la lista con la que se podrá interactuar para modificar pasos.
        //La lista se crea a partir de un arreglo donde se almacenaron los nombres de los pasos sin realizar.
        lista = new JList(todo_pasos);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setBackground(new Color(238, 238, 238));
        lista.setBorder(new BorderUIResource.LineBorderUIResource(new Color(238, 238, 238)));
        lista.setBounds(115, 480, 250, 140);
        lista.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarBoton.doClick();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    ocultarElementos();
                }
            }
        });
        panelContenedor.add(lista);
        scrollDescripcion = new JScrollPane(panelContenedor);
        scrollDescripcion.setBounds(80, 470, 250, 150);
        return scrollDescripcion;
    }

}
