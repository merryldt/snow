package com.summer.icommon.exception;


import com.summer.icommon.utils.ResponseCode;

public class RequestParameterErrorException extends GeneralException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RequestParameterErrorException() {
		super(ResponseCode.REQUEST_PARAMETER_ERROR);
	}
	
	public RequestParameterErrorException(Throwable ex) {
		super(ResponseCode.REQUEST_PARAMETER_ERROR, ex);
	}

}
