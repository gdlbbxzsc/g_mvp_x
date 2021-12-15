package c.g.a.x.module_web.webview;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.views.view.CommonTitleBar;
import c.g.a.x.module_web.JsUtils;
import c.g.a.x.module_web.R;
import c.g.a.x.module_web.databinding.ActivityWebviewBinding;


@Route(path = Constant.WEBVIEW_ACTIVITY)
public class WebViewActivity extends MvpActivity<ActivityWebviewBinding, Presenter> implements Contract.View {

    @Autowired()
    public String title;
    @Autowired()
    public String url;

    private CommonTitleBar commonTitlebar;

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void getDataFromPreActivity() throws Exception {

    }

    @Override
    protected void initView() {
        commonTitlebar = new CommonTitleBar(this).setStyleWhite().setTitle(title);

        binder.webview.getSettings().setDefaultTextEncodingName("utf-8");
        binder.webview.getSettings().setSupportZoom(false);
        binder.webview.getSettings().setBuiltInZoomControls(false);
        binder.webview.setWebViewClient(new MyWebViewClient());
        binder.webview.setWebChromeClient(new MyWebChromeClient());
        binder.webview.getSettings().setJavaScriptEnabled(true);
        binder.webview.getSettings().setDomStorageEnabled(true);
//        webview.getSettings()
//                .setUserAgentString(
//                        "Mozilla/5.0 (Linux; U; Android 2.2; en-gb; Nexus One Build/FRF50) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile
//                        Safari/533.1");
//        webview.getSettings()
//                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        binder.webview.getSettings().setUseWideViewPort(true);
        binder.webview.getSettings().setLoadWithOverviewMode(true);
        binder.webview.addJavascriptInterface(new JsUtils(getActivity(), binder.webview), "callClass");

    }


    @Override
    protected void initData() {
        binder.webview.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

            new AlertDialog.Builder(context)
                    .setTitle("提示信息")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> result.confirm())
                    .setOnKeyListener((dialog, keyCode, event) -> true)
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }

        // 处理javascript中的confirm
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

            new AlertDialog.Builder(context)
                    .setTitle("提示信息")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> result.confirm())
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> result.cancel())
                    .setOnKeyListener((dialog, keyCode, event) -> true)
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }

        /**
         * 覆盖默认的window.prompt
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {

            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);

            new AlertDialog.Builder(context)
                    .setTitle("提示信息")
                    .setMessage(message)
                    .setView(et)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> result.confirm(et.getText().toString()))
                    .setNeutralButton(android.R.string.cancel, (dialog, which) -> result.cancel())
                    .setOnKeyListener((dialog, keyCode, event) -> true)
                    .setCancelable(false)
                    .create().show();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String titleStr) {
            super.onReceivedTitle(view, titleStr);
            if (TextUtils.isEmpty(title)) commonTitlebar.titlebar_center.setText(titleStr);
        }
    }
}