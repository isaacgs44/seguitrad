package gui;

import dominio.Campo;
import javax.swing.*;

import dominio.ListaTramites;
import dominio.PasoEspecifico;
import dominio.TramiteEspecifico;
import excepcion.BaseDatosException;
import excepcion.TramiteException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private static final long serialVersionUID = 4260055797852194459L;
    private JMenuBar inicioMenuBar;
    private JMenu archivoMenu;
    private JMenuItem nuevoTramiteMenu;
    private JMenuItem abrirTramiteMenu;
    private JMenuItem guardarTramiteMenu;
    private JMenuItem guardarTramiteComoMenu;
    private JMenuItem modificarTramiteMenu;
    private JMenuItem propiedadesMenu;
    private JMenuItem cerrarTramiteMenu;
    private JMenuItem salirMenu;
    private JMenu tramiteMenu;
    private JMenuItem seguimientoMenu;
    private JMenuItem nuevoRegistroMenu;
    private JMenuItem modificarRegistroMenu;
    private JMenuItem eliminarRegistroMenu;
    private JMenu alertasMenu;
    private JMenuItem nuevasAlertasMenu;
    private JMenuItem tramitesInconclusosMenu;
    private JMenu estadisticasMenu;
    private JMenuItem nuevasConsultaMenu;
    private JMenuItem abrirConsultaMenu;
    private JMenu ayudaMenu;
    private JMenuItem verManualMenu;
    private JMenuItem acercaDeMenu;
    private ListaTramites lista = new ListaTramites();

    public VentanaPrincipal() {
        super("Seguitrad UMAR");

        inicioMenuBar = new JMenuBar();
        setJMenuBar(inicioMenuBar);
        archivoMenu = new JMenu("Archivo");
        archivoMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/archivo.png")));
        inicioMenuBar.add(archivoMenu);

        nuevoTramiteMenu = new JMenuItem("Nuevo trámite");
        nuevoTramiteMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/nuevoTramite.png")));
        nuevoTramiteMenu.addActionListener(this);
        archivoMenu.add(nuevoTramiteMenu);

        abrirTramiteMenu = new JMenuItem("Abrir trámite");
        abrirTramiteMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/abrirTramite.png")));
        abrirTramiteMenu.addActionListener(this);
        archivoMenu.add(abrirTramiteMenu);
        archivoMenu.addSeparator();

        guardarTramiteMenu = new JMenuItem("Guardar trámite");
        guardarTramiteMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/guardar.png")));
        guardarTramiteMenu.addActionListener(this);
        archivoMenu.add(guardarTramiteMenu);

        guardarTramiteComoMenu = new JMenuItem("Guardar trámite como");
        guardarTramiteComoMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/guardarComo.png")));
        guardarTramiteComoMenu.addActionListener(this);
        archivoMenu.add(guardarTramiteComoMenu);

        modificarTramiteMenu = new JMenuItem("Modificar trámite");
        modificarTramiteMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificarTramite.png")));
        modificarTramiteMenu.addActionListener(this);
        archivoMenu.add(modificarTramiteMenu);

        propiedadesMenu = new JMenuItem("Propiedades");
        propiedadesMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/propiedades.png")));
        propiedadesMenu.addActionListener(this);
        archivoMenu.add(propiedadesMenu);

        cerrarTramiteMenu = new JMenuItem("Cerrar trámite");
        cerrarTramiteMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrarTramite.png")));
        cerrarTramiteMenu.addActionListener(this);
        archivoMenu.add(cerrarTramiteMenu);
        archivoMenu.addSeparator();

        salirMenu = new JMenuItem("Salir");
        salirMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/salirTramite.png")));
        salirMenu.addActionListener(this);
        archivoMenu.add(salirMenu);

        tramiteMenu = new JMenu("Trámite");
        tramiteMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/tramite.png")));
        inicioMenuBar.add(tramiteMenu);

        seguimientoMenu = new JMenuItem("Seguimiento");
        seguimientoMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/seguimiento.png")));
        seguimientoMenu.addActionListener(this);
        tramiteMenu.add(seguimientoMenu);
        tramiteMenu.addSeparator();

        nuevoRegistroMenu = new JMenuItem("Nuevo registro");
        nuevoRegistroMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/nuevoRegistro.png")));
        nuevoRegistroMenu.addActionListener(this);
        tramiteMenu.add(nuevoRegistroMenu);

        modificarRegistroMenu = new JMenuItem("Modificar registro");
        modificarRegistroMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificarRegistro.png")));
        modificarRegistroMenu.addActionListener(this);
        tramiteMenu.add(modificarRegistroMenu);

        eliminarRegistroMenu = new JMenuItem("Eliminar registro");
        eliminarRegistroMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/eliminarRegistroTra.png")));
        eliminarRegistroMenu.addActionListener(this);
        tramiteMenu.add(eliminarRegistroMenu);

        alertasMenu = new JMenu("Alertas");
        alertasMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/alertas.png")));
        inicioMenuBar.add(alertasMenu);

        nuevasAlertasMenu = new JMenuItem("Nuevas alertas");
        nuevasAlertasMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/nuevasAlertas.png")));
        nuevasAlertasMenu.addActionListener(this);
        alertasMenu.add(nuevasAlertasMenu);

        tramitesInconclusosMenu = new JMenuItem("Trámites inconclusos");
        tramitesInconclusosMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/tramitesInconclusos.png")));
        tramitesInconclusosMenu.addActionListener(this);
        alertasMenu.add(tramitesInconclusosMenu);

        estadisticasMenu = new JMenu("Estadísticas");
        estadisticasMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/estadistica.png")));
        inicioMenuBar.add(estadisticasMenu);

        nuevasConsultaMenu = new JMenuItem("Nueva consulta");
        nuevasConsultaMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/nuevaConsulta.png")));
        nuevasConsultaMenu.addActionListener(this);
        estadisticasMenu.add(nuevasConsultaMenu);

        abrirConsultaMenu = new JMenuItem("Abrir consulta");
        abrirConsultaMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/abrirConsulta.png")));
        abrirConsultaMenu.addActionListener(this);
        estadisticasMenu.add(abrirConsultaMenu);

        ayudaMenu = new JMenu("Ayuda");
        ayudaMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/ayuda.png")));
        inicioMenuBar.add(ayudaMenu);

        verManualMenu = new JMenuItem("Ver manual");
        verManualMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/verManual.png")));
        verManualMenu.addActionListener(this);
        ayudaMenu.add(verManualMenu);

        acercaDeMenu = new JMenuItem("Acerca de.....");
        acercaDeMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/acercaDe.png")));
        acercaDeMenu.addActionListener(this);
        ayudaMenu.add(acercaDeMenu);

        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/umar.jpg")));

        setLayout(new FlowLayout());
        JLabel labelImagen = new JLabel();
        ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagenes/fondo.png"));
        Image imagenEscalada = imagenFondo.getImage().getScaledInstance(-1, getSize().height - 80, Image.SCALE_SMOOTH);
        labelImagen.setIcon(new ImageIcon(imagenEscalada));
        add(labelImagen);
        getContentPane().setBackground(Color.BLACK);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                try {
                    salir();
                } catch (BaseDatosException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nuevoTramiteMenu)) {
            new DialogoNuevoTramite(this);
            establecerTitulo();
        } else if (e.getSource().equals(abrirTramiteMenu)) {
            try {
                abrirTramite();
            } catch (BaseDatosException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(guardarTramiteMenu)) {
            if (lista.getTramite() == null) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto", "Guardar trámite", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    lista.guardarArchivo();
                } catch (BaseDatosException ex) {
                    ex.printStackTrace();
                }
                establecerTitulo();
            }
        } else if (e.getSource().equals(guardarTramiteComoMenu)) {
            if (lista.getTramite() == null) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto", "Guardar trámite como", JOptionPane.ERROR_MESSAGE);
            } else {
                lista.guardarArchivoComo();
                establecerTitulo();
            }
        } else if (e.getSource().equals(modificarTramiteMenu)) {
            if (lista.getTramite() == null) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto", "Modificar trámite", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoModificarTramite(this);
                establecerTitulo();
            }
        } else if (e.getSource().equals(propiedadesMenu)) {
            if (lista.getTramite() == null) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto", "Propiedades", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoPropiedades(this);
            }
        } else if (e.getSource().equals(cerrarTramiteMenu)) {
            if (lista.getTramite() == null) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto", "Cerrar trámite", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    cerrarTramite();
                } catch (BaseDatosException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource().equals(salirMenu)) {
            try {
                salir();
            } catch (BaseDatosException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(seguimientoMenu)) {
            if (lista.getTramite() == null || lista.getListaTramitesEsp().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto o \nno existen trámites específicos almacenados", "Seguimiento", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoBuscarSeguimiento(this);
            }
        } else if (e.getSource().equals(nuevoRegistroMenu)) {
            if (lista.getTramite() == null) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto", "Nuevo registro", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoNuevoRegistro(this);
            }
        } else if (e.getSource().equals(modificarRegistroMenu)) {
            if (lista.getTramite() == null || lista.getListaTramitesEsp().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto o \nno existen trámites específicos almacenados", "Modificar registro", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoBuscarModificar(this);
               
            }
        } else if (e.getSource().equals(eliminarRegistroMenu)) {
            if (lista.getTramite() == null || lista.getListaTramitesEsp().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto o \nno existen trámites específicos almacenados", "Eliminar registro", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoEliminarRegistro(this);
            }
        } else if (e.getSource().equals(nuevasAlertasMenu)) {
            if (lista.getListaTramitesEsp().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No existen trámites específicos almacenados", "Nuevas alertas", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoNuevasAlertas(this);
            }
        } else if (e.getSource().equals(tramitesInconclusosMenu)) {
            if (lista.getListaTramitesEsp().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No existen trámites específicos almacenados", "Trámites inconclusos", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoTramitesInconclusos(this);
            }
        } else if (e.getSource().equals(nuevasConsultaMenu)) {
            if (lista.getTramite() == null) {
                JOptionPane.showMessageDialog(this, "No hay ningún trámite abierto", "Nueva consulta", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoNuevaConsulta(this);
                establecerTitulo();
            }
        } else if (e.getSource().equals(abrirConsultaMenu)) {
            if (lista.getListaConsultas().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No existen consultas almacenadas", "Abrir consulta", JOptionPane.ERROR_MESSAGE);
            } else {
                new DialogoAbrirConsulta(this);
                establecerTitulo();
            }
        } else if (e.getSource().equals(verManualMenu)) {
            verManual();
        } else if (e.getSource().equals(acercaDeMenu)) {
            mostrarInformacionSistema();
        }
    }

    /**
     * 
     */
    private void abrirTramite() throws BaseDatosException {
        if (lista.getTramite() != null) {
            cerrarTramite();
        }
        if (lista.getTramite() == null) {
            try {
                lista.abrirArchivo();                             
                System.out.println("tramite: " + lista.getTramite().getNombreTramite());
                System.out.println("Archivo: " + lista.getTramite().getNombreArchivo());
                ArrayList<TramiteEspecifico> listaTramitesEsp = lista.getListaTramitesEsp();
                
                for(TramiteEspecifico t : listaTramitesEsp){
                    System.out.println("idTramite" + t.getIdTramite());
                    ArrayList<PasoEspecifico> pasosEspecificos = t.getPasosEspecificos();
                    System.out.println("----------PASOS----------");
                    System.out.println("Tamaño lista pasos "+pasosEspecificos.size());
                    for (int z = 0;  z < pasosEspecificos.size(); z++) {
                        PasoEspecifico p = pasosEspecificos.get(z);
                        System.out.println("Nombre: " + p.getNombrePaso());
                        System.out.println("Documento: " + p.getDocumento());
                        System.out.println("Fecha_Límite: " + p.getFechaLimite());
                        System.out.println("Realización: " + p.getFechaRealizacion());
                        System.out.println("Repeticion: "+ p.getRepeticion());
                    }
                    
                }
                
                establecerTitulo();
            } catch (TramiteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), e1.getTitulo(), JOptionPane.ERROR_MESSAGE);
            } catch (BaseDatosException e2) {
                JOptionPane.showMessageDialog(this, e2.getMessage(), e2.getTitulo(), JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e3) {
                JOptionPane.showMessageDialog(this, e3.getMessage(), "Error en Base de Datos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 
     */
    private void cerrarTramite() throws BaseDatosException {
        if (getTitle().startsWith("*")) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea guardar los cambios en el sistema?"
                    + "\nSi elige \"No\" se perderán los cambios realizados.", "Seguitrad UMAR",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                lista.guardarArchivo();
            } else if (respuesta == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        lista.cerrarArchivo();
        establecerTitulo();
    }

    /**
     * 
     */
    private void salir() throws BaseDatosException {
        if (getTitle().startsWith("*")) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea guardar los cambios en el sistema?"
                    + "\nSi elige \"No\" se perderán los cambios realizados.", "Seguitrad UMAR",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                lista.guardarArchivo();
                System.exit(0);
            } else if (respuesta == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    /**
     * 
     */
    private void mostrarInformacionSistema() {
        JOptionPane.showMessageDialog(this, "Software para control de seguimiento de trámites administrativos"
                + "\n Proyecto Interno Clave 2II1501"
                + "\n\nSistema elaborado por:"
                + "\nIlse Janet Hernández Méndez (Tesista, UMAR)"
                + "\nArely Jijón Cortés (Servicio Social, UMAR)"
                + "\nJosé de Jesús Cruz Flores (Estancia, IT Tuxtla Gutiérrez)"
                + "\nM. en C. Manuel Alejandro Valdés Marrero"
                + "\n\nCopyright UMAR " + '\u00A9' + " 2016", "Acerca de... Seguitrad",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 
     */
    private void verManual() {
        File path = new File("manual.pdf");
        try {
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "El manual no puede ser abierto."
                    + "\nVerifique que el manual y la aplicación\n"
                    + "para abrir archivos PDF se encuentren instalados.",
                    "Ver manual", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void establecerTitulo() {
        String titulo = "Seguitrad UMAR";
        if (lista.getTramite() != null) {
            titulo = lista.getTramite().getNombreTramite() + " - " + titulo;
            if (lista.isHayCambios()) {
                titulo = "* " + titulo;
            }
            tramiteMenu.setText(lista.getTramite().getNombreTramite());
        } else {
            tramiteMenu.setText("Trámite");
        }
        setTitle(titulo);
    }

    /**
     * @return the lista
     */
    public ListaTramites getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(ListaTramites lista) {
        this.lista = lista;
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}
