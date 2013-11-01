package ticketline.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.apache.log4j.Logger;

import ticketline.cfg.ConfigFactory;
import ticketline.gui.components.BrowserControl;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.URL;


/**
 * News Anzeigen Gui
 * 
 * @author PatrickF
 * @version 0.6
 */
public class NewsAnzeigenGui extends JPanel implements ActionListener, ComponentListener,HyperlinkListener,MouseListener {
	private static final long serialVersionUID = 1L;
		
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(NewsAnzeigenGui.class);
	private static ArrayList<JTextPane> newsArray;
	private static String newsTitle;
	
	private JTextPane news;
	private JScrollPane scrl_pane;
	private JPanel inside_panel;
	private String rssDate;
	
	
	public NewsAnzeigenGui() {
		Gui.setPanelSize();		
		addComponentListener(this);
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
			
		//Customize the Panel
		
		setBorder(new TitledBorder(lang.getString("PNL_NEWS")));
		setLayout(new GridLayout());
		
		//setMinimumSize(new Dimension(570,600));
		
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		//handle news cache - no cache set
		if(newsArray == null || newsArray.size() <= 0) {
			inside_panel = new JPanel();
			
		    try {
	
	            URL feedUrl = new URL(ConfigFactory.getConfig().getNewsUri());
	
	            SyndFeedInput input = new SyndFeedInput();
	            SyndFeed feed = input.build(new XmlReader(feedUrl));
	
	            SyndEntryImpl feddentry = new SyndEntryImpl();
	           
	            //setting additional info to the border
	            newsTitle = feed.getTitle();
	            
	            //setting up cache array
	            newsArray = new ArrayList<JTextPane>(Math.min(ConfigFactory.getConfig().getNewsCount(),feed.getEntries().size()));
	            inside_panel.setLayout(new GridLayout(Math.min(ConfigFactory.getConfig().getNewsCount(),feed.getEntries().size()),1));
	    		
	    		//general gridbaglayout adjustments
	    	    c.insets = new Insets(5,5,0,0);
	    	    c.anchor = GridBagConstraints.LINE_START;
	            
	
	            for (int i = 0; i < Math.min(ConfigFactory.getConfig().getNewsCount(),feed.getEntries().size()) /*feed.getEntries().size()*/; i++) {
	                
	                feddentry = (SyndEntryImpl) feed.getEntries().get(i);
	                
	                c.gridx = 0;
	    			c.gridy = i;
	    			
	    			news = new JTextPane();
	    			news.addHyperlinkListener(this);
	    			news.setEditable(false);
	    			news.setMinimumSize(new Dimension(500,30));	
	    			news.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), 60));
	    			news.setMaximumSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), 200));			
	    			
	    			news.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	    			
	    			//safing input
	    			if(feddentry.getTitle() == null)
	    				feddentry.setTitle("");
	    			if(feddentry.getPublishedDate() == null) {
	    				rssDate = "";
	    			} else {
	    				rssDate = feddentry.getPublishedDate().toString();
	    			}
	    			if(feddentry.getDescription() == null) {
	    				SyndContentImpl syc = new SyndContentImpl();
	    				syc.setValue("");
	    				feddentry.setDescription(syc);
	    			}
	    			if(feddentry.getAuthor() == null)
	    				feddentry.setAuthor("");
	    			if(feddentry.getUri() == null)
	    				feddentry.setUri("");
	    			
	    			//displaying as html
	    			news.setContentType("text/html");
	    			news.setText("<div style='margin:5px;font-family: Verdana,Vera Sans, Arial, Helvetica, sans-serif;font-size:small;line-height:20px;font-size:85%;color:#333;line-height:1.5em;'>" +
	    					"<h1 style='font-size:medium;'>"+feddentry.getTitle()+" <em style='font-size:x-small;color:#999;'>"+rssDate+"</em></h1>" +
	    					"<p>"+feddentry.getDescription().getValue() +
	    					" <em>"+feddentry.getAuthor()+"</em></p></div>");
	    			news.setToolTipText(feddentry.getUri().toString());
	    			news.addMouseListener(this);
	    			
	    			//add to cache
	    			newsArray.add(news);
	    			//add to panel
	    			inside_panel.add(news,c);
	            }
	
	        } catch (Exception e) {
	        	inside_panel.add(new JLabel(lang.getString("OPT_NEWS_ERROR")));
	        	//JOptionPane.showMessageDialog(this, lang.getString("OPT_NEWS_ERROR"),lang.getString("OPH_NEWS_ERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("News:RSS:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
				/*e.printStackTrace();
	            System.out.println("ERROR: " + e.getMessage());*/
	        }
	    //return from cache
		} else {
			inside_panel = new JPanel();
			for(int i=0;i<newsArray.size();i++) {
				inside_panel.setLayout(new GridLayout(newsArray.size(),1));
				c.gridx = 0;
    			c.gridy = i;
				//add to panel
    			inside_panel.add(newsArray.get(i),c);
			}
		}
	    
		setBorder(new TitledBorder(lang.getString("PNL_NEWS")+": "+newsTitle));
		
	  //the scroll pane
		scrl_pane = new JScrollPane(inside_panel);
		//scrl_pane.setMaximumSize(new Dimension(500,400));
	    add(scrl_pane);
	    
	    logger.info("NewsAnzeigenGui has started");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	/**
	 * Hyperlink listener
	 * Shows a mini browser
	 */
	public void hyperlinkUpdate(HyperlinkEvent event) {
	    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
	    	BrowserControl.displayURL(event.getURL().toString());
	    }
	}
	
	/*
	 * MOUSE LISTENER
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Component temp = e.getComponent();
		temp.setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Component temp = e.getComponent();
		temp.setBackground(Color.WHITE);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		Component temp = me.getComponent();
		BrowserControl.displayURL(((JComponent) temp).getToolTipText());
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
				
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * COMPONENT LISTENER
	 * (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent e) {
		e.getComponent().setLocation(0,0);
		setSize(new Dimension(ConfigFactory.getConfig().getPnl_mainAreaWidth(),ConfigFactory.getConfig().getPnl_mainAreaHeight()));
		paintAll(this.getGraphics());
           
    }

	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}


}
