package util.charting.charts;

import java.io.Serializable;

import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.data.Range;

/**
 * This class is necessary, 'cauz jfreechart is a crappy piece of software and
 * ignores setUpperMargin/setLowerMargin
 * 
 * @author inso
 * 
 */
public class SoulAxis extends SymbolAxis implements Serializable{
	
	/** For serialization. */
	private static final long serialVersionUID = 666L;

	// some space for the episode title
	private double realUpperMargin = 1d; // increased margin
	private double realLowerMargin = 0.5;


	public SoulAxis(String label, String[] sv) {
		super(label, sv);
	}

	protected void autoAdjustRange() {

		Plot plot = getPlot();
		if (plot == null) {
			return; // no plot, no data
		}

		if (plot instanceof ValueAxisPlot) {

			// ensure that all the symbols are displayed
			double upper = this.getSymbols().length - 1;
			double lower = 0;
			double range = upper - lower;

			// ensure the autorange is at least <minRange> in size...
			double minRange = getAutoRangeMinimumSize();
			if (range < minRange) {
				upper = (upper + lower + minRange) / 2;
				lower = (upper + lower - minRange) / 2;
			}

			if (getAutoRangeIncludesZero()) {
				if (getAutoRangeStickyZero()) {
					if (upper <= 0.0) {
						upper = 0.0;
					} else {
						upper = upper + realUpperMargin;
					}
					if (lower >= 0.0) {
						lower = 0.0;
					} else {
						lower = lower - realLowerMargin;
					}
				} else {
					upper = Math.max(0.0, upper + realUpperMargin);
					lower = Math.min(0.0, lower - realLowerMargin);
				}
			} else {
				if (getAutoRangeStickyZero()) {
					if (upper <= 0.0) {
						upper = Math.min(0.0, upper + realUpperMargin);
					} else {
						upper = upper + realUpperMargin * range;
					}
					if (lower >= 0.0) {
						lower = Math.max(0.0, lower - realLowerMargin);
					} else {
						lower = lower - realLowerMargin;
					}
				} else {
					upper = upper + realUpperMargin;
					lower = lower - realLowerMargin;
				}
			}

			setRange(new Range(lower, upper), false, false);

		}

	}

}
