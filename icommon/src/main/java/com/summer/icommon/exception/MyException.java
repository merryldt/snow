package com.summer.icommon.exception;

import com.summer.icommon.utils.ResponseCode;

/**
 * 自定义检查性异常
 */
public class MyException  extends  Exception{


    /**
     * 一个无参构造函数
     * 一个带有String参数的构造函数，并传递给父类的构造函数。
     * 一个带有String参数和Throwable参数，并都传递给父类构造函数
     * 一个带有Throwable 参数的构造函数，并传递给父类的构造函数。
     */

    static final long serialVersionUID = 7818375828146090155L;

    private ResponseCode responseCode;

    public MyException()
    {
    }
    public MyException(String message)
    {
        super(message);
    }
    public MyException(ResponseCode responseCode, Throwable cause)
    {
        super(responseCode.getMessage(), cause);
    }
    public MyException(Throwable cause)
    {
        super(cause);
    }
    public MyException(ResponseCode responseCode) {
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
