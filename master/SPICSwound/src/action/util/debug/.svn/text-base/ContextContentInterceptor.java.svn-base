package util.debug;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.seam.annotations.intercept.Interceptor;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.core.BijectionInterceptor;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.ConversationalInterceptor;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

@Interceptor(around = { ConversationalInterceptor.class,
		BijectionInterceptor.class })
public class ContextContentInterceptor {

	private static Log log = Logging.getLog(ContextContentInterceptor.class);

	@AroundInvoke
	public Object checkSerializability(InvocationContext invocation)
			throws Exception {
		
		String invokedMethodName = invocation.getMethod().getName();
		
		log.info("****************** invocation on method #0 ***********************", invokedMethodName);
		try {
			Conversation currentConversation = Conversation.instance();
			log.info("Active conversation with id #0 isLongRunning? #1",
					currentConversation.getId(), currentConversation
							.isLongRunning());

			Context conversationContext = Contexts.getConversationContext();
			String[] conversationContextContents = conversationContext
					.getNames();
			for (String s : conversationContextContents) {
				log.info(" name #0 toString: #1", s, conversationContext.get(s));
			}
		} catch (IllegalStateException ise) {
			log.warn("Could not load Conversation, msg: #0", ise.getMessage());
		}

		Object result = invocation.proceed();

		log.info("************************ after invocation ************************");

		try {
			Conversation currentConversation = Conversation.instance();
			log.info("Active conversation with id #0 isLongRunning? #1",
					currentConversation.getId(), currentConversation
							.isLongRunning());

			Context conversationContext = Contexts.getConversationContext();
			String[] conversationContextContents = conversationContext
					.getNames();
			for (String s : conversationContextContents) {
				log.info(" name #0 toString: #1", s, conversationContext.get(s));
			}
		} catch (IllegalStateException ise) {
			log.warn("Could not load Conversation, msg: #0", ise.getMessage());
		}
		
		log.info("****************** invocation on method #0 finished ******************", invokedMethodName);
		return result;
	}
}
