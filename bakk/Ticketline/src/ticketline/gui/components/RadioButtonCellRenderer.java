package ticketline.gui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * For rendering Radiobuttons in a cell (JTable)
 * @author PatrickF
 * @version 1.0
 */

public class RadioButtonCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

	private static final long serialVersionUID = 5421593780487912774L;

	public RadioButtonCellRenderer() {
		//setBackground(Color.WHITE);
	}
	  
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
			
		if (value == null)
	    	return null;
	    
		if(isSelected) {
	    	((Component) value).setBackground(new Color(184,207,229));
	    } else {
	    	((Component) value).setBackground(Color.WHITE);
	    }
	    return (Component) value;
	}
	
	// The following methods override the defaults for performance reasons
    public void validate() {}
    public void revalidate() {}
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}
