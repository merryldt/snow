package com.summer.icommon.utils;

import java.io.Serializable;
import java.util.List;

public class ResponseModel<T> implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int code;
  private String message;
  private T data;
  private int count;
  private T parent;
  private String ans;
  private List<T> datas;
  private  Float score;


    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public ResponseModel() {
    // TODO Auto-generated constructor stub
  }

  public ResponseModel(int code, String message, T responseData) {
    this.code = code;
    this.message= message;
    this.data = responseData;
  }
  
  public ResponseModel(int code, String message) {
	    this.code = code;
	    this.message = message;
	  }

  public static <T> ResponseModel<T> success(T data) {
    return new ResponseModel<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
  }
  public ResponseModel(ResponseCode responseCode, T data,T data2) {
    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
    this.data = data;
  }
  public ResponseModel(ResponseCode responseCode, T data,T data2,T parent) {
    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
    this.data = data;
    this.parent = parent;
  }
  public ResponseModel(ResponseCode responseCode, T data) {
    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
    this.data = data;
  }


  public ResponseModel(ResponseCode responseCode) {
	    this.code = responseCode.getCode();
	    this.message = responseCode.getMessage();
	    this.data = null;
	  }


  public T getParent() {
    return parent;
  }

  public void setParent(T parent) {
    this.parent = parent;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T responseData) {
    this.data = responseData;
  }

  public static <T> ResponseModel<T> ERROR(ResponseCode responseCode) {
    return new ResponseModel<T>(responseCode, null);
  }

  public static <T> ResponseModel<T> ERROR( String message) {
    return new ResponseModel<T>(ResponseCode.ERROR.getCode(), message);
  }
  
  public static <T> ResponseModel<T> SUCCESS(){
	   return SUCCESS(null);
  }
  
  public static <T> ResponseModel<T> SUCCESS(T t){
	  return new ResponseModel<T>(ResponseCode.OK, t);
  }

  public int getCount() {
    return count;
  }


  public void setCount(int count) {
    this.count = count;
  }


  public String getAns() {
    return ans;
  }

  public void setAns(String ans) {
    this.ans = ans;
  }

  public List<T> getDatas() {
    return datas;
  }

  public void setDatas(List<T> datas) {
    this.datas = datas;
  }

  public static boolean isOk(ResponseModel response) {
    if (ResponseCode.OK.getCode() == response.getCode()) {
      return true;
    }
    return false;
  }

  public static boolean isError(ResponseModel response) {
    if (ResponseCode.ERROR.getCode() == response.getCode()) {
      return true;
    }
    return false;
  }
}
