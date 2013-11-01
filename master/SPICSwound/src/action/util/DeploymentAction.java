package util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.ejb.EJB;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;

import bean.TestDataGenerator;
import bean.TestDataGeneratorBean;

@Name("DeploymentAction")
@Startup
@Scope(ScopeType.APPLICATION)
public class DeploymentAction {
	
	private static ResourceBundle deploymentProps;
	private static boolean errorReadingProps = false;
	private static Exception errorReadingPropsException;
	
	private static final String PROP_MAINVERSION = "mainversion";
	private static final String PROP_REVISION = "revision";
	private static final String PROP_TESTDATA = "testdata";
	private static final String PROP_SPICS_INSTANCE = "spics.instance";
	private static final String PROP_SHOW_AUSWERTUNG = "showauswertung";
	private static final String PROP_PATIENT_VISIBILITY = "patientvisibilityenabled";
	private static final String PROP_DEPLOY_SOUL_TESTDATA = "soultestdata";
	private static final String PROP_BUILDTIMESTAMP = "buildtimestamp";
	private static final String PROP_FACELET_DEBUG = "facelet.debug";
	private static final String PROP_MAIL_NOTIFICATION = "mailnotificationenabled";
	private static final String PROP_ADMIN_EMAIL = "admin.email";
	private static final String PROP_MAIL_HOST = "mail.smtp.host";
	private static final String PROP_MAIL_PORT = "mail.smtp.port";
	private static final String PROP_MAIL_FROM = "mail.from";
	private static final String PROP_MAIL_AUTH = "mail.smtp.auth";
	private static final String PROP_MAIL_AUTH_USER = "mail.smtp.auth.user";
	private static final String PROP_MAIL_AUTH_PW = "mail.smtp.auth.pw";
	
	
	@Logger
	private Log log;
	
	@EJB
	private TestDataGenerator testDataGenerator;
	
	private Boolean resetDB;
	
	static {
		try {
			deploymentProps = ResourceBundle.getBundle("deployment", Locale.getDefault(), Thread.currentThread().getContextClassLoader());
		
		} catch (MissingResourceException e) {
			errorReadingProps = true;
			errorReadingPropsException = e;
			
		}
	}

	@Create
	public void atStartup() {
	
		if(errorReadingProps) {
			log.warn("Error loading deployment properties: #0", errorReadingPropsException.getMessage());
			
		}
				
		if(isBooleanPropertyEnabled(PROP_TESTDATA)) {
			log.debug("generating testdata");
			if(testDataGenerator == null) {
				testDataGenerator = (TestDataGenerator)Component.getInstance(TestDataGeneratorBean.class);
			}
			testDataGenerator.generateTestForm(isBooleanPropertyEnabled(PROP_DEPLOY_SOUL_TESTDATA));
		}
		
		log.info("system properties: #0 #1", System.getProperty("spics.instance"), System.getProperty("spics.logo.url"));
		
	}
	
	public String getBuildNumber() {
		return getStringProperty(PROP_MAINVERSION) + getStringProperty(PROP_REVISION) + "." + getStringProperty(PROP_BUILDTIMESTAMP);
	}
	
	public String getSpicsInstance() {
		return getStringProperty(PROP_SPICS_INSTANCE);
	}
	
	public String getFaceletDebug() {
		return getStringProperty(PROP_FACELET_DEBUG);
	}

	public Boolean getResetDB() {
		return resetDB;
	}

	public void setResetDB(Boolean resetDB) {
		this.resetDB = resetDB;
	}
	
	public boolean isShowAuswertung() {
		return isBooleanPropertyEnabled(PROP_SHOW_AUSWERTUNG);
	}
	
	public boolean isPatientVisibilityEnabled() {
		return isBooleanPropertyEnabled(PROP_PATIENT_VISIBILITY);
	}
	
	public boolean isMailNotificationEnabled() {
		return isBooleanPropertyEnabled(PROP_MAIL_NOTIFICATION);
	}
	
	public String getAdminEmail() {
		return getStringProperty(PROP_ADMIN_EMAIL);
	}
	
	public String getMailHost() {
		return getStringProperty(PROP_MAIL_HOST);
	}
	
	public String getMailPort() {
		return getStringProperty(PROP_MAIL_PORT);
	}
	
	public String getMailFrom() {
		return getStringProperty(PROP_MAIL_FROM);
	}
	public String getMailAuth() {
		return getStringProperty(PROP_MAIL_AUTH);
	}
	public String getMailAuthUser() {
		return getStringProperty(PROP_MAIL_AUTH_USER);
	}
	public String getMailAuthPw() {
		return getStringProperty(PROP_MAIL_AUTH_PW);
	}
	
	
	/**
	 * fail save getter for boolean property - handles and logs MissingResourceException, then returns false
	 * @param propertyName
	 * @return
	 */
	public boolean isBooleanPropertyEnabled(String propertyName) {
		return Boolean.parseBoolean(getStringProperty(propertyName).trim());	
	}
	
	/**
	 * fail save getter for string property - handles and logs MissingResourceException, then returns empty string
	 * @param propertyName
	 * @return
	 */
	public String getStringProperty(String propertyName) {
		try {
			String result = deploymentProps.getString(propertyName);
			log.debug("loading property name #0, value #1, length #2", propertyName, result, result.length());
			return result;
		} catch (MissingResourceException e) {
			log.warn("Missing Resource in deployment.properties: #0", propertyName);
			return "";
		}
	}

}
