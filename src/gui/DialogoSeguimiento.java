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
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.BorderUIResource;
import lib.UtileriasArchivo;

/**
 * Lleva el control de seguimiento de un trámite.
 * <p>
 * Muestra un diálogo con todos los datos de un trámite específico. Sirve para
 * llevar el control de seguimiento de los pasos que el tramitante va
 * realizando.
 * </p>
 * <p>
 * El diálogo muestra tres paneles para dividir por secciones; en la primer
 * sección se encuentran los detalles del trámite, en la segunda los pasos
 * realizados y en la tercer sección los pasos que aún no se realizan.
 * </p>
 *
 * @author Isaac
 * @author Cresencio
 */
public class DialogoSeguimiento extends JDialog implements ActionListener {

    /**
     * Versión de serialización de clase.
     */
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
     * El botón muestra un diálogo donde se pueden apreciar los detalles del
     * trámite. El diálogo detalles muestra datos no editables del trámite.
     */
    private JButton detallesBoton;
    /**
     * Inicia el diálogo modificar registro.
     */
    private JButton modificarBoton;
    /**
     * Inicia el diálogo eliminar registro.
     */
    private JButton eliminarBoton;
    /**
     * Sirve para abrir el documento de un paso realizado
     */
    private JButton verDocBoton;
    /**
     * Botón para cargar un documento específico.
     */
    private JButton cargarDocBoton;
    /**
     * Botón para quitar el documento asociado a un trámite realizado.
     */
    private JButton quitarDocBoton;
    /**
     * Botón para eliminar un paso seleccionado.
     */
    private JButton eliminarPasoBoton;
    /**
     * Contiene los nombres de todos los pasos sin realizar. En esta lista están
     * almacenados todos los pasos que no han sido realizados.
     */
    private JList<String> descripcion;
    /**
     * Contiene la lista de pasos sin realizar. Este modelo contiene la lista
     * descripcion donde se almacenan los nombres de pasos no realizados.
     */
    private DefaultListModel<String> modeloDescripcion;
    /**
     * Scroll para desplazarse entre pasos no realizados. Si la lista de pasos
     * supera el número admitido en la interfaz se añade un scroll.
     */
    private JScrollPane scrollDescripcion;
    /**
     * Botón para editar la fecha de realización de un paso y habilitar la
     * opcion de cargar un documento.
     */
    private JButton editarBoton;
    /**
     * Almacena la ruta del documento cargado.
     */
    private JTextField datoEspecifico;
    /**
     * Permite seleccionar un documento específico.
     */
    private JButton carpetaBoton;
    /**
     * Abre el documento establecido como plantilla del paso.
     */
    private JButton verPlantillaBoton;
    /**
     * Guarda los cambios realizados en memoria y regresa al diálogo buscar
     * segumiento.
     */
    private JButton aceptarBoton;
    /**
     * Descarta los cambios realizados.
     */
    private JButton cancelarBoton;
    /**
     * Despliega un calendario, por defecto tiene la fecha actual. Este
     * calendario permite elegir la fecha en que se realizó un paso
     */
    private JDateChooser fechaChooser;
    /**
     * Añade un paso a la lista de pasos realizados.
     */
    private JButton agregarBoton;
    /**
     * Muestra un panel con una tabla de pasos que han sido realizados.
     */
    private PanelPasoRealizado panelPasoRealizado;
    /**
     * Permite desplazarse en la tabla de pasos. Sirve para desplazarse sobre
     * los pasos que ya fueron realizados.
     */
    private JScrollPane scroll;
    /**
     * Ubica el diálogo dentro de la ventana principal del sistema. Se obtiene
     * todos los datos del trámite específico y del trámite.
     */
    private VentanaPrincipal ventanaPrincipal;
    /**
     * Referencia al trámite en el que se está trabajando.
     */
    private TramiteEspecifico tramiteEspecifico;
    /**
     * Almacena una copia temporal de los pasos.
     */
    private ArrayList<PasoEspecifico> pasosModificados;
    /**
     * Almacena los pasos específicos del trámite.
     */
    private PasoEspecifico paso;
    /**
     * Etiqueta nombre. Almacena el nombre del solicitante.
     */
    private JLabel etiquetaNombre;
    /**
     * Almacena los elementos visuales correspondientes al paso. En este panel
     * están contenidos los elementos de la tercer sección que corresponde a los
     * pasos sin realizar.
     */
    private JPanel panelContenedor;
    /**
     * Almacena cada uno de los nombres de los pasos específicos. Ésta lista
     * contiene los nombres de los pasos específicos y es utilizada para saber
     * la posición del elemento seleccionado al momento de editar.
     */
    private JList<String> lista;

    /**
     * Variable para identificar si se realizó alguna modificación.
     */
    private Boolean modificado = false;

    /**
     * Constructor que recibe el trámite seleccionado y los datos que se
     * obtuvieron al abrir la base de datos.
     * <p>
     * Este constructor se encarga de llenar la lista
     * <code>pasosModificados</code> que es con la que se trabaja hasta que el
     * usuario presione el botón aceptar. El constructor crea una copia de los
     * pasos específicos del trámite para trabajar, si se decide descartar los
     * cambios realizados la copia se pierde y se continúa con los pasos
     * originales.
     * </p>
     *
     * @param ventanaPrincipal Permite obtener todos los datos referentes al
     * trámite actual que se encuentren almacenados en la base de datos y ubicar
     * el diálogo en la ventana principal.
     * @param tramiteEspecifico Trámite especifico actual.
     *
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
            p2.setIdPasoEsp(p.getIdPasoEsp());
            p2.setNombrePaso(p.getNombrePaso());
            p2.setNumPaso(p.getNumPaso());
            p2.setDocumento(p.getDocumento());
            p2.setFechaLimite(p.getFechaLimite());
            p2.setFechaRealizacion(p.getFechaRealizacion());
            p2.setRealizado(p.isRealizado());
            p2.setRepeticion(p.getRepeticion());
            p2.setCambio(false);
            p2.setNuevo(false);
            pasosModificados.add(p2);
        }
        initComponents(tramiteEspecifico);
    }

    /**
     * Método que inicializa los elementos visuales del diálogo. Recibe el
     * trámite con el que se está trabajando para llenar los campos vacíos de la
     * interfaz, desde ésta vista se lleva a cabo el control de los pasos
     * específicos.
     *
     * @param tramiteEspecifico hace referencia al registro del trámite con el
     * que se está trabajando.
     */
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

    /**
     * Habilita elementos de la GUI de la tercer sección. Hasta que el usuario
     * selecciona un elemento de la lista de pasos no realizados se habilitan
     * los elementos, ya que no pueden estar activos si no se seleccionó algún
     * paso específico.
     */
    public void mostrarElementos() {
        fechaChooser.setEnabled(true);
        agregarBoton.setEnabled(true);
        datoEspecifico.setEnabled(true);
        carpetaBoton.setEnabled(true);
        verPlantillaBoton.setEnabled(true);
    }

    /**
     * Deshabilita elementos visuales de la tercer sección(pasos no realizados).
     * Mientras el usuario no haga selección de algún elemento de la lista de
     * pasos sin realizar,los botones, calendario y todo lo demás se mantienen
     * deshabilitados.
     */
    public void ocultarElementos() {
        fechaChooser.setEnabled(false);
        agregarBoton.setEnabled(false);
        datoEspecifico.setEnabled(false);
        carpetaBoton.setEnabled(false);
        verPlantillaBoton.setEnabled(false);
        datoEspecifico.setText("");
        etiquetaNombre.setText("Nombre: ");
    }

    /**
     * Método para abrir la plantilla establecida para el paso. Permite
     * abrir/ver el documento que se haya establecido como plantilla, recibe la
     * ruta donde se encuentra almacenado el documento e intenta abrir, en caso
     * de que el documento no existiera se muestra un diálogo indicando el
     * error.
     *
     * @param ruta Ruta donde se encuentra almacenada la plantilla. .
     */
    public void verPlantilla(String ruta) {
        try {
            File path = new File(ruta);
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se ha cargado una plantilla al paso",
                    "Ver plantilla", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método que escucha los eventos de los botones de
     * <code>DialogoSeguimiento</code>.
     * <p>
     * Este metodo ejecuta las acciones/eventos que se hayan establecido para un
     * determinado boton.
     * </p>
     *
     * @param e Almacena las acciones que el boton invoque cuando el usuario
     * interactúa con algún boton.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelarBoton) {
            cancelar();
        }
        if (e.getSource() == detallesBoton) {
            new DialogoDetalles(ventanaPrincipal, tramiteEspecifico);
        }
        if (e.getSource() == modificarBoton) {
            modificar();
        }
        if (e.getSource() == eliminarBoton) {
            eliminarRegistro();
        }
        if (e.getSource() == aceptarBoton) {
            aceptar();
        }
        if (e.getSource() == editarBoton) {
            editar();
        }
        if (e.getSource() == agregarBoton) {
            agregar();
        }
        if (e.getSource() == verPlantillaBoton) {
            verPlantillaBtn();
        }
        if (e.getSource() == carpetaBoton) {
            carpetaBtn();
        }
        if (e.getSource() == eliminarPasoBoton) {
            eliminarBtn();
        }
        if (e.getSource() == verDocBoton) {
            verDocumento();
        }
        if (e.getSource() == cargarDocBoton) {
            cargarDocumento();
        }

        if (e.getSource() == quitarDocBoton) {
            quitarDocumento();
        }

    }

    /**
     * Método para almacenar la información de los pasos específicos del
     * trámite.
     *
     * .
     */
    private void aceptar() {
        if (getModificado()) {
            JOptionPane.showMessageDialog(this, "Los cambios han sido almacenados\nexitosamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
        tramiteEspecifico.setPasosEspecificos(pasosModificados);
        ventanaPrincipal.getLista().setHayCambios(true);
        dispose();
    }

    /**
     * Método para eliminar un trámite específico. Dentro de este método se
     * valida si el usuario ha realizado cambios y se le advierte que puede
     * haber pérdida de datos si no guarda los cambios.
     *
     * .
     */
    private void eliminarRegistro() {
        if (getModificado()) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios realizados antes de continuar?", "Advertencia", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmacion == JOptionPane.YES_OPTION) {
                aceptarBoton.doClick();
                new DialogoEliminarRegistro(ventanaPrincipal);
            }
            if (confirmacion == JOptionPane.NO_OPTION) {
                dispose();
                new DialogoEliminarRegistro(ventanaPrincipal);
            }
        } else {
            dispose();
            new DialogoEliminarRegistro(ventanaPrincipal);
        }
    }

    /**
     * Método para modificar un trámite específico. Dentro de este método se
     * valida si el usuario ha realizado cambios y se le advierte que puede
     * haber pérdida de datos si no guarda los cambios.
     *
     * .
     */
    private void modificar() {
        if (getModificado()) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios realizados antes de continuar?", "Advertencia", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmacion == JOptionPane.YES_OPTION) {
                aceptarBoton.doClick();
                new DialogoModificarRegistro(ventanaPrincipal, tramiteEspecifico);
            }
            if (confirmacion == JOptionPane.NO_OPTION) {
                dispose();
                new DialogoModificarRegistro(ventanaPrincipal, tramiteEspecifico);
            }
        } else {
            dispose();
            new DialogoModificarRegistro(ventanaPrincipal, tramiteEspecifico);
        }
    }

    /**
     * Método para eliminar el documento cargado en el paso seleccionado.
     * Permite eliminar el archivo del documento que se haya asociado a un paso
     * específico realizado.
     *
     * @see PanelPasoRealizado#posicionMarcada() .
     */
    private void quitarDocumento() {
        if (panelPasoRealizado.posicionMarcada() != -1) {
            int indiceS = panelPasoRealizado.posicionMarcada();
            if (getPasoSeleccionadoRealizado(indiceS).getDocumento() != null && !getPasoSeleccionadoRealizado(indiceS).getDocumento().equals("")) {
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "Se eliminará el documento asociado este paso,\n¿Desea continuar?", "Advertencia",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    File path = new File(getPasoSeleccionadoRealizado(indiceS).getDocumento());
                    path.delete();
                    getPasoSeleccionadoRealizado(indiceS).setDocumento(null);
                    getPasoSeleccionadoRealizado(indiceS).setCambio(true);
                    JOptionPane.showMessageDialog(this, "Documento eliminado",
                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    setModificado(true);
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

    /**
     * Permite abrir el documento específico asociado a un paso. Mediante la
     * posición del paso marcado, se determina que documento abrir(En caso de
     * que el paso tenga documento).
     *
     * @see PanelPasoRealizado#posicionMarcada()
     * @throws IOException Se genera cuando existe la ruta(En la base de datos o
     * en memoria) pero el archivo se daño o se ha eliminado. .
     */
    private void verDocumento() {
        if (panelPasoRealizado.posicionMarcada() != -1) {
            int indiceS = panelPasoRealizado.posicionMarcada();
            if (getPasoSeleccionadoRealizado(indiceS).getDocumento() != null && !"".equals(getPasoSeleccionadoRealizado(indiceS).getDocumento())) {
                try {
                    File path = new File(getPasoSeleccionadoRealizado(indiceS).getDocumento());
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
     * Elimina el paso seleccionado. En caso de que se hayan realizado pasos que
     * requieran del que se desea eliminar (que tengan seriación) debe de
     * generar un mensaje de error.
     *
     * .
     */
    private void eliminarBtn() {
        if (panelPasoRealizado.posicionMarcada() != -1) {
            if (getSecuenciaPasosEliminar(panelPasoRealizado.posicionMarcada())) {
                JOptionPane.showMessageDialog(this, "Este paso tiene pasos seriados que ya han sido realizados",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "Se perderá la fecha de realización del paso\n"
                        + "y la documentación asociada a este,\n¿Desea eliminarlo?", "Advertencia",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    setModificado(true);
                    eliminarPasoRealizado(panelPasoRealizado.posicionMarcada());
                    resetPanel();
                    ocultarElementos();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe existir al menos un paso para poder eliminar",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre una ventana para seleccionar un archivo específico. Si el paso que
     * se está editando tiene documento específico, se habilita el campo para
     * almacenar la ruta del archivo.
     *
     * .
     */
    private void carpetaBtn() {
        int[] selectedIndices = lista.getSelectedIndices();
        int posicion = selectedIndices[0];
        if (isconDocumento(posicion)) {
            datoEspecifico.setEditable(true);
            seleccionarDocumentoEspecifico();
        } else {
            datoEspecifico.setEditable(false);
        }
    }

    /**
     * Abre la plantilla para el paso seleccionado. Si el paso no tiene
     * plantilla, entonces el boton se deshabilita, de lo contrario se abre el
     * documento de la plantilla.
     *
     * .
     */
    private void verPlantillaBtn() {
        int[] selectedIndices = lista.getSelectedIndices();
        int posicion = selectedIndices[0];
        if (obtenerPlantilla(posicion).equals("Sin plantilla")) {
            verPlantillaBoton.setEnabled(false);
        } else {
            verPlantilla(obtenerPlantilla(posicion));
        }
    }

    /**
     * Añade un paso a la lista de pasos realizados. Este método coloca un paso
     * en la tabla de pasos realizados siempre y cuando el paso esté
     * seleccionado.
     *
     * @throws ArrayIndexOutOfBoundsException Se genera cuando no se ha
     * seleccionado ningún paso de la lista y el índice que se selecciona es -1.
     * .
     */
    private void agregar() {
        try {
            int[] selectedIndices = lista.getSelectedIndices();
            int posicion = selectedIndices[0];
            if (!"".equals(datoEspecifico.getText()) && datoEspecifico.getText() != null) {
                guardarDocumentoEspecifico(datoEspecifico.getText(), posicion);
            }
            setModificado(true);
            realizarPaso(posicion);
            estado.setText(tramiteEspecifico.getValores().get(4)[0]);
            resetPanel();
        } catch (ArrayIndexOutOfBoundsException Ex) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un paso especifico de la lista",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para editar un paso específico no realizado.
     * <p>
     * Siempre y cuando el paso seleccionado no tenga seriación para poder
     * realizarse, este boton habilitará las opciones para modificar la fecha en
     * que el paso fue realizado, permite ver la plantilla(en caso de que
     * exista) y agregar la ruta de un documento específico(en caso de haber
     * establecido que ese paso puede tener documento)</p>
     *
     *
     */
    private void editar() {
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

    /**
     * Descarta los cambios hechos en el diálogo seguimiento y regresa al
     * diálogo buscar seguimiento.
     * <p>
     * Todos lo cambios realizados a los pasos son ignorados. Si ha habido
     * algúna modificación, se pide una confirmación de que realmente desea
     * descartar cambios, si no hubo ninguna modificación, la ventana se cierra
     * y regresa al diálogo <code>DialogoBuscarSeguimiento</code>.
     * </p>
     *
     * .
     */
    private void cancelar() {
        if (getModificado()) {
            int respuesta2 = JOptionPane.showConfirmDialog(this, "Se perderán los cambios realizados. ¿Desea continuar?", "Aviso",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta2 == JOptionPane.YES_OPTION) {
                dispose();
            }
        } else {
            dispose();
        }

    }

    /**
     * Método para eliminar un paso realizado. Este método elimina un paso
     * realizado y coloca el trámite al último estado de algún paso que se
     * encuentre realizado y que tenga cambio de estado, en caso de eliminar
     * todos los pasos el método coloca el estado con su valor por defecto.
     *
     * @param indice Recibe la posición del elemento a eliminar
     * @see #getPasoSeleccionadoRealizado(int)
     * @see TramiteEspecifico#modificarCampo(dominio.Campo, java.lang.String[])
     *
     */
    public void eliminarPasoRealizado(int indice) {
        PasoEspecifico pEliminar = getPasoSeleccionadoRealizado(indice);
        //Si el paso contaba con documentación, la documentación también se elimina.
        if (getPasoSeleccionadoRealizado(indice).getDocumento() != null && !"".equals(getPasoSeleccionadoRealizado(indice).getDocumento())) {
            File path = new File(getPasoSeleccionadoRealizado(indice).getDocumento());
            path.delete();
        }
        if (pEliminar.getRepeticion() != 0) {
            pEliminar.setDocumento(null);
            pEliminar.setFechaRealizacion(null);
            pEliminar.setRealizado(false);
            pEliminar.setCambio(true);
        } else {
            ventanaPrincipal.getLista().getPasosBasura().add(pEliminar);
            pasosModificados.remove(pEliminar);
        }
        //Determinar el cambio de estado del trámite en caso de que el paso eliminado tuviera cambio
        //Si se eliminan todos los pasos, se obtendrá el valor por defecto
        for (Paso p : ventanaPrincipal.getLista().getTramite().getPasos()) {
            for (PasoEspecifico pe : pasosModificados) {
                if (pe.isRealizado() && pe.getNombrePaso().contains(p.getNombrePaso()) && p.isConCambioEstado()) {
                    int posicion = tramiteEspecifico.buscarCampo("Estado");
                    String[] val = new String[1];
                    val[0] = p.getEstado();
                    tramiteEspecifico.modificarCampo(tramiteEspecifico.obtenerCampo(posicion), val);
                    tramiteEspecifico.setCambioEstado(true);
                    estado.setText(tramiteEspecifico.getValores().get(4)[0]);
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
                    tramiteEspecifico.setCambioEstado(true);
                    estado.setText(tramiteEspecifico.getValores().get(4)[0]);
                }
            }
        }
    }

    /**
     * Método que verifica si un paso tiene plantilla asociada. A través de la
     * posición seleccionada de la lista de pasos sin realizar se obtiene el
     * paso específico del cual se hará la verificación para determinar si tiene
     * plantilla.
     *
     * @param indice Almacena la posición del paso específico seleccionado de la
     * lista de pasos sin realizar.
     * @return Devuelve la ruta de la plantilla(Si se tuviera), de lo contrario
     * indica que el paso no tiene plantilla. .
     */
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

    /**
     * Permite saber si un paso seleccionado es con documento. Este método itera
     * la lista de pasos sin realizar y determina con la posición recibida si el
     * paso seleccionado puede tener documento.
     *
     * @param indice Contiene la posición del paso seleccionado de la lista de
     * pasos sin realizar.
     * @return Verdadero si el paso es con documento, Falso si es lo contrario.
     * .
     */
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

    /**
     * Permite seleccionar un documento. Abre un diálogo para seleccionar la
     * ruta de un documento y almacena el directorio en la variable
     * <code>datoEspecifico</code>.
     *
     * .
     */
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

    /**
     * Almacena un documento específico en la carpeta del trámite. Permite
     * cargar un documento a un paso específico para un trámite específico
     * seleccionado de los pasos realizados dependiendo del paso que haya sido
     * marcado.
     * <p>
     * Si el paso tiene cargado un documento, se notifica al usuario y se da la
     * posibilidad de reemplazar el documento.</p>
     *
     * -Cresencio
     */
    private void cargarDocumento() {
        if (panelPasoRealizado.posicionMarcada() != -1 && !panelPasoRealizado.obtenerNombreBotonDocumento(panelPasoRealizado.posicionMarcada()).equals("Sin documento")) {
            int indiceS = panelPasoRealizado.posicionMarcada();
            if (getPasoSeleccionadoRealizado(indiceS).getDocumento() == null || "".equals(getPasoSeleccionadoRealizado(indiceS).getDocumento())) {
                FileDialog fd = new FileDialog(new Frame(), "Seleccionar documento ", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir"));
                fd.setFile("*.pdf; *.doc; *.docx;");
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
                    getPasoSeleccionadoRealizado(indiceS).setCambio(true);
                    JOptionPane.showMessageDialog(this, "Documento cargado exitosamente",
                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    setModificado(true);
                    resetPanel();
                }
            } else {
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "Ya existe un documento, ¿Desea Reemplazarlo?", "Advertencia",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
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
                        setModificado(true);
                        resetPanel();
                    } else if (respuesta == JOptionPane.NO_OPTION) {
                        dispose();
                    }
                }
            }
        }
    }

    /**
     * Realiza la transferencia del documento específico a la carpeta del
     * trámite. Este método realiza la carga del documento seleccionado para
     * asociar a un paso en el directorio físico del trámite.
     *
     * @param ruta Contiene la dirección del documento seleccionado.
     * @param indice Contiene la posición del paso específico seleccionado para
     * cargarle un documento específico.
     */
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
        UtileriasArchivo.copiarArchivo(ruta, rutaNueva);
        getPasoSeleccionadoSinRealizar(indice).setDocumento(rutaNueva);
        JOptionPane.showMessageDialog(this, "Documento cargado exitosamente",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Devuelve el paso realizado seleccionado. Este método recibe la posición
     * del paso seleccionado de la lista de pasos realizados y devuelve un
     * objeto PasoEspecifico.
     *
     * @param indice Contiene la posición del paso seleccionado en la lista de
     * pasos realizados.
     * @return Objeto <code>PasoEspecifico</code> .
     */
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

    /**
     * Devuelve el paso sin realizar que haya sido seleccionado. Este método
     * recibe la posición del paso seleccionado de la lista de pasos sin
     * realizar y devuelve un objeto PasoEspecifico.
     *
     * @param indice Contiene la posición del paso seleccionado en la lista de
     * pasos sin realizar.
     * @return Objeto <code>PasoEspecifico</code> .
     */
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

    /**
     * Actualiza el panel de pasos realizados.
     *
     * .
     */
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
     * Método que establece si un paso puede realizarse.
     * <p>
     * Esta función sirve para saber que pasos de los que no han sido realizados
     * son seriados, y para cambiar el estado de un trámite en caso de que la
     * realización del paso tenga cambio de estado.
     * </p>
     *
     * @param indice Indica la posición del paso a realizar y se ocupa para
     * obtener la secuencia dentro del método.
     * @return El nombre del paso requerido en caso de tener secuencia, o la
     * autorización de realización.
     *
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
     *
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
     * <p>
     * Cuando el paso a realizar ha aprobado todas las validaciones, este método
     * coloca en el paso realizado los datos que el usuario haya editado y lo
     * mueve al panel de pasos realizados.
     * </p>
     *
     * @param indice Recibe el número del paso que se realizará.
     *
     */
    public void realizarPaso(int indice) {
        Date d = new Date(fechaChooser.getDate().getTime());
        PasoEspecifico pe = getPasoSeleccionadoSinRealizar(indice);
        if (pe.getRepeticion() > 0) {
            pe.setRealizado(true);
            pe.setFechaRealizacion(d);
            //pe.setDocumento(datoEspecifico.getText());
            pe.setCambio(true);
            pe.setNuevo(false);
        }
        //Si el paso especifico es indefinido, se crea un objeto de tipo PasoEspecifico para añadirlo
        //a pasos realizados para manejarlo de forma independiente y evitar sobreescritura.
        if (pe.getRepeticion() == 0) {
            PasoEspecifico pNuevo = new PasoEspecifico();
            pNuevo.setNombrePaso(pe.getNombrePaso());
            pNuevo.setNumPaso(pe.getNumPaso());
            pNuevo.setRealizado(true);
            //pNuevo.setDocumento(datoEspecifico.getText());
            pNuevo.setFechaRealizacion(d);
            pNuevo.setFechaLimite(null);
            pNuevo.setRepeticion(0);
            pNuevo.setNuevo(true);
            pNuevo.setCambio(false);
            pasosModificados.add(pNuevo);
        }
        tramiteEspecifico.setCambioEstado(true);
        ocultarElementos();
    }

    /**
     * Crea la lista de pasos que no han sido realizados.
     * <p>
     * Permite crear la lista de pasos que no hayan sido marcados como
     * realizados, así como la adición de eventos al momento de hacer doble
     * click sobre algún elemento de la lista.
     * </p>
     *
     * @return Contenedor de la lista de pasos sin realizar. .
     */
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
        lista = new JList<>(todo_pasos);
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

    /**
     * Método con el que se comprueba si el usuario realizó modificaciones a los
     * pasos específicos.
     *
     * @return Verdadero si se realizó algún cambio, de lo contrario retorna
     * falso.
     */
    private Boolean getModificado() {
        return modificado;
    }

    /**
     * Recibe el valor que tome el parámetro y lo asigna a la variable global.
     *
     * @param modificado Contiene el valor agregado (puede ser verdadero o
     * falso).
     */
    private void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

}
