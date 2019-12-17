package com.capfsd.mod.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ResponseResult<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private Map<String, Object> extraInfo;

    public ResponseResult(int code, String message, T data, Map<String, Object> extraInfo) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.extraInfo = extraInfo;
    }


    public static ResponseResult<Object> fail(String message, Map<String, Object> extraInfo) {
        return new ResponseResult<>(-1, message, null, extraInfo);
    }

    public static <T> ResponseResult<T> success(String message, T data, Map<String, Object> extraInfo) {
        return new ResponseResult<>(0, message, data, extraInfo);
    }
}
