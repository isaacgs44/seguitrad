/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dominio.Paso;
import dominio.Tramite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import lib.JIntegerTextField;

/**
 *
 * @author Cresenciofl
 */
public class DialogoModificarPaso extends JPanel implements ActionListener, ItemListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5406836847391738657L;
	private JTextField nombrePaso;
    private JRadioButton radioBotonPropio;
    private JRadioButton radioBotonOtro;
    private JTextField otro;
    private JRadioButton radioBotonFechaLimiteSi;
    private JRadioButton radioBotonFechaLimiteNo;
    private JRadioButton radioBotonPlantillaSi;
    private JRadioButton radioBotonPlantillaNo;
    private JTextField plantilla;
    private JRadioButton radioBotonDocumentoSi;
    private JRadioButton radioBotonDocumentoNo;
    private JRadioButton radioBotonCambioEstadoSi;
    private JRadioButton radioBotonCambioEstadoNo;
    private JComboBox<String> cambioEstado;
    private JRadioButton radioBotonSecuenciaSi;
    private JRadioButton radioBotonSecuenciaNo;
    private JComboBox<Paso> secuencia;
    private JRadioButton radioBotonObligatorioSi;
    private JRadioButton radioBotonObligatorioNo;
    private JRadioButton radioBotonRepeticionSi;
    private JRadioButton radioBotonRepeticionNo;
    private JButton seleccionarPlantillaBoton;
    private JRadioButton radioBotonVeces;
    private JRadioButton radioBotonIndefinido;
    private JIntegerTextField repeticion;

    public DialogoModificarPaso(Tramite tramite, Tramite tramiteAnterior, boolean hayTramitesEspecificos) {
        JLabel etiquetaNombre = new JLabel("Nombre: ");
		etiquetaNombre.setBounds(60, 40, 150, 50);
		add(etiquetaNombre);
		nombrePaso = new JTextField(20);
		nombrePaso.setBounds(210, 50, 200, 25);
		add(nombrePaso);

		JLabel etiquetaResponsable = new JLabel("Responsable: ");
		etiquetaResponsable.setBounds(60, 70, 150, 50);
		add(etiquetaResponsable);

		radioBotonPropio = new JRadioButton("Propio");
		radioBotonPropio.setBounds(210, 80, 90, 25);
		radioBotonPropio.addItemListener(this);
		add(radioBotonPropio);
		radioBotonOtro = new JRadioButton("Otro", false);
		radioBotonOtro.setBounds(315, 80, 50, 25);
		radioBotonOtro.addItemListener(this);
		add(radioBotonOtro);
		ButtonGroup grupo1 = new ButtonGroup();
		grupo1.add(radioBotonPropio);
		grupo1.add(radioBotonOtro);

		otro = new JTextField(20);
		otro.setBounds(400, 80, 200, 25);
		add(otro);

		JLabel etiquetaFechaLimite = new JLabel("Fecha Límite: ");
		etiquetaFechaLimite.setBounds(60, 100, 150, 50);
		add(etiquetaFechaLimite);

		radioBotonFechaLimiteSi = new JRadioButton("Sí");
		radioBotonFechaLimiteSi.setBounds(210, 100, 50, 55);
		add(radioBotonFechaLimiteSi);
		radioBotonFechaLimiteNo = new JRadioButton("No");
		radioBotonFechaLimiteNo.setBounds(315, 100,50, 55);
		add(radioBotonFechaLimiteNo);
		ButtonGroup grupo2 = new ButtonGroup();
		grupo2.add(radioBotonFechaLimiteSi);
		grupo2.add(radioBotonFechaLimiteNo);

		JLabel etiquetaPlantilla = new JLabel("Plantilla: ");
		etiquetaPlantilla.setBounds(60, 140, 150, 50);
		add(etiquetaPlantilla);

		radioBotonPlantillaSi = new JRadioButton("Sí");
		radioBotonPlantillaSi.setBounds(210, 155, 50, 25);
		radioBotonPlantillaSi.addItemListener(this);
		add(radioBotonPlantillaSi);

		plantilla = new JTextField(20);
		plantilla.setEditable(false);
		plantilla.setBounds(265, 155, 200, 25);
		add(plantilla);
		
		seleccionarPlantillaBoton = new JButton("");
		seleccionarPlantillaBoton.setBounds(480, 143, 40, 35);
		seleccionarPlantillaBoton.setIcon(new ImageIcon(getClass().getResource("/imagenes/folder.png")));
		seleccionarPlantillaBoton.addActionListener(this);
		add(seleccionarPlantillaBoton);
		
		radioBotonPlantillaNo = new JRadioButton("No");
		radioBotonPlantillaNo.setBounds(580, 155, 50, 25);
		radioBotonPlantillaNo.addItemListener(this);
		add(radioBotonPlantillaNo);
		ButtonGroup grupo3 = new ButtonGroup();
		grupo3.add(radioBotonPlantillaSi);
		grupo3.add(radioBotonPlantillaNo);

		JLabel etiquetaDocumentoEspecifico = new JLabel("Documento específico: ");
		etiquetaDocumentoEspecifico.setBounds(60, 185, 150, 50);
		add(etiquetaDocumentoEspecifico);

		radioBotonDocumentoSi = new JRadioButton("Sí");
		radioBotonDocumentoSi.setBounds(210, 185, 50, 55);
		add(radioBotonDocumentoSi);
		radioBotonDocumentoNo = new JRadioButton("No");
		radioBotonDocumentoNo.setBounds(315, 185, 50, 55);
		add(radioBotonDocumentoNo);
		ButtonGroup grupo4 = new ButtonGroup();
		grupo4.add(radioBotonDocumentoSi);
		grupo4.add(radioBotonDocumentoNo);

		JLabel etiquetaCambioEstado = new JLabel("Cambio de estado: ");
		etiquetaCambioEstado.setBounds(60, 225, 150, 50);
		add(etiquetaCambioEstado);

		radioBotonCambioEstadoSi = new JRadioButton("Sí");
		radioBotonCambioEstadoSi.setBounds(210, 240, 50, 25);
		radioBotonCambioEstadoSi.addItemListener(this);
		add(radioBotonCambioEstadoSi);

		cambioEstado = new JComboBox<String>();
		cambioEstado.setBounds(265, 240, 200, 25);
		add(cambioEstado);

		radioBotonCambioEstadoNo = new JRadioButton("No");
		radioBotonCambioEstadoNo.setBounds(480, 240, 50, 25);
		radioBotonCambioEstadoNo.addItemListener(this);
		add(radioBotonCambioEstadoNo);
		ButtonGroup grupo5 = new ButtonGroup();
		grupo5.add(radioBotonCambioEstadoSi);
		grupo5.add(radioBotonCambioEstadoNo);

		JLabel etiquetaSecuencia = new JLabel("Secuencia: ");
		etiquetaSecuencia.setBounds(60, 260, 150, 50);
		add(etiquetaSecuencia);

		radioBotonSecuenciaSi = new JRadioButton("Sí");
		radioBotonSecuenciaSi.setBounds(210, 275, 50, 25);
		radioBotonSecuenciaSi.addItemListener(this);
		add(radioBotonSecuenciaSi);

		secuencia = new JComboBox<Paso>();
		secuencia.setBounds(265, 275, 200, 25);
		add(secuencia);

		radioBotonSecuenciaNo = new JRadioButton("No");
		radioBotonSecuenciaNo.setBounds(480, 275, 50, 25);
		radioBotonSecuenciaNo.addItemListener(this);
		add(radioBotonSecuenciaNo);
		ButtonGroup grupo6 = new ButtonGroup();
		grupo6.add(radioBotonSecuenciaSi);
		grupo6.add(radioBotonSecuenciaNo);

		JLabel etiquetaObligatorio = new JLabel("Obligatorio: ");
		etiquetaObligatorio.setBounds(60, 300, 150, 50);
		add(etiquetaObligatorio);

		radioBotonObligatorioSi = new JRadioButton("Sí");
		radioBotonObligatorioSi.setBounds(210, 310, 50, 25);
		add(radioBotonObligatorioSi);
		radioBotonObligatorioNo = new JRadioButton("No");
		radioBotonObligatorioNo.setBounds(315, 310, 50, 25);
		add(radioBotonObligatorioNo);
		ButtonGroup grupo7 = new ButtonGroup();
		grupo7.add(radioBotonObligatorioSi);
		grupo7.add(radioBotonObligatorioNo);

		JLabel etiquetaRepeticion = new JLabel("Repetición: ");
		etiquetaRepeticion.setBounds(60, 335, 150, 50);
		add(etiquetaRepeticion);

		radioBotonRepeticionSi = new JRadioButton("Sí");
		radioBotonRepeticionSi.addItemListener(this);
		radioBotonRepeticionSi.setBounds(210, 350, 50, 25);
		add(radioBotonRepeticionSi);

		radioBotonVeces = new JRadioButton("");
		radioBotonVeces.setBounds(315, 350, 20, 25);
		radioBotonVeces.addItemListener(this);
		add(radioBotonVeces);

		repeticion = new JIntegerTextField(2);
		repeticion.setBounds(345, 350, 50, 25);
		add(repeticion);

		JLabel etiquetaVeces = new JLabel("Veces ");
		etiquetaVeces.setBounds(400, 335, 150, 50);
		add(etiquetaVeces);

		radioBotonIndefinido = new JRadioButton("Indefinida");
		radioBotonIndefinido.setBounds(480, 350, 100, 25);
		radioBotonIndefinido.addItemListener(this);
		add(radioBotonIndefinido);

		radioBotonRepeticionNo = new JRadioButton("No");
		radioBotonRepeticionNo.addItemListener(this);
		radioBotonRepeticionNo.setBounds(580, 350, 50, 25);
		add(radioBotonRepeticionNo);

		ButtonGroup grupo8 = new ButtonGroup();
		grupo8.add(radioBotonRepeticionSi);
		grupo8.add(radioBotonRepeticionNo);
		ButtonGroup grupo9 = new ButtonGroup();
		grupo9.add(radioBotonIndefinido);
		grupo9.add(radioBotonVeces);
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public static void main(String[] args) {
        DialogoModificarPaso dmp=new DialogoModificarPaso(null, null, true);
        JDialog jd=new JDialog();
        jd.add(dmp);
        jd.setVisible(true);
    }
}
