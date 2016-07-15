package gui;

import basedatos.BaseDatos;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dominio.Campo;
import dominio.Consulta;
import dominio.Tramite;
import dominio.TramiteEspecifico;
import excepcion.BaseDatosException;
import excepcion.TramiteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.JIntegerTextField;

public class DialogoNuevaConsulta extends JDialog implements ActionListener {

    private static final long serialVersionUID = 2033028194599228455L;

    private JComboBox<Campo> comboCampos;
    private JButton agregarCriterioBoton;
    private JList<String> criteriosBusquedaList;
    private DefaultListModel<String> modeloCriteriosBusqueda;
    private JScrollPane scrollCriteriosBusqueda;
    private JButton quitarBoton;
    private JButton modificarBoton;
    private JButton realizarBusquedaBoton;
    private JButton guardarConsultaBoton;
    private JButton nuevaConsultaBoton;
    private JButton cerrarBoton;
    private JButton detallesBoton;
    private JTable tablaConsulta;
    private DefaultTableModel modeloTablaConsulta;
    private final String[] columnNames = {"Nombre ", "Título", "Fecha de inicio",
        "Fecha de fin", "Estado"};

    private JLabel etiquetaValores;
    private JTextField valoresTexto;
    private JLabel etiquetaValoresNumeroMayor;
    private JLabel etiquetaValoresNumeroMenor;
    private JIntegerTextField valoresNumeroMayor;
    private JIntegerTextField valoresNumeroMenor;
    private JLabel etiquetaFechaInicio;
    private JLabel etiquetaFechaFin;
    private JDateChooser fechaChooserInicio;
    private JDateChooser fechaChooserFin;
    private JLabel etiquetaValoresCombo;
    private JComboBox<String> valoresCombo;
    private JLabel etiquetaValoresLista;
    private JList<String> valoresLista;
    private DefaultListModel<String> modeloValoresLista;
    private JScrollPane scrollValoresLista;
    private JLabel etiquetaAyudaLista;

    private Tramite tramite;
    private Consulta consulta;
    private VentanaPrincipal ventanaPrincipal;
    private boolean hayCambios;

    public DialogoNuevaConsulta(VentanaPrincipal ventanaPrincipal) {
        this(ventanaPrincipal, new Consulta());
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        tramite = ventanaPrincipal.getLista().getTramite();
    }

    public DialogoNuevaConsulta(VentanaPrincipal ventanaPrincipal, Consulta consulta) {
        super(ventanaPrincipal, "Nueva consulta", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        this.consulta = consulta;
        tramite = ventanaPrincipal.getLista().getTramite();

        JLabel etiquetaCampos = new JLabel("Campos: ");
        etiquetaCampos.setBounds(60, 15, 150, 50);
        add(etiquetaCampos);
        comboCampos = new JComboBox<Campo>();
        comboCampos.setBounds(150, 25, 200, 25);
        add(comboCampos);

        etiquetaValores = new JLabel("Valor: ");
        etiquetaValores.setBounds(500, 25, 100, 50);
        add(etiquetaValores);
        valoresTexto = new JTextField();
        valoresTexto.setBounds(600, 35, 200, 25);
        add(valoresTexto);
        etiquetaValoresNumeroMayor = new JLabel("Mayor o igual que");
        etiquetaValoresNumeroMayor.setBounds(400, 25, 150, 50);
        add(etiquetaValoresNumeroMayor);
        valoresNumeroMayor = new JIntegerTextField(0);
        valoresNumeroMayor.setBounds(510, 35, 100, 25);
        add(valoresNumeroMayor);
        etiquetaValoresNumeroMenor = new JLabel("Menor o igual que");
        etiquetaValoresNumeroMenor.setBounds(640, 25, 150, 50);
        add(etiquetaValoresNumeroMenor);
        valoresNumeroMenor = new JIntegerTextField(0);
        valoresNumeroMenor.setBounds(760, 35, 100, 25);
        add(valoresNumeroMenor);
        etiquetaFechaInicio = new JLabel("Fecha inicial: ");
        etiquetaFechaInicio.setBounds(500, 25, 150, 50);
        add(etiquetaFechaInicio);
        fechaChooserInicio = new JDateChooser();
        fechaChooserInicio.setBounds(600, 35, 100, 30);
        add(fechaChooserInicio);
        etiquetaFechaFin = new JLabel("Fecha final: ");
        etiquetaFechaFin.setBounds(720, 25, 150, 50);
        add(etiquetaFechaFin);
        fechaChooserFin = new JDateChooser();
        fechaChooserFin.setBounds(800, 35, 100, 30);
        add(fechaChooserFin);
        etiquetaValoresCombo = new JLabel("Valores");
        etiquetaValoresCombo.setBounds(500, 25, 100, 50);
        add(etiquetaValoresCombo);
        valoresCombo = new JComboBox<String>();
        valoresCombo.setBounds(600, 35, 200, 25);
        add(valoresCombo);
        etiquetaValoresLista = new JLabel("Valores");
        etiquetaValoresLista.setBounds(500, 25, 100, 50);
        add(etiquetaValoresLista);
        modeloValoresLista = new DefaultListModel<String>();
        valoresLista = new JList<String>(modeloValoresLista);
        valoresLista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollValoresLista = new JScrollPane(valoresLista);
        scrollValoresLista.setBounds(600, 35, 200, 120);
        add(scrollValoresLista);
        etiquetaAyudaLista = new JLabel("Se pueden seleccionar varios valores usando la tecla <Ctrl>");
        etiquetaAyudaLista.setBounds(500, 160, 400, 30);
        add(etiquetaAyudaLista);

        agregarCriterioBoton = new JButton("Agregar criterio");
        agregarCriterioBoton.setBounds(150, 120, 200, 30);
        agregarCriterioBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/agregarCriterio.png")));
        agregarCriterioBoton.addActionListener(this);
        add(agregarCriterioBoton);

        JLabel etiquetaCriteriosBusqueda = new JLabel("Criterios de búsqueda");
        etiquetaCriteriosBusqueda.setBounds(60, 180, 250, 50);
        etiquetaCriteriosBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(etiquetaCriteriosBusqueda);

        modeloCriteriosBusqueda = new DefaultListModel<String>();
        criteriosBusquedaList = new JList<String>(modeloCriteriosBusqueda);
        criteriosBusquedaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(criteriosBusquedaList);
        scrollCriteriosBusqueda = new JScrollPane();
        scrollCriteriosBusqueda.setBounds(150, 230, 250, 140);
        scrollCriteriosBusqueda.setViewportView(criteriosBusquedaList);
        criteriosBusquedaList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getSource().equals(criteriosBusquedaList)) {
                    if (e.getClickCount() == 2) {
                        modificar();
                    }
                }
            }
        });
        add(scrollCriteriosBusqueda);

        modificarBoton = new JButton("Modificar");
        modificarBoton.setBounds(450, 260, 150, 30);
        modificarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/modificarConsulta.png")));
        modificarBoton.addActionListener(this);
        add(modificarBoton);

        quitarBoton = new JButton("Quitar");
        quitarBoton.setBounds(450, 300, 150, 30);
        quitarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/quitar.png")));
        quitarBoton.addActionListener(this);
        add(quitarBoton);

        realizarBusquedaBoton = new JButton("Realizar búsqueda");
        realizarBusquedaBoton.setBounds(700, 220, 200, 30);
        realizarBusquedaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/realizarBusqueda.png")));
        realizarBusquedaBoton.addActionListener(this);
        add(realizarBusquedaBoton);

        guardarConsultaBoton = new JButton("Guardar Consulta");
        guardarConsultaBoton.setBounds(700, 260, 200, 30);
        guardarConsultaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/guardarConsulta.png")));
        guardarConsultaBoton.addActionListener(this);
        add(guardarConsultaBoton);

        nuevaConsultaBoton = new JButton("Nueva consulta");
        nuevaConsultaBoton.setBounds(700, 300, 200, 30);
        nuevaConsultaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/nuevaConsultaQ.png")));
        nuevaConsultaBoton.addActionListener(this);
        add(nuevaConsultaBoton);

        cerrarBoton = new JButton("Cerrar");
        cerrarBoton.setBounds(700, 340, 200, 30);
        cerrarBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        cerrarBoton.addActionListener(this);
        add(cerrarBoton);

        Object[][] data = {};
        modeloTablaConsulta = new DefaultTableModel(data, columnNames);
        tablaConsulta = new JTable(modeloTablaConsulta);
        JScrollPane scrollPane = new JScrollPane(tablaConsulta);
        scrollPane.setBounds(25, 450, 700, 140);
        add(scrollPane);

        detallesBoton = new JButton("Detalles");
        detallesBoton.setBounds(750, 500, 150, 30);
        detallesBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/detalles.png")));
        detallesBoton.addActionListener(this);
        add(detallesBoton);

        inicializar();

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    cerrar();
                } catch (BaseDatosException ex) {
                    Logger.getLogger(DialogoNuevaConsulta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        setSize(1020, 730);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    private void inicializar() {
        etiquetaValores.setVisible(false);
        valoresTexto.setVisible(false);
        etiquetaValoresNumeroMayor.setVisible(false);
        valoresNumeroMayor.setVisible(false);
        etiquetaValoresNumeroMenor.setVisible(false);
        valoresNumeroMenor.setVisible(false);
        etiquetaFechaInicio.setVisible(false);
        fechaChooserInicio.setVisible(false);
        etiquetaFechaFin.setVisible(false);
        fechaChooserFin.setVisible(false);
        etiquetaValoresCombo.setVisible(false);
        valoresCombo.setVisible(false);
        etiquetaValoresLista.setVisible(false);
        scrollValoresLista.setVisible(false);
        etiquetaAyudaLista.setVisible(false);

        for (Campo c : tramite.getCampos()) {
            comboCampos.addItem(c);
        }
        comboCampos.addActionListener(this);
        comboCampos.setSelectedIndex(0);
        hayCambios = false;

        if (!consulta.getNombreConsulta().isEmpty()) {
            // cargar los datos de la consulta
            consulta = new Consulta(consulta);
            for (int i = 0; true; i++) {
                Campo c = consulta.obtenerCampo(i);
                if (c == null) {
                    break;
                }
                String[] valores = consulta.obtenerValores(i);
                String criterio = c.getNombreCampo();
                switch (c.getTipo()) {
                    case FECHA:

                        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        if (!valores[0].isEmpty()) {
                            criterio += " >= " + formato.format(new Date(Long.parseLong(valores[0])));
                        }
                        if (!valores[1].isEmpty()) {
                            criterio += " <= " + formato.format(new Date(Long.parseLong(valores[1])));
                        }
                        break;
                    case NUMERO:
                        if (!valores[0].isEmpty()) {
                            criterio += " >= " + valores[0];
                        }
                        if (!valores[1].isEmpty()) {
                            criterio += " <= " + valores[1];
                        }
                        break;
                    case OPCEXCL:
                        criterio += " = " + valores[0];
                        break;
                    case OPCMULT:
                        criterio += " = ";
                        for (int indice = 0; indice < valores.length; indice++) {
                            if (indice != 0) {
                                criterio += " + ";
                            }
                            criterio += valores[indice];
                        }
                        break;
                    case TEXTO:
                        criterio += " = " + valores[0];
                        break;
                }
                modeloCriteriosBusqueda.addElement(criterio);
            }
            setTitle("Consulta " + consulta.getNombreConsulta());
        } else {
            setTitle("Nueva consulta");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(comboCampos)) {
            cambiarTipo();
        } else if (e.getSource().equals(agregarCriterioBoton)) {
            agregarCriterio();
        } else if (e.getSource().equals(modificarBoton)) {
            modificar();
        } else if (e.getSource().equals(quitarBoton)) {
            quitar();
        } else if (e.getSource().equals(realizarBusquedaBoton)) {
            try {
                realizarBusqueda();
            } catch (BaseDatosException | SQLException ex) {
                Logger.getLogger(DialogoNuevaConsulta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(guardarConsultaBoton)) {
            try {
                guardarConsulta();
            } catch (BaseDatosException ex) {
                Logger.getLogger(DialogoNuevaConsulta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(nuevaConsultaBoton)) {
            try {
                iniciarConsulta();
            } catch (BaseDatosException ex) {
                Logger.getLogger(DialogoNuevaConsulta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(cerrarBoton)) {
            try {
                cerrar();
            } catch (BaseDatosException ex) {
                Logger.getLogger(DialogoNuevaConsulta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(detallesBoton)) {
            mostrarDetalles();
        }
    }

    private void cambiarTipo() {
        if (comboCampos.getSelectedItem() == null) {
            return;
        }
        Campo c = (Campo) comboCampos.getSelectedItem();
        switch (c.getTipo()) {
            case TEXTO:
                etiquetaValores.setVisible(true);
                valoresTexto.setVisible(true);
                etiquetaValoresNumeroMayor.setVisible(false);
                valoresNumeroMayor.setVisible(false);
                etiquetaValoresNumeroMenor.setVisible(false);
                valoresNumeroMenor.setVisible(false);
                etiquetaFechaInicio.setVisible(false);
                fechaChooserInicio.setVisible(false);
                etiquetaFechaFin.setVisible(false);
                fechaChooserFin.setVisible(false);
                etiquetaValoresCombo.setVisible(false);
                valoresCombo.setVisible(false);
                etiquetaValoresLista.setVisible(false);
                scrollValoresLista.setVisible(false);
                etiquetaAyudaLista.setVisible(false);
                break;
            case NUMERO:
                etiquetaValores.setVisible(false);
                valoresTexto.setVisible(false);
                etiquetaValoresNumeroMayor.setVisible(true);
                valoresNumeroMayor.setVisible(true);
                etiquetaValoresNumeroMenor.setVisible(true);
                valoresNumeroMenor.setVisible(true);
                etiquetaFechaInicio.setVisible(false);
                fechaChooserInicio.setVisible(false);
                etiquetaFechaFin.setVisible(false);
                fechaChooserFin.setVisible(false);
                etiquetaValoresCombo.setVisible(false);
                valoresCombo.setVisible(false);
                etiquetaValoresLista.setVisible(false);
                scrollValoresLista.setVisible(false);
                etiquetaAyudaLista.setVisible(false);
                break;
            case FECHA:
                etiquetaValores.setVisible(false);
                valoresTexto.setVisible(false);
                etiquetaValoresNumeroMayor.setVisible(false);
                valoresNumeroMayor.setVisible(false);
                etiquetaValoresNumeroMenor.setVisible(false);
                valoresNumeroMenor.setVisible(false);
                etiquetaFechaInicio.setVisible(true);
                fechaChooserInicio.setVisible(true);
                etiquetaFechaFin.setVisible(true);
                fechaChooserFin.setVisible(true);
                etiquetaValoresCombo.setVisible(false);
                valoresCombo.setVisible(false);
                etiquetaValoresLista.setVisible(false);
                scrollValoresLista.setVisible(false);
                etiquetaAyudaLista.setVisible(false);
                break;
            case OPCEXCL:
                etiquetaValores.setVisible(false);
                valoresTexto.setVisible(false);
                etiquetaValoresNumeroMayor.setVisible(false);
                valoresNumeroMayor.setVisible(false);
                etiquetaValoresNumeroMenor.setVisible(false);
                valoresNumeroMenor.setVisible(false);
                etiquetaFechaInicio.setVisible(false);
                fechaChooserInicio.setVisible(false);
                etiquetaFechaFin.setVisible(false);
                fechaChooserFin.setVisible(false);
                etiquetaValoresCombo.setVisible(true);
                valoresCombo.setVisible(true);
                valoresCombo.removeAllItems();
                for (String opcion : c.getValorDefectoOpciones()) {
                    valoresCombo.addItem(opcion);
                }
                etiquetaValoresLista.setVisible(false);
                scrollValoresLista.setVisible(false);
                etiquetaAyudaLista.setVisible(false);
                break;
            case OPCMULT:
                etiquetaValores.setVisible(false);
                valoresTexto.setVisible(false);
                etiquetaValoresNumeroMayor.setVisible(false);
                valoresNumeroMayor.setVisible(false);
                etiquetaValoresNumeroMenor.setVisible(false);
                valoresNumeroMenor.setVisible(false);
                etiquetaFechaInicio.setVisible(false);
                fechaChooserInicio.setVisible(false);
                etiquetaFechaFin.setVisible(false);
                fechaChooserFin.setVisible(false);
                etiquetaValoresCombo.setVisible(false);
                valoresCombo.setVisible(false);
                etiquetaValoresLista.setVisible(true);
                scrollValoresLista.setVisible(true);
                etiquetaAyudaLista.setVisible(true);
                modeloValoresLista.clear();
                for (String opcion : c.getValorDefectoOpciones()) {
                    modeloValoresLista.addElement(opcion);
                }
                break;
        }
    }

    private void agregarCriterio() {
        // obtener el campo seleccionado
        Campo campo = (Campo) comboCampos.getSelectedItem();
        String valor = "";
        String criterio = campo.getNombreCampo();
        String[] valores = null;
        // obtener los valores escritos y limpiar
        switch (campo.getTipo()) {
            case TEXTO:
                if (valoresTexto.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe escribir una palabra a buscar", "Agregar criterio", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    valor = valoresTexto.getText().trim();
                    criterio += " = " + valor;
                    valoresTexto.setText("");
                    valores = new String[1];
                    valores[0] = valor;
                }
                break;
            case NUMERO:
                if (valoresNumeroMayor.getText().trim().isEmpty() && valoresNumeroMenor.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe escribir al menos un valor numérico para buscar", "Agregar criterio", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    valores = new String[2];
                    if (!valoresNumeroMayor.getText().trim().isEmpty()) {
                        valores[0] = String.valueOf(valoresNumeroMayor.getValue());
                        criterio += " >= " + valores[0];
                        valoresNumeroMayor.setText("");
                    } else {
                        valores[0] = "";
                    }
                    if (!valoresNumeroMenor.getText().trim().isEmpty()) {
                        valores[1] = String.valueOf(valoresNumeroMenor.getValue());
                        criterio += " <= " + valores[1];
                        valoresNumeroMenor.setText("");
                    } else {
                        valores[1] = "";
                    }
                }
                break;
            case OPCEXCL:
                if (valoresCombo.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un valor de la lista desplegable para buscar", "Agregar criterio", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    valor = (String) valoresCombo.getSelectedItem();
                    criterio += " = " + valor;
                    valoresCombo.setSelectedIndex(0);
                    valores = new String[1];
                    valores[0] = valor;
                }
                break;
            case OPCMULT:
                if (valoresLista.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un valor para buscar", "Agregar criterio", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    valores = new String[valoresLista.getSelectedIndices().length];
                    criterio += " = ";
                    int[] indices = valoresLista.getSelectedIndices();
                    for (int i = 0; i < valores.length; i++) {
                        valores[i] = modeloValoresLista.getElementAt(indices[i]);
                        if (i != 0) {
                            criterio += " + ";
                        }
                        criterio += valores[i];
                    }
                    valoresLista.removeSelectionInterval(indices[0], indices[indices.length - 1]);
                }
                break;
            case FECHA:
                if (fechaChooserInicio.getDate() == null && fechaChooserFin.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos una fecha para buscar", "Agregar criterio", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    valores = new String[2];
                    DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    if (fechaChooserInicio.getDate() != null) {
                        valores[0] = String.valueOf(fechaChooserInicio.getDate().getTime());
                        criterio += " >= " + formato.format(fechaChooserInicio.getDate());
                        fechaChooserInicio.setDate(null);
                    } else {
                        valores[0] = "";
                    }
                    if (fechaChooserFin.getDate() != null) {
                        valores[1] = String.valueOf(fechaChooserFin.getDate().getTime());
                        criterio += " <= " + formato.format(fechaChooserFin.getDate());
                        fechaChooserFin.setDate(null);
                    } else {
                        valores[1] = "";
                    }
                }
                break;
        }
        // agregar a la lista y a consulta
        modeloCriteriosBusqueda.addElement(criterio);
        consulta.agregarCampo(campo, valores);
        if (!hayCambios) {
            hayCambios = true;
            setTitle("* " + getTitle());
        }
    }

    private void modificar() {
        int posicion;
        // tomar la posición seleccionada
        posicion = criteriosBusquedaList.getSelectedIndex();
        // validar que haya algo seleccionado
        if (posicion < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista", "Quitar criterio", JOptionPane.ERROR_MESSAGE);
        } else {
            // mostrar los datos de nuevo en los componentes
            Campo campo = consulta.obtenerCampo(posicion);
            String[] valores = consulta.obtenerValores(posicion);
            comboCampos.setSelectedIndex(campo.getNumCampo());
            switch (campo.getTipo()) {
                case FECHA:
                    if (valores[0].isEmpty()) {
                        fechaChooserInicio.setDate(null);
                    } else {
                        fechaChooserInicio.setDate(new Date(Long.parseLong(valores[0])));
                    }
                    if (valores[1].isEmpty()) {
                        fechaChooserFin.setDate(null);
                    } else {
                        fechaChooserFin.setDate(new Date(Long.parseLong(valores[1])));
                    }
                    break;
                case NUMERO:
                    valoresNumeroMayor.setText(valores[0]);
                    valoresNumeroMenor.setText(valores[1]);
                    break;
                case OPCEXCL:
                    valoresCombo.setSelectedItem(valores[0]);
                    break;
                case OPCMULT:
                    int[] indices = new int[valores.length];
                    for (int i = 0; i < valores.length; i++) {
                        indices[i] = modeloValoresLista.indexOf(valores[i]);
                    }
                    valoresLista.setSelectedIndices(indices);
                    break;
                case TEXTO:
                    valoresTexto.setText(valores[0]);
                    break;
            }
            quitar();
            if (!hayCambios) {
                hayCambios = true;
                setTitle("* " + getTitle());
            }
        }
    }

    private void quitar() {
        int posicion;
        // tomar la posición seleccionada
        posicion = criteriosBusquedaList.getSelectedIndex();
        // validar que haya algo seleccionado
        if (posicion < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista", "Quitar criterio", JOptionPane.ERROR_MESSAGE);
        } else {
            // quitar el criterio
            modeloCriteriosBusqueda.remove(posicion);
            consulta.quitarCampo(posicion);
            if (!hayCambios) {
                hayCambios = true;
                setTitle("* " + getTitle());
            }
        }
    }
    
     /**
     * Método que ejecuta la consulta y cambia el modelo de la tabla de la interfaz
     *
     * @see Consulta#ejecutarConsulta(java.util.ArrayList) 
     * @see VentanaPrincipal#getLista()
     * 
     * @throws excepcion.BaseDatosException
     * @throws java.sql.SQLException
     *
     * @autor isaac
     */
    private void realizarBusqueda() throws BaseDatosException, SQLException {
        if (!modeloCriteriosBusqueda.isEmpty()) {
            this.tablaConsulta.setModel(consulta.ejecutarConsulta(ventanaPrincipal.getLista().getListaTramitesEsp()));
        } else {
            JOptionPane.showMessageDialog(this, "Primero debe agregar criterios de búsqueda."
                    + "\nFavor de intentarlo de nuevo.", "Realizar búsqueda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarConsulta() throws BaseDatosException {
        if (hayCambios) {
            if (!modeloCriteriosBusqueda.isEmpty()) {
                String nombre = (String) JOptionPane.showInputDialog(this, "Escriba un nombre para reconocer a esta consulta",
                        "Guardar Consulta", JOptionPane.QUESTION_MESSAGE, null, null, consulta.getNombreConsulta());
                if (nombre != null) {
                    if (!nombre.trim().isEmpty()) {
                        consulta.setNombreConsulta(nombre);
                        try {
                            ventanaPrincipal.getLista().agregarConsulta(consulta);
                            ventanaPrincipal.getLista().setHayCambios(true);
                            setTitle(nombre + " - Consulta");
                            hayCambios = false;
                            JOptionPane.showMessageDialog(this, "Consulta guardada con éxito", "Guardar consulta", JOptionPane.INFORMATION_MESSAGE);
                        } catch (TramiteException e) {
                            JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "El nombre escrito no es válido para el sistema."
                                + "\nFavor de intentarlo de nuevo.", "Guardar consulta", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Primero debe establecer criterios de búsqueda."
                        + "\nFavor de intentarlo de nuevo.", "Guardar consulta", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay datos nuevos para guardar.",
                    "Guardar consulta", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void iniciarConsulta() throws BaseDatosException {
        // guardar antes de hacer una nueva consulta
        if (hayCambios && !modeloCriteriosBusqueda.isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea guardar la consulta antes de iniciar una nueva?"
                    + "\nSi elige \"No\" se perderá la consulta realizada.", "Seguitrad UMAR",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                guardarConsulta();
            } else if (respuesta == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        // limpiar todo
        modeloCriteriosBusqueda.clear();
        Object[][] data = {};
        modeloTablaConsulta = new DefaultTableModel(data, columnNames);
        tablaConsulta = new JTable(modeloTablaConsulta);
        consulta = new Consulta();
        setTitle("Nueva consulta");
    }

    private void cerrar() throws BaseDatosException {
        if (hayCambios && !modeloCriteriosBusqueda.isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea guardar la consulta?"
                    + "\nSi elige \"No\" se perderá la consulta realizada.", "Seguitrad UMAR",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                guardarConsulta();
                dispose();
            } else if (respuesta == JOptionPane.NO_OPTION) {
                dispose();
            }
        } else {
            dispose();
        }
    }

    private void mostrarDetalles() {
        // Obtener la fila seleccionada
        // validar que haya algo seleccionado
        // Obtener el trámite específico seleccionado
        new DialogoSeguimiento(ventanaPrincipal, null);
    }
}
