package util.charting.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.block.Arrangement;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.FlowArrangement;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.Day;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import util.charting.ChartProcessor;
import util.charting.ChartRepository;
import util.charting.ChartingException;
import util.charting.NatAttributeIdentifier;
import util.charting.SpicsChart;
import entities.Patient;
import entities.TrialData;
import entities.TrialForm;
import entities.value.DateValue;
import entities.value.DecimalValue;
import entities.value.IntegerValue;
import entities.value.StringValue;
import entities.value.Value;

public class SoulChart implements SpicsChart {

	private static final long serialVersionUID = 1L;

	private static HashMap<String, Color> colourMap = new HashMap<String, Color>();

	private static HashMap<String, String> abbrMap = new HashMap<String, String>();

	private static Range chartRange;

	private static Font standardFont = new Font("Arial", Font.PLAIN, 13);

	private static Font smallFont = new Font("Arial", Font.PLAIN, 10);

	private static Color medikamentColor = new Color(247, 247, 247);

	static {
		colourMap.put("default", new Color(19, 131, 253, 63)); // to prevent
		// nullpointers

		// art der stoerung
		int alpha = 100;
		colourMap.put("Episode - F31.0 HMA", new Color(255, 153, 0, alpha));
		colourMap.put("Episode - F31.1 MAN ohne psychot. Sympt.", new Color(
				255, 102, 0, alpha));
		colourMap.put("Episode - F31.2 MAN mit psychot. Sympt.", new Color(221,
				8, 6, alpha));
		colourMap.put("Episode - F31.3 DEP mittelgradig", new Color(0, 204,
				255, alpha));
		colourMap.put("Episode - F31.4 DEP ohne psychot. Sympt.", new Color(51,
				102, 255, alpha));
		colourMap.put("Episode - F31.5 DEP mit psychot. Sympt.", new Color(0,
				0, 212, alpha));
		colourMap.put("Episode - F31.6 gemischte Episode", new Color(204, 153,
				255, alpha));
		colourMap.put("Episode - F31.8 RapidCycling", new Color(252, 243, 5,
				alpha));
		colourMap.put("Intervall - Symptomfrei", new Color(153, 204, 0, alpha));
		colourMap.put("Intervall - SDE", new Color(204, 255, 255, alpha));
		colourMap.put("Intervall - HMA", new Color(255, 204, 153, alpha));
		colourMap.put("Intervall - Kognitive Beeinträcht.", new Color(153, 204,
				0, alpha));
		colourMap.put("Sonstiges", new Color(255, 255, 255, alpha));

		abbrMap.put("Episode - F31.0 HMA", "F31.0 HMA");
		abbrMap.put("Episode - F31.1 MAN ohne psychot. Sympt.", "F31.1 MAN");
		abbrMap.put("Episode - F31.2 MAN mit psychot. Sympt.", "F31.2 MAN");
		abbrMap.put("Episode - F31.3 DEP mittelgradig", "F31.3 DEP");
		abbrMap.put("Episode - F31.4 DEP ohne psychot. Sympt.", "F31.4 DEP");
		abbrMap.put("Episode - F31.5 DEP mit psychot. Sympt.", "F31.5 DEP");
		abbrMap.put("Episode - F31.6 gemischte Episode", "F31.6 DEP");
		abbrMap.put("Episode - F31.8 RapidCycling", "F31.8 Rap.Cyc.");
		abbrMap.put("Intervall - Symptomfrei", "Symptomfrei");
		abbrMap.put("Intervall - SDE", "SDE");
		abbrMap.put("Intervall - HMA", "HMA");
		abbrMap.put("Intervall - Kognitive Beeinträcht.", "Kogn. Beeinträcht.");
		abbrMap.put("Sonstiges", "Sonstiges");

		// calculate chart range
		Day today = new Day();
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.setTimeInMillis(today.getFirstMillisecond());
		startDate.add(GregorianCalendar.MONTH, -6);

		chartRange = new Range(startDate.getTimeInMillis(), today
				.getFirstMillisecond());
	}

	private String documentationName = null;

	private Long stammFormId = null;
	private Long stammFormGeschlechtId = null;
	private Long stammFormGebjahrId = null;

	private Long untFormId = null;
	private Long untFormBeginnId = null;
	private Long untFormArtId = null;

	private Long medFormId = null;
	private Long medFormBeginnId = null;
	private Long medFormEndId = null;
	private Long medFormMedId = null;
	private Long medFormDosisId = null;

	private Patient patient = null;

	private Dimension preferredDim = null;

	private boolean init = false;

	private Map<String, TaskSeries> medMap = new HashMap<String, TaskSeries>();

	public JFreeChart getChart(ChartProcessor chartProcessor) {

		if (!init) {
			try {
				this.init(chartProcessor.getChartRepository());
			} catch (ChartingException e) {
				e.printStackTrace();
				return null;
			}
		}

		if (init == false || patient == null)
			return null;

		this.reset();

		List<TrialData> stammData = chartProcessor.searchTrialData(stammFormId,
				patient);
		List<TrialData> medData = chartProcessor.searchTrialData(medFormId,
				patient);
		List<TrialData> episodeData = chartProcessor.searchTrialData(untFormId,
				patient);

		for (TrialData td : medData) {

			Value beginValue = td.getValueByAttributeId(this.medFormBeginnId);
			Value endValue = td.getValueByAttributeId(this.medFormEndId);

			if (beginValue == null)
				continue;

			Date beginnDate = ((DateValue) beginValue).getValue();
			Date endDate = null;

			// wenns kein enddatum gibt, dann nehmen wir an, die medikation
			// läuft noch
			if (endValue == null) {
				endDate = new Date();
			} else {
				endDate = ((DateValue) endValue).getValue();
			}

			// die medikation hat geendet vor den monaten, die gechartet werden
			if (endDate.getTime() < chartRange.getLowerBound())
				continue;

			Value wirkstoffValue = td.getValueByAttributeId(this.medFormMedId);
			Value dosisValue = td.getValueByAttributeId(this.medFormDosisId);

			// wirkstoff und dosis müssen vorhanden sein
			if (wirkstoffValue == null || dosisValue == null)
				continue;

			String wirkstoff = ((StringValue) wirkstoffValue).getValue();

			Integer dosis = new Integer(((DecimalValue) dosisValue).getValue()
					.intValue());

			TaskSeries ts = getTaskSeries(wirkstoff);
			ts.add(new Task(dosis.toString(), beginnDate, endDate));
		}

		TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();

		// add elements with more than one element to chart
		for (TaskSeries ts : medMap.values()) {
			if (ts.getItemCount() > 0) {
				taskseriescollection.add(ts);
			}
		}

		XYTaskDataset medDataSet = new XYTaskDataset(taskseriescollection);

		JFreeChart jfreechart = ChartFactory.createXYBarChart(null, "Resource",
				false, "Timing", medDataSet, PlotOrientation.HORIZONTAL, false,
				false, false);
		
		StringBuffer header = new StringBuffer();
		header.append("Patient: ");
		header.append(patient.getKennnummer());

		// stammdaten is fill once
		if (stammData.size() > 0) {
			TrialData stammDaten = stammData.get(0);
			
			Value geschlechtValue = stammDaten
					.getValueByAttributeId(this.stammFormGeschlechtId);
			if (geschlechtValue != null) {
				String geschlecht = ((StringValue) geschlechtValue).getValue();
				header.append(", ");
				header.append(geschlecht.toLowerCase());
			}

			Value gebJahrValue = stammDaten
					.getValueByAttributeId(this.stammFormGebjahrId);
			if (gebJahrValue != null) {
				Integer gebJahr = ((IntegerValue) gebJahrValue).getValue();
				header.append(", geb. ");
				header.append(gebJahr);
			}
		}
		
		TextTitle subtitle = new TextTitle(header.toString(), standardFont
				.deriveFont(Font.BOLD));
		subtitle.setMargin(0, 0, 15, 0);
		jfreechart.addSubtitle(subtitle);

		// jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();

		XYBarRenderer xybarrenderer = new SoulXYBarRenderer();
		xyplot.setRenderer(xybarrenderer);

		xyplot.setRangeGridlinesVisible(false);
		xyplot.setDomainGridlinePaint(Color.black);

		// das diagramm soll bei langen medikamentennamen wachsen,
		// daher orientiert sich die diagrammbreite am längsten medikamentennamen
		String maxMed = "";
		String[] tmp = new String[medMap.keySet().size()];
		int i = 0;
		for (String s : medMap.keySet()) {
			tmp[i] = s;
			i++;
			
			if (s.length() > maxMed.length()){
				maxMed = s;
			}
				
		}
		


		SoulAxis symbolaxis = new SoulAxis(null, tmp); // Wirkstoff Achse
		xyplot.setDomainAxis(symbolaxis);

		symbolaxis.setGridBandsVisible(false);

		xyplot.setDomainAxis(symbolaxis);

		xybarrenderer.setBarPainter(new StandardXYBarPainter());
		xybarrenderer.setBasePaint(medikamentColor);
		xybarrenderer.setAutoPopulateSeriesPaint(false);
		xybarrenderer.setShadowVisible(false);
		xybarrenderer.setMargin(0.3);
		xybarrenderer.setBaseOutlinePaint(Color.black);
		xybarrenderer.setDrawBarOutline(true);

		xybarrenderer.setUseYInterval(true);
		DateAxis dauerAchse = new DateAxis(null);
		dauerAchse.setAutoTickUnitSelection(false);
		dauerAchse.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1,
				new SimpleDateFormat("MMM yy")));
		xyplot.setRangeAxis(dauerAchse); // Dauer

		XYItemLabelGenerator xyItemLabelGenerator = new DescriptionBasedTaskLabelGenerator(); // customize
		xybarrenderer.setBaseItemLabelGenerator(xyItemLabelGenerator);
		xybarrenderer.setBaseItemLabelsVisible(true);
		xybarrenderer.setBaseItemLabelFont(smallFont);
		ItemLabelPosition itemlabelposition = new ItemLabelPosition(
				ItemLabelAnchor.CENTER, TextAnchor.CENTER_RIGHT);
		xybarrenderer.setBasePositiveItemLabelPosition(itemlabelposition);
		xybarrenderer
				.setPositiveItemLabelPositionFallback(new ItemLabelPosition());

		xyplot.getRangeAxis().setTickLabelFont(standardFont);
		xyplot.getDomainAxis().setTickLabelFont(standardFont);

		long oldEnd = (new Day()).getFirstMillisecond(); // today

		xyplot.getRangeAxis().setAutoRange(false);
		xyplot.getRangeAxis().setRange(chartRange);

		// use treemap to sort trial datas.
		TreeMap<Date, String> episodes = new TreeMap<Date, String>(
				new SoulComparator());
		for (TrialData td : episodeData) {

			Value beginValue = td.getValueByAttributeId(this.untFormBeginnId);

			// don't chart invalid entries (begin is mandatory!)
			if (beginValue == null)
				continue;

			Date beginDate = ((DateValue) beginValue).getValue();

			String art = ((StringValue) td
					.getValueByAttributeId(this.untFormArtId)).getValue();
			episodes.put(beginDate, art);
		}

		Set<Date> episodeKeys = episodes.keySet();

		// filter old values
		boolean filtered = episodeKeys.size() <= 1; // wenns nur eine episode
		// gibt, dann laeuft sie
		// immer noch und es muss
		// nicht gefiltert werden
		if (!filtered) {
			LinkedList<Date> keyList = new LinkedList<Date>(episodeKeys);
			while (!filtered && keyList.size() > 1) {
				Date d1 = keyList.get(0);
				Date d2 = keyList.get(1);
				if (d1.getTime() < chartRange.getLowerBound()
						&& d2.getTime() < chartRange.getLowerBound()) {
					keyList.remove(0);
					episodes.remove(d1);
				} else {
					filtered = true;
				}
			}
		}

		// we use a set to save the names of the charted episodes
		Set<String> chartedEpisodes = new HashSet<String>();
		for (Date beginnDate : episodeKeys) {

			String art = episodes.get(beginnDate);
			IntervalMarker intervalmarker = new IntervalMarker(beginnDate
					.getTime(), oldEnd, getColor(art), new BasicStroke(2.0F),
					null, null, 1.0F);
			intervalmarker.setLabel(getAbbreviation(art));
			intervalmarker.setLabelAnchor(RectangleAnchor.TOP);
			intervalmarker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
			intervalmarker.setLabelFont(standardFont);

			xyplot.addRangeMarker(intervalmarker, Layer.BACKGROUND);
			chartedEpisodes.add(art);

			oldEnd = beginnDate.getTime();
		}

		// create legend for the charted episodes
		LegendItemSource li = new SoulEpisodeLegendSource(colourMap, abbrMap,
				chartedEpisodes);
		Arrangement ar = new FlowArrangement();
		LegendTitle legend = new LegendTitle(li, ar, null);
		legend.setFrame(new BlockBorder());
		legend.setPosition(RectangleEdge.BOTTOM);
		legend.setMargin(new RectangleInsets(20D, 50D, 0, 50D));
		jfreechart.addLegend(legend);
		
		// set preferred dimension according to the number of meds.
		// 'cause the bar width depends on the chart height.
		this.preferredDim = new Dimension(600,
				80 + medMap.keySet().size() * 30);
		
		// add space for legend
		if (chartedEpisodes.size() > 0) {
			this.preferredDim.height += 45;
		}
		
		// die breite hängt vom längsten medikamenten namen ab
		BufferedImage bi = jfreechart.createBufferedImage(preferredDim.width,
				preferredDim.height);
		Graphics2D g2 = bi.createGraphics();
		
		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout layout = new TextLayout(maxMed, standardFont,
				frc);
		Rectangle2D bounds = layout.getBounds();
		preferredDim.width = (int) (500 + bounds.getWidth());
		g2.dispose();
		
		return jfreechart;
	}

	public String getDescription() {
		return "SoulChart";
	}

	public String getName() {
		return "SoulChart";
	}

	public void init(ChartRepository chartRepository) throws ChartingException {

		// get StammdatenForm

		NatAttributeIdentifier natStammGeb = new NatAttributeIdentifier(
				"Daten", "Geburtsjahr");
		NatAttributeIdentifier natStammGeschlecht = new NatAttributeIdentifier(
				"Daten", "Geschlecht");

		TrialForm stammForm = chartRepository.findTrialForm(documentationName,
				"Stammdaten", natStammGeb, natStammGeschlecht);

		this.stammFormId = stammForm.getId();
		this.stammFormGebjahrId = natStammGeb.id;
		this.stammFormGeschlechtId = natStammGeschlecht.id;

		// get MedikationForm
		NatAttributeIdentifier natMedBeginn = new NatAttributeIdentifier(
				"Daten", "Beginndatum");
		NatAttributeIdentifier natMedEnde = new NatAttributeIdentifier("Daten",
				"Enddatum");
		NatAttributeIdentifier natMedMed = new NatAttributeIdentifier("Daten",
				"Wirkstoff");
		NatAttributeIdentifier natMedDosis = new NatAttributeIdentifier(
				"Daten", "Dosierung");

		TrialForm medForm = chartRepository.findTrialForm(documentationName,
				"Medikation", natMedBeginn, natMedEnde, natMedMed, natMedDosis);

		this.medFormId = medForm.getId();
		this.medFormBeginnId = natMedBeginn.id;
		this.medFormEndId = natMedEnde.id;
		this.medFormMedId = natMedMed.id;
		this.medFormDosisId = natMedDosis.id;

		// get UntersuchungForm
		NatAttributeIdentifier natUntBeginn = new NatAttributeIdentifier(
				"Daten", "Beginndatum");
		NatAttributeIdentifier natUntArt = new NatAttributeIdentifier("Daten",
				"Art");

		TrialForm untForm = chartRepository.findTrialForm(documentationName,
				"Untersuchung", natUntBeginn, natUntArt);

		this.untFormId = untForm.getId();
		this.untFormBeginnId = natUntBeginn.id;
		this.untFormArtId = natUntArt.id;

		init = true;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	private void reset() {
		medMap.clear();
		this.preferredDim = null;
	}

	public static Color getColor(String key) {
		Color c = colourMap.get(key);

		return (c != null) ? c : colourMap.get("Sonstiges");
	}

	public static String getAbbreviation(String key) {
		String s = abbrMap.get(key);

		return (s != null) ? s : key;
	}

	public TaskSeries getTaskSeries(String med) {

		TaskSeries ts = medMap.get(med);

		if (ts == null) {
			ts = new TaskSeries(med);
			medMap.put(med, ts);
		}

		return ts;
	}

	public Dimension getPreferredDimension() {
		return this.preferredDim;
	}

	public void setDocumentationName(String name) {
		this.documentationName = name;

	}
}
