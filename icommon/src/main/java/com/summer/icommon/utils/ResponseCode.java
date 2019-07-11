package com.summer.icommon.utils;

public enum ResponseCode {


	SUCCESS(200, "操作成功"),
	OK(0, "SUCCESS"),
	ERROR(-1, "未知错误"),
	BAD_REQUEST(400, "请求参数缺失"),
	REQUEST_METHOD_ERROR(405, "请求方式不支持"),
	UNSUPPORTED_MEDIA_TYPE(415, "请求参数格式不正确"),
	REQUEST_NOTFOUND_ERROR(404,"请求资源不存在"),
	NOT_ACCEPTABLE(406,"Not Acceptable"),
	SERVER_ERROR(500,"服务器错误。"),
	NOT_USER(10000, "没有该用户"),
    REQUEST_ERROR(10001,"请求失败请重新再试"),
	DATABASE_ERROR(10002,"数据库操作失败"),
	REQUEST_PARAMETER_NULL(10003,"请求参数为空"),
	LOGIN_ERROR(10004, "登陆失败"),
	REQUEST_PARAMETER_ERROR(10005,"请求参数错误"),
	PERMISSION_DENIED(10006,"没有权限。"),
	LOGIN_OUT_OF_DATE(10007, "登陆已过期,请重新登陆"),
	PARAMETER_TYPE_MISMATCH(10008,"参数类型不匹配"),
	SERVER_RUNTIME_ERROR(10009, "[服务器]运行时异常"),
	NULL_POINTER_ERROR(10010, "[服务器]空值异常"),
	CLASS_CAST_ERROR(10011, "[服务器]数据类型转换异常"),
	IO_ERROR(10012, "[服务器]IO异常"),
	NO_SUCH_METHOD_ERROR(10013, "[服务器]未知方法异常"),
	INDEX_OUTOF_BOUNDS_ERROR(10014, "[服务器]数组越界异常"),
	NETWORK_ERROR(020, "[服务器]网络异常"),
	dd(012, "[服务器]运行时异常");
	private int code;
	private String message;
	private String m;

	private ResponseCode() {
	}
	private ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int errcode) {
		this.code = errcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
