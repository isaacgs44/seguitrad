package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DialogoBuscarSeguimiento extends JDialog implements ActionListener {

    private static final long serialVersionUID = -7452219010901731594L;
    private JButton cerrarBoton;
    private JButton seguimientoBoton;
    private PanelBuscar panelBuscar;
    private VentanaPrincipal ventanaPrincipal;

    public DialogoBuscarSeguimiento(VentanaPrincipal ventanaPrincipal) {
        super(ventanaPrincipal, "Buscar registro", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;

        cerrarBoton = new JButton("Cerrar");
        cerrarBoton.addActionListener(this);
        cerrarBoton.setBounds(560, 380, 120, 35);
        cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        add(cerrarBoton);

        seguimientoBoton = new JButton("Seguimiento");
        seguimientoBoton.addActionListener(this);
        seguimientoBoton.setBounds(220, 380, 150, 35);
        seguimientoBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/seguimiento.png")));
        add(seguimientoBoton);

        panelBuscar = new PanelBuscar(ventanaPrincipal.getLista().getListaTramitesEsp());
        panelBuscar.setBounds(20, 20, 840, 350);
        add(panelBuscar);

        setSize(900, 465);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cerrarBoton)) {
            dispose();
        } else if (e.getSource().equals(seguimientoBoton)) {
            //new DialogoSeguimiento(ventanaPrincipal, ventanaPrincipal.getLista().getListaTramitesEsp().get(panelBuscar.getTramiteSeleccionado().getIdTramite()));
            if (panelBuscar.getTramiteSeleccionado() != null) {
                new DialogoSeguimiento(ventanaPrincipal, panelBuscar.getTramiteSeleccionado());
                panelBuscar.buscar("", "");
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un registro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
