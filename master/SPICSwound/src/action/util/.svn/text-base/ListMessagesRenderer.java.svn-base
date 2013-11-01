package util;

import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlMessagesRendererBase;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_impl.util.NullIterator;

/**
 * Render FacesMessages like HtmlMessagesRenderer but with
 * better CSS
 *  
 * @author inso
 */

public class ListMessagesRenderer extends HtmlMessagesRendererBase {

	protected static final Log log = LogFactory.getLog(ListMessagesRenderer.class);
	
	private static final String LAYOUT_SUMMARY = "summary";

	public void encodeEnd(FacesContext facesContext, UIComponent component)	throws IOException {
		super.encodeEnd(facesContext, component);
		renderMessages(facesContext, component);
	}

	protected void renderMessages(FacesContext facesContext,
			UIComponent messages) throws IOException {
		MessagesIterator messagesIterator = new MessagesIterator(facesContext, isGlobalOnly(messages));

		
		if (messagesIterator.hasNext()) {
			String layout = getLayout(messages);
			if (layout == null) {
				if (log.isDebugEnabled()) {
					log.debug("No messages layout given, using default layout 'list'.");
					layout = LAYOUT_LIST;
				}
			} else if (layout.equals(LAYOUT_SUMMARY)) {
				log.debug("Layout 'summary' chosen");
			} else {
				if (log.isWarnEnabled()	&& !layout.equalsIgnoreCase(LAYOUT_LIST)) {
					log.warn("Unsupported messages layout '" + layout + "' - using default layout 'list'.");
					layout = LAYOUT_LIST;
				}
			}
			renderList(facesContext, messages, messagesIterator, layout);
		}
	}

	private void renderList(FacesContext facesContext, UIComponent messages, MessagesIterator messagesIterator,String layout) throws IOException {
		ResponseWriter writer = facesContext.getResponseWriter();
		HtmlRendererUtils.writeIdIfNecessary(writer, messages, facesContext);
		
		//get messages for for summary layout
		ResourceBundle rb = ResourceBundle.getBundle("messages",facesContext.getViewRoot().getLocale(),Thread.currentThread().getContextClassLoader());
		
		String styleClass ="";
		int count = 0;
		
		while (messagesIterator.hasNext()) {
			FacesMessage message = (FacesMessage) messagesIterator.next();
			// superclass cares for getting the right style and styleClass
			// for this message by message severity and component configuration
			String[] styleAndClass = getStyleAndStyleClass(messages, message.getSeverity());
			String style = styleAndClass[0];
			styleClass = styleAndClass[1];
			
			if ("list".equals(layout) || count == 0) {
				writer.startElement(HTML.DIV_ELEM, messages); //element with border and icon
				// apply style and styleClass to DIV
				String styleClassOp = "";
				if ("summary".equals(layout)) { styleClassOp = styleClass+"Summary"; } else { styleClassOp = styleClass; }
				HtmlRendererUtils.renderHTMLAttribute(writer,HTML.STYLE_CLASS_ATTR,HTML.CLASS_ATTR,styleClassOp);
				HtmlRendererUtils.renderHTMLAttribute(writer,HTML.STYLE_ATTR,HTML.STYLE_ATTR,style);
				//start of summary block
				if ("summary".equals(layout)) {
					if ("warningMessage".equals(styleClass)) {
						writer.write(rb.getString("warn.overview.start"));
					} else if("errorMessage".equals(styleClass)) {
						writer.write(rb.getString("error.overview.start"));
					}
					writer.startElement(HTML.UL_ELEM, messages);
				}
			}
			
			if ("summary".equals(layout)) {
				writer.startElement(HTML.LI_ELEM, messages);
			}
			
			renderSingleFacesMessage(facesContext, messages, message, messagesIterator.getClientId());
			
			if ("summary".equals(layout)) {
				writer.endElement(HTML.LI_ELEM);
			} else if ("list".equals(layout)) {
				writer.endElement(HTML.DIV_ELEM);
			}
			
			count++;
		}
		
		
		//end of summary block
		if ("summary".equals(layout)) {
			writer.endElement(HTML.UL_ELEM);
			if ("warningMessage".equals(styleClass)) {
				writer.write(rb.getString("warn.overview.end"));
			} else if ("errorMessage".equals(styleClass)) {
				writer.write(rb.getString("error.overview.end"));
			}
			writer.endElement(HTML.DIV_ELEM);
		}
		
	}

	protected void renderSingleFacesMessage(FacesContext facesContext,
			UIComponent message, FacesMessage facesMessage,
			String messageClientId) throws IOException {

		String summary = getSummary(facesContext, message, facesMessage,
				messageClientId);
		String detail = getDetail(facesContext, message, facesMessage,
				messageClientId);

		String title = getTitle(message);
		boolean tooltip = isTooltip(message);

		if (title == null && tooltip) {
			title = summary;
		}

		ResponseWriter writer = facesContext.getResponseWriter();

		// changed by Stefan: only display summary if details is not empty!
		boolean showSummary = isShowSummary(message) && (summary != null) && (detail == null);
		boolean showDetail = isShowDetail(message) && (detail != null);

		// render summary and detail in separate span elements
		// with distinct style classes
		
		if (showSummary && !(title == null && tooltip)) {
			writer.startElement(HTML.SPAN_ELEM,message);
			writer.writeAttribute(HTML.CLASS_ATTR,"summary",null);
			writer.writeText(summary, null);
			writer.endElement(HTML.SPAN_ELEM);
		}

		if (showDetail) {
			writer.startElement(HTML.SPAN_ELEM,message);
			writer.writeAttribute(HTML.CLASS_ATTR,"detail",null);
			writer.writeText(detail, null);
			writer.endElement(HTML.SPAN_ELEM);
		}
	}

	protected String getSummary(FacesContext facesContext, UIComponent message,	FacesMessage facesMessage, String msgClientId) {
		return facesMessage.getSummary();
	}

	protected String getDetail(FacesContext facesContext, UIComponent message, FacesMessage facesMessage, String msgClientId) {
		return facesMessage.getDetail() != facesMessage.getSummary() ? facesMessage.getDetail() : null;
	}


	// copied from HtmlMessagesRendererBase since it is private there
	@SuppressWarnings("unchecked")
	private static class MessagesIterator implements Iterator {
		private FacesContext _facesContext;

		private Iterator _clientIdsWithMessagesIterator;

		private Iterator _componentMessagesIterator = null;

		private String _clientId = null;

		public MessagesIterator(FacesContext facesContext, boolean globalOnly) {
			_facesContext = facesContext;
			if (globalOnly) {
				_clientIdsWithMessagesIterator = NullIterator.instance();
			} else {
				_clientIdsWithMessagesIterator = facesContext
						.getClientIdsWithMessages();
			}
			_componentMessagesIterator = null;
			_clientId = null;
		}

		public boolean hasNext() {
			return 
			_clientIdsWithMessagesIterator.hasNext()
					|| (_componentMessagesIterator != null && _componentMessagesIterator
							.hasNext());
		}

		public Object next() {

				if (_componentMessagesIterator != null
					&& _componentMessagesIterator.hasNext()) {
				return _componentMessagesIterator.next();
			} else {
				_clientId = (String) _clientIdsWithMessagesIterator.next();
				_componentMessagesIterator = _facesContext
						.getMessages(_clientId);
				return _componentMessagesIterator.next();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException(this.getClass().getName()
					+ " UnsupportedOperationException");
		}

		public String getClientId() {
			return _clientId;
		}
	}
}
