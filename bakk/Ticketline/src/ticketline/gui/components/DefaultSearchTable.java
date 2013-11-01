package ticketline.gui.components;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

/**
 * A table template for all search tables
 * @author PatrickF
 * @version 0.1
 */
public class DefaultSearchTable extends JTable{
	private static final long serialVersionUID = 308829262868597490L;
	
	/**
	 * CONSTRUCTOR with some table settings
	 */
	public DefaultSearchTable() {
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setAutoCreateRowSorter(true);
		this.getTableHeader().setReorderingAllowed(false);
	}
	
	/**
	 * CONSTRUCTOR with some adjustable table settings
	 */
	public DefaultSearchTable(boolean rowsorter, boolean reordering) {
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setAutoCreateRowSorter(rowsorter);
		this.getTableHeader().setReorderingAllowed(reordering);
	}
	
	/**
	 * The principle is to set the preferred size at each column 
	 * according to the relative proportion specified at the percentages array passed in.
	 * see: http://www.oracle.com/technology/products/jdev/howtos/1013/jtablewidth/columwidth.html
	 * 
	 * @param percentageColumnWidth
	 */
	public void setPreferredColumnWidths(Double[] percentageColumnWidth) { 
	      Dimension tableDim = this.getPreferredSize(); 
	   
	      double total = 0; 
	      for(int i = 0; i < getColumnModel().getColumnCount(); i++) 
	        total += percentageColumnWidth[i]; 
	   
	   
	      for(int i = 0; i < getColumnModel().getColumnCount(); i++) { 
	        TableColumn column = getColumnModel().getColumn(i); 
	        column.setPreferredWidth((int) (tableDim.width * (percentageColumnWidth[i] / total))); 
	      } 
	}
	
	/**
	 * Simple auto row sorter setter
	 */
	
	public void setAutoRowOff() {
		this.setAutoCreateRowSorter(false);
	}

}
