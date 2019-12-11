package c.g.a.x.module_web;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class JsUtils {

    private final Activity context;

    private final WebView webView;

    public JsUtils(Activity context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    @JavascriptInterface
    public void shareToWeChat(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        context.startActivity(intent);
    }
//
//    @JavascriptInterface
//    public void closePage() {
//        context.finish();
//    }
//
//    @JavascriptInterface
//    public void closeH5Page() {
//        closePage();
//    }
//
//    /**
//     * @param type     0好友 1朋友圈
//     * @param fileName 文件名
//     * @param url      下载链接
//     */
//    @JavascriptInterface
//    public void shareImage(String url, String fileName, int type) {
//        HttpAction.getInstance().get(url).subscribe(new BaseObserver<ResponseBody>(context) {
//            @Override
//            public void onSuccess(ResponseBody response) {
//                File file = new File(HttpConstant.URL.DOWN_LOAD_PATH, System.currentTimeMillis() + ".jpg");
//                try {
//                    if (!file.exists()) {
//                        file.createNewFile();
//                    }
//                    FileHelper.getInstance().writeFile(response.bytes(), file);
//                    wechatShareImage(file, type);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(int code, String message) {
//
//            }
//        });
//    }
//
//    private void wechatShareImage(File file, int type) {
//        try {
//            String path = file.getAbsolutePath();
//            File resultFile = LubanUtil.getInstance().getBuilder().get(path);
//            Bitmap bitmap = BitmapFactory.decodeFile(resultFile.getAbsolutePath());
////            WechatHelper.getInstance(context).initWechatUtils(context).shareImages(bitmap, type);
//            switch (type) {
//                case 0: {//微信
//                    WeChatHelper.getInstance(context).shareTypeWXSceneSession().image(bitmap).share();
//                }
//                break;
//                case 1: {//朋友圈
//                    WeChatHelper.getInstance(context).shareTypeWXSceneTimeline().image(bitmap).share();
//                }
//                break;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param type    0好友 1朋友圈
//     * @param title   分享文字标题
//     * @param content 分享文字内容
//     * @param url     分享链接
//     */
//    @JavascriptInterface
//    public void wxShare(int type, String title, String content, String url) {
//        Log.e("wxShare", url);
////            WechatUtils.getInstance().initWechatUtils(context).sendWebPage(url, title, content, type);
//        switch (type) {
//            case 0: {//微信
//                WeChatHelper.getInstance(context).shareTypeWXSceneSession().title(title).webpage(url).description(content).share();
//            }
//            break;
//            case 1: {//朋友圈
//                WeChatHelper.getInstance(context).shareTypeWXSceneTimeline().title(title).webpage(url).description(content).share();
//            }
//            break;
//        }
//    }
//
//    @JavascriptInterface
//    public void downLoadFile(String url, String fileName) {
//        Log.e("downLoadFile", url + "   " + fileName);
//        HttpAction.getInstance().get(url).subscribe(new BaseObserver<ResponseBody>(context) {
//            @Override
//            public void onSuccess(ResponseBody response) {
//                try {
//                    File file = new File(HttpConstant.URL.DOWN_LOAD_PATH, System.currentTimeMillis() + ".jpg");
//                    if (file.exists()) {
//                        file.createNewFile();
//                    }
//                    FileHelper.getInstance().writeFile(response.bytes(), file);
//                    insertIntoSystemAlbum(file);
//                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(int code, String message) {
//
//            }
//        });
//    }
//
//
//    @JavascriptInterface
//    public String getToken() {
//        String token = HttpHelper.getInstance().oldToken;
//        if (TextUtils.isEmpty(token)) {
//            return null;
//        } else {
//            return "Bearer " + token;
//        }
//    }
}
