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
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
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
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import util.charting.ChartProcessor;
import util.charting.ChartRepository;
import util.charting.ChartingException;
import util.xml.IXMLImportExport;
import util.xml.XMLImportExport;
import util.xml.XmlImportExportException;
import entities.Attribute;
import entities.Patient;
import entities.Trial;
import entities.TrialData;
import entities.TrialForm;
import entities.value.DateValue;
import entities.value.DecimalValue;
import entities.value.IntegerValue;
import entities.value.StringValue;

public class TestChartX extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	private static HashMap<String, Color> colourMap = new HashMap<String, Color>();

	private static HashMap<String, String> abbrMap = new HashMap<String, String>();

	private static String[] meds;

	private static Range chartRange;

	private static Font standardFont = new Font("Arial", Font.PLAIN, 13);

	private static Font smallFont = new Font("Arial", Font.PLAIN, 10);

	static {
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

		meds = new String[] { "aspirin", "parkemed", "halset" };

		// calculate chart range
		Day today = new Day();
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.setTimeInMillis(today.getFirstMillisecond());
		startDate.add(GregorianCalendar.MONTH, -6);

		chartRange = new Range(startDate.getTimeInMillis(), today
				.getFirstMillisecond());
	}

	private TrialForm stammForm = null; // TODO change to id and reload

	private TrialForm medForm = null; // TODO change to id and reload
	// on generation

	private TrialForm untForm = null; // TODO change to id and reload
	// on generation

	private Long stammFormGeburtsjahr = null;
	private Long stammFormGeschlecht = null;

	private Long untFormBeginnId = null;
	private Long untFormArtId = null;

	private Long medFormBeginnId = null;
	private Long medFormEndId = null;
	private Long medFormMedId = null;
	private Long medFormDosisId = null;

	private Patient patient = null;

	private Map<String, TaskSeries> medMap = new HashMap<String, TaskSeries>();

	public TestChartX() {
		super("test");
	}

	public TestChartX(String s) {
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(600, 215));
		setContentPane(jpanel);
	}

	private JFreeChart createChart(IntervalXYDataset intervalxydataset) {

		JFreeChart jfreechart = ChartFactory.createXYBarChart(null, "Resource",
				false, "Timing", intervalxydataset, PlotOrientation.HORIZONTAL,
				false, false, false);

		// jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		XYBarRenderer xybarrenderer = new SoulXYBarRenderer();
		xyplot.setRenderer(xybarrenderer);

		xyplot.setRangeGridlinesVisible(false);
		xyplot.setDomainGridlinePaint(Color.black);

		String[] tmp = new String[medMap.keySet().size()];
		int i = 0;
		for (String s : medMap.keySet()) {
			tmp[i] = s;
			i++;
		}

		SoulAxis symbolaxis = new SoulAxis(null, tmp); // Wirkstoff Achse
		xyplot.setDomainAxis(symbolaxis);

		symbolaxis.setGridBandsVisible(false);

		xybarrenderer.setBarPainter(new StandardXYBarPainter());
		xybarrenderer.setBasePaint(Color.white);
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
		xyplot.getRangeAxis().setLabelFont(standardFont);
		xyplot.getDomainAxis().setLabelFont(standardFont);

		long oldEnd = (new Day()).getFirstMillisecond(); // today

		xyplot.getRangeAxis().setAutoRange(false);
		xyplot.getRangeAxis().setRange(chartRange);

		// TODO only for this patient
		List<TrialData> stammDatas = stammForm.getTrialDatas();
		// stammdaten is fill once
		if (stammDatas.size() > 0) {
			TrialData stammDaten = stammDatas.get(0);
			StringBuffer header = new StringBuffer();
			header.append("Patient: ");
			header.append(patient.getKennnummer());
			String geschlecht = ((StringValue) stammDaten
					.getValueByAttributeId(this.stammFormGeschlecht))
					.getValue();
			if (geschlecht != null) {
				header.append(", ");
				header.append(geschlecht.toLowerCase());
			}
			Integer gebJahr = ((IntegerValue) stammDaten
					.getValueByAttributeId(this.stammFormGeburtsjahr))
					.getValue();
			if (gebJahr != null) {
				header.append(", geb. ");
				header.append(gebJahr);
			}

			TextTitle subtitle = new TextTitle(header.toString(), standardFont
					.deriveFont(Font.BOLD));
			subtitle.setMargin(0, 0, 15, 0);
			jfreechart.addSubtitle(subtitle);
		}

		// TODO only for this patient
		List<TrialData> tds = untForm.getTrialDatas();

		// use treemap to sort trial datas
		TreeMap<Date, String> episodes = new TreeMap<Date, String>(
				new SoulComparator());
		for (TrialData td : tds) {
			Date beginDate = ((DateValue) td
					.getValueByAttributeId(this.untFormBeginnId)).getValue();
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
					.getTime(), oldEnd, colourMap.get(art), new BasicStroke(
					2.0F), null, null, 1.0F);
			intervalmarker.setLabel(getAbbreviation(art));
			intervalmarker.setLabelAnchor(RectangleAnchor.TOP);
			intervalmarker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
			intervalmarker.setLabelFont(new Font("SansSerif", Font.ITALIC, 12));

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

		return jfreechart;
	}

	public JPanel createDemoPanel() {
		ChartPanel cp = new ChartPanel(createChart(new XYTaskDataset(
				createTasks())));
		return cp;
	}

	public JFreeChart getJFC() {
		return createChart(new XYTaskDataset(createTasks()));
	}

	private TaskSeriesCollection createTasks() {

		Trial t = null;
		try {
			t = buildSoulTrial();
			addTestData(t);
		} catch (ChartingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// generate an entry for each medicine
		for (String wk : meds) {
			medMap.put(wk, new TaskSeries(wk));
		}

		for (TrialData td : medForm.getTrialDatas()) {

			Date beginnDate = ((DateValue) td
					.getValueByAttributeId(this.medFormBeginnId)).getValue();
			Date endDate = ((DateValue) td
					.getValueByAttributeId(this.medFormEndId)).getValue();

			if (endDate.getTime() < TestChartX.chartRange.getLowerBound())
				continue;

			String wirkstoff = ((StringValue) td
					.getValueByAttributeId(this.medFormMedId)).getValue();
			BigDecimal dosis = ((DecimalValue) td
					.getValueByAttributeId(this.medFormDosisId)).getValue();

			TaskSeries ts = medMap.get(wirkstoff); // XXX: check null
			ts.add(new Task(new Integer(dosis.intValue()).toString(),
					beginnDate, endDate));
		}

		TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();

		// add elements with more than one element to chart
		for (TaskSeries ts : medMap.values()) {
			if (ts.getItemCount() > 0) {
				taskseriescollection.add(ts);
			}
		}

		return taskseriescollection;
	}

	public static void main(String[] args) throws Exception {
		TestChartX xytaskdatasetdemo1 = new TestChartX(
				"JFreeChart : XYTaskDatasetDemo1.java");

		BufferedImage bi = xytaskdatasetdemo1.getJFC().createBufferedImage(600,
				215);
		Graphics2D g2 = bi.createGraphics();
		
		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout layout = new TextLayout("halset", standardFont,
				frc);
		Rectangle2D bounds = layout.getBounds();
		System.out.println(bounds.getWidth());
		g2.dispose();

		// OutputStream out = new FileOutputStream("test.png");
		//
		// ChartUtilities.writeChartAsPNG(out, xytaskdatasetdemo1.getJFC(), 600,
		// 215);
		//
		// out.close();

		xytaskdatasetdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(xytaskdatasetdemo1);
		xytaskdatasetdemo1.setVisible(true);

	}

	public Trial buildSoulTrial() throws ChartingException {

		System.out.println("Reading TrialForms ... started");

		Trial trial = new Trial();
		trial.setName("soul test trial");

		IXMLImportExport trialReader = new XMLImportExport();
		// this.getClass().getClassLoader(
		File fStamm = new File("trialforms/Stammdaten_2.zip");
		File fMed = new File("trialforms/Medikation_3.zip");
		File fUnter = new File("trialforms/Untersuchung_2.zip");

		try {
			stammForm = trialReader.readTrialFormFromZip(fStamm);
			stammForm.setTrial(trial);
			trial.getTrialForms().add(stammForm);

			medForm = trialReader.readTrialFormFromZip(fMed);
			medForm.setTrial(trial);
			trial.getTrialForms().add(medForm);

			untForm = trialReader.readTrialFormFromZip(fUnter);
			untForm.setTrial(trial);
			trial.getTrialForms().add(untForm);

		} catch (XmlImportExportException e) {
			throw new ChartingException(e);
		}

		System.out.println("Reading TrialForms ... done");

		return trial;
	}

	public void addTestData(Trial soulTrial) throws ChartingException {

		patient = new Patient();
		patient.setKennnummer("xyz");

		// add stammdaten
		Attribute gebAttr = null;
		Attribute geschlechtAttr = null;
		for (Attribute a : stammForm.fullSortedAttributeList()) {
			if (a.getName().equals("Geburtsjahr")) {
				gebAttr = a;
				System.out.println("Stammdatenformular - Geburtsjahr gefunden");
				gebAttr.setId(1L);
				this.stammFormGeburtsjahr = a.getId();
			}
			if (a.getName().equals("Geschlecht")) {
				geschlechtAttr = a;
				System.out.println("Stammdatenformular - Geschlecht gefunden");
				geschlechtAttr.setId(2L);
				this.stammFormGeschlecht = a.getId();
			}
		}

		// add untersuchungs data

		Attribute begAttr = null;
		Attribute endAttr = null;
		Attribute medAttr = null;
		Attribute dosAttr = null;

		for (Attribute a : medForm.fullSortedAttributeList()) {
			if (a.getName().equals("Beginndatum")) {
				begAttr = a;
				System.out
						.println("Medikationsformular - Beginndatum gefunden");
				begAttr.setId(1L);
				this.medFormBeginnId = a.getId();
			}
			if (a.getName().equals("Enddatum")) {
				endAttr = a;
				System.out.println("Medikationsformular - Enddatum gefunden");
				endAttr.setId(2L);
				this.medFormEndId = a.getId();
			}
			if (a.getName().equals("Wirkstoff")) {
				medAttr = a;
				System.out.println("Medikationsformular - Wirkstoff gefunden");
				medAttr.setId(3L);
				this.medFormMedId = a.getId();
			}
			if (a.getName().equals("Dosierung")) {
				dosAttr = a;
				System.out.println("Medikationsformular - Dosierung gefunden");
				dosAttr.setId(4L);
				this.medFormDosisId = a.getId();
			}
		}

		Attribute begUntAttr = null;
		Attribute artAttr = null;

		for (Attribute a : untForm.fullSortedAttributeList()) {
			if (a.getName().equals("Beginndatum")) {
				begUntAttr = a;
				begUntAttr.setId(666L); // just for testing
				this.untFormBeginnId = a.getId();
				System.out
						.println("Untersuchungsformular - Beginndatum gefunden");
			}
			if (a.getName().equals("Art")) {
				artAttr = a;
				artAttr.setId(667L); // just for testing
				this.untFormArtId = a.getId();
				System.out.println("Untersuchungsformular - Art gefunden");
			}
		}

		TrialData stammData = new TrialData();
		stammData.setPatient(patient);
		stammData.setTrialform(stammForm);

		IntegerValue geb = new IntegerValue();
		geb.setAttribute(gebAttr);
		geb.setTrialData(stammData);
		geb.setValue(1970);
		stammData.getValues().add(geb);

		StringValue geschlecht = new StringValue();
		geschlecht.setAttribute(geschlechtAttr);
		geschlecht.setTrialData(stammData);
		geschlecht.setValue("Männlich");
		stammData.getValues().add(geschlecht);

		stammForm.getTrialDatas().add(stammData);

		TrialData trialData = new TrialData();
		trialData.setPatient(patient);
		trialData.setTrialform(medForm);

		DateValue beg1 = new DateValue();
		beg1.setAttribute(begAttr);
		beg1.setTrialData(trialData);
		beg1.setValue(new Date(new GregorianCalendar(2008, 11, 1)
				.getTimeInMillis()));
		trialData.getValues().add(beg1);

		DateValue end1 = new DateValue();
		end1.setAttribute(endAttr);
		end1.setTrialData(trialData);
		end1.setValue(new Date(new GregorianCalendar(2008, 11, 7)
				.getTimeInMillis()));
		trialData.getValues().add(end1);

		StringValue wk1 = new StringValue();
		wk1.setAttribute(medAttr);
		wk1.setTrialData(trialData);
		wk1.setValue("halset");
		trialData.getValues().add(wk1);

		DecimalValue dos1 = new DecimalValue();
		dos1.setAttribute(dosAttr);
		dos1.setTrialData(trialData);
		dos1.setValue(new BigDecimal(4.0));
		trialData.getValues().add(dos1);

		medForm.getTrialDatas().add(trialData);

		TrialData trialData2 = new TrialData();
		trialData2.setPatient(patient);
		trialData2.setTrialform(medForm);

		DateValue beg2 = new DateValue();
		beg2.setAttribute(begAttr);
		beg2.setTrialData(trialData2);
		beg2.setValue(new Date(new GregorianCalendar(2008, 11, 9)
				.getTimeInMillis()));
		trialData2.getValues().add(beg2);

		DateValue end2 = new DateValue();
		end2.setAttribute(endAttr);
		end2.setTrialData(trialData2);
		end2.setValue(new Date(new GregorianCalendar(2008, 11, 24)
				.getTimeInMillis()));
		trialData2.getValues().add(end2);

		StringValue wk2 = new StringValue();
		wk2.setAttribute(medAttr);
		wk2.setTrialData(trialData2);
		wk2.setValue("aspirin");
		trialData2.getValues().add(wk2);

		DecimalValue dos2 = new DecimalValue();
		dos2.setAttribute(dosAttr);
		dos2.setTrialData(trialData2);
		dos2.setValue(new BigDecimal(5));
		trialData2.getValues().add(dos2);

		medForm.getTrialDatas().add(trialData2);

		TrialData trialData3 = new TrialData();
		trialData3.setPatient(patient);
		trialData3.setTrialform(medForm);

		DateValue beg3 = new DateValue();
		beg3.setAttribute(begAttr);
		beg3.setTrialData(trialData3);
		beg3.setValue(new Date(new GregorianCalendar(2008, 9, 7)
				.getTimeInMillis()));
		trialData3.getValues().add(beg3);

		DateValue end3 = new DateValue();
		end3.setAttribute(endAttr);
		end3.setTrialData(trialData3);
		end3.setValue(new Date(new GregorianCalendar(2008, 11, 9)
				.getTimeInMillis()));
		trialData3.getValues().add(end3);

		StringValue wk3 = new StringValue();
		wk3.setAttribute(medAttr);
		wk3.setTrialData(trialData3);
		wk3.setValue("parkemed");
		trialData3.getValues().add(wk3);

		DecimalValue dos3 = new DecimalValue();
		dos3.setAttribute(dosAttr);
		dos3.setTrialData(trialData3);
		dos3.setValue(new BigDecimal(2));
		trialData3.getValues().add(dos3);

		medForm.getTrialDatas().add(trialData3);

		// TrialData trialData4 = new TrialData();
		// trialData4.setPatient(patient);
		// trialData4.setTrialform(medForm);
		//
		// DateValue beg4 = new DateValue();
		// beg4.setAttribute(begAttr);
		// beg4.setTrialData(trialData4);
		// beg4.setValue(new Date(new GregorianCalendar(2008, 11, 20)
		// .getTimeInMillis()));
		// trialData4.getValues().add(beg4);
		//
		// DateValue end4 = new DateValue();
		// end4.setAttribute(endAttr);
		// end4.setTrialData(trialData4);
		// end4.setValue(new Date(new GregorianCalendar(2008, 11, 24)
		// .getTimeInMillis()));
		// trialData4.getValues().add(end4);
		//
		// StringValue wk4 = new StringValue();
		// wk4.setAttribute(medAttr);
		// wk4.setTrialData(trialData4);
		// wk4.setValue("halset");
		// trialData4.getValues().add(wk4);
		//
		// DecimalValue dos4 = new DecimalValue();
		// dos4.setAttribute(dosAttr);
		// dos4.setTrialData(trialData4);
		// dos4.setValue(new BigDecimal(3));
		// trialData4.getValues().add(dos4);
		//
		// medForm.getTrialDatas().add(trialData4);

		// episoden testdata

		TrialData trialData5 = new TrialData();
		trialData5.setPatient(patient);
		trialData5.setTrialform(untForm);

		DateValue beg5 = new DateValue();
		beg5.setAttribute(begUntAttr);
		beg5.setTrialData(trialData5);
		beg5.setValue(new Date((new Day(13, 8, 2008)).getFirstMillisecond()));
		trialData5.getValues().add(beg5);

		StringValue art5 = new StringValue();
		art5.setAttribute(artAttr);
		art5.setTrialData(trialData5);
		art5.setValue("Episode - F31.1 MAN ohne psychot. Sympt.");
		trialData5.getValues().add(art5);

		untForm.getTrialDatas().add(trialData5);

		TrialData trialData6 = new TrialData();
		trialData6.setPatient(patient);
		trialData6.setTrialform(untForm);

		DateValue beg6 = new DateValue();
		beg6.setAttribute(begUntAttr);
		beg6.setTrialData(trialData6);
		beg6.setValue(new Date((new Day(15, 12, 2008)).getFirstMillisecond()));
		trialData6.getValues().add(beg6);

		StringValue art6 = new StringValue();
		art6.setAttribute(artAttr);
		art6.setTrialData(trialData6);
		art6.setValue("Episode - F31.0 HMA");
		trialData6.getValues().add(art6);

		untForm.getTrialDatas().add(trialData6);
	}

	public JFreeChart getChart(ChartProcessor chartProcessor) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		return "SoulChart";
	}

	public void init(ChartRepository chartRepository) throws ChartingException {
		// TODO Auto-generated method stub

	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public static Color getColor(String key) {
		Color c = colourMap.get("key");

		return (c != null) ? c : colourMap.get("Sonstiges");
	}

	public static String getAbbreviation(String key) {
		String s = abbrMap.get(key);

		return (s != null) ? s : key;
	}

}
