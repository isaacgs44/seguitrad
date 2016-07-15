package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	private int panelNumero=30;
	
	public PanelPasoRealizado() {
		crearPanel(panelNumero);
	}
	private void crearPanel(int n) {
		this.setSize(-1, 30 * n);
		this.setLayout(new GridLayout(n, 1));
		etiquetaVacia1 = new JLabel[n];
		etiquetaVacia2 = new JLabel[n];
		checkDocumento = new JCheckBox[n];
		mostrarDocButon = new JButton[n];
		verPlantillaButon = new JButton[n];
		JPanel panelContenedor;
		for (int i = 0; i < n; i++) {		
			panelContenedor = new JPanel();
			panelContenedor.setLayout(new FlowLayout(FlowLayout.LEFT));
			etiquetaVacia1[i] = new JLabel("Etiqueta 1," + (i + 1));
			etiquetaVacia1[i].setPreferredSize(new Dimension(250, 30));
			panelContenedor.add(etiquetaVacia1[i]);
			
			etiquetaVacia2[i] = new JLabel("Etiqueta 2," + (i + 1));
			etiquetaVacia2[i].setPreferredSize(new Dimension(100, 30));
			panelContenedor.add(etiquetaVacia2[i]);
			
			verPlantillaButon[i] = new JButton(" ver plantilla," + (i + 1));
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
			panelContenedor.add(checkDocumento[i]);
			this.add(panelContenedor);	
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
