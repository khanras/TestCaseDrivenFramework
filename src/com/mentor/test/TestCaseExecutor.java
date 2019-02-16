package com.mentor.test;

import java.io.IOException;
import java.util.List;

import com.mentor.test.reader.TestCaseReader;
import com.mentor.test.utils.Utilities;

import dataReader.TestConfigurationReader;

public class TestCaseExecutor {
	
	private TestCaseReader testCaseReader=null;
	private WebDriverOperator webDriverOperator = null;
	private TestConfigurationReader testConfigurationReader=null;
	
	public TestCaseExecutor() throws Exception {
		try {
			testCaseReader = new TestCaseReader(Utilities.readProperty("xlsPath"));
			webDriverOperator = new WebDriverOperator(Utilities.readProperty("URL"),Utilities.readProperty("webDriverPath"),Utilities.readProperty("xlsPath"));
			testConfigurationReader=new TestConfigurationReader(Utilities.readProperty("pomPath"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		executeAllTestCases();
	}
	
	public void executeAllTestCases() {
		List<String> sheets = testCaseReader.getAllTestSheetNames();
		sheets.forEach(sheet->execute(sheet));
	}
	
	
	private void execute(String sheetName) {
	
		
        List<TestMetaInfo> list = testCaseReader.getAllTestMetaInfo(sheetName);
        for(int i=0;i<list.size(); i++) {
        	TestMetaInfo  testMetaInfo = list.get(i);
        	ActionType actionType = testMetaInfo.getActionType();
        	switch(actionType)
        	{
        		case CLICK:
        			webDriverOperator.clickBy(testMetaInfo,testConfigurationReader);
        			break;
        		case SENDKEY:
        			webDriverOperator.sendKeysBy(testMetaInfo,testConfigurationReader);
        			break;
        		case WAITUNTILCLICK:
        			webDriverOperator.waitToBeClickableBy(testMetaInfo,testConfigurationReader);
        			break;
        		case QUIT:
        			webDriverOperator.tearDown();
        			break;
        		default:
        			assert false:"Invalid action type";
        			break;
        	}
        }
	}

	/*private void action_Click(TestMetaInfo testMetaInfo) {
		//LocatorType locatorType=testMetaInfo.getLocatorType();
		//String locatorKey=testMetaInfo.getLocatorKey();
		//LocatorType locatorType=testConfigurationReader.getPOMType(locatorKey);
		webDriverOperator.clickBy(testMetaInfo,testConfigurationReader);
	}

	private void action_WaitUntilClick(TestMetaInfo testMetaInfo, WebDriverOperator webDriverOperator) {
		LocatorType locatorType=testMetaInfo.getLocatorType();
		String locatorKey=testMetaInfo.getData();
		String locatorType=testConfigurationReader.getPOMType(locatorKey);
		webDriverOperator.waitToBeClickableBy(locatorType,locatorType);
	}


	private void action_SendKey(TestMetaInfo testMetaInfo, WebDriverOperator webDriverOperator) {
		LocatorType locatorType=testMetaInfo.getLocatorType();
		String locatorKey=testMetaInfo.getData();
		String locatorType=testConfigurationReader.getPOMType(locatorKey);
		String data=testConfigurationReader.getPOMValue(locatorKey);
		webDriverOperator.sendKeysBy(locatorType,locatorType,data);
	}*/
	
	public static void main(String[] args) throws Exception {
		new TestCaseExecutor();
	}
	
}
