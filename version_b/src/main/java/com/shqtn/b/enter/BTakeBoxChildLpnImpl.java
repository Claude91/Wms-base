package com.shqtn.b.enter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shqtn.b.enter.params.BTakeBoxSubmitParams;
import com.shqtn.base.BaseApp;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.LpnCheck;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.bean.enter.TakeBoxPlan;
import com.shqtn.base.bean.params.TakeBoxSubmitParams;
import com.shqtn.base.controller.view.IDialogView;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.enter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2018/1/10
 * 描述: 用于装箱操作中，内部装LPN
 *
 * @author ql
 */
public class BTakeBoxChildLpnImpl extends AbstractBTakeBoxChild<CodeLpn,TakeBoxSubmitParams> implements View.OnClickListener, AdapterView.OnItemClickListener {
    private CommonAdapter<CodeLpn> adapter;
    private IDialogView dialogView;

    public BTakeBoxChildLpnImpl(@NonNull TakeBoxPlan takeBoxGoodsPlan, @NonNull TakeBoxGoods takeBoxGoods, IDialogView dialog) {
        super(takeBoxGoodsPlan, takeBoxGoods);
        this.dialogView = dialog;
        adapter = new CommonAdapter<CodeLpn>(R.layout.item_take_box_child_lpn) {
            @Override
            public void setItemContent(ViewHolder holder, CodeLpn lpn, int position) {
                View delView = holder.getViewById(R.id.item_take_box_child_lpn_iv_del);

                if (lpn.isTag()) {
                    delView.setVisibility(View.GONE);
                } else {
                    delView.setVisibility(View.VISIBLE);
                }
                delView.setTag(position);
                delView.setOnClickListener(BTakeBoxChildLpnImpl.this);

                holder.setLabelText(R.id.item_take_box_child_lpn_ltv_no, lpn.getLpnNo());

            }
        };
    }

    @Override
    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public int getDecodeType() {
        return CodeCallback.TAG_LPN;
    }

    @Override
    public String getHintText() {
        return "请扫描箱子编码";
    }

    @Override
    public void setListView(ListView lv) {
        lv.setAdapter(adapter);
    }

    StringBuffer strAppend = new StringBuffer();

    @Override
    public boolean isCanAdd(CodeLpn lpn) {
        for (CodeLpn codeLpn : getChilds()) {
            if (codeLpn.getLpnNo().equals(lpn.getLpnNo())) {
                strAppend.delete(0, strAppend.length());
                strAppend.append("箱子:").append(lpn.getLpnNo()).append("\r\n").append("不能重复添加");
                dialogView.displayMsgDialog(strAppend.toString());
                return false;
            }
        }
        if (getChilds().size() > getAddMaxSize()) {
            dialogView.displayMsgDialog("箱子已经达到最大数量");
            return false;
        }
        return true;
    }

    @Override
    public void addChildren(CodeLpn lpn) {
        getChilds().add(lpn);
        adapter.update(getChilds());
    }

    @Override
    public TakeBoxSubmitParams getOverSubmit() {
        return createSubmitParams(TakeBoxSubmitParams.SUBMIT_FLAG_TAKE_OVER);
    }

    private double getAddMaxSize(){
        return operateGoodsPlan.getConversionRate();
    }


    @Override
    public TakeBoxSubmitParams getSubmit() {
        return createSubmitParams(TakeBoxSubmitParams.SUBMIT_FLAG_TAKE_SUBMIT);
    }

    @NonNull
    private TakeBoxSubmitParams createSubmitParams(String flag) {
        BTakeBoxSubmitParams params = new BTakeBoxSubmitParams();
        params.setOverFlag(flag);
        DepotBean depot = DepotUtils.getDepot(BaseApp.getInstance());
        params.setWhCode(depot.getWhcode());
        params.setOverFlag(flag);
        List<TakeBoxSubmitParams.SubmitSku> list = new ArrayList<>();
        ArrayList<CodeLpn> childs = getChilds();

        for (CodeLpn child : childs) {
            TakeBoxSubmitParams.SubmitSku lpnSubmit = new TakeBoxSubmitParams.SubmitSku();
            lpnSubmit.setPackCode(child.getLpnNo());
            lpnSubmit.setPackStatus(child.getPackStatus());
            list.add(lpnSubmit);
        }
        params.setSkuList(list);

        params.setCountNum(childs.size());
        params.setPackLevel(operateGoodsPlan.getPackLevel());
        params.setBatchNo(operateGoods.getBatchNo());
        params.setDocNo(manifest);
        params.setIkey(mOpearetManifest.getIkey());
        params.setfLpnNo(getBoxNo());
        return params;
    }


    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        removeChildren(tag);
        adapter.update(getChilds());
    }

    @Override
    public void setChilds(ArrayList chlids) {
        adapter.setNewData(chlids);
        super.setChilds(chlids);
    }

    @Override
    public void addOverChild(LpnCheck lpnStatus) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
