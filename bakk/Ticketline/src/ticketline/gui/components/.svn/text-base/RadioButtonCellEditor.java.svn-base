package ticketline.gui.components;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

/**
 * For rendering and editing a radiobutton in a cell
 * @author PatrickF
 * @version 0.1
 */
public class RadioButtonCellEditor extends DefaultCellEditor implements ItemListener {
	private static final long serialVersionUID = 1L;
	
	private JRadioButton button;

	  public RadioButtonCellEditor(JCheckBox checkBox) {
	    super(checkBox);
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
	    if (value == null)
	      return null;
	    button = (JRadioButton) value;
	    button.addItemListener(this);
	    return (Component) value;
	  }

	  public Object getCellEditorValue() {
	    button.removeItemListener(this);
	    return button;
	  }

	  public void itemStateChanged(ItemEvent e) {
	    super.fireEditingStopped();
	  }
	}
