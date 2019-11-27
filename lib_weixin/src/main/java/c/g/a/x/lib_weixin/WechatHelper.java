package c.g.a.x.lib_weixin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import c.g.a.x.lib_base.BaseActivity;
import c.g.a.x.lib_base.BaseFragment;
import c.g.a.x.lib_rxbus.rxbus.RxBus;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.views.toast.SysToast;

public final class WechatHelper {

    public static final String APP_ID = BuildConfig.wx_app_id;
    public static final String APP_SECRET = BuildConfig.wx_app_secret;

    private static final int THUMB_SIDE_LENGTH = 150;
    private static final int THUMB_MAX_SIZE_KB = 31;

    private Context context;
    private IWXAPI wxApi;

    private WXMediaMessage wxMediaMessage = new WXMediaMessage();
    private int shareType = SendMessageToWX.Req.WXSceneSession;

    private String msgType;

    public static final WechatHelper getInstance(Context context) {
        return new WechatHelper(context);
    }

    private WechatHelper(Context context) {
        this.context = context;
        wxApi = WXAPIFactory.createWXAPI(context, APP_ID, false);
        wxApi.registerApp(APP_ID);
    }

    public final void handleIntent(Intent var1, IWXAPIEventHandler var2) {
        wxApi.handleIntent(var1, var2);
    }

    public final boolean isWXAppInstalled() {
        return wxApi.isWXAppInstalled();
    }

    public boolean login() {
        if (!wxApi.isWXAppInstalled()) {
            SysToast.showToastShort(context, "您未安装微信");
            return false;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "toutoule";
        return wxApi.sendReq(req);
    }

    public final void pay(BaseActivity key, MyPayInfo payInfo, OnPayResultListener listener) {
        pay(key.toString() + PayResultMsg.class.getName(), payInfo, listener);
    }

    public final void pay(BaseFragment key, MyPayInfo payInfo, OnPayResultListener listenerr) {
        pay(key.toString() + PayResultMsg.class.getName(), payInfo, listenerr);
    }

    public final void pay(Class key, MyPayInfo payInfo, OnPayResultListener listener) {
        pay(key.toString() + PayResultMsg.class.getName(), payInfo, listener);
    }
 
    public final void pay(String key, MyPayInfo payInfo, OnPayResultListener listener) {
        if (!wxApi.isWXAppInstalled()) {
            SysToast.showToastShort(context, "您未安装微信");
            return;
        }

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

    public final WechatHelper shareTypeWXSceneSession() {
        if (!wxApi.isWXAppInstalled()) return this;
        shareType = SendMessageToWX.Req.WXSceneSession;
        return this;
    }

    public final WechatHelper shareTypeWXSceneTimeline() {
        if (!wxApi.isWXAppInstalled()) return this;
        shareType = SendMessageToWX.Req.WXSceneTimeline;
        return this;
    }

    public final WechatHelper text(String text) {
        if (!wxApi.isWXAppInstalled()) return this;
        WXTextObject textObj = new WXTextObject(text);
        wxMediaMessage.mediaObject = textObj;
        msgType = "text";
        return this;
    }

    public final WechatHelper image(Bitmap bmp) {
        if (!wxApi.isWXAppInstalled()) return this;
        WXImageObject imgObj = new WXImageObject(bmp);
        wxMediaMessage.mediaObject = imgObj;
        bmp.recycle();
        msgType = "image";
        return this;
    }

    public final WechatHelper webpage(String url) {
        if (!wxApi.isWXAppInstalled()) return this;
        WXWebpageObject webpageObj = new WXWebpageObject(url);
        wxMediaMessage.mediaObject = webpageObj;
        msgType = "webpage";
        return this;
    }

    public final WechatHelper video(String url) {
        if (!wxApi.isWXAppInstalled()) return this;
        WXVideoObject videoObj = new WXVideoObject();
        videoObj.videoUrl = url;
        wxMediaMessage.mediaObject = videoObj;
        msgType = "video";
        return this;
    }

    public final WechatHelper title(String title) {
        if (!wxApi.isWXAppInstalled()) return this;
        wxMediaMessage.title = title;
        return this;
    }

    public final WechatHelper description(String description) {
        if (!wxApi.isWXAppInstalled()) return this;
        wxMediaMessage.description = description;
        return this;
    }

    public final WechatHelper thumbData(int drawableId) {
        if (!wxApi.isWXAppInstalled()) return this;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), drawableId);
        thumbData2Msg(thumb);
        return this;
    }

    public final WechatHelper thumbData(String path) {
        if (!wxApi.isWXAppInstalled()) return this;
        Bitmap thumb = BitmapFactory.decodeFile(path);
        thumbData2Msg(thumb);
        return this;
    }

    public final WechatHelper thumbData(Bitmap bitmap) {
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
        wxMediaMessage.thumbData = bitmap2Bytes(thumb, THUMB_MAX_SIZE_KB);
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

    private byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    private String buildTransaction(final String type) {
        return (type == null ? "" : type) + System.currentTimeMillis();
    }


    public void shareToFriend(File file, int type) {
        if (!wxApi.isWXAppInstalled()) return;

        try {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", type == 0 ? "com.tencent.mm.ui.tools.ShareImgUI" : "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction("android.intent.action.SEND");
            intent.setType("image/*");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            } else {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareTofriendsCircle(File file) {
        if (!wxApi.isWXAppInstalled()) return;
        try {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
            ArrayList<Uri> imageUris = new ArrayList<Uri>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                imageUris.add(contentUri);
            } else {
                imageUris.add(Uri.fromFile(file));
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnPayResultListener {
        void onPayResultListener(PayResultMsg msg);
    }
}
