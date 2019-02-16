package com.mentor.test;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dataReader.TestConfigurationReader;


public class WebDriverOperator {

	/*private String outputDir = "D:/Tests/snaps/";
	private List<String> screenShots = new ArrayList<>();
	private List<String> messages = new ArrayList<>();*/
	private WebDriver dv;
	private WebDriverWait wait = null;
	
	public WebDriverOperator(String URL, String webDriverPath, String xlsPath) {
		System.setProperty("webdriver.chrome.driver", webDriverPath);
		dv = new ChromeDriver();
		dv.manage().window().maximize();
		dv.get(URL);
		wait = new WebDriverWait(getWebDriver(), 30);
	}
	
	public WebDriver getWebDriver() {
		return dv;
	}
	
	/*public void addToScreenShots(String file) {
		screenShots.add(file);
	}

	public void addToMessages(String message) {
		messages.add(message);
	}

	public void OpenBrowser(String URL) {

	}

	public void success() {
		System.out.println("Test case ran successfully");
		screenShots.stream().forEach(file -> {
			new File(file).deleteOnExit();
		});
	}

	public void failed() {
		messages.stream().forEach(System.out::println);
		System.out.println("File screen shots here " + outputDir);
	}

	public WebDriverOperator takeScreenshot() {
		try {
			TakesScreenshot ts = (TakesScreenshot) dv;
			File scrFile = ts.getScreenshotAs(OutputType.FILE);
			//String timestamp = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss").format(new Date());
			File output = new File(outputDir + "Snap" + "_" + screenShots.size() + ".png");
			FileUtils.copyFile(scrFile, output);
			addToScreenShots(output.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot: " + e.getMessage());
		}
		return this;
	}
	
	public WebDriverOperator takeScreenshot(String elementId) {
		try {
			waitVisibilityOfElement( elementId);
			takeScreenshot();
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot: " + e.getMessage());
		}
		return this;
	}
	
	public WebDriverOperator waitVisibilityOfElement(String elementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementId)));
		return this;
	}
	
	public WebDriverOperator waitToBeClickableByXPath(String elementId) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementId)));
		return this;
	}
	
	public WebDriverOperator waitById(String elementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
		return this;
	}
	
	public WebDriverOperator clickByXPath(String xpath) {
		getWebDriver().findElement(By.xpath(xpath)).click();
		return this;
	}

	public WebDriverOperator clickByID(String id) {
		getWebDriver().findElement(By.id(id)).click();
		return this;
	}
	
	public WebDriverOperator clearById(String elementId) {
		getWebDriver().findElement(By.id(elementId)).clear();
		return this;
	}
	
	public WebDriverOperator selectDropdownByID(String id,String text) {
		clickByID(id);
		Select dropdown = new Select(getWebDriver().findElement(By.id(id)));
		dropdown.selectByVisibleText(text);
		return this;
	}

	public WebDriverOperator sendKeysById(String elementId, String value) {
		getWebDriver().findElement(By.id(elementId)).sendKeys(value);
		return this;
	}
	
	public WebDriverOperator sendKeysByXPath(String elementId, String value) {
		getWebDriver().findElement(By.xpath(elementId)).sendKeys(value);
		return this;
	}
	
	public TestLinkTestErrorReporter sendKeysByXPathByKey(String key) {
		TestKey data = testData.getData(key);
		return sendKeysByXPath(data.getValue(), data.getType());
	}
	
	public String getTextByXPath(String xpath) {
		return getWebDriver().findElement(By.xpath(xpath)).getText();
	}
	
	public void setTestDataSheetName(String testDataSheetName) {
		testData.setDataSheetName(testDataSheetName);
	}*/
	
	/*================================================================================================================*/
	
	public List<String> getLocatorTypeValueData(TestMetaInfo testMetaInfo, TestConfigurationReader testConfigurationReader) {
		List<String> list =new ArrayList<>();
		String locatorType= testConfigurationReader.getPOMType(testMetaInfo.getLocatorKey());
		list.add(locatorType);
		String locatorValue=testConfigurationReader.getPOMValue(testMetaInfo.getLocatorKey());
		list.add(locatorValue);
		if(testMetaInfo.getData()!=null) {
			String sendkeyData=testConfigurationReader.getPOMType("TestData",testMetaInfo.getData());
			list.add(sendkeyData);
		}
		return list;
	}
	public List<String> getLocatorTypeValueData(TestMetaInfo testMetaInfo, TestConfigurationReader testConfigurationReader,String sheetName) {
		String locatorType= testConfigurationReader.getPOMType(sheetName,testMetaInfo.getLocatorKey());
		String locatorValue=testConfigurationReader.getPOMValue(sheetName,testMetaInfo.getLocatorKey());
		List<String> list =new ArrayList<>();
		list.add(locatorType);
		list.add(locatorValue);
		return list;	
	}


	
	/* Common method for creating By object */

	private By GetBy(String locatorType,String locatorValue) {
		switch(locatorType)
    	{
    		case "xpath":
    			return By.xpath(locatorValue);
    		case "id":
    			return By.id(locatorValue);
    		case "name":
    			return By.name(locatorValue);
    		case "tagname":
    			return By.tagName(locatorValue);
    		case "linktext":
    			return By.linkText(locatorValue);
    		case "partialLinkText":
    			return By.partialLinkText(locatorValue);
    		case "classname":
    			return By.className(locatorValue);
    		default:
    			assert false:"Invalid Locator Type";
    			break;
    	}
		return null;
	}

	/* Common method for different type of wait */
	
	public void waitToBeClickableBy(TestMetaInfo testMetaInfo,TestConfigurationReader testConfigurationReader) {
		List<String> list=getLocatorTypeValueData(testMetaInfo,testConfigurationReader);
		By by = GetBy(list.get(0), list.get(1));
		//logger.info("Waiting until the "+"*"+POMKey+"*"+" is getting clickable.");
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public WebDriverOperator implicitWait(int timeInMilliSec) throws Exception {
		//logger.info("Waiting for: "+timeInMilliSec+"MilliSec.");
		Thread.sleep(timeInMilliSec);
		return this;
	}

	/* Common method for click */
	
	public void clickBy(TestMetaInfo testMetaInfo,TestConfigurationReader testConfigurationReader) {
		List<String> list=getLocatorTypeValueData(testMetaInfo,testConfigurationReader);
		By by = GetBy(list.get(0), list.get(1));
		dv.findElement(by).click();
	}
	
	
	/*Common method to send a string value for a field */

	public void sendKeysBy(TestMetaInfo testMetaInfo,TestConfigurationReader testConfigurationReader) {
		List<String> list=getLocatorTypeValueData(testMetaInfo,testConfigurationReader);
		By by = GetBy(list.get(0), list.get(1));
		dv.findElement(by).sendKeys(list.get(2));
	}

	/* Common method to clear a field */

	public void clearById(TestMetaInfo testMetaInfo,TestConfigurationReader testConfigurationReader) {
		List<String> list=getLocatorTypeValueData(testMetaInfo,testConfigurationReader);
		By by = GetBy(list.get(0), list.get(1));
		dv.findElement(by).clear();
	}

	public void tearDown() {
		dv.quit();
	}
	
	/* Common method to select a drop-down value of a field 

	public TestExecutor selectDropdownBy(String POMKey, String text) {
		clickBy(POMKey);
		By by = GetBy(POMKey);
		Select dropdown = new Select(dv.findElement(by));
		dropdown.selectByVisibleText(text);
		return this;
	}

	 

	 Common method to get a string value from a field 

	public String getTextBy(String POMKey) {
		By by = GetBy(POMKey);
		return dv.findElement(by).getText();
	}

	 Common method to verify page title 

	public TestExecutor verifyTitle(String expectedTitle) {
		String value = testData.getData(StaticData.pageTitle, expectedTitle);
		Assert.assertEquals(dv.getTitle(), value);
		logger.info("*"+dv.getTitle()+"*"+": Page title has been verified.");
		return this;
	}

	 Common method to take screenshot 

	public void takeScreenshot(String screenshotPath, String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) dv;
			File scrFile = ts.getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
			String path = testData.getData(StaticData.suitData, screenshotPath);
			StaticData.fileName = path + screenshotName + "_" + timestamp + ".png";
			FileUtils.copyFile(scrFile, new File(StaticData.fileName));
			if(!screenshotPath.equals("ScreenshotPath"))
				logger.info("Sacrrenshot of "+"*"+dv.getTitle()+"*"+" page has been captured.");
		} catch (Exception e) {
			logger.error("Exception while taking screenshot: " + e.getMessage());
		}
	}*/
}
