package ticketline.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * For rendering Date in a cell (JTable)
 * @author PatrickF
 * @version 1.0
 */

public class DateCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 7196048269937892686L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
	    	return null;
	   
		
		if(isSelected) {
			setOpaque(true);
	    	setBackground(new Color(184,207,229));
	    } else {
	    	setOpaque(true);
	    	setBackground(Color.WHITE);
	    }
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime((Date) value);
		
		setText(numberWithLeadingZero(c.get(Calendar.DAY_OF_MONTH),2)+"."+numberWithLeadingZero((c.get(Calendar.MONTH)+1),2)+"."+c.get(Calendar.YEAR));
		setToolTipText(((Date)value).toString());
	    return this;
	}
	
	// The following methods override the defaults for performance reasons
    public void validate() {}
    public void revalidate() {}
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
    
    /**
     * Fill the number with leading zeros until its "length" digits long
     * @param num - number to format
     * @param length - resulting total length
     * @return string with leading zeros
     */
    private String numberWithLeadingZero(Integer num,Integer length) {
	    String format = String.format("%%0%dd", length); 
	    return String.format(format, num);
    }
}
