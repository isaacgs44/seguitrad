package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class DialogoDetalles extends JDialog implements ActionListener {

    private static final long serialVersionUID = -7127205367581348873L;
    private JButton cerrarBoton;
    private JLabel etiquetaVaciaNombre;
    private JLabel etiquetaVaciaTitulo;
    private JLabel etiquetaVaciaFechaInicio;
    private JLabel etiquetaVaciaFechaFin;
    private JLabel etiquetaVaciaEstado;
    private JLabel etiquetaVaciaCamposExtras;

    public DialogoDetalles(VentanaPrincipal ventanaPrincipal) {
        super(ventanaPrincipal, "Detalles del trámite", true);
        setLayout(null);

        JLabel etiquetaNombreSolicitante = new JLabel("Nombre del solicitante: ");
        etiquetaNombreSolicitante.setBounds(20, 30, 150, 50);
        add(etiquetaNombreSolicitante);
        etiquetaVaciaNombre = new JLabel("*");
        etiquetaVaciaNombre.setBounds(170, 30, 150, 50);
        add(etiquetaVaciaNombre);

        JLabel etiquetaTitulo = new JLabel("Título: ");
        etiquetaTitulo.setBounds(20, 60, 150, 50);
        add(etiquetaTitulo);
        etiquetaVaciaTitulo = new JLabel("*");
        etiquetaVaciaTitulo.setBounds(170, 60, 150, 50);
        add(etiquetaVaciaTitulo);

        JLabel etiquetaFechaInicio = new JLabel("Fecha de inicio: ");
        etiquetaFechaInicio.setBounds(20, 90, 150, 50);
        add(etiquetaFechaInicio);
        etiquetaVaciaFechaInicio = new JLabel("*");
        etiquetaVaciaFechaInicio.setBounds(170, 90, 150, 50);
        add(etiquetaVaciaFechaInicio);

        JLabel etiquetaFechaFin = new JLabel("Fecha de fin: ");
        etiquetaFechaFin.setBounds(20, 120, 150, 50);
        add(etiquetaFechaFin);
        etiquetaVaciaFechaFin = new JLabel("*");
        etiquetaVaciaFechaFin.setBounds(170, 120, 150, 50);
        add(etiquetaVaciaFechaFin);

        JLabel etiquetaEstado = new JLabel("Estado: ");
        etiquetaEstado.setBounds(20, 150, 150, 50);
        add(etiquetaEstado);
        etiquetaVaciaEstado = new JLabel("*");
        etiquetaVaciaEstado.setBounds(170, 150, 150, 50);
        add(etiquetaVaciaEstado);

        JLabel etiquetaCamposExtras = new JLabel("Campos Extras: ");
        etiquetaCamposExtras.setBounds(20, 180, 150, 50);
        add(etiquetaCamposExtras);
        etiquetaVaciaCamposExtras = new JLabel("*");
        etiquetaVaciaCamposExtras.setBounds(170, 180, 150, 50);
        add(etiquetaVaciaCamposExtras);

        cerrarBoton = new JButton("Cerrar");
        cerrarBoton.addActionListener(this);
        cerrarBoton.setBounds(150, 420, 150, 35);
        cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        add(cerrarBoton);

        inicializar();

        setSize(450, 500);
        setResizable(false);
        setLocationRelativeTo(ventanaPrincipal);
        setVisible(true);
    }

    private void inicializar() {
        // TODO Auto-generated method stub
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cerrarBoton)) {
            dispose();
        }
    }
}
