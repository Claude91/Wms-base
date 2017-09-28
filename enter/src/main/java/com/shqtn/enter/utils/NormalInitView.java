package com.shqtn.enter.utils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.controller.view.IDialogView;
import com.shqtn.enter.controller.ListActivityController;

/**
 * Created by android on 2017/9/28.
 */

public class NormalInitView {
    
    public static void notSelectDepot(ListActivityController.View view){
       view.onRefreshComplete();
       view.displayMsgDialog("请前往设置仓库");
       view.setListViewModel(PullToRefreshBase.Mode.DISABLED);
    }
}
