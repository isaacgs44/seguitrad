package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import lib.MiTableModel;
import dominio.Paso;

public class PanelTablaPasos extends JPanel {
	private static final long serialVersionUID = 885740401371217567L;
	
	private JTable table;
	private MiTableModel modelo;
	private final String[] columnNames = {"Nombre", "Responsable", "Fecha límite", "Plantila", 
			"Documento específico", "Cambio de estado", "Secuencia", "Obligatorio", "Repetición"};
	
	public PanelTablaPasos() {
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
	
	public void modificarTabla(ArrayList<Paso> datos) {
		String[][] data = new String[datos.size()][columnNames.length];
		for (int i = 0; i < datos.size(); i++) {
			Paso p = datos.get(i);
			data[i][0] = p.getNombrePaso();
			data[i][1] = p.getResponsable();
			data[i][2] = p.isConFechaLimite() ? "Sí" : "No";
			data[i][3] = p.isConPlantilla() ? 
					(p.getPlantilla().isEmpty() ? "(Sin selección)" : p.getPlantilla()) : "No";
			data[i][4] = p.isConDocumento() ? "Sí" : "No";
			data[i][5] = p.isConCambioEstado() ? p.getEstado() : "No";
			data[i][6] = p.getSecuencia() != null ? p.getSecuencia().getNombrePaso() : "No";
			data[i][7] = p.isObligatorio() ? "Sí" : "No";
			data[i][8] = p.getRepeticion() == 1 ? "No" 
					: (p.getRepeticion() == 0 ? "Indefinida" : p.getRepeticion() + " veces");
		}
		modelo = new MiTableModel(data, columnNames);
		table.setModel(modelo);
	}
	
	public void agregarFila(Paso datos) {
		String[] data = new String[columnNames.length];
		data[0] = datos.getNombrePaso();
		data[1] = datos.getResponsable();
		data[2] = datos.isConFechaLimite() ? "Sí" : "No";
		data[3] = datos.isConPlantilla() ? 
				(datos.getPlantilla().isEmpty() ? "(Sin selección)" : datos.getPlantilla()) : "No";
		data[4] = datos.isConDocumento() ? "Sí" : "No";
		data[5] = datos.isConCambioEstado() ? datos.getEstado() : "No";
		data[6] = datos.getSecuencia() != null ? datos.getSecuencia().getNombrePaso() : "No";
		data[7] = datos.isObligatorio() ? "Sí" : "No";
		data[8] = datos.getRepeticion() == 1 ? "No" 
				: (datos.getRepeticion() == 0 ? "Indefinida" : datos.getRepeticion() + " veces");
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
