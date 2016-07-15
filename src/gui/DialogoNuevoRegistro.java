package gui;

import excepcion.BaseDatosException;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import lib.JIntegerTextField;

import com.toedter.calendar.JDateChooser;

import dominio.Campo;
import dominio.Paso;
import dominio.PasoEspecifico;
import dominio.Tramite;
import dominio.TramiteEspecifico;
import excepcion.TramiteEspecificoException;
import excepcion.TramiteException;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Guarda un nuevo trámite especifico.
 * <p>
 * Muestra todos los pasos, campos y valores por defecto de un trámite. Guarda
 * los valores que se le asignan a los campos y pasos en un nuevo trámite
 * específico.
 * </p>
 * <p>
 * La ventana cuenta con dos paneles donde se muestran los campos y pasos de un
 * trámite. Dos botones; uno para aceptar (se guarda un nuevo trámite específico)
 * y uno para cancelar (no se guarda ningún cambio).
 * </p>
 * 
 * @author jesus
 *
 */
public class DialogoNuevoRegistro extends JDialog implements ActionListener {

    private static final long serialVersionUID = -5023492823414749837L;
    /**
     * Guarda un nuevo trámite específico.
     */
    private JButton aceptarBoton;
    /**
     *  Cierra la ventana y no se guarda ningún cambio.
     */
    private JButton cancelarBoton;
    /**
     * Muestra un panel con los campos del trámite.
     */
    private PanelCampo panelCampo;
    /**
     * Scroll que sirve para desplazar el panel donde se muestran los campos.
     */
    private JScrollPane scrollCampo;
    /**
     * Muestra un panel con los pasos del trámite que tienen fecha límite y son
     * obligatorios.
     */
    private PanelDetallesPaso panelDetallesPaso;
    /**
     *  Scroll que sirve para desplazar el panel donde se muestran los pasos.
     */
    private JScrollPane scrollDetallesPaso;
    /**
     * Referencia al trámite que se está utilizando.
     */
    private Tramite tramite;
    /**
     * Ubica el diálogo dentro de la ventana principal del sistema. Se obtiene
     * todos los datos y trámites que se han guardado.
     */
    private VentanaPrincipal ventanaPrincipal;

    /**
     * Inicializa los paneles y botones que se muestran en la ventana.
     * <p>
     * Se crean los objetos <code>PanelDetallesPasos</code> y
     * <code>PanelCampo</code>, estos muestran los campos y pasos de un trámite.
     * </p>
     * Se crean los métodos para escuchar los eventos de los botones.
     * 
     * @param ventanaPrincipal	Referencia para obtener el trámite que se está utilizando.
     * 
     * @see PanelCampo
     * @see PanelDetallesPaso
     */
    public DialogoNuevoRegistro(VentanaPrincipal ventanaPrincipal) {
        super(ventanaPrincipal, "Nuevo registro", true);
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        tramite = ventanaPrincipal.getLista().getTramite();

        aceptarBoton = new JButton("Aceptar");
        aceptarBoton.setBounds(240, 640, 150, 35);
        aceptarBoton.addActionListener(this);
        aceptarBoton.setIcon(new ImageIcon(getClass().getResource(
                "/imagenes/aceptar.png")));
        add(aceptarBoton);

        cancelarBoton = new JButton("Cancelar");
        cancelarBoton.setBounds(630, 640, 150, 35);
        cancelarBoton.addActionListener(this);
        cancelarBoton.setIcon(new ImageIcon(getClass().getResource(
                "/imagenes/cancelar.png")));
        add(cancelarBoton);

        panelCampo = new PanelCampo(tramite.getCampos());
        scrollCampo = new JScrollPane(panelCampo);
        scrollCampo.setBounds(50, 10, 920, 300);
        add(scrollCampo);

        JLabel etiquetaAvisoNuevoRegistro = new JLabel(
                "Los campos marcados con * son obligatorios");
        etiquetaAvisoNuevoRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
        etiquetaAvisoNuevoRegistro.setForeground(Color.RED);
        etiquetaAvisoNuevoRegistro.setBounds(60, 310, 300, 50);
        add(etiquetaAvisoNuevoRegistro);

        panelDetallesPaso = new PanelDetallesPaso(tramite.getPasos());
        scrollDetallesPaso = new JScrollPane(panelDetallesPaso);
        scrollDetallesPaso.setBounds(50, 360, 920, 250);
        add(scrollDetallesPaso);

        setSize(1020, 730);
        setLocationRelativeTo(ventanaPrincipal);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Método que escucha los eventos de los botones aceptar y cancelar. Si se
     * presiona el botón aceptar se guardan el trámite especifico. Si se
     * presiona el botón cancelar se muestra un mensaje de confirmación y no se
     * guarda ningún cambio.
     * 
     * 
     * @param e		Indica qué evento se va a realizar.
     * @see #aceptar()
     * @see #cancelar()
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cancelarBoton)) {
            cancelar();
        } else if (e.getSource().equals(aceptarBoton)) {
            try {
                try {
                    aceptar();
                } catch (ParseException ex) {
                } catch (TramiteException | SQLException ex) {
                    Logger.getLogger(DialogoNuevoRegistro.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (BaseDatosException ex) {
            }
        }
    }

    /**
     * Guarda los valores en un nuevo trámite especifico si la validación de los
     * datos es correcta. Cierra la ventana.
     * Si la validación de los datos no es correcta se manda un mensaje de error.
     * 
     * @see #validar()
     * @see #guardarValores()
     */
    private void aceptar() throws BaseDatosException, ParseException, TramiteException, SQLException {
        try {
            validar();
            guardarValores();
            JOptionPane.showMessageDialog(this,
                    "Se han guardado correctamente los valores",
                    "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (TramiteEspecificoException err) {
            JOptionPane.showMessageDialog(this, err.getMessage(),
                    err.getTitulo(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra la ventana y no guarda ningún cambio.
     */
    private void cancelar() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "Está seguro que desea salir?", "Confirmación",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    /**
     * Verifica que los campos obligatorios tengan asignado algún valor.
     * <p>
     * Recorre todos los campos de un trámite y verifica el tipo de campo (
     * <code>FECHA</code>,<code> NUMERO</code>,<code> TEXTO</code>,
     * <code> OPCEXCL</code>,<code> OPCMULT</code>). Si el campo es de tipo
     * fecha, verifica que la fecha no sea nula. Si el campo es de tipo texto o
     * número, verifica que se le asigne algún valor. Si el campo es de tipo
     * opciones múltiples u opciones excluyentes verifica que tenga seleccionado
     * algún valor.
     * </p>
     * 
     * @throws TramiteEspecificoException
     *             Excepción que es lanzada si alguno de los campos es
     *             obligatorio y no se asigna ningún valor.
     */
    private void validar() throws TramiteEspecificoException {
        ArrayList<Campo> campos = tramite.getCampos();
        ArrayList<Object> componentes = panelCampo.getComponentes();
        int index = 0;
        JTextField texto;
        JDateChooser fecha;
        JIntegerTextField numero;
        JList<?> lista;
        JComboBox<?> comboBox;
        for (Campo c : campos) {
            switch (c.getTipo()) {
                case FECHA:
                    fecha = (JDateChooser) componentes.get(index);
                    if (c.isObligatorio() && fecha.getDate() == null) {
                        throw new TramiteEspecificoException(
                                TramiteEspecificoException.FECHA_NULL);
                    }
                    break;
                case NUMERO:
                    numero = (JIntegerTextField) componentes.get(index);
                    if (c.isObligatorio() && numero.getText().equals("")) {
                        throw new TramiteEspecificoException(
                                TramiteEspecificoException.NUMERO_VACIO);
                    }
                    break;
                case OPCEXCL:
                    comboBox = (JComboBox<?>) componentes.get(index);
                    if (c.isObligatorio() && comboBox.getSelectedIndex() == -1) {
                        throw new TramiteEspecificoException(
                                TramiteEspecificoException.COMBO_VACIO);
                    }
                    break;
                case OPCMULT:
                    lista = (JList<?>) componentes.get(index);
                    if (c.isObligatorio() && lista.getSelectedIndex() == -1) {
                        throw new TramiteEspecificoException(
                                TramiteEspecificoException.LISTA_VACIO);
                    }
                    break;
                case TEXTO:
                    texto = (JTextField) componentes.get(index);
                    if (c.isObligatorio() && texto.getText().equals("")) {
                        throw new TramiteEspecificoException(
                                TramiteEspecificoException.CAMPO_TEXTO_VACIO);
                    }
                    break;
            }
            index++;
        }
    }

    /**
     * Guarda los valores que se le han asignado a los campos y pasos.
     * <p>
     * Recorre todo los campos del trámite y verifica de que tipo es (
     * <code>FECHA</code>,<code> NUMERO</code>,<code> TEXTO</code>,
     * <code> OPCEXCL</code>,<code> OPCMULT</code>).
     * </p>
     * <p>
     * Para los campos se guardan los valores en un arreglo.
     * </p>
     * <p>
     * Recorre todos los pasos del trámite, los que tengan fechas límite y que
     * sean obligatorios se guardan la fechas que se le asigna al paso
     * específico.
     * </p>
     * <p>
     * Se asigna un número de id al trámite, el número de id de trámite se basa
     * en último trámite guardado. Agrega el trámite específico a la lista de
     * trámites.
     * </p>
     */
    private void guardarValores() throws BaseDatosException, ParseException, TramiteException, SQLException {
        TramiteEspecifico tramiteEspecifico = new TramiteEspecifico(ventanaPrincipal.getLista().getBd());
        ArrayList<TramiteEspecifico> listaTramiteEsp = ventanaPrincipal.getLista().getListaTramitesEsp();
        ArrayList<Campo> campos = tramite.getCampos();
        ArrayList<Paso> pasos = tramite.getPasos();
        ArrayList<Object> componentes = panelCampo.getComponentes();
        ArrayList<JDateChooser> fechas = panelDetallesPaso.getComponentes();
        int index = 0;
        int idTramite = 1;
        if (listaTramiteEsp.size() > 0) {
            TramiteEspecifico ultimoTramite = listaTramiteEsp.get((listaTramiteEsp.size() - 1));
            idTramite = ultimoTramite.getIdTramite() + 1;
        }
        JTextField texto;
        JDateChooser fecha;
        JIntegerTextField numero;
        JList<?> lista;
        JComboBox<?> comboBox;
        ListModel<?> modelo;
        for (Campo c : campos) {
            String[] valores = null;
            switch (c.getTipo()) {
                case FECHA:
                    valores = new String[1];
                    fecha = (JDateChooser) componentes.get(index);
                    DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    Date d = new Date(fecha.getDate().getTime());
                    valores[0] = formato.format(d);
                    break;
                case NUMERO:
                    valores = new String[1];
                    numero = (JIntegerTextField) componentes.get(index);
                    valores[0] = numero.getText();
                    break;
                case OPCEXCL:
                    valores = new String[1];
                    comboBox = (JComboBox<?>) componentes.get(index);
                    valores[0] = (String) comboBox.getSelectedItem();
                    break;
                case OPCMULT:
                    lista = (JList<?>) componentes.get(index);
                    modelo = lista.getModel();
                    int[] indices = lista.getSelectedIndices();
                    valores = new String[1];
                    valores[0] = "";
                    for (int i = 0; i < indices.length; i++) {
                        valores[0] += (String) modelo.getElementAt(indices[i]); // obtenemos los valores a partitr de los índices
                        if (i != indices.length - 1) // mientras no sea el ultimo elemento, separamos con una "/".
                        {
                            valores[0] += " / ";
                        }
                    }
                    break;
                case TEXTO:
                    valores = new String[1];
                    texto = (JTextField) componentes.get(index);
                    valores[0] = texto.getText();
                    break;
            }
            tramiteEspecifico.agregarCampo(c, valores);
            index++;
        }
        index = 0;
        for (Paso p : pasos) {
                for (int i = 1; i <= p.getRepeticion(); i++) {
                    PasoEspecifico pasoEspecifico = new PasoEspecifico();
                    pasoEspecifico.setNombrePaso(p.getNombrePaso());
                    pasoEspecifico.setNumPaso(p.getNumPaso());
                    pasoEspecifico.setRepeticion(p.getRepeticion());
                    pasoEspecifico.setRealizado(false);
                    pasoEspecifico.setFechaRealizacion(null);
                    pasoEspecifico.setDocumento("");
                    if (p.isObligatorio() && p.isConFechaLimite()) {
                        fecha = fechas.get(index);
                        pasoEspecifico.setFechaLimite(fecha.getDate());
                        index++;
                    } else {
                        pasoEspecifico.setFechaLimite(null);
                    }
                    tramiteEspecifico.agregarPasoEspecifico(pasoEspecifico);
                } 
        }
        tramiteEspecifico.setIdTramite(idTramite);
        ventanaPrincipal.getLista().agregarTramiteEspecifico(tramiteEspecifico);
       
        //insertar el tramite especifico en BD
        tramiteEspecifico.insertarTramiteEspecifico();
    }
}