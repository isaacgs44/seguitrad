package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dominio.Tramite;
import lib.JIntegerTextField;

public class DialogoNuevasAlertas extends JDialog implements ActionListener {

    private static final long serialVersionUID = -1476962832053963721L;
    private JCheckBox checkConfiguracion;
    private JLabel etiquetaDiasAlerta;
    private JButton detallesBoton;
    private JButton cerrarBoton;
    private JButton modificarTramiteBoton;
    private JIntegerTextField diasAlertas;
    private VentanaPrincipal ventanaPrincipal;
    private Tramite tramite;

    public DialogoNuevasAlertas(VentanaPrincipal ventanaPrincipal) {
        super(ventanaPrincipal, "Nuevas alertas", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        this.tramite = ventanaPrincipal.getLista().getTramite();

        Object[][] data = {
            {"", "", "", "", "", ""}};
        String[] columnNames = {"Nombre Solicitante", "Título", "Estado",
            "Paso a vencer", "Fecha límite", "Responsable"};
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 60, 650, 200);
        add(scrollPane);

        detallesBoton = new JButton("Detalles");
        detallesBoton.setBounds(720, 80, 120, 35);
        detallesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/detalles.png")));
        detallesBoton.addActionListener(this);
        add(detallesBoton);

        checkConfiguracion = new JCheckBox("Ver configuración de alerta");
        checkConfiguracion.setBounds(150, 280, 200, 30);
        checkConfiguracion.addActionListener(this);
        add(checkConfiguracion);

        etiquetaDiasAlerta = new JLabel("# días para alerta");
        etiquetaDiasAlerta.setBounds(200, 320, 100, 25);
        add(etiquetaDiasAlerta);
        diasAlertas = new JIntegerTextField(0);
        diasAlertas.setBounds(310, 320, 50, 25);
        diasAlertas.setEditable(false);
        add(diasAlertas);

        modificarTramiteBoton = new JButton("Modificar trámite");
        modificarTramiteBoton.setBounds(400, 315, 200, 35);
        modificarTramiteBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
        modificarTramiteBoton.addActionListener(this);
        add(modificarTramiteBoton);

        cerrarBoton = new JButton("Cerrar");
        cerrarBoton.setBounds(390, 400, 120, 30);
        cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        cerrarBoton.addActionListener(this);
        add(cerrarBoton);

        inicializar();

        setSize(900, 465);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    private void inicializar() {
        etiquetaDiasAlerta.setVisible(false);
        diasAlertas.setVisible(false);
        modificarTramiteBoton.setVisible(false);
        // Consultar el tramite y poner los días de alerta
        diasAlertas.setValue(tramite.getDiasAlerta());
        // Consultar los trámites específicos y llenar la tabla
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cerrarBoton)) {
            dispose();
        } else if (e.getSource().equals(modificarTramiteBoton)) {
            dispose();
            new DialogoModificarTramite(ventanaPrincipal);
        } else if (e.getSource().equals(detallesBoton)) {
            mostrarDetalles();
        } else if (e.getSource().equals(checkConfiguracion)) {
            verConfiguracion();
        }
    }

    private void mostrarDetalles() {
        // obtener la fila seleccionada de la tabla
        // obtener el trámite específico
        // llamar al DialogoSeguimiento y mostrar los datos
        new DialogoSeguimiento(ventanaPrincipal, null);
    }

    private void verConfiguracion() {
        if (checkConfiguracion.isSelected()) {
            etiquetaDiasAlerta.setVisible(true);
            diasAlertas.setVisible(true);
            modificarTramiteBoton.setVisible(true);
        } else {
            etiquetaDiasAlerta.setVisible(false);
            diasAlertas.setVisible(false);
            modificarTramiteBoton.setVisible(false);
        }
    }
}
