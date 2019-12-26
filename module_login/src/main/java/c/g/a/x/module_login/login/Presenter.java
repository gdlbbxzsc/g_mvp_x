package c.g.a.x.module_login.login;


import c.g.a.x.lib_mvp.base.BasePresenter;

public class Presenter<T extends Contract.View> extends BasePresenter<T> implements Contract.Presenter {


    public Presenter(T baseView) {
        super(baseView);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }


    @Override
    public void getImageCode(String phone) {
//        Httpac.context(getBaseView().getContext()).get(request).progress().toObservable().subscribe((Consumer<ResponseBody>) responseBody -> {
//            byte[] imageBytes = responseBody.bytes();
//            if (null != imageBytes && imageBytes.length > 0) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                getBaseView().getImageCode(bitmap);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                throwable.printStackTrace();
//                SysToast.showToastShort(getBaseView().getContext(), "图片加载异常，点击重试");
//            }
//        });
    }

    @Override
    public void sendSmsCode(String phone) {
        getBaseView().sendSmsCodeSucc();
        getBaseView().sendSmsCodeFail();
    }

    @Override
    public void login(String phone) {
        getBaseView().login();
    }
}
