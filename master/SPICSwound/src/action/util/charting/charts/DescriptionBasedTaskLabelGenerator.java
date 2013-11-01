package util.charting.charts;

import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.xy.XYDataset;

public class DescriptionBasedTaskLabelGenerator implements XYItemLabelGenerator{

	public String generateLabel(XYDataset dataset, int series, int item) {
		XYTaskDataset tmp = (XYTaskDataset)dataset;
		Task t = tmp.getTasks().getSeries(series).get(item);
		return t.getDescription();
	}
	
	

}
