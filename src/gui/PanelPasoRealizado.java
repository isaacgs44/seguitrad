package gui;

import dominio.Paso;
import dominio.PasoEspecifico;
import dominio.TramiteEspecifico;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelPasoRealizado extends JPanel implements ActionListener {

    private static final long serialVersionUID = -8692794042074788858L;
    private JLabel[] etiquetaVacia1;
    private JLabel[] etiquetaVacia2;
    private JCheckBox[] checkDocumento;
    private JButton[] mostrarDocButon;
    private JButton[] verPlantillaButon;
    private ArrayList<PasoEspecifico> pasoEspecificos;
    private ArrayList<TramiteEspecifico> tramiteEspecificos;
    private ArrayList<PasoEspecifico> pasosRealizados;
    private int tramiteId;

    public PanelPasoRealizado(VentanaPrincipal ventanaPrincipal, TramiteEspecifico tramiteEspecifico) {
        tramiteEspecificos = ventanaPrincipal.getLista().getListaTramitesEsp();
        pasosRealizados = new ArrayList<>();
        int tramiteActual = tramiteEspecifico.getIdTramite();
        tramiteId=tramiteActual;
        for (TramiteEspecifico te : tramiteEspecificos) {
            int numPasosEsp = ventanaPrincipal.getLista().getListaTramitesEsp().get(te.getIdTramite()).getPasosEspecificos().size();
            if (te.getIdTramite() == tramiteActual) {
                while (numPasosEsp >= 1) {
                    pasoEspecificos = ventanaPrincipal.getLista().getListaTramitesEsp().get(te.getIdTramite()).getPasosEspecificos();
                    numPasosEsp--;
                }
            }
        }
        int i = 0;
        for (PasoEspecifico pe : pasoEspecificos) {
            if (pe.isRealizado()) {
                //pasosRealizados.add(pe);
                pasosRealizados.add(tramiteActual, pe);
                i++;
            }
        }
        crearPanel(i, tramiteActual);
    }

    private void crearPanel(int n, int tramiteActual) {
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
        for (int i = 0; i < n; i++) {
            panelContenedor = new JPanel();
            panelContenedor.setLayout(new FlowLayout(FlowLayout.LEFT));
            etiquetaVacia1[i] = new JLabel(pasosRealizados.get(tramiteActual).getNombrePaso() + (i + 1));
            etiquetaVacia1[i].setPreferredSize(new Dimension(250, 30));
            panelContenedor.add(etiquetaVacia1[i]);
            Date d = pasosRealizados.get(tramiteActual).getFechaRealizacion();
            DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
            etiquetaVacia2[i] = new JLabel(formato.format(d));
            etiquetaVacia2[i].setPreferredSize(new Dimension(100, 30));
            panelContenedor.add(etiquetaVacia2[i]);

            verPlantillaButon[i] = new JButton(" ver plantilla");
            verPlantillaButon[i].setActionCommand("ver plantilla");
            verPlantillaButon[i].setName(String.valueOf(i));
            verPlantillaButon[i].addActionListener(this);
            verPlantillaButon[i].setPreferredSize(new Dimension(130, 30));
            panelContenedor.add(verPlantillaButon[i]);

            mostrarDocButon[i] = new JButton("mostrar doc," + (i + 1));
            mostrarDocButon[i].setActionCommand("mostrar doc");
            mostrarDocButon[i].setName(String.valueOf(i));
            mostrarDocButon[i].addActionListener(this);
            mostrarDocButon[i].setPreferredSize(new Dimension(130, 30));
            panelContenedor.add(mostrarDocButon[i]);

            checkDocumento[i] = new JCheckBox("" + (i + 1));
            checkDocumento[i].setPreferredSize(new Dimension(20, 30));
            grupo.add(checkDocumento[i]);
            panelContenedor.add(checkDocumento[i]);
            this.add(panelContenedor);
        }
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

}
}