package c.g.a.x.lib_weixin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;

import c.g.a.x.lib_rxbus.base.BaseMsg;
import c.g.a.x.lib_rxbus.rxbus.RxBus;
import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.base.BaseActivity;
import c.g.a.x.lib_support.base.BaseFragment;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.lib_weixin.http.WxAccessTokenResponse;
import c.g.a.x.lib_weixin.http.WxHttpHelper;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public final class WeChatHelper {

    private static final String APP_ID = BuildConfig.wx_app_id;
    private static final String APP_SECRET = BuildConfig.wx_app_secret;
    private static final String APP_NAME = BuildConfig.wx_app_name;

    private static final int THUMB_SIDE_LENGTH = 150;
    private static final int THUMB_MAX_SIZE_KB = 31;

    private Context context;
    private IWXAPI wxApi;

    private final WXMediaMessage wxMediaMessage = new WXMediaMessage();
    private int shareType = SendMessageToWX.Req.WXSceneSession;

    private String msgType;

    private static volatile WeChatHelper weChatHelper;

    public static WeChatHelper getInstance(Context context) {
        if (weChatHelper == null) {
            synchronized (WeChatHelper.class) {
                if (weChatHelper == null) {
                    weChatHelper = new WeChatHelper(context);
                }
            }
        }
        return weChatHelper;
    }

//    public static final WeChatHelper getInstance(Context context) {
//        return new WeChatHelper(context);
//    }

    private WeChatHelper(Context ctx) {
        this.context = ctx.getApplicationContext();
        wxApi = WXAPIFactory.createWXAPI(this.context, APP_ID, false);
        wxApi.registerApp(APP_ID);
    }

    public synchronized final void close() {
        wxApi.unregisterApp();
        wxApi = null;
        weChatHelper = null;
    }

    public final void handleIntent(Intent var1, IWXAPIEventHandler var2) {
        wxApi.handleIntent(var1, var2);
    }


    public final boolean isWXAppInstalled() {
        boolean b = wxApi.isWXAppInstalled();
        if (!b) SysToast.showToastShort(context, "您未安装微信");
        return b;
    }


    public final boolean loginForUserInfo(OnAccessTokenToUserinfoListener listener) {
        RxBus.register0(this, WxUserInfo.class, new Consumer<WxUserInfo>() {
            @Override
            public void accept(WxUserInfo wxUserInfo) throws Exception {
                listener.onAccessTokenToUserinfoListener(wxUserInfo);
                RxBus.removeDisposable0(this, WxUserInfo.class);
            }
        });

        boolean b = login();
        if (!b) RxBus.removeDisposable0(this, WxUserInfo.class);
        return b;
    }

    public void open() {
        if (!isWXAppInstalled()) return;
        wxApi.openWXApp();
    }

    public final boolean login() {
        if (!isWXAppInstalled()) return false;

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = APP_NAME;
        return wxApi.sendReq(req);
    }

    public final void accessTokenToUserInfo(String state, String code) {
        if (!state.equals(APP_NAME)) {
            RxBus.post0(new WxUserInfo(false, null));
            return;
        }
        WxHttpHelper httpHelper = new WxHttpHelper();
        httpHelper.accessToken(WeChatHelper.APP_ID, WeChatHelper.APP_SECRET, code, "authorization_code")
                .flatMap((Function<WxAccessTokenResponse, ObservableSource<ResponseBody>>) wxAccessTokenResponse -> httpHelper.userinfo(wxAccessTokenResponse.getAccess_token(), wxAccessTokenResponse.getOpenid()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    RxBus.post0(new WxUserInfo(true, responseBody.string()));
                }, throwable -> {
                    RxBus.post0(new WxUserInfo(false, null));
                    throwable.printStackTrace();
                });
//        ;
    }

    public final void pay(BaseActivity key, MyPayInfo payInfo, OnPayResultListener listener) {
        pay(key.toString() + PayResultMsg.class.getName(), payInfo, listener);
    }

    public final void pay(BaseFragment key, MyPayInfo payInfo, OnPayResultListener listenerr) {
        pay(key.toString() + PayResultMsg.class.getName(), payInfo, listenerr);
    }

    public final void pay(Object key, MyPayInfo payInfo, OnPayResultListener listener) {
        pay(key.toString() + PayResultMsg.class.getName(), payInfo, listener);
    }

    public final void pay(String key, MyPayInfo payInfo, OnPayResultListener listener) {
        if (!isWXAppInstalled()) return;

        RxBus.register0(key, PayResultMsg.class, payResultMsg -> {
            if (listener != null) listener.onPayResultListener(payResultMsg);
            RxBus.removeDisposable0(key);
        });
        PayReq req = new PayReq();
        req.appId = payInfo.appId;
        req.partnerId = payInfo.partnerId;
        req.prepayId = payInfo.prepayId;
        req.packageValue = payInfo.packageValue;
        req.nonceStr = payInfo.nonceStr;
        req.timeStamp = payInfo.timeStamp;
        req.sign = payInfo.sign;
        req.extData = "app data";
        boolean b = wxApi.sendReq(req);
        if (!b) RxBus.removeDisposable0(key);
    }

    public final WeChatHelper shareTypeWXSceneSession() {
        if (!wxApi.isWXAppInstalled()) return this;
        shareType = SendMessageToWX.Req.WXSceneSession;
        return this;
    }

    public final WeChatHelper shareTypeWXSceneTimeline() {
        if (!wxApi.isWXAppInstalled()) return this;
        shareType = SendMessageToWX.Req.WXSceneTimeline;
        return this;
    }

    public final WeChatHelper text(String text) {
        if (!wxApi.isWXAppInstalled()) return this;
        wxMediaMessage.mediaObject = new WXTextObject(text);
        msgType = "text";
        return this;
    }

    public final WeChatHelper image(Bitmap bmp) {
        if (!wxApi.isWXAppInstalled()) return this;
        wxMediaMessage.mediaObject = new WXImageObject(bmp);
        bmp.recycle();
        msgType = "image";
        return this;
    }

    public final WeChatHelper webpage(String url) {
        if (!wxApi.isWXAppInstalled()) return this;
        wxMediaMessage.mediaObject = new WXWebpageObject(url);
        msgType = "webpage";
        return this;
    }

    public final WeChatHelper video(String url) {
        if (!wxApi.isWXAppInstalled()) return this;
        WXVideoObject videoObj = new WXVideoObject();
        videoObj.videoUrl = url;
        wxMediaMessage.mediaObject = videoObj;
        msgType = "video";
        return this;
    }

    public final WeChatHelper title(String title) {
        if (!wxApi.isWXAppInstalled()) return this;
        wxMediaMessage.title = title;
        return this;
    }

    public final WeChatHelper description(String description) {
        if (!wxApi.isWXAppInstalled()) return this;
        wxMediaMessage.description = description;
        return this;
    }

    public final WeChatHelper thumbData(int drawableId) {
        if (!wxApi.isWXAppInstalled()) return this;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), drawableId);
        thumbData2Msg(thumb);
        return this;
    }

    public final WeChatHelper thumbData(String path) {
        if (!wxApi.isWXAppInstalled()) return this;
        Bitmap thumb = BitmapFactory.decodeFile(path);
        thumbData2Msg(thumb);
        return this;
    }

    public final WeChatHelper thumbData(Bitmap bitmap) {
        if (!wxApi.isWXAppInstalled()) {
            bitmap.recycle();
            return this;
        }
        Bitmap thumb = Bitmap.createScaledBitmap(bitmap, THUMB_SIDE_LENGTH, THUMB_SIDE_LENGTH, true);
        thumbData2Msg(thumb);
        bitmap.recycle();
        return this;
    }

    private void thumbData2Msg(Bitmap thumb) {
        if (!wxApi.isWXAppInstalled()) return;
        wxMediaMessage.thumbData = AndroidUtils.bitmap2Bytes(thumb, THUMB_MAX_SIZE_KB);
        thumb.recycle();
    }


    public final void share() {
        if (!wxApi.isWXAppInstalled()) return;
        try {
            check();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("=========> WeChat share fail");
            return;
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(msgType);
        req.message = wxMediaMessage;
        req.scene = shareType;

        wxApi.sendReq(req);
    }

    private void check() throws Exception {
        if (wxMediaMessage.mediaObject == null)
            throw new Exception("no mediaObject in wxMediaMessage");

        if (TextUtils.isEmpty(wxMediaMessage.title))
            wxMediaMessage.title = context.getString(R.string.app_name);
//        wxMediaMessage.description;
        if (wxMediaMessage.thumbData == null || wxMediaMessage.thumbData.length <= 0)
            thumbData(R.mipmap.ic_launcher);
    }


    private String buildTransaction(final String type) {
        return (type == null ? "" : type) + System.currentTimeMillis();
    }


//    public void shareToFriend(File file, int type) {
//        if (!wxApi.isWXAppInstalled()) return;
//
//        try {
//            Intent intent = new Intent();
//            ComponentName comp = new ComponentName("com.tencent.mm", type == 0 ? "com.tencent.mm.ui.tools.ShareImgUI" : "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//            intent.setComponent(comp);
//            intent.setAction("android.intent.action.SEND");
//            intent.setType("image/*");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
//                intent.putExtra(Intent.EXTRA_STREAM, contentUri);
//            } else {
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//            }
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void shareTofriendsCircle(File file) {
//        if (!wxApi.isWXAppInstalled()) return;
//        try {
//            Intent intent = new Intent();
//            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//            intent.setComponent(comp);
//            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//            intent.setType("image/*");
//            ArrayList<Uri> imageUris = new ArrayList<Uri>();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
//                imageUris.add(contentUri);
//            } else {
//                imageUris.add(Uri.fromFile(file));
//            }
//            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static final class MyPayInfo implements Serializable {

        public String appId;
        public String partnerId;
        public String prepayId;
        public String nonceStr;
        public String timeStamp;
        public String packageValue;
        public String sign;
        public String extData;
    }

    public static final class PayResultMsg extends BaseMsg {
    }

    public static final class WxUserInfo extends BaseMsg {

        public boolean succ;

        public String userInfoStr;


        public WxUserInfo() {

        }

        public WxUserInfo(boolean succ, String weChatInfo) {
            this.succ = succ;
            this.userInfoStr = weChatInfo;
        }
    }

    public interface OnPayResultListener {
        void onPayResultListener(PayResultMsg msg);
    }

    public interface OnAccessTokenToUserinfoListener {
        void onAccessTokenToUserinfoListener(WxUserInfo msg);
    }
}
