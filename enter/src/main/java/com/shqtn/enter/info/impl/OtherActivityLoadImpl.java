package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.print.aty.BoxPrintActivity;

/**
 * @author ql
 *         创建时间:2017/11/24
 *         描述:
 */

public class OtherActivityLoadImpl implements IActivityLoad.OtherActivityLoad {
    @Override
    public Class getPrintBoxActivity(Bundle bundle) {
        return BoxPrintActivity.class;
    }
}
