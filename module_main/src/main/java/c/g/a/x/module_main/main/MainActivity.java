package c.g.a.x.module_main.main;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.android.utils.NotificationHelper;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.lib_support.views.view.FlowLayout;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityMainBinding;
import pub.devrel.easypermissions.AfterPermissionGranted;


@Route(path = Constant.MAIN_ACTIVITY)
public class MainActivity extends MvpActivity<ActivityMainBinding, Presenter> implements Contract.View {


    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    protected void initView() {

        NotificationHelper.checkEnabledDialog(context);
        //        ApkVersionHelper.getInstance(context).getVersion();

//        GlobalApplication application = (GlobalApplication) getApplication();
//        application.checkLogin4GoLogin(context);

//        IndexFragment fragment = IndexFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).commit();

//        SpDefaultHelper sp = (SpDefaultHelper) SpMnger.getInstance().open();
//        sp.putRemember(true);
//
//        SpMnger.getDefault().putRemember(false);
//        Logger.e(sp.isRemember());

//        View tv = findViewById(R.id.textView);
//        new SPClick<>(tv).subscribe(view -> {
//            Logger.e("===");
//        });

//        MoneyHelper helper = MoneyHelper.getInstance4Fen("1234567890");
//        helper.change2Yuan();
//        Logger.e("========", helper.getDouble());
//        Logger.e("========", helper.getLong().toString());
//        Logger.e("========", helper.getStringNormal());
//        Logger.e("========", helper.getStringNormalTrimZero());
//        Logger.e("========", helper.getStringQuantile());
//        Logger.e(StringUtils.encryptIDCard("0123456789abcd"));
//        Logger.e(StringUtils.encryptMobile("12345678901"));
//        Logger.e(StringUtils.encrypt("1234567890abc", 3, 3));
//        Logger.e(StringUtils.encrypt("1234567890abc", 3, 3, "qw"));

//        5时8分
//        Logger.e("sssss=====>", new DateHelper(18502922).getDvalue(0).getString(DateHelper.Pattern.PATTERN_D3_T3_1), " ", DateHelper.Pattern.PATTERN_D3_T3_1);
//        Logger.e("sssss=====>", new DateHelper(18502922).getDvalue(0).getString(DateHelper.Pattern.PATTERN_D2_T3_2), " ", DateHelper.Pattern.PATTERN_D2_T3_2);
//        Logger.e("sssss=====>", new DateHelper(18502922).getDvalue(0).getString(DateHelper.Pattern.PATTERN_D2_T2_2), " ", DateHelper.Pattern.PATTERN_D2_T2_2);
//        Logger.e("sssss=====>", new DateHelper(18502922).getDvalue(0).getString(DateHelper.Pattern.PATTERN_T2_2), " ", DateHelper.Pattern.PATTERN_T2_2);
//        Logger.e("sssss=====>", new DateHelper(18502922).getDvalue(0).getString(DateHelper.Pattern.PATTERN_T2_1), " ", DateHelper.Pattern.PATTERN_T2_1);
//        Logger.e("sssss=====>", new DateHelper(18502922).getDvalue(0).getString("HHmmss"), " ", "HHmmss");
//        Logger.e("sssss=====>", new DateHelper().getDvalue(0).getString(DateHelper.Pattern.PATTERN_D3_T3_1), " ", DateHelper.Pattern.PATTERN_D3_T3_1);
//        Logger.e("sssss=====>", new DateHelper().getDvalue(0).getString(DateHelper.Pattern.PATTERN_D2_T3_2), " ", DateHelper.Pattern.PATTERN_D2_T3_2);
//        Logger.e("sssss=====>", new DateHelper().getDvalue(0).getString(DateHelper.Pattern.PATTERN_D2_T2_2), " ", DateHelper.Pattern.PATTERN_D2_T2_2);
//        Logger.e("sssss=====>", new DateHelper().getDvalue(0).getString(DateHelper.Pattern.PATTERN_T2_2), " ", DateHelper.Pattern.PATTERN_T2_2);
//        Logger.e("sssss=====>", new DateHelper().getDvalue(0).getString(DateHelper.Pattern.PATTERN_T2_1), " ", DateHelper.Pattern.PATTERN_T2_1);

        viewDataBinding.FlowLayout.setLayoutItemCreater(new FlowLayout.FlowLayoutItemCreater<TextView, String>() {
            @Override
            public int getLayoutId() {
                return R.layout.layout_tv;
            }

            @Override
            public void initItem(int i, TextView view, String vo) {
                view.setText(vo);
            }

            @Override
            public void onFlowLayoutItemClick(int i, TextView view, String vo, boolean click) {
                SysToast.showToastShort(context, vo);
            }


        });
        viewDataBinding.FlowLayout.setMaxLines(3);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("abc" + i);
        }
        viewDataBinding.FlowLayout.setDatas(list);
    }


    @Override
    protected void initData() {
    }


    @AfterPermissionGranted(Presenter.REQUEST_CODE_4_DOSOMETHING)
    @Override
    public void doSomeThing() {
//        UpVersionService.start(context, "http://sjws.ssl.qihucdn.com/mobile/shouji360/360safesis/20180620-1605/360MobileSafe_7.7.6.1018.apk");
    }

}
