package com.mentor.test;

public class TestMetaInfo {

	
	private Integer sequence;
	//private String parentSheetName;
	private ActionType actionType;
	private String locatorKey;
	//private String locatorValue;
	private String testData;
	//private String extra1;
	//private String extra2;
	//private String extra3;
	
	public TestMetaInfo(Integer sequence, ActionType actionType, String locatorKey, String testData) {
		super();
		this.sequence = sequence;
		//this.parentSheetName = parentSheetName;
		this.actionType = actionType;
		this.locatorKey = locatorKey;
		//this.locatorValue = locatorValue;
		this.testData = testData;
		//this.extra1 = extra1;
		//this.extra2 = extra2;
		//this.extra3 = extra3;
	}
	
	
	public Integer getSequence() {
		return sequence;
	}

	

	public ActionType getActionType() {
		return actionType;
	}

	public String getLocatorKey() {
		return locatorKey;
	}

	public String getData() {
		return testData;
	}
	
	/*public String getLocatorValue() {
		return locatorValue;
	}
	
	public String getParentSheetName() {
		return parentSheetName;
	}
	
	public String getExtra1() {
		return extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public String getExtra3() {
		return extra3;
	}*/
}
