package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DialogoBuscarModificar extends JDialog implements ActionListener {

    private static final long serialVersionUID = -8136554221466770239L;
    private JButton cerrarBoton;
    private JButton modificarRegistroBoton;
    private PanelBuscar panelBuscar;
    private VentanaPrincipal ventanaPrincipal;

    public DialogoBuscarModificar(VentanaPrincipal ventanaPrincipal) {
        super(ventanaPrincipal, "Buscar registro", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;

        cerrarBoton = new JButton("Cerrar");
        cerrarBoton.addActionListener(this);
        cerrarBoton.setBounds(560, 380, 120, 35);
        cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        add(cerrarBoton);

        modificarRegistroBoton = new JButton("Modificar registro");
        modificarRegistroBoton.addActionListener(this);
        modificarRegistroBoton.setBounds(220, 380, 200, 35);
        modificarRegistroBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificarRegistro.png")));
        add(modificarRegistroBoton);

        panelBuscar = new PanelBuscar(ventanaPrincipal.getLista().getListaTramitesEsp());
        panelBuscar.setBounds(20, 20, 840, 350);
        add(panelBuscar);

        setSize(900, 465);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cerrarBoton)) {
            dispose();
        } else if (e.getSource().equals(modificarRegistroBoton)) {
            //FIXME trucado temporal para abrir un registro específico, la línea comentada es la buena
            //new DialogoModificarRegistro(ventanaPrincipal, ventanaPrincipal.getLista().getListaTramites().get(0));
             new DialogoModificarRegistro(ventanaPrincipal, panelBuscar.getTramiteSeleccionado());
             panelBuscar.buscar("", "");
           
        }
    }
}