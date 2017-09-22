package com.shqtn.base.info.code;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-5-12.
 */
public class AllotBean {

    private String DOC_CODE;
    /**
     * 用于判别当前二维码的类型
     */
    private String DOC_TYPE;
    /**
     * 当前类别的名称
     */
    private String DOC_VALUE;

    private CodeGoods product;

    private ArrayList<CodeGoods> CODE_LIST;

    public String getDOC_CODE() {
        return DOC_CODE;
    }

    public void setDOC_CODE(String DOC_CODE) {
        this.DOC_CODE = DOC_CODE;
    }

    public String getDOC_TYPE() {
        return DOC_TYPE;
    }

    public void setDOC_TYPE(String DOC_TYPE) {
        this.DOC_TYPE = DOC_TYPE;
    }

    public String getDOC_VALUE() {
        return DOC_VALUE;
    }

    public void setDOC_VALUE(String DOC_VALUE) {
        this.DOC_VALUE = DOC_VALUE;
    }

    public CodeGoods getProduct() {
        return product;
    }

    public void setProduct(CodeGoods product) {
        this.product = product;
    }

    public ArrayList<CodeGoods> getCODE_LIST() {
        return CODE_LIST;
    }

    public void setCODE_LIST(ArrayList<CodeGoods> CODE_LIST) {
        this.CODE_LIST = CODE_LIST;
    }
}
