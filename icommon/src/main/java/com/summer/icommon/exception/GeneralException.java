package com.summer.icommon.exception;

import com.summer.icommon.utils.ResponseCode;

public class GeneralException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResponseCode responseCode;

	public GeneralException() {
		// TODO Auto-generated constructor stub
	}

	public GeneralException(String errorMsg) {
		super(errorMsg);
	}
	
	public GeneralException(ResponseCode responseCode, Throwable ex) {
		super(responseCode.getMsg(), ex);
		this.setResponseCode(responseCode);
	}

	public GeneralException(ResponseCode responseCode) {
		super(responseCode.getMsg());
		this.setResponseCode(responseCode);
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	

}
