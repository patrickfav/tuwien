package util.charting.charts;

import java.awt.Color;
import java.util.HashMap;
import java.util.Set;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;

public class SoulEpisodeLegendSource implements LegendItemSource {

	private HashMap<String, Color> colourMap = new HashMap<String, Color>();

	private HashMap<String, String> abbrMap = new HashMap<String, String>();

	private Set<String> episodes = null;

	public SoulEpisodeLegendSource(HashMap<String, Color> colourMap,
			HashMap<String, String> abbrMap) {
		super();
		this.colourMap = colourMap;
		this.abbrMap = abbrMap;
	}

	public SoulEpisodeLegendSource(HashMap<String, Color> colourMap,
			HashMap<String, String> abbrMap, Set<String> episodes) {
		super();
		this.colourMap = colourMap;
		this.abbrMap = abbrMap;
		this.episodes = episodes;
	}

	public LegendItemCollection getLegendItems() {

		LegendItemCollection collection = new LegendItemCollection();

		// show all episode types available in color map
		if (episodes == null) {
			for (String titel : colourMap.keySet()) {
				LegendItem item = new LegendItem(abbrMap.get(titel));
				item.setFillPaint(colourMap.get(titel));
				collection.add(item);
			}
		} 
		// show only charted episodes
		else {
			for (String titel : episodes) {
				LegendItem item = new LegendItem(abbrMap.get(titel));
				item.setFillPaint(colourMap.get(titel));
				collection.add(item);
			}
		}

		return collection;
	}

}
