package gui;

import dominio.Paso;
import dominio.PasoEspecifico;
import dominio.TramiteEspecifico;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelPasoRealizado extends JPanel implements ActionListener {

    private static final long serialVersionUID = -8692794042074788858L;
    private JLabel[] etiquetaVacia1;
    private JLabel[] etiquetaVacia2;
    private JCheckBox[] checkDocumento;
    private JButton[] mostrarDocButon;
    private JButton[] verPlantillaButon;
    private ArrayList<PasoEspecifico> pasosRealizados;
    private VentanaPrincipal ventanaPrincipal;
    ArrayList<PasoEspecifico> pasosEspecificos;
    String nombreBotonPlantilla[];
    String nombrePaso_Plantilla[];
    String nombreBotonDocumento[];
    String nombrePaso_Documento[];

    public PanelPasoRealizado(ArrayList<PasoEspecifico> pasosEspecificos, VentanaPrincipal ventanaPrincipal) {
        int i = 0;
        this.pasosEspecificos=pasosEspecificos;
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

    private void crearPanel(int n) {
        this.setSize(-1, 30 * n);
        this.setLayout(new GridLayout(n, 1));
        etiquetaVacia1 = new JLabel[n];
        etiquetaVacia2 = new JLabel[n];
        checkDocumento = new JCheckBox[n];
        mostrarDocButon = new JButton[n];
        verPlantillaButon = new JButton[n];
        JPanel panelContenedor;
        ButtonGroup grupo = new ButtonGroup();

        System.out.println("Valor de N : " + n);
        //for (int i = 0; i < n; i++) {
        int i = 0;
        for (PasoEspecifico realizado : pasosRealizados) {
            panelContenedor = new JPanel();
            panelContenedor.setLayout(new FlowLayout(FlowLayout.LEFT));
            etiquetaVacia1[i] = new JLabel(realizado.getNombrePaso());
            etiquetaVacia1[i].setPreferredSize(new Dimension(250, 30));
            panelContenedor.add(etiquetaVacia1[i]);
            Date d = realizado.getFechaRealizacion();
            DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
            etiquetaVacia2[i] = new JLabel(formato.format(d));
            etiquetaVacia2[i].setPreferredSize(new Dimension(100, 30));
            panelContenedor.add(etiquetaVacia2[i]);

            String nombreBtnPlantilla = "";
            for (int j = 0; j < nombrePaso_Plantilla.length; j++) {
                if (realizado.getNombrePaso().contains(nombrePaso_Plantilla[j])) {
                    nombreBtnPlantilla = nombreBotonPlantilla[j];
                }
            }
            verPlantillaButon[i] = new JButton(nombreBtnPlantilla);
            verPlantillaButon[i].setActionCommand(nombreBtnPlantilla);
            verPlantillaButon[i].setName(String.valueOf(i));
            verPlantillaButon[i].addActionListener(this);
            verPlantillaButon[i].setPreferredSize(new Dimension(130, 30));
            panelContenedor.add(verPlantillaButon[i]);

            String nombreBtnDocumento = "";
            for (int j = 0; j < nombrePaso_Documento.length; j++) {
                if (realizado.getNombrePaso().contains(nombrePaso_Documento[j]) && nombreBotonDocumento[j].equals("Con_documento")) {
                    if (realizado.getDocumento() == null || realizado.getDocumento().equals("")) {
                        nombreBtnDocumento = "Vacío";
                    }
                    if (realizado.getDocumento() != null && !"".equals(realizado.getDocumento())) {
                        System.out.println("Documento Almacenado" + realizado.getDocumento());
                        nombreBtnDocumento = "Mostrar documento";
                    }
                }
                if (realizado.getNombrePaso().contains(nombrePaso_Documento[j]) && nombreBotonDocumento[j].equals("Sin_documento")) {
                    nombreBtnDocumento = "Sin documento";
                }
            }
            mostrarDocButon[i] = new JButton(nombreBtnDocumento);
            mostrarDocButon[i].setActionCommand(nombreBtnDocumento);
            mostrarDocButon[i].setName(String.valueOf(i));
            mostrarDocButon[i].addActionListener(this);
            mostrarDocButon[i].setPreferredSize(new Dimension(130, 30));
            panelContenedor.add(mostrarDocButon[i]);

            checkDocumento[i] = new JCheckBox("" + (i + 1));
            checkDocumento[i].setPreferredSize(new Dimension(20, 30));
            checkDocumento[0].setSelected(true);
            grupo.add(checkDocumento[i]);
            panelContenedor.add(checkDocumento[i]);
            this.add(panelContenedor);
            i++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0
    ) {
        if (arg0.getActionCommand().equalsIgnoreCase("Mostrar documento")) {
            JButton boton = (JButton) arg0.getSource();
            int valor = Integer.parseInt(boton.getName());
            if (boton.getText().equals("Mostrar documento")) {
                if (obtenerPasoSeleccionado(valor).getDocumento() != null && !"".equals(obtenerPasoSeleccionado(valor).getDocumento())) {
                    File path = new File(obtenerPasoSeleccionado(valor).getDocumento());
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
        if (arg0.getActionCommand().equalsIgnoreCase("Ver plantilla")) {
            JButton boton = (JButton) arg0.getSource();
            int valor = Integer.parseInt(boton.getName());
            verPlantilla(obtenerPlantillaPasosRealizados(valor));
            //ds.verPlantilla(ds.obtenerPlantillaPasosRealizados(indiceBotonPlantilla(valor), tramiteEspecifico));
        }
    }

    public int indiceCheck() {
        int posicion = -1;
        for (int i = 0; i < checkDocumento.length; i++) {
            if (checkDocumento[i].isSelected()) {
                posicion = i;
            }
        }

        return posicion;
    }

    public String obtenerNombreBotonDocumento(int indice) {
        String nombreBoton = "";
        if (mostrarDocButon[indice].getText().equals("Sin documento")) {
            nombreBoton = mostrarDocButon[indice].getText();
        }
        return nombreBoton;
    }

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

    public void verPlantilla(String ruta) {
        File path = new File(ruta);
        try {
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error abriendo la plantilla",
                    "Ver plantilla", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void nombreBtnPlantilla(VentanaPrincipal ventanaPrincipal) {
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

    public final void nombreBtnDocumento(VentanaPrincipal ventanaPrincipal) {
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
