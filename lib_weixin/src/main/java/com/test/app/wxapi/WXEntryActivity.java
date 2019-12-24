package com.test.app.wxapi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.lib_weixin.WeChatHelper;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

   private Context context;
   private WeChatHelper wechatHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        wechatHelper = WeChatHelper.getInstance(context);
        wechatHelper.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wechatHelper.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Logger.e("WXEntryActivity onReq:", "COMMAND_GETMESSAGE_FROM_WX");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Logger.e("WXEntryActivity onReq:", "COMMAND_SHOWMESSAGE_FROM_WX");
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                Logger.e("WXEntryActivity onReq:", "COMMAND_LAUNCH_BY_WX");
                break;
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        Logger.e("WXEntryActivity onResp:", "COMMAND_SENDMESSAGE_TO_WX");
                        SysToast.showToastShort(context, "分享成功");
                        break;
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        SendAuth.Resp saResp = (SendAuth.Resp) resp;
                        Logger.e("WXEntryActivity onResp:", "COMMAND_SENDAUTH", saResp.state, " ", saResp.code);
                        wechatHelper.accessTokenToUserInfo(saResp.state, saResp.code);
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                SysToast.showToastShort(context, "用户已取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
        }
        finish();
    }
}
