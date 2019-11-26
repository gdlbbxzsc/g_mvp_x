package com.test.app.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import c.g.a.x.lib_weixin.WechatHelper;


@SuppressWarnings("deprecation")
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private Context context;
    private WechatHelper wechatHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Log.e("onReq", "COMMAND_GETMESSAGE_FROM_WX");
//			goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Log.e("onReq", "COMMAND_SHOWMESSAGE_FROM_WX");
//			goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                Log.e("onReq", "COMMAND_LAUNCH_BY_WX");
//			Toast.makeText(this, R.string.launch_from_wx, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
                    Log.e("onResp", "COMMAND_SENDMESSAGE_TO_WX");
                    Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                } else if (ConstantsAPI.COMMAND_SENDAUTH == resp.getType() && ((SendAuth.Resp) resp).state.equals("toutoule")) {
                    String errCode = ((SendAuth.Resp) resp).code;
                    getResultForRx(errCode);
                    Log.e("onResp", "COMMAND_SENDMESSAGE_TO_WX" + ((SendAuth.Resp) resp).state);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(context, "用户已取消", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            default:
                break;

        }
        finish();
    }

    private void getResultForRx(String code) {
//        Flowable.create((FlowableEmitter<WeChatResultResponse> e) -> {
//            String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatUtils.AUTH_APP_ID + "&secret="
//                    + WechatUtils.AUTH_APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
//            JSONObject jsonObject = JsonUtils.initSSLWithHttpClinet(path);
//            Log.e("getResultForRx", jsonObject.toString());
//            WeChatResultResponse resultResponse = JsonMananger.jsonToBean(jsonObject.toString(), WeChatResultResponse.class);
//            e.onNext(resultResponse);
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((weChatResultResponse) -> {
//                    getUIDForRx(weChatResultResponse.getOpenid(), weChatResultResponse.getAccess_token());
//                }, (throwable) ->
//                        Toast.makeText(context, "授权失败，请重新尝试", Toast.LENGTH_SHORT).show());
    }

    private void getUIDForRx(final String openId, final String accessToken) {
//        Flowable.create((FlowableEmitter<String> e) -> {
//            String path = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN&access_token=" + accessToken + "&openid="
//                    + openId;
//            JSONObject jsonObject = JsonUtils.initSSLWithHttpClinet(path);
//            e.onNext(jsonObject.toString());
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((jsonString) -> {
//                    /*{
//                        "sex": 0,
//                        "nickname": "",
//                        "unionid": "",
//                        "privilege": [ ],
//                        "province": "",
//                        "openid": "",
//                        "language": "",
//                        "headimgurl": "",
//                        "country": "",
//                        "city": ""
//                    }*/
//                    JSONObject jsonObject = new JSONObject(jsonString);
//                    //调接口
//                    saveInfo(jsonObject.getString("nickname"), jsonObject.getString("openid"));
//                }, (throwable) -> Toast.makeText(context, "授权失败，请重新尝试", Toast.LENGTH_SHORT).show());
    }


    private void saveInfo(String nickname, String openid) {
//        GetAppChangeCustomerInfoRequest request = new GetAppChangeCustomerInfoRequest(
//                YuGuoApplication.userInfo.getCustomerId(), null, null, null, null, null, nickname, openid, -1);
//        HttpAction.getInstance().appChangeCustomerInfo(request).subscribe(new BaseObserver<>(context, response -> {
//            int code = response.getCode();
//            if (200 == code) {
//                EventBusNotificationResponse notificationResponse = new EventBusNotificationResponse();
//                notificationResponse.setNotificationType(2);
//                notificationResponse.setWxNickName(nickname);
//                EventBus.getDefault().post(notificationResponse);
//            } else {
//                ToastUtil.showToast(context, "绑定失败");
//            }
//        }));
    }
}
