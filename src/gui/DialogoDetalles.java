package gui;

import dominio.ListaTramites;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dominio.TramiteEspecifico;

/**
 * Permite ver los detalles de un trámite específico.
 *
 */
public class DialogoDetalles extends JDialog implements ActionListener {

    private static final long serialVersionUID = -7127205367581348873L;
    private JButton cerrarBoton;
    private JLabel etiquetaVaciaNombre;
    private JLabel etiquetaVaciaTitulo;
    private JLabel etiquetaVaciaFechaInicio;
    private JLabel etiquetaVaciaFechaFin;
    private JLabel etiquetaVaciaEstado;
    private JPanel camposExtras;
    private TramiteEspecifico tramiteEspecifico;

    /**
     * Constructor que recibe los objetos necesarios para extraer la información
     * del trámite y del trámite específico.
     *
     * @param ventanaPrincipal Ventana sobre la que se mostrará el diálogo.
     * @param tramiteEspecifico Almacena el trámite específico con el que se
     * está trabajando.
     */
    public DialogoDetalles(VentanaPrincipal ventanaPrincipal, TramiteEspecifico tramiteEspecifico) {
        super(ventanaPrincipal, "Detalles del trámite", true);
        setLayout(null);
        this.tramiteEspecifico = tramiteEspecifico;

        JLabel etiquetaNombreSolicitante = new JLabel("Nombre del solicitante: ");
        etiquetaNombreSolicitante.setBounds(20, 30, 150, 50);
        add(etiquetaNombreSolicitante);
        etiquetaVaciaNombre = new JLabel("*");
        etiquetaVaciaNombre.setBounds(210, 30, 150, 50);
        add(etiquetaVaciaNombre);

        JLabel etiquetaTitulo = new JLabel("Título: ");
        etiquetaTitulo.setBounds(20, 60, 150, 50);
        add(etiquetaTitulo);
        etiquetaVaciaTitulo = new JLabel("*");
        etiquetaVaciaTitulo.setBounds(210, 60, 150, 50);
        add(etiquetaVaciaTitulo);

        JLabel etiquetaFechaInicio = new JLabel("Fecha de inicio: ");
        etiquetaFechaInicio.setBounds(20, 90, 150, 50);
        add(etiquetaFechaInicio);
        etiquetaVaciaFechaInicio = new JLabel("*");
        etiquetaVaciaFechaInicio.setBounds(210, 90, 150, 50);
        add(etiquetaVaciaFechaInicio);

        JLabel etiquetaFechaFin = new JLabel("Fecha de fin: ");
        etiquetaFechaFin.setBounds(20, 120, 150, 50);
        add(etiquetaFechaFin);
        etiquetaVaciaFechaFin = new JLabel("*");
        etiquetaVaciaFechaFin.setBounds(210, 120, 150, 50);
        add(etiquetaVaciaFechaFin);

        JLabel etiquetaEstado = new JLabel("Estado: ");
        etiquetaEstado.setBounds(20, 150, 150, 50);
        add(etiquetaEstado);
        etiquetaVaciaEstado = new JLabel("*");
        etiquetaVaciaEstado.setBounds(210, 150, 150, 50);
        add(etiquetaVaciaEstado);

        camposExtras = new JPanel();
        JScrollPane panel = new JScrollPane(camposExtras);
        panel.setBounds(20, 190, 400, 150);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(panel);

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

    /**
     * Método para llenar la interfáz a partir de la lista de valores que se
     * almacenó al abrir el trámite.
     *
     * @see ListaTramites#abrirArchivo()
     */
    private void inicializar() {
        etiquetaVaciaNombre.setText(tramiteEspecifico.getValores().get(0)[0]);
        etiquetaVaciaTitulo.setText(tramiteEspecifico.getValores().get(1)[0]);
        etiquetaVaciaFechaInicio.setText(tramiteEspecifico.getValores().get(2)[0]);
        etiquetaVaciaFechaFin.setText(tramiteEspecifico.getValores().get(3)[0]);
        etiquetaVaciaEstado.setText(tramiteEspecifico.getValores().get(4)[0]);

        int numeroCampos = tramiteEspecifico.getCampos().size() - 5;
        camposExtras.setLayout(new GridLayout(numeroCampos, 2, 1, 5));
        for (int i = 5; i < tramiteEspecifico.getCampos().size(); i++) {
            JLabel etiqueta = new JLabel(tramiteEspecifico.getCampos().get(i).getNombreCampo() + ":");
            etiqueta.setPreferredSize(new Dimension(150, 30));
            camposExtras.add(etiqueta);
            JLabel valor = new JLabel(tramiteEspecifico.getValores().get(i)[0]);
            camposExtras.add(valor);
        }
    }

    /**
     * Método que escucha los eventos de los botones.
     * <p>
     * Este metodo ejecuta las acciones/eventos que se hayan establecido para un
     * determinado botón.
     * </p>
     *
     * @param e Almacena las acciones que el botón invoque cuando el usuario
     * interactúa con algún botón.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cerrarBoton)) {
            dispose();
        }
    }
}
