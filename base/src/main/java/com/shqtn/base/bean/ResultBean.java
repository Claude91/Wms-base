package com.shqtn.base.bean;

/**
 * Created by Administrator on 2017-7-10.
 */
public class ResultBean {

    private boolean rs;//用于判断请求是否成功
    private Object data;//当返回参数为一个对象
    private Object rows;//当返回为数组时的对象
    private Integer total;//返回为数组时的长度
    private String message;//返回的错误信息
    private String code;//错误编码;

    public boolean isRs() {
        return rs;
    }

    public void setRs(boolean rs) {
        this.rs = rs;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
