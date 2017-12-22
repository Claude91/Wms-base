package com.shqtn.enter.print.bean;

import java.util.List;

/**
 * 创建时间:2017/12/5
 * 描述:
 *
 * @author ql
 */

public class BarCode {

    /**
     * DOC_VALUE : SKU
     * DOC_TYPE : W04
     * DOC_CODE : 08600043
     * CODE_LIST : []
     */

    private String DOC_VALUE;
    private String DOC_TYPE;
    private String DOC_CODE;
    private List<Decode> CODE_LIST;

    public String getDOC_VALUE() {
        return DOC_VALUE;
    }

    public void setDOC_VALUE(String DOC_VALUE) {
        this.DOC_VALUE = DOC_VALUE;
    }

    public String getDOC_TYPE() {
        return DOC_TYPE;
    }

    public void setDOC_TYPE(String DOC_TYPE) {
        this.DOC_TYPE = DOC_TYPE;
    }

    public String getDOC_CODE() {
        return DOC_CODE;
    }

    public void setDOC_CODE(String DOC_CODE) {
        this.DOC_CODE = DOC_CODE;
    }

    public List<Decode> getCODE_LIST() {
        return CODE_LIST;
    }

    public void setCODE_LIST(List<Decode> CODE_LIST) {
        this.CODE_LIST = CODE_LIST;
    }
}
