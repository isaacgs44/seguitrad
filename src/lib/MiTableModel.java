package lib;

import javax.swing.table.DefaultTableModel;

public class MiTableModel extends DefaultTableModel {
	private static final long serialVersionUID = -682571440882196144L;
	
    public MiTableModel(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public boolean isCellEditable(int row, int column) {
        return false;
    }
}
