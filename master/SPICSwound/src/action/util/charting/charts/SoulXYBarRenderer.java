package util.charting.charts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.Range;

/**
 * this renderer skips labels, which don't fit
 * @author inso
 *
 */
public class SoulXYBarRenderer extends XYBarRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public void drawRangeMarker(Graphics2D g2, XYPlot plot,
			ValueAxis rangeAxis, Marker marker, Rectangle2D dataArea) {

		if (marker instanceof IntervalMarker) {
			this.drawRangeIntervalMarker(g2, plot, rangeAxis, marker, dataArea);
		} else {
			super.drawRangeMarker(g2, plot, rangeAxis, marker, dataArea);
		}

	}

	private void drawRangeIntervalMarker(Graphics2D g2, XYPlot plot,
			ValueAxis rangeAxis, Marker marker, Rectangle2D dataArea) {

		IntervalMarker im = (IntervalMarker) marker;
		double start = im.getStartValue();
		double end = im.getEndValue();
		Range range = rangeAxis.getRange();
		if (!(range.intersects(start, end))) {
			return;
		}

		double start2d = rangeAxis.valueToJava2D(start, dataArea, plot
				.getRangeAxisEdge());
		double end2d = rangeAxis.valueToJava2D(end, dataArea, plot
				.getRangeAxisEdge());
		double low = Math.min(start2d, end2d);
		double high = Math.max(start2d, end2d);

		PlotOrientation orientation = plot.getOrientation();
		Rectangle2D rect = null;
		if (orientation == PlotOrientation.HORIZONTAL) {
			// clip left and right bounds to data area
			low = Math.max(low, dataArea.getMinX());
			high = Math.min(high, dataArea.getMaxX());
			rect = new Rectangle2D.Double(low, dataArea.getMinY(), high - low,
					dataArea.getHeight());
		} else if (orientation == PlotOrientation.VERTICAL) {
			// clip top and bottom bounds to data area
			low = Math.max(low, dataArea.getMinY());
			high = Math.min(high, dataArea.getMaxY());
			rect = new Rectangle2D.Double(dataArea.getMinX(), low, dataArea
					.getWidth(), high - low);
		}

		if (marker.getLabel() != null) {

			Font labelFont = marker.getLabelFont();

			// check if the label fits
			FontRenderContext frc = g2.getFontRenderContext();
			TextLayout layout = new TextLayout(marker.getLabel(), labelFont,
					frc);
			Rectangle2D bounds = layout.getBounds();

			if (bounds.getWidth() > rect.getWidth()) {
				// skipping string - it's to long
				marker.setLabel(" ");
			}
		}

		super.drawRangeMarker(g2, plot, rangeAxis, marker, dataArea);

	}
}
