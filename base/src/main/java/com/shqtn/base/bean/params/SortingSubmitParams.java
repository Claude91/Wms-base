package com.shqtn.base.bean.params;

import java.util.ArrayList;

import com.shqtn.base.bean.exit.SortingGoods;

/**
 * Created by android on 2017/8/1.
 */

public class SortingSubmitParams {
    private ArrayList<SortingGoods> sortList;

    public ArrayList<SortingGoods> getSortList() {
        return sortList;
    }

    public void setSortList(ArrayList<SortingGoods> sortList) {
        this.sortList = sortList;
    }
}
