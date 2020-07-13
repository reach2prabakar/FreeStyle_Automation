package com.client.automation.keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
/*import org.jsoup.Jsoup;
 import org.jsoup.nodes.Document;
 import org.jsoup.nodes.Element;*/
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Lists {

	private static Logger logger = Logger.getLogger(Lists.class.getName());
	public String logMessage = null;
	private String keyword = "Dropdown";
	public String err1 = "No";
	public WebDriver driver;
	private boolean hasviewMore = false;
	public WebElement listelement = null;
	public By dropdownby = null;
	public String objprop = null;
	private HashMap<String, String> listMap = new HashMap<>();

	public Lists(WebDriver driver) {
		this.driver = driver;
		// addDropdownsToMap();
	}

	public String getalllistvalues(By dropdownName, String objproperty)
			throws Exception {

		err1 = "No";
		this.dropdownby = dropdownName;
		new Wait(driver).waitForPageLoad();
		WebElement listele = null;
		try {
			System.out.println(dropdownName.toString());
			System.out.println(objproperty);
			listele = driver.findElement(dropdownName);
		} catch (Exception e) {
			err1 = "At test step - Keyword = Lists , Action = getalllistvalues  error in geting the list values line number "
					+ Thread.currentThread().getStackTrace()[1].getLineNumber();
			System.out.println(err1);
			// logging
			logger.error(err1);
		}
		try {
			setlistElement(listele, objproperty);
		} catch (Exception e) {
			err1 = "At test step - Keyword = Lists , Action = getalllistvalues  error in geting the list values line number "
					+ Thread.currentThread().getStackTrace()[1].getLineNumber();
			System.out.println(err1);
			// logging
			logger.error(err1);
		}

		return err1;

	}

	public String clickListItem(String listItemName) throws Exception {

		try {
			err1 = "No";
			new Wait(driver).waitForPageLoad();
			// WebElement listele = driver.findElement(dropdownName);
			// setlistElement(listele,objproperty);
			err1 = clickItems(listItemName);
			return err1;
		} catch (Exception e) {
			err1 = "At test step - Keyword = Lists , Action = clickListItem  error in click list values";
			System.out.println(err1);
			// logging
			logger.error(err1);
			return err1;
		}

	}

	public List<String> getListvalues() {
		ArrayList<String> arrlist = new ArrayList<>();
		System.out.println("hasviewMore in getlistvalue:" + hasviewMore);
		getlistElement();

		if (hasviewMore) {
			//System.out.println("listelement :" + listelement);
			WebElement viewmoreactive = listelement.findElement(By
					.tagName("span"));
			System.out.println("viewmore :"
					+ viewmoreactive.getAttribute("style"));
			if (viewmoreactive.getAttribute("style").equalsIgnoreCase(
					"display: none;")) {
				java.util.List<WebElement> lst = listelement.findElements(By
						.tagName("li"));
				for (WebElement lstvalue : lst) {
					System.out.println("lstvalue.getText() :"
							+ lstvalue.findElement(By.tagName("a")).getText());
					arrlist.add(lstvalue.getText());
				}
			} else {
				// listelement.click();

				viewmoreactive.click();
				java.util.List<WebElement> lst = listelement.findElements(By
						.tagName("li"));
				for (WebElement lstvalue : lst) {
					String listtext = lstvalue.findElement(By.tagName("a"))
							.getText();
					System.out.println("lstvalue.getText() :" + listtext);
					if (listtext.contains("..")) {
						String listtextwithoutdot = lstvalue.findElement(
								By.tagName("a")).getAttribute("title");
						arrlist.add(listtextwithoutdot);
					} else {
						arrlist.add(lstvalue.getText());
					}
				}
			}
		} else {
			boolean generallist = true;
			List<WebElement> lst = null;
			try {
				lst = listelement.findElements(By.tagName("li"));
			//	System.out.println("lst: " + lst.size());
				for (WebElement lstvalue : lst) {
					System.out.println("lstvalue.getText() :"
							+ lstvalue.findElement(By.tagName("a")).getText());
					arrlist.add(lstvalue.findElement(By.tagName("a")).getText());
				}
				if (lst.size() > 0) {
					generallist = false;
				}
			} catch (Exception e) {
				System.out
						.println("Tagname li is not found , moving to general list");
			}
			//System.out.println("generallist "+generallist);
			if (generallist) {
				try {
					lst = driver.findElements(dropdownby);
				} catch (Exception e) {
					err1 = "Error in getListvalues line number "
							+ Thread.currentThread().getStackTrace()[1]
									.getLineNumber();
					System.out.println(err1);
				}
				for (WebElement lstvalue : lst) {
					//System.out.println("lstvalue.getText() :+ lstvalue.getText());
					arrlist.add(lstvalue.getText());
				}
			}

		}
	//	System.out.println("Arraylist is :" + arrlist);
		return arrlist;
	}

	public String clickItems(String selectitem) {
		try {
			System.out.println("enter into clickitems");
			boolean flag = false;
			WebElement element = driver.findElement(By
					.cssSelector("#actionsMenu .viewMoreMenus"));
			System.out.println("viewmore object value is" + element.getText());
			if (element.getText().equalsIgnoreCase("view more")) {
				element.click();
				flag = true;
				Thread.sleep(1000);
			}

			if (flag) {
				WebElement lst = driver.findElement(By
						.partialLinkText(selectitem));
				lst.click();
				flag = true;
				System.out.println("flag is " + flag);

			} else {

				WebElement lst = driver.findElement(By
						.partialLinkText(selectitem));
				lst.click();
				flag = true;
				System.out.println("flag is " + flag);

			}
			if (flag != true) {
				err1 = "******** Error occoured in test step *********: "
						+ keyword + ", Action - " + "clicklistitem"
						+ " - not present please check..... Locator : "
						+ selectitem; // Preparing Error message when unable to
										// perform action
				System.out.println(err1); // Print on Console
				logger.info(err1);

			}
			// System.out.println("Arraylist is :"+arrlist);
			// return arrlist;
			return err1;
		} catch (Exception e) {
			err1 = "******** Error occoured in test step *********: " + keyword
					+ ", Action - " + "clicklistitem"
					+ " - not present please check..... Locator : "
					+ selectitem; // Preparing Error message when unable to
									// perform action
			System.out.println(err1); // Print on Console
			// logging
			logger.error(err1);
			// Log.error(err1); //Print on Log
			return err1;
		}
	}

	public void setlistElement(WebElement listele, String objproperty) {
		this.objprop = objproperty;
		if (listele.getText().trim().equalsIgnoreCase("Actions")) {
			System.out.println("Setting list element :" + listelement);
			try {
				this.listelement = driver.findElement(By
						.cssSelector(objproperty + "+ul"));
			} catch (Exception e) {
				err1 = "At test step - Keyword = Lists , Action = getalllistvalues  method -setlistElement  error in adding ul to the object property";
				System.out.println(err1);
			}
			System.out.println("Setting list element :" + listelement);
		} else {
			System.out.println("Setting list element without action :"
					+ listelement);
			this.listelement = listele;

		}

		sethasviewMore(objproperty);
	}

	public WebElement getlistElement() {

		return listelement;
	}

	private boolean sethasviewMore(String objproperty) {
		// commented for future purpose
		/*
		 * String html = driver.getPageSource();
		 * System.out.println("html:"+html); Document doc = Jsoup.parse(html);
		 * doc.getAllElements(); String cuthtml =
		 * listelement.getAttribute("outerHTML");
		 * 
		 * System.out.println("element :"+doc.after(cuthtml));
		 * System.out.println("listel :"+listelement);
		 * System.out.println("imporatant :"
		 * +listelement.getAttribute("outerHTML"));
		 */
		WebElement hasmore = null;
		try {
			// hasmore =
			// driver.findElement(By.cssSelector(listelement.toString()+"+ul>span:not([style*=display]):not([style*=none])"));

			hasmore = listelement.findElement(By.tagName("span"));
		} catch (Exception e) {
			//err1 = "At test step - Keyword = Lists , Action = getalllistvalues  error in finding the tag name of hasviewMore link ";
			System.out.println("hashmore link is returned as null");
		}
		

		hasviewMore = hasmore != null ? true : false;

		System.out.println("hasviewmore :" + hasviewMore);
		return hasviewMore;

	}

	/*
	 * public void getcssfromjsoup(WebElement ele,By by){
	 * 
	 * String html = driver.getPageSource(); Document doc = Jsoup.parse(html);
	 * String cuthtml = ele.getAttribute("outerHTML");
	 * System.out.println("element :"+doc.after(cuthtml));
	 * 
	 * 
	 * } public String getAbsoluteXPath(WebElement element) { return (String)
	 * ((JavascriptExecutor) driver).executeScript(
	 * "function absoluteXPath(element) {"+ "var comp, comps = [];"+
	 * "var parent = null;"+ "var xpath = '';"+
	 * "var getPos = function(element) {"+ "var position = 1, curNode;"+
	 * "if (element.nodeType == Node.ATTRIBUTE_NODE) {"+ "return null;"+ "}"+
	 * "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {"
	 * + "if (curNode.nodeName == element.nodeName) {"+ "++position;"+ "}"+ "}"+
	 * "return position;"+ "};"+
	 * 
	 * "if (element instanceof Document) {"+ "return '/';"+ "}"+
	 * 
	 * "for (; element && !(element instanceof Document); element = element.nodeType == Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {"
	 * + "comp = comps[comps.length] = {};"+ "switch (element.nodeType) {"+
	 * "case Node.TEXT_NODE:"+ "comp.name = 'text()';"+ "break;"+
	 * "case Node.ATTRIBUTE_NODE:"+ "comp.name = '@' + element.nodeName;"+
	 * "break;"+ "case Node.PROCESSING_INSTRUCTION_NODE:"+
	 * "comp.name = 'processing-instruction()';"+ "break;"+
	 * "case Node.COMMENT_NODE:"+ "comp.name = 'comment()';"+ "break;"+
	 * "case Node.ELEMENT_NODE:"+ "comp.name = element.nodeName;"+ "break;"+
	 * "}"+ "comp.position = getPos(element);"+ "}"+
	 * 
	 * "for (var i = comps.length - 1; i >= 0; i--) {"+ "comp = comps[i];"+
	 * "xpath += '/' + comp.name.toLowerCase();"+
	 * "if (comp.position !== null) {"+ "xpath += '[' + comp.position + ']';"+
	 * "}"+ "}"+
	 * 
	 * "return xpath;"+
	 * 
	 * "} return absoluteXPath(arguments[0]);", element); }
	 */

}
