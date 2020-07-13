package com.client.automation.keywords;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.BodyTerm;
import javax.mail.search.RecipientStringTerm;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.client.automation.library.DynamicVarRepository;
import com.client.automation.library.Global;


/**
 * Email - Keyword
 * @class Email
 * @description - Email related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class Email{
	
	private static Logger logger = Logger.getLogger(Email.class.getName());
	public String logMessage = null;
	private String keyword = "Email";
	public String err1="No";
	public String confirmemaillink;
	//private WebDriver driver;
	private DynamicVarRepository dynamicVariables;
	
	public Email(DynamicVarRepository dynamicVariables) {
		this.dynamicVariables = dynamicVariables;
	}


	/**
	 * Email - Keyword & searchSubject - Action
	 * @method searchSubject
	 * @description - searchSubject action to Search Subject in  Unread emails
	 * 
	 * @throws Exception
	 */

	public String searchSubject(String emailData) throws  Exception {

		String[] emailArray = emailData.split(","); // Get all details from Test Data 
		String id = emailArray[0];
		String pwd = emailArray[1];
		String searchName = emailArray[2];
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message[] messages = null;
		ArrayList<Message> messagelist = new ArrayList<Message>();
		messages = folder.getMessages();
		//StringBuffer buffer = new StringBuffer();
		for(Message mail : messages){
			messagelist.add(mail); //Add all Unread mails to Message list
		}

		//Iterate throgh all Emails
		if(messagelist.size()!=0){
			for(Message mail : messagelist){
				String appSubjValue = mail.getSubject().trim(); //Get Subject of Emails
				if (appSubjValue.toLowerCase().trim().contains(searchName.toLowerCase().trim())){ //Check email if Subject Matched
					logMessage = "At test Step -"+ keyword + ", Action = "+ "searchSubject" +", Value from Application = " +appSubjValue + ", Expected Value = "+ searchName;
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					break;
				}else{
					err1 = "No match found in email";
					System.out.println(err1); // Print on Console
					//logging
					logger.info(err1);
					break;
				}
			}
		}else{
			err1 = "No Unread Message in email";
			//logging
			logger.error(err1);
			System.out.println(err1); // Print on Console

		}
		return err1; //Return messages
	}


	/**
	 * Email - Keyword & searchRecipient - Action
	 * @method searchRecipient
	 * @description - searchRecipient action to Search recipient in email.
	 * 
	 * @throws Exception
	 */
	public String searchRecipient(String emailData) throws  Exception {

		String[] emailArray = emailData.split(",");
		String id = emailArray[0];
		String pwd = emailArray[1];
		String searchReceipient = emailArray[2];
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		String appRecipientValue;
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message[] messages = null;
		ArrayList<Message> messagelist = new ArrayList<Message>();
		messages = folder.getMessages();
		//StringBuffer buffer = new StringBuffer();
		for(Message mail : messages){
			messagelist.add(mail); //Add all unread emails to List
		}
		boolean addressSearch=false;
		if(messagelist.size()!=0){
			for(Message mail : messagelist){
				Address[] recipients = mail.getRecipients(Message.RecipientType.TO); // Get all address in the TO list
				for (Address address : recipients) {
					appRecipientValue = address.toString(); //Get induvidaul email address
					if (appRecipientValue.toLowerCase().trim().contains(searchReceipient.toLowerCase().trim())){ //Verify each against expected one
						logMessage = "At test Step -"+ keyword + ", Action = "+ "searchRecipient" +", Value from Application = " +appRecipientValue + ", Expected Value = "+ searchReceipient;
						System.out.println(logMessage); // Print on Console
						//logging
						logger.info(logMessage);
						addressSearch=true;
						break; //End look once found value
					}
				}
				if (addressSearch==true){//coming out of look when TO address found for first unread message
					break;
				}else{
					err1 = "No match found in email for TO Address = "+searchReceipient;
					System.out.println(err1); // Print on Console
					//logging
					logger.info(err1);
					break;
				}
			}
		}else{
			err1 = "No Unread Message in email";
			System.out.println(err1); // Print on Console
			//logging
			logger.info(err1);

		}
		return err1; //Return message
	}

	/**
	 * Email - Keyword & searchBody - Action
	 * @method searchBody
	 * @description - searchBody action to Search recipient in email.
	 * 
	 * @throws Exception
	 */
	public String searchBody(String emailData) throws  Exception {

		String[] emailArray = emailData.split(",");
		String id = emailArray[0];
		String pwd = emailArray[1];
		String searchBody = emailArray[2];
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		//String appRecipientValue;
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message[] messages = null;
		ArrayList<Message> messagelist = new ArrayList<Message>();
		messages = folder.getMessages();
		//StringBuffer buffer = new StringBuffer();
		for(Message mail : messages){
			messagelist.add(mail); //Add all unread emails to Message list
		}
		boolean bodySearch=false;
		if(messagelist.size()!=0){
			for(Message mail : messagelist){
				String appBodyValue;
				BufferedReader reader = new BufferedReader(new InputStreamReader(mail.getInputStream())); //Get Body of Email
				while((appBodyValue = reader.readLine()) != null){  //Read each line and compare
					if (appBodyValue.trim().contains(searchBody.trim())){  
						logMessage = "At test Step -"+ keyword + ", Action = "+ "searchBody" +", Value from Application = " +appBodyValue + ", Expected Value = "+ searchBody;
						System.out.println(logMessage); // Print on Console
						//logging
						logger.info(logMessage);
						bodySearch=true;
						break; //Break loop once search found
					}
				}
				if(bodySearch == true){
					break;
				}
			}
			if(bodySearch != true){
				err1 = "No match found in email body = "+searchBody;
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
			}
		}else{
			err1 = "No Unread Message in email";
			System.out.println(err1); // Print on Console
			//logging
			logger.info(err1);

		}
		return err1; //return message
	}

	/**
	 * Email - Keyword & searchToSubjectBody - Action
	 * @method searchToSubjectBody
	 * @description - searchToSubjectBody action to Search recipient in email.
	 * 
	 * @throws Exception
	 */
	public String searchToSubjectBody(String emailData) throws  Exception {
		String[] emailArray = emailData.split(",");
		String id = emailArray[0];
		String pwd = emailArray[1];
		String searchTo = emailArray[2];
		String searchSubject = emailArray[3];
		String searchBody = emailArray[4];
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		String appRecipientValue;
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message[] messages = null;
		//ArrayList<Message> messagelist = new ArrayList<Message>();
		messages = folder.getMessages();
		//StringBuffer buffer = new StringBuffer();
		boolean subjectSearch = false;
		//for(Message mail : messages){
		//	messagelist.add(mail); //Add all unread emails to message list
		//}
		//if(messagelist.size()!=0){
		if(messages.length!=0){
			for (int msgIndex = (messages.length-1); msgIndex >= (messages.length - 100) ; msgIndex--){
				//for(Message mail : messagelist){
				Message mail = messages[msgIndex];
				String appSubjValue = mail.getSubject().trim();
				if (appSubjValue.toLowerCase().trim().contains(searchSubject.toLowerCase().trim())){
					logMessage = "At test Step -"+ keyword + ", Action = "+ "searchToSubjectBody" +", Value from Application = " +appSubjValue + ", Expected Value = "+ searchSubject;
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					subjectSearch=true;
					Address[] recipients = mail.getRecipients(Message.RecipientType.TO);
					for (Address address : recipients) {
						appRecipientValue = address.toString();
						if (appRecipientValue.toLowerCase().trim().contains(searchTo.toLowerCase().trim())){
							logMessage = "At test Step -"+ keyword + ", Action = "+ "searchToSubjectBody" +", Value from Application = " +appRecipientValue + ", Expected Value = "+ searchTo;
							System.out.println(logMessage); // Print on Console
							//logging
							logger.info(logMessage);
							break;
						}
					}
					String appBodyValue;
					BufferedReader reader = new BufferedReader(new InputStreamReader(mail.getInputStream()));
					while((appBodyValue = reader.readLine()) != null){
						if (appBodyValue.trim().contains(searchBody.trim())){
							logMessage = "At test Step -"+ keyword + ", Action = "+ "searchToSubjectBody" +", Value from Application = " +appBodyValue + ", Expected Value = "+ searchBody;
							System.out.println(logMessage); // Print on Console
							//logging
							logger.info(logMessage);
							break;
						}
					}
					break;
				}
				if(subjectSearch == true){
					break;
				}
			}
			if(subjectSearch!= true){
				err1 = "No matches found in email for "+emailData;
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
			}
		}else{
			err1 = "No Unread Message in email";
			System.out.println(err1); // Print on Console
			//logging
			logger.info(err1);
		}
		return err1;
	}


	/**
	 * Email - Keyword & getApplicationLink - Action
	 * @method getApplicationLink
	 * @description - getApplicationLink action to get Application link present in Email
	 * 
	 * @throws Exception
	 */	
	public String getApplicationLink(String emailData) throws  Exception
	{
		String[] emailArray = emailData.split(",");
		String id = emailArray[0];
		String pwd = emailArray[1];
		String searchTo = emailArray[2];
		String searchSubject = emailArray[3];
		String startSearchKey=emailArray[4];
		String endSearchKey=emailArray[5];
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		String appRecipientValue;
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message[] messages = null;
		messages = folder.getMessages();
		StringBuffer buffer = new StringBuffer();
		boolean subjectSearch = false;
		String appURLLink = "No";
		if(messages.length!=0){
			for (int msgIndex = (messages.length-1); msgIndex >=0 ; msgIndex--){
				Message mail = messages[msgIndex];
				String appSubjValue = mail.getSubject().trim();
				if (appSubjValue.toLowerCase().trim().contains(searchSubject.toLowerCase().trim())){
					logMessage = "At test Step -"+ keyword + ", Action = "+ "getApplicationLink" +", Value from Application for Subject of Email = " +appSubjValue + ", Expected Value of Subject in Email= "+ searchSubject;
					System.out.println(logMessage); // Print on Console
					logger.info(logMessage);
					subjectSearch=true;
					Address[] recipients = mail.getRecipients(Message.RecipientType.TO);
					for (Address address : recipients) {
						appRecipientValue = address.toString();
						if (appRecipientValue.toLowerCase().trim().contains(searchTo.toLowerCase().trim())){
							logMessage = "At test Step -"+ keyword + ", Action = "+ "getApplicationLink" +", Value from Application for Recipients = " +appRecipientValue + ", Expected Value of Recipients= "+ searchTo;
							System.out.println(logMessage); // Print on Console
							logger.info(logMessage);
							break;
						}
					}
					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader(mail.getInputStream()));
					while((line = reader.readLine()) != null){
						buffer.append(line);
					}
					String appURLLinkBody=buffer.substring(buffer.indexOf("Notification Email"));
					int startindex =  appURLLinkBody.indexOf(startSearchKey);
					int endindex = appURLLinkBody.indexOf(endSearchKey);
					appURLLink = appURLLinkBody.substring(startindex,endindex);
					appURLLink=appURLLink.replace(".com=/", ".com/");
					appURLLink=appURLLink.replace("=3D", "");
					appURLLink=appURLLink.replace("=", "");
					appURLLink=appURLLink.replace("?upn", "?upn=");
					logMessage = "At test Step -"+ keyword + ", Action = "+ "getApplicationLink" +", Application Link from Email = " +appURLLink;
					System.out.println(logMessage); // Print on Console
					logger.info(logMessage);
				}
				if(subjectSearch == true){
					break;
				}
			}
			if(appURLLink=="No"){
				appURLLink = "No match found in email ... Error in Getting Link from EMAIL, Pls Check";
				System.out.println(appURLLink);
				logger.info(appURLLink);
			}
		}
		return appURLLink;
	}

	/**
	 * Email - Keyword & getLink - Action
	 * @method getLink
	 * @description - getLink action to get link present in Email
	 * 
	 * @throws Exception
	 */	
	public String getLink(String id,String pwd,String subject,String searchName) throws  Exception
	{
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		System.out.println("Unread messages:"+ folder.getUnreadMessageCount());
		logger.info("Unread messages:"+ folder.getUnreadMessageCount());
		Message[] messages = null;
		ArrayList<Message> messagelist = new ArrayList<Message>();
		messages = folder.search(new BodyTerm(searchName),folder.getMessages());
		StringBuffer buffer = new StringBuffer();
		for(Message mail : messages){
			messagelist.add(mail);
		}
		if(messagelist.size()!=0){
			for(Message mail : messagelist){
				String line;
				BufferedReader reader = new BufferedReader(new InputStreamReader(mail.getInputStream()));
				while((line = reader.readLine()) != null){
					buffer.append(line);
				}
				int test = 0;
				Pattern p = Pattern.compile("[a-z?a-z]+=[0-9a-zA-Z&=]+[=]");
				Matcher m = p.matcher(buffer);
				while(m.find()){
					test = 1;
					confirmemaillink = "https://perf-aaa.modria.com/"+m.group();
				}
				if(test == 0)
				{
					String errMain = "No match found in email";
					System.out.println(errMain);
					logger.error(errMain);
				}
			}
		}
		return confirmemaillink;
	}	

	/**
	 * Email - Keyword & getLink - Action
	 * @method getLink
	 * @description - getLink action to get link present in Email
	 * 
	 * @throws Exception
	 */	
	public boolean isEmailTriggered(String searchemail,String id,String pwd,String subject) throws Exception{
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		boolean emailTriggered = false;
		Message[] messages = null;
		messages = folder.search(new RecipientStringTerm(Message.RecipientType.TO, searchemail));
		for(Message mail : messages)
		{
			if(!(mail.isSet(Flags.Flag.SEEN))&&((mail.getSubject()).equals(subject)))
			{
				emailTriggered = true;
				break;
			}
		}
		return emailTriggered;
	}

	/**
	 * Email - Keyword & getCaseNumber - Action
	 * @method getCaseNumber
	 * @description - getCaseNumber action to get link present in Email
	 * 
	 * @throws Exception
	 */	
	public String getCaseNumber(String searchemail,String id,String pwd,String subject) throws Exception{
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		System.out.println("Unread messages:"+ folder.getUnreadMessageCount());
		logger.info("Unread messages:"+ folder.getUnreadMessageCount());
		String CaseId =null;
		Message[] messages = null;
		ArrayList<Message> messagelist = new ArrayList<Message>();
		messages = folder.search(new RecipientStringTerm(Message.RecipientType.TO, searchemail));
		StringBuffer buffer = new StringBuffer();
		for(Message mail : messages)
		{
			if(!(mail.isSet(Flags.Flag.SEEN))&&((mail.getSubject()).equals(subject)))
			{
				messagelist.add(mail);
			}
		}
		if(messagelist.size()!=0){
			for(Message mail : messagelist){
				String line;
				BufferedReader reader = new BufferedReader(new InputStreamReader(mail.getInputStream()));
				while((line = reader.readLine()) != null){
					buffer.append(line);
				}	
				Pattern P = Pattern.compile("\\d{2}-\\d{2}-\\d{4}-\\d{4}");
				Matcher m = P.matcher(buffer);
				while(m.find()){
					CaseId = m.group();
				}
			}
		}
		else{
			String errMain = "No mail found";
			System.out.println(errMain);
			logger.error(errMain);
		}
		return CaseId;
	}
	/**
	 * Email - Keyword & setPasswordfromemail - Action
	 * @method setpassword
	 * @description - setpassword from email for random emails
	 * 
	 * @throws Exception
	 */

	public String setPassword(String emailData,String tcinput_variables) throws  Exception {
		String[] emailArray = emailData.split(",");
		//String id = emailArray[0];
		String subjectExpected = emailArray[1].trim();
		
		try{
			//FirefoxDriver driver = new FirefoxDriver();
			//FirefoxProfile ffprofile = new ProfilesIni().getProfile("default");
			FirefoxProfile ffprofile = new ProfilesIni().getProfile("default");
			ffprofile.setPreference("browser.download.folderList",2);
			ffprofile.setPreference("browser.download.manager.showWhenStarting",false);
			ffprofile.setPreference("browser.download.dir",Global.getDownloadsPath());
			ffprofile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,application/zip,application/octet-stream");
			FirefoxDriver driver = new FirefoxDriver(ffprofile);
			
			//HtmlUnitDriver driver = new HtmlUnitDriver(true);
		
		driver.get("http://mailnesia.com/mailbox/"+emailArray[0]);
		Thread.sleep(2000);
		boolean clickicon = false;
		WebElement emailsubject = null;
		try{
			emailsubject=driver.findElement(By.partialLinkText("Uit elkaar"));
			emailsubject.click();
			}catch(Exception e){
				clickicon = true;
			}
			//String subjectText=emailsubject.getText();
			if(clickicon){
				driver.findElement(By.xpath("//a[contains(@onclick,'event.stopPropagation()')]")).click();
			}
		
		
		
		
		Thread.sleep(5000);
		//driver.switchTo().frame(driver.findElement(By.id("messageframe")));
		String setpasslink=driver.findElement(By.linkText("Set password")).getAttribute("href");
		System.out.println(setpasslink);
		logger.info(setpasslink);
		String[] setpasstoken=setpasslink.split("=");
		System.out.println("token is:"+setpasstoken[1]);
		logger.info("token is:"+setpasstoken[1]);
		if (tcinput_variables.equalsIgnoreCase("#SETPASSWORDTOKEN#")){
			System.out.println("enter into hashmap "+tcinput_variables);
			logger.info("enter into hashmap "+tcinput_variables);
			dynamicVariables.addObjectToMap(tcinput_variables, setpasstoken[1]);
			System.out.println(dynamicVariables.getObjectProperty(tcinput_variables));
			logger.info(dynamicVariables.getObjectProperty(tcinput_variables));
			
		}
		
		/*driver.get(setpasslink);
		Thread.sleep(2000);
		driver.findElement(By.id("password")).sendKeys("test");
		driver.findElement(By.id("confirm-password")).sendKeys("test");
		driver.findElement(By.xpath(".//*[@id='set_password_form']/div[3]/button")).click();*/
		boolean resetpass = driver.findElement(By.linkText("Set password")).isDisplayed();
		if(resetpass){
			logMessage = "At test Step - Keyword = "+keyword+", Action = setpasswordfromemail"+" , subject = " + subjectExpected.toString();
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		}
		else{
			err1 = "At test Step - Keyword = "+keyword+", Action = setpasswordfromemail"+" , subject = " + subjectExpected.toString();
			System.out.println(err1); // Print on Console // Print on Console
			//logging
			logger.error(err1);
			//Log.info(err1);
			}
		driver.close();
		}
		catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+"setpasswordfromemail"+" , Locator = " + subjectExpected.toString();
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			//logging
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		
		return err1;
	}


	/**
	 * Email - Keyword & getPasswordResetLink - Action
	 * @method getPasswordResetLink
	 * @description - getPasswordResetLink action to get passwordResetlink in Email
	 * 
	 * @throws Exception
	 */	
	public String getPasswordResetLink(String emailData) throws  Exception
	{
		String[] emailArray = emailData.split(",");
		String id = emailArray[0];
		String pwd = emailArray[1].trim();
		String searchTo = emailArray[2];
		String searchSubject = emailArray[3];
		String startSearchKey=emailArray[4];
		String endSearchKey=emailArray[5];
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol","imaps");
		Session session = Session.getDefaultInstance(props,null);
		Store store = session.getStore("imaps");
		String appRecipientValue;
		store.connect("imap.gmail.com",id,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message[] messages = null;
		messages = folder.getMessages();
		StringBuffer buffer = new StringBuffer();
		boolean subjectSearch = false;
		String appURLLink = "No";
		if(messages.length!=0){
			for (int msgIndex = (messages.length-1); msgIndex >=0 ; msgIndex--){
				Message mail = messages[msgIndex];
				String appSubjValue = mail.getSubject().trim();
				if (appSubjValue.toLowerCase().trim().contains(searchSubject.toLowerCase().trim())){
					logMessage = "At test Step -"+ keyword + ", Action = "+ "getApplicationLink" +", Value from Application for Subject of Email = " +appSubjValue + ", Expected Value of Subject in Email= "+ searchSubject;
					System.out.println(logMessage); // Print on Console
					logger.info(logMessage);
					subjectSearch=true;
					Address[] recipients = mail.getRecipients(Message.RecipientType.TO);
					for (Address address : recipients) {
						appRecipientValue = address.toString();
						if (appRecipientValue.toLowerCase().trim().contains(searchTo.toLowerCase().trim())){
							logMessage = "At test Step -"+ keyword + ", Action = "+ "getApplicationLink" +", Value from Application for Recipients = " +appRecipientValue + ", Expected Value of Recipients= "+ searchTo;
							System.out.println(logMessage); // Print on Console
							logger.info(logMessage);
							break;
						}
					}
					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader(mail.getInputStream()));
					while((line = reader.readLine()) != null){
						buffer.append(line);
					}
					String appURLLinkBody=buffer.substring(0);
					int startindex =  appURLLinkBody.indexOf(startSearchKey);
					int endindex = appURLLinkBody.indexOf(endSearchKey);
					appURLLink = appURLLinkBody.substring(startindex + startSearchKey.length(),endindex);
					logMessage = "At test Step -"+ keyword + ", Action = "+ "getApplicationLink" +", Application Link from Email = " +appURLLink;
					System.out.println(logMessage); // Print on Console
					logger.info(logMessage);
				}
				if(subjectSearch == true){
					break;
				}
			}
			if(appURLLink=="No"){
				appURLLink = "No match found in email ... Error in Getting Link from EMAIL, Pls Check";
				System.out.println(appURLLink);
				logger.error(appURLLink);
			}
		}
		return appURLLink;
	}

}
