package com.summer.icommon.exception;

import com.summer.icommon.utils.ResponseCode;

/**
 * 自定义非检查性异常
 */
public class GeneralException extends RuntimeException {

	/**
	 * 一个无参构造函数
	 * 一个带有String参数的构造函数，并传递给父类的构造函数。
	 * 一个带有String参数和Throwable参数，并都传递给父类构造函数
	 * 一个带有Throwable 参数的构造函数，并传递给父类的构造函数。
	 */
	private static final long serialVersionUID = 1L;

	private ResponseCode responseCode;

	public GeneralException() {
		// TODO Auto-generated constructor stub
	}

	public GeneralException(String errorMsg) {
		super(errorMsg);
	}

	public GeneralException(Throwable ex) {
		super(ex);
	}

	public GeneralException(ResponseCode responseCode, Throwable ex) {
		super(responseCode.getMessage(), ex);
		this.setResponseCode(responseCode);
	}

	public GeneralException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.setResponseCode(responseCode);
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	

}
