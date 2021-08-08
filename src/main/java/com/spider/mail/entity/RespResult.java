package com.spider.mail.entity;

/**
 * @author pengmin
 * @date 2021/8/9 0:12
 */

public class RespResult {

    private Integer code;

    private Object data;

    public RespResult() {
    }

    public RespResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
