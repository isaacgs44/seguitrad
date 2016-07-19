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
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.UtileriasArchivo;

public class DialogoSeguimiento extends JDialog implements ActionListener {

    private static final long serialVersionUID = -7800314788050081571L;
    private JTextField nombreSolicitante;
    private JTextField Titulo;
    private JTextField fechaInicio;
    private JLabel etiquetaFechaFin;
    private JTextField fechaFin;
    private JTextField estado;
    private JButton detallesBoton;
    private JButton modificarBoton;
    private JButton eliminarBoton;
    private JButton verDocBoton;
    private JButton cargarDocBoton;
    private JButton quitarDocBoton;
    private JButton eliminarPasoBoton;
    private JList<String> descripcion;
    private DefaultListModel<String> modeloDescripcion;
    private JScrollPane scrollDescripcion;
    private JButton editarBoton;
    private final JTextField datoEspecifico;
    private JButton carpetaBoton;
    private JButton verPlantillaBoton;
    private JButton aceptarBoton;
    private JButton cancelarBoton;
    private JDateChooser fechaChooser;
    private JButton agregarBoton;
    private PanelPasoRealizado panelPasoRealizado;
    private JScrollPane scroll;
    private VentanaPrincipal ventanaPrincipal;
    private TramiteEspecifico tramiteEspecifico;
    private PasoEspecifico paso;
    private JLabel etiquetaNombre;

    public DialogoSeguimiento(VentanaPrincipal ventanaPrincipal, TramiteEspecifico tramiteEspecifico) {
        super(ventanaPrincipal, "Seguimiento", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        this.tramiteEspecifico = tramiteEspecifico;

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

        paso = new PasoEspecifico(tramiteEspecifico);
        scrollDescripcion = new JScrollPane(paso);
        scrollDescripcion.setBounds(80, 470, 250, 150);
        scrollDescripcion.setAutoscrolls(true);
        add(scrollDescripcion);

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
        //fechaChooser.setPreferredSize(new Dimension(100,30));
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

        panelPasoRealizado = new PanelPasoRealizado(tramiteEspecifico, ventanaPrincipal);
        scroll = new JScrollPane(panelPasoRealizado);
        scroll.setBounds(50, 220, 700, 200);
        add(scroll);

        nombreSolicitante.setEditable(false);
        Titulo.setEditable(false);
        fechaInicio.setEditable(false);
        fechaFin.setEditable(false);
        estado.setEditable(false);

        fechaChooser.setVisible(false);
        agregarBoton.setVisible(false);
        datoEspecifico.setVisible(false);
        carpetaBoton.setVisible(false);
        verPlantillaBoton.setVisible(false);

        setSize(1040, 730);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    public void mostrarElementos() {
        fechaChooser.setVisible(true);
        agregarBoton.setVisible(true);
        datoEspecifico.setVisible(true);
        carpetaBoton.setVisible(true);
        verPlantillaBoton.setVisible(true);
    }

    public void ocultarElementos() {
        fechaChooser.setVisible(false);
        agregarBoton.setVisible(false);
        datoEspecifico.setVisible(false);
        carpetaBoton.setVisible(false);
        verPlantillaBoton.setVisible(false);
    }

    public void pasoRealizado(int indice) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date d = new Date(fechaChooser.getDate().getTime());
        formato.format(d);
        paso.realizarPaso(d, true, datoEspecifico.getText(), indice, tramiteEspecifico);
        ocultarElementos();
    }

    public void verPlantilla(String ruta) {
        File path = new File(ruta);
        try {
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "El paso no tiene una plantilla definida",
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
            dispose();
        }
        if (e.getSource() == editarBoton) {
            int[] selectedIndices = paso.getLista().getSelectedIndices();
            if (selectedIndices.length != 0) {
                mostrarElementos();
                etiquetaNombre.setText("Nombre : " + paso.nombrePasoSeleccionado(selectedIndices[0], tramiteEspecifico));
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un paso especifico",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == agregarBoton) {
            int[] selectedIndices = paso.getLista().getSelectedIndices();
            int posicion = selectedIndices[0];
            pasoRealizado(posicion);
            dispose();
            new DialogoSeguimiento(ventanaPrincipal, tramiteEspecifico);
        }
        if (e.getSource() == verPlantillaBoton) {
            int[] selectedIndices = paso.getLista().getSelectedIndices();
            int posicion = selectedIndices[0];
            verPlantilla(obtenerPlantilla(posicion, tramiteEspecifico));
        }
        if (e.getSource() == carpetaBoton) {
            datoEspecifico.setEditable(true);
            seleccionarDocumentoEspecifico();
        }
        if (e.getSource() == eliminarPasoBoton) {
            if (panelPasoRealizado.indiceCheck() != -1) {
                eliminarPasoRealizado(panelPasoRealizado.indiceCheck());
                dispose();
                new DialogoSeguimiento(ventanaPrincipal, tramiteEspecifico);
            }
        }

        if (e.getSource() == verDocBoton) {
            if (panelPasoRealizado.indiceCheck() != -1) {
                int indiceS = panelPasoRealizado.indiceCheck();
                if (obtenerPasoSeleccionado(indiceS).getDocumento() != null && !"".equals(obtenerPasoSeleccionado(indiceS).getDocumento())) {
                    File path = new File(obtenerPasoSeleccionado(indiceS).getDocumento());
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
            if (panelPasoRealizado.indiceCheck() != -1 && !panelPasoRealizado.obtenerNombreBotonDocumento(panelPasoRealizado.indiceCheck()).equals("Sin documento")) {
                int indiceS = panelPasoRealizado.indiceCheck();
                if (obtenerPasoSeleccionado(indiceS).getDocumento() == null || "".equals(obtenerPasoSeleccionado(indiceS).getDocumento())) {
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
                        rutaNueva = this.ventanaPrincipal.getLista().getBd().getDirectorio() + "doc_" + tramitante + "_" + obtenerPasoSeleccionado(indiceS).getNombrePaso() + extension;
                        UtileriasArchivo.copiarArchivo(ruta, rutaNueva);
                        obtenerPasoSeleccionado(indiceS).setDocumento(rutaNueva);
                        JOptionPane.showMessageDialog(this, "Documento cargado exitosamente",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe documento",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
        }

        if (e.getSource() == quitarDocBoton) {

        }

    }

    public void eliminarPasoRealizado(int indice) {
        int i = 0;
        for (PasoEspecifico pe : tramiteEspecifico.getPasosEspecificos()) {
            if (pe.isRealizado()) {
                if (i == indice) {
                    pe.setRealizado(false);
                }
                i++;
            }
        }
    }

    public String obtenerPlantilla(int indice, TramiteEspecifico tramiteEspecifico) {
        int numPasosFaltantes = 0;
        String plantilla = null;
        for (PasoEspecifico pe : tramiteEspecifico.getPasosEspecificos()) {
            if (!pe.isRealizado()) {
                numPasosFaltantes++;
            }
        }
        String[] todo_pasos = new String[numPasosFaltantes];
        int i = 0;
        for (PasoEspecifico pe : tramiteEspecifico.getPasosEspecificos()) {
            if (!pe.isRealizado()) {
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

    private void seleccionarDocumentoEspecifico() {
        FileDialog fd = new FileDialog(new Frame(), "Seleccionar plantilla ", FileDialog.LOAD);
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
    }

    private PasoEspecifico obtenerPasoSeleccionado(int indice) {
        int i = 0;
        for (PasoEspecifico pe : tramiteEspecifico.getPasosEspecificos()) {
            if (pe.isRealizado()) {
                if (i == indice) {
                    return pe;
                }
                i++;
            }
        }
        return null;
    }
}
