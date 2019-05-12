package com.summer.isnow.dto;

import java.io.Serializable;

public class ArgumentInvalidResult implements Serializable{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String field;  
    private Object rejectedValue;  
    private String defaultMessage;  
  
    public String getField() {  
        return field;  
    }  
    public void setField(String field) {  
        this.field = field;  
    }  
    public Object getRejectedValue() {  
        return rejectedValue;  
    }  
    public void setRejectedValue(Object rejectedValue) {  
        this.rejectedValue = rejectedValue;  
    }  
    public String getDefaultMessage() {  
        return defaultMessage;  
    }  
    public void setDefaultMessage(String defaultMessage) {  
        this.defaultMessage = defaultMessage;  
    }  
}  
