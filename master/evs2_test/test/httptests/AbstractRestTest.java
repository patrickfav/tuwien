package httptests;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXB;

import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import security.SecureChannelAsym;
import server.RestServer;
import testdata.TestDataGenerator;

import com.google.gson.Gson;

import constants.ContentType;

public class AbstractRestTest {
	public static final String HOST = "http://localhost:9000/";
	
	public static final int TESTGENERATOR_SALT = 463;
	
	public static final String SKILL_SERVICE = "skills";
	public static final String PERSON_SERVICE = "persons";
	public static final String COMPANY_SERVICE = "companies";
	public static final String OCCUPATION_SERVICE = "occupations";
	
	public static final String SKILL_CONTENTTYPE = ContentType.XML;
	public static final String PERSON_CONTENTTYPE = ContentType.XML;
	public static final String COMPANY_CONTENTTYPE = ContentType.JSON;
	public static final String OCCUPATION_CONTENTTYPE = ContentType.JSON;
	
	public static final String SEARCH_URL_KEYWORD = RestServer.SEARCH_URL_KEYWORD;
	
	protected static HttpClient client;
	protected static TestDataGenerator tdg;
	
	protected static SecureChannelAsym scAsym;
	
	//protected static Logger log = Logger.getLogger(TestDataPersister.class);
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		scAsym = new SecureChannelAsym("keys/bill.pem", "keys/server.pub.pem");
		tdg = new TestDataGenerator(TESTGENERATOR_SALT);
		client = new HttpClient();
		
    	client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
    	//client.setMaxConnectionsPerAddress(200); // max 200 concurrent connections to every address
    	//client.setThreadPool(new QueuedThreadPool(250)); // max 250 threads
    	client.setTimeout(10000); // 10 seconds timeout; if no server reply, the request expires
    	client.start();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		client.stop();
		client = null;
		tdg = null;
	}
	
	
	public static String serializeJson(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);	
	}
	
	public static String serializeXml(Object o) throws UnsupportedEncodingException {
		ByteArrayOutputStream f = new ByteArrayOutputStream(); 
		JAXB.marshal(o, f);
		return f.toString("utf-8");
	}
	
	protected String serialize(Object o,String contentType, ContentExchange exchange) throws UnsupportedEncodingException {
		ByteArrayOutputStream f;
		if(contentType.equals(ContentType.JSON)) {
			Gson gson = new Gson();
			exchange.setRequestContentType("text/plain");
			return gson.toJson(o);
		} else if(contentType.equals(ContentType.XML)) {
			f = new ByteArrayOutputStream();
			JAXB.marshal(o, f);
			exchange.setRequestContentType("text/xml");
			return f.toString("utf-8");
		} else {
			return null;
		}
	}
	
	protected <T> T deserialize(String content,Class<T> clazz, String contentType) {
		if(contentType.equals(ContentType.JSON)) {
			Gson gson = new Gson();
			return gson.fromJson(content, clazz);
		} else if(contentType.equals(ContentType.XML)) {
			return JAXB.unmarshal(new StringReader(content), clazz);
		} else {
			return null;
		}
	}
	
	public static boolean isXMLContentType(String type) {
		return type.equals(ContentType.XML);
	}
	
	public static boolean isJSONContentType(String type) {
		return type.equals(ContentType.JSON);
	}
}
