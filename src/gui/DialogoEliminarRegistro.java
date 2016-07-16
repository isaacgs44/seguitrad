package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * Elimina el trámite específico seleccionado.
 * <p>
 * Busca un trámite específico dependiendo del nombre del solicitante o titulo
 * ingresado, los resultados se muestran en una tabla y elimina de la tabla y de
 * la lista de trámites el trámite específico seleccionado.
 * </p>
 * 
 * @author jesus
 * @see gui.PanelBuscar
 *
 */
public class DialogoEliminarRegistro extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1714426157964322168L;
    /**
     * 
    Cierra la ventana eliminar registro.
     */
    private JButton cerrarBoton;
    /**
     * Elimina el registro seleccionado en la tabla.
     */
    private JButton eliminarRegistroBoton;
    /**
     * Referencia a la clase <code>PanelBuscar</code>. Clase que contiene todos
     * los métodos y componentes necesarios para realizar la búsqueda de
     * trámites específicos.
     * 
     * @see gui.PanelBuscar
     */
    private PanelBuscar panelBuscar;
    /**
     * Ubica el diálogo dentro de la ventana principal del sistema. Se obtiene
     * todos los datos y trámites que se han guardado.
     * 
     * @see gui.VentanaPrincipal
     */
    private VentanaPrincipal ventanaPrincipal;

    /**
     * <p>
     * Agrega el panel buscar y los botones que se visualizan en la ventana.
     * </p>
     * 
     * @param ventanaPrincipal. Referencia a la clase <code>VentanaPrincipal</code>
     * @see #ventanaPrincipal.
     */
    public DialogoEliminarRegistro(VentanaPrincipal ventanaPrincipal) {
        super(ventanaPrincipal, "Buscar registro", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        cerrarBoton = new JButton("Cerrar");
        cerrarBoton.addActionListener(this);
        cerrarBoton.setBounds(560, 380, 120, 35);
        cerrarBoton.setIcon(new ImageIcon(getClass().getResource(
                "/imagenes/cerrar.png")));
        add(cerrarBoton);

        eliminarRegistroBoton = new JButton("Eliminar registro");
        eliminarRegistroBoton.addActionListener(this);
        eliminarRegistroBoton.setBounds(220, 380, 200, 35);
        eliminarRegistroBoton.setIcon(new ImageIcon(getClass().getResource(
                "/imagenes/eliminar.png")));
        add(eliminarRegistroBoton);

        panelBuscar = new PanelBuscar(ventanaPrincipal.getLista().getListaTramitesEsp());
        panelBuscar.setBounds(20, 20, 840, 350);
        add(panelBuscar);

        setSize(900, 465);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    /**
     * <p>
     * Elimina el trámite específico seleccionado en la tabla de la lista de
     * trámites. Elimina de la tabla la fila seleccionada.
     * </p>
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cerrarBoton)) {
            dispose();
        } else if (e.getSource().equals(eliminarRegistroBoton)) {
            int index = ventanaPrincipal.getLista().getListaTramitesEsp().lastIndexOf(panelBuscar.getTramiteSeleccionado());
            System.out.println("INDEX : " + index);
  
            ventanaPrincipal.getLista().getListaTramitesEsp().remove(index);
            System.out.println("File Seleccionada : " + panelBuscar.obtenerFilaSeleccionada());
            panelBuscar.eliminarFilaSeleccionada(panelBuscar.obtenerFilaSeleccionada());
            panelBuscar.buscar("", "");
        }
    }
}
