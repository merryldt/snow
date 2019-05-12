package com.summer.icommon.utils;

public enum ResponseCode {

	OK(0, "SUCCESS"),
	ERROR(-1, "未知错误"),
	SERVER_ERROR(500,"服务器错误。"),
	REQUEST_PARAMETER_ERROR(30011,"请求参数错误"),
	PERMISSION_DENIED(30002,"没有权限。"),
	REQUEST_NOTFOUND_ERROR(404,"请求资源不存在");



	private int code;
	private String msg;
	private String m;

	private ResponseCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int errcode) {
		this.code = errcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
