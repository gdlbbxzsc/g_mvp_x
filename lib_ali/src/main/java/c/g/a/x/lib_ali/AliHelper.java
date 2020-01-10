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
//    private AliHelper(Activity activity) {
////        this.activity = activity;
//    }

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

    public static final class AliResult {

        private String resultStatus;
        private String result;
        private String memo;

        private String resultCode;
        private String authCode;
        private String aliPayOpenId;
        private String userId;

        public AliResult(Map<String, String> rawResult) {
            if (rawResult == null) {
                return;
            }

            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult.get(key);
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult.get(key);
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult.get(key);
                }
            }
            if (TextUtils.isEmpty(result)) return;
            String[] resultValue = result.split("&");
            if (resultValue.length <= 0) return;
            for (String value : resultValue) {
                if (value.startsWith("alipay_open_id")) {
                    aliPayOpenId = removeBrackets(getValue("alipay_open_id=", value));
                    continue;
                }
                if (value.startsWith("auth_code")) {
                    authCode = removeBrackets(getValue("auth_code=", value));
                    continue;
                }
                if (value.startsWith("result_code")) {
                    resultCode = removeBrackets(getValue("result_code=", value));
                    continue;
                }
                if (value.startsWith("user_id")) {
                    userId = removeBrackets(getValue("user_id=", value));
                    continue;
                }
            }
        }

        private String removeBrackets(String str) {

            if (TextUtils.isEmpty(str)) return str;

            if (str.startsWith("\"")) str = str.replaceFirst("\"", "");

            if (str.endsWith("\"")) str = str.substring(0, str.length() - 1);

            return str;
        }

        private String getValue(String header, String data) {
            return data.substring(header.length());
        }

        @Override
        public String toString() {
            return "resultStatus={" + resultStatus + "};memo={" + memo + "};result={" + result + "}";
        }

        /**
         * @return the resultStatus
         */
        public String getResultStatus() {
            return resultStatus;
        }

        /**
         * @return the memo
         */
        public String getMemo() {
            return memo;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }

        /**
         * @return the resultCode
         */
        public String getResultCode() {
            return resultCode;
        }

        /**
         * @return the authCode
         */
        public String getAuthCode() {
            return authCode;
        }

        /**
         * @return the aliPayOpenId
         */
        public String getAliPayOpenId() {
            return aliPayOpenId;
        }

        public String getUserId() {
            return userId;
        }
    }

    public interface AliResultListener {
        void result(boolean suc, AliResult aliResult);
    }
}
