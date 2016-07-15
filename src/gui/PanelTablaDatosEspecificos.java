package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import lib.MiTableModel;
import dominio.Campo;

public class PanelTablaDatosEspecificos extends JPanel {
	private static final long serialVersionUID = 7013745406257698462L;
	
	private JTable table;
	private MiTableModel modelo;
	private final String[] columnNames = {"Nombre", "Obligatorio", "Tipo", "Valores"};
	
	public PanelTablaDatosEspecificos() {
		setLayout(new BorderLayout());
		
		Object[][] data = {};
		modelo = new MiTableModel(data, columnNames);
		table = new JTable(modelo);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
    }
	
	public void deshabilitarTabla() {
		table.setEnabled(false);
	}
	
	public void modificarTabla(ArrayList<Campo> datos) {
		String[][] data = new String[datos.size()][columnNames.length];
		for (int i = 0; i < datos.size(); i++) {
			Campo c = datos.get(i);
			data[i][0] = c.getNombreCampo();
			data[i][1] = c.isObligatorio() ? "Sí" : "No";
			data[i][2] = c.getTipo().toString();
			data[i][3] = c.getValorDefecto();
		}
		modelo = new MiTableModel(data, columnNames);
		table.setModel(modelo);
	}
	
	public void agregarFila(Campo datos) {
		String[] data = new String[columnNames.length];
		data[0] = datos.getNombreCampo();
		data[1] = datos.isObligatorio() ? "Sí" : "No";
		data[2] = datos.getTipo().toString();
		data[3] = datos.getValorDefecto();
		modelo.addRow(data);
	}
	
	public void moverFila(int posicionInicial, int posicionFinal) {
		modelo.moveRow(posicionInicial, posicionInicial, posicionFinal);
		table.setRowSelectionInterval(posicionFinal, posicionFinal);
	}
	
	/**
	 * Obtiene el n&uacute;mero de filas seleccionadas.
	 * <p>
	 * Si hay varias filas seleccionadas o si no hay ninguna, 
	 * regresar&aacute; -1; si hay una seleccionada, regresar&aacute; la posici&oacute;n.
	 * @return	la fila seleccionada o -1 si hay selecci&oacute;n m&uacute;ltiple
	 * o no hay selecci&oacute;n.
	 */
	public int obtenerFilaSeleccionada() {
		int numFilas = table.getSelectedRowCount();		
		return (numFilas <= 1) ? table.getSelectedRow() : -1;
	}
	
	public void quitarFila(int posicion) {
		modelo.removeRow(posicion);
		table.clearSelection();
	}
}
