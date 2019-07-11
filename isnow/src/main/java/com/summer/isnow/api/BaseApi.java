package com.summer.isnow.api;

import com.summer.icommon.utils.ResponseCode;
import com.summer.icommon.utils.ResponseModel;

public abstract  class BaseApi {

    public ResponseModel reContent(ResponseCode responseCode,Object data){
        return new ResponseModel(responseCode,data);
    }
}
