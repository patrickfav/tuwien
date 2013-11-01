package ticketline.gui.components;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * Creates a Table Model from Vectors and does not allow
 * editing of cells
 * 
 * @author PatrickM, PatrickF
 * @version 0.2
 *
 */
public class NoEditTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public NoEditTableModel(Vector<Vector<String>> data, Vector<String> colnames) {
		super(data,colnames);
	}
	
	public NoEditTableModel(Object[][] data, String[] colnames) {
		super(data,colnames);
	}
	
	public NoEditTableModel() {
	}


	@Override
	public boolean isCellEditable(int row, int col) { 
		return false; 
	}
}
