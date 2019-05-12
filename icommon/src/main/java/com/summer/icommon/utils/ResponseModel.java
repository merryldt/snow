package com.summer.icommon.utils;

import java.io.Serializable;
import java.util.List;

public class ResponseModel<T> implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int code;
  private String msg;
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

  public ResponseModel(int code, String msg, T responseData) {
    this.code = code;
    this.msg = msg;
    this.data = responseData;
  }
  
  public ResponseModel(int code, String msg) {
	    this.code = code;
	    this.msg = msg;
	  }
  public ResponseModel(ResponseCode responseCode, T data,T data2) {
    this.code = responseCode.getCode();
    this.msg = responseCode.getMsg();
    this.data = data;
  }
  public ResponseModel(ResponseCode responseCode, T data,T data2,T parent) {
    this.code = responseCode.getCode();
    this.msg = responseCode.getMsg();
    this.data = data;
    this.parent = parent;
  }
  public ResponseModel(ResponseCode responseCode, T data) {
    this.code = responseCode.getCode();
    this.msg = responseCode.getMsg();
    this.data = data;
  }


  public ResponseModel(ResponseCode responseCode) {
	    this.code = responseCode.getCode();
	    this.msg = responseCode.getMsg();
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

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
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

  public static <T> ResponseModel<T> ERROR( String msg) {
    return new ResponseModel<T>(ResponseCode.ERROR.getCode(), msg);
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
