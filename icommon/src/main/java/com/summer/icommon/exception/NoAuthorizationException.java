package com.summer.icommon.exception;


import com.summer.icommon.utils.ResponseCode;

public class NoAuthorizationException extends GeneralException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoAuthorizationException() {
		super(ResponseCode.PERMISSION_DENIED);
	}
	
	public NoAuthorizationException(Throwable ex) {
		super(ResponseCode.PERMISSION_DENIED, ex);
	}
}
