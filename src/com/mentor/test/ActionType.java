package com.mentor.test;

public enum ActionType {

	CLICK("click"),
    SENDKEY("sendkey"),
	GETTEXT("gettext"),
	WAITUNTILVISIBILE("waittobevisibile"),
	WAITUNTILCLICK("waittobeclickable"),
	SCREENSHOOT("screenshot"),
	QUIT("quit");

    String actionMsg;

    ActionType (String actionMsg) {
        this. actionMsg = actionMsg;    
    }
    
    public String getActionMsg() {
        return actionMsg;
    }
    
    public static ActionType getTypeByActionMsg(String actionMsg) {
    	for(ActionType acc:values()) {
    		if(acc.getActionMsg().equals(actionMsg)) {
    			return acc;
    		}
    	}
    	return null;
    }

}
