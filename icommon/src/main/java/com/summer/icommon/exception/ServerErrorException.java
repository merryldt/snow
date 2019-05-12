package com.summer.icommon.exception;

import com.summer.icommon.utils.ResponseCode;

public class ServerErrorException extends GeneralException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerErrorException() {
		super(ResponseCode.SERVER_ERROR);
	}
	
	public ServerErrorException(Throwable ex) {
		super(ResponseCode.SERVER_ERROR, ex);
	}
}
