package com.mentor.test;

public enum LocatorType {

	xpath("xpath"),
	id("id"),
	name("name"),
	className("className"),
	tagName("tagName"),
	linkText("linkText"),
	partialLinkText("partialLinkText"),
	cssSelector("cssSelector");

    String locator;

    LocatorType (String locator) {
        this. locator = locator;    
    }
    
    public String getLocatorType() {
        return locator;
    }
    
    public static LocatorType getLocatorTypebyLocator(String locator) {
    	for(LocatorType acc:values()) {
    		if(acc.getLocatorType().equals(locator)) {
    			return acc;
    		}
    	}
    	return null;
    }
}
