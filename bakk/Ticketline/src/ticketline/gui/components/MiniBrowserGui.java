package ticketline.gui.components;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

import org.apache.log4j.Logger;

import ticketline.cfg.ConfigFactory;

/**
 * Mini Browser to Display RSS News
 * 
 * DEPRECATED!!!
 * 
 * @author PatrickF, http://www.java-tips.org/java-se-tips/javax.swing/how-to-create-a-simple-browser-in-swing-3.html
 * @version 0.2
 */
public class MiniBrowserGui extends DefaultTicketlineJFrame implements HyperlinkListener {
    
	private static final long serialVersionUID = 7126019685001255578L;
	private static Logger logger = Logger.getLogger(MiniBrowserGui.class);
	private static ResourceBundle lang;
	
	// These are the buttons for iterating through the page list.
    private JButton backButton, forwardButton;
    
    // Page location text field.
    private JTextField locationTextField;
    
    // Editor pane for displaying pages.
    private JTextPane displayEditorPane;
    
    // Browser's list of pages that have been visited.
    private ArrayList pageList = new ArrayList();
    
   /**
    * CONSTRUCTOR
    * @param uri - the initial Site to display
    */
    public MiniBrowserGui(URL uri) {
    	lang = ConfigFactory.getConfig().getLanguageBundle();
    	
    	// Set application title.
    	setTitle(lang.getString("PNL_BROWSER"));
    	
        
        // Set window size.
        setSize(800, 600);
        
        // Handle closing events.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                actionExit();
            }
        });
        
        // Set up button panel.
        JPanel buttonPanel = new JPanel();
        backButton = new JButton(lang.getString("BTN_BROWSER_BACK"));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionBack();
            }
        });
        backButton.setEnabled(false);
        buttonPanel.add(backButton);
        forwardButton = new JButton(lang.getString("BTN_BROWSER_FORWARD"));
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionForward();
            }
        });
        forwardButton.setEnabled(false);
        buttonPanel.add(forwardButton);
        locationTextField = new JTextField(35);
        locationTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionGo();
                }
            }
        });
        buttonPanel.add(locationTextField);
        JButton goButton = new JButton(lang.getString("BTN_BROWSER_GO"));
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionGo();
            }
        });
        buttonPanel.add(goButton);
        
        // Set up page display.
        displayEditorPane = new JTextPane();
        displayEditorPane.setContentType("text/html");
        displayEditorPane.setEditable(false);
        displayEditorPane.addHyperlinkListener(this);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(displayEditorPane), BorderLayout.CENTER);
        
        //inital show this webpage
        showPage(uri, true);
        setVisible(true);
        
        logger.info("MiniBrowserGui has startet.");
    }
    
    // Exit this program.
    private void actionExit() {
        dispose();
    }
    
    // Go back to the page viewed before the current page.
    private void actionBack() {
        URL currentUrl = displayEditorPane.getPage();
        int pageIndex = pageList.indexOf(currentUrl.toString());
        try {
            showPage(
                    new URL((String) pageList.get(pageIndex - 1)), false);
        } catch (Exception e) {}
    }
    
    // Go forward to the page viewed after the current page.
    private void actionForward() {
        URL currentUrl = displayEditorPane.getPage();
        int pageIndex = pageList.indexOf(currentUrl.toString());
        try {
            showPage(
                    new URL((String) pageList.get(pageIndex + 1)), false);
        } catch (Exception e) {}
    }
    
    // Load and show the page specified in the location text field.
    private void actionGo() {
        URL verifiedUrl = verifyUrl(locationTextField.getText());
        if (verifiedUrl != null) {
            showPage(verifiedUrl, true);
        } else {
            showError("Invalid URL");
        }
    }
    
    // Show dialog box with error message.
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Verify URL format.
    private URL verifyUrl(String url) {
        // Only allow HTTP URLs.
        if (!url.toLowerCase().startsWith("http://"))
            return null;
        
        // Verify format of URL.
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            return null;
        }
        
        return verifiedUrl;
    }
    
  /* Show the specified page and add it to
     the page list if specified. */
    private void showPage(URL pageUrl, boolean addToList) {
        // Show hour glass cursor while crawling is under way.
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        try {
            // Get URL of page currently being displayed.
            URL currentUrl = displayEditorPane.getPage();
            
            // Load and display specified page.
            displayEditorPane.setPage(pageUrl);
            
            // Get URL of new page being displayed.
            URL newUrl = displayEditorPane.getPage();
            
            // Add page to list if specified.
            if (addToList) {
                int listSize = pageList.size();
                if (listSize > 0) {
                    int pageIndex =
                            pageList.indexOf(currentUrl.toString());
                    if (pageIndex < listSize - 1) {
                        for (int i = listSize - 1; i > pageIndex; i--) {
                            pageList.remove(i);
                        }
                    }
                }
                pageList.add(newUrl.toString());
            }
            
            // Update location text field with URL of current page.
            locationTextField.setText(newUrl.toString());
            
            // Update buttons based on the page being displayed.
            updateButtons();
        } catch (Exception e) {
            // Show error messsage.
            showError("Unable to load page");
        } finally {
            // Return to default cursor.
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
  /* Update back and forward buttons based on
     the page being displayed. */
    private void updateButtons() {
        if (pageList.size() < 2) {
            backButton.setEnabled(false);
            forwardButton.setEnabled(false);
        } else {
            URL currentUrl = displayEditorPane.getPage();
            int pageIndex = pageList.indexOf(currentUrl.toString());
            backButton.setEnabled(pageIndex > 0);
            forwardButton.setEnabled(
                    pageIndex < (pageList.size() - 1));
        }
    }
    
    // Handle hyperlink's being clicked.
    public void hyperlinkUpdate(HyperlinkEvent event) {
        HyperlinkEvent.EventType eventType = event.getEventType();
        if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
            if (event instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent linkEvent =
                        (HTMLFrameHyperlinkEvent) event;
                HTMLDocument document =
                        (HTMLDocument) displayEditorPane.getDocument();
                document.processHTMLFrameHyperlinkEvent(linkEvent);
            } else {
                showPage(event.getURL(), true);
            }
        }
    }
}


