package c.g.a.x.lib_ali;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;

import java.util.Map;

import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.views.toast.SysToast;


public final class AliHelper {
 
//    private Activity activity;
//    private AliResultListener aliResultListener;


//    public static final AliHelper getInstance(Activity activity) {
//        return new AliHelper(activity);
//    }

    private AliHelper(Activity activity) {
//        this.activity = activity;
    }

    public static String getSDKVersion(Activity activity) {
        return new PayTask(activity).getVersion();
    }

    public static void authV2(Activity activity, final String authInfo, AliResultListener listener) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Map<String, String> resultMap = new AuthTask(activity).authV2(authInfo, true);
                final AliResult result = new AliResult(resultMap);
                Logger.e("ali authV2 result===>", result.toString());
                activity.runOnUiThread(() -> {
                    // 判断resultStatus 为“9000”且result_code 为“200”则代表授权成功
                    boolean b = TextUtils.equals(result.getResultStatus(), "9000") && TextUtils.equals(result.getResultCode(), "200");
                    SysToast.showToastShort(activity, b ? "授权成功" : "授权失败");
                    listener.result(b, result);
                });
            }
        }.start();
    }

    public static void payV2(Activity activity, final String payInfo, AliResultListener listener) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Map<String, String> resultMap = new PayTask(activity).payV2(payInfo, true);
                final AliResult result = new AliResult(resultMap);
                Logger.e("ali payV2 result===>", result.toString());
                activity.runOnUiThread(() -> {
                    // 判断resultStatus 为9000则代表支付成功
                    boolean b = TextUtils.equals(result.getResultStatus(), "9000");
                    SysToast.showToastShort(activity, b ? "支付成功" : "支付失败");
                    listener.result(b, result);
                });
            }
        }.start();
    }

//    public void appAliPayForRx(String payInfo) {
//        Observable.create((ObservableOnSubscribe<PayResult>) emitter -> {
//            Map<String, String> result = new PayTask(activity).payV2(payInfo, true);
//            Logger.e("ali payV2 result===>", result.toString());
//            PayResult payResult = new PayResult(result);
//            emitter.onNext(payResult);
//        }).subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((payResult) -> {
//                    String resultStatus = payResult.getResultStatus();
//
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        SysToast.showToastShort(activity, "支付成功");
//
//                    } else {
//                        SysToast.showToastShort(activity, "支付失败");
//                    }
//                }, (throwable) -> SysToast.showToastShort(activity, "支付失败"));
//    }


    public interface AliResultListener {
        void result(boolean succ, AliResult aliResult);
    }
}
