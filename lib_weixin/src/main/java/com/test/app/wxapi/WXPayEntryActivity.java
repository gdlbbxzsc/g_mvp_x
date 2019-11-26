package com.test.app.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import c.g.a.x.lib_rxbus.rxbus.RxBus;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.lib_weixin.PayResultMsg;
import c.g.a.x.lib_weixin.WechatHelper;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private WechatHelper wechatHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wechatHelper = WechatHelper.getInstance(this);
        wechatHelper.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wechatHelper.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String respString = null;
            if (resp.errCode == 0) {
                respString = "支付成功";
                RxBus.post0(new PayResultMsg());
            } else if (resp.errCode == -1) {
                respString = "支付错误";
            } else if (resp.errCode == -2) {
                respString = "取消支付";
            }
            SysToast.showToastShort(this, respString);
        }
        finish();
    }
}