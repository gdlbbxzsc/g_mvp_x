package c.g.a.x.module_main.main;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_guide.GuideController;
import c.g.a.x.lib_guide.GuidePage;
import c.g.a.x.lib_guide.HighLight;
import c.g.a.x.lib_guide.HighLightGuide;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.android.utils.NotificationHelper;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityMainBinding;
import c.g.a.x.module_main.main.index.IndexFragment;
import c.g.a.x.module_main.main.mine.MineFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;


@Route(path = Constant.MAIN_ACTIVITY)
public class MainActivity extends MvpActivity<ActivityMainBinding, Presenter> implements Contract.View {

    private MainFragmentStateAdapter mainAdapter;

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void getDataFromPreActivity() throws Exception {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected void initView() {
        NotificationHelper.checkEnabledDialog(context);

        mainAdapter = new MainFragmentStateAdapter(this);
        mainAdapter.addTabItem(R.id.rb_main_index, new IndexFragment());
        mainAdapter.addTabItem(R.id.rb_main_mine, new MineFragment());

        binder.vpContainer.setAdapter(mainAdapter);
        binder.vpContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binder.rgMain.check(mainAdapter.rbIds.get(position));
            }
        });
        binder.rgMain.setOnCheckedChangeListener((group, checkedId) -> binder.vpContainer.setCurrentItem(mainAdapter.rbIds.indexOf(checkedId)));

        binder.vpContainer.setUserInputEnabled(false); //true:滑动，false：禁止滑动
        binder.rgMain.check(mainAdapter.rbIds.get(0));
        binder.vpContainer.setCurrentItem(0);
        binder.ivMainBottom.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                ARouter.getInstance().build(Constant.LOGIN_ACTIVITY).navigation(context);
            }
        });

    }

    @Override
    protected void initData() {
        test();
    }

    @AfterPermissionGranted(Presenter.REQUEST_CODE_4_DOSOMETHING)
    @Override
    public void doSomeThing() {
    }

    private void test() {
        //        ImageLoader.loadHead(context, binder.iv1, "http://f.hiphotos.baidu.com/zhidao/pic/item/3c6d55fbb2fb4316984c0f4122a4462309f7d3be.jpg");
//        ImageLoader.load(context, binder.iv2, "http://f.hiphotos.baidu.com/zhidao/pic/item/3c6d55fbb2fb4316984c0f4122a4462309f7d3be.jpg");

//        PlaylistBox box = new PlaylistBox();
//        box.deleteAll();
//        Playlist vo = new Playlist();
//        vo.name = "连佳凡";
//        box.insert(vo);
//        vo = box.select("gdl");
//        Logger.e(vo.id, "======== ", vo.name);


//        ApkVersionHelper.UpdateDialog a=ApkVersionHelper.getInstance(context).installTypeDialog().versionCode(10).versionName("11.0").url("sdfds").update();


        GuideController controller = new GuideController(this)
                .addGuidePage(new GuidePage("aaaaaa")
                        .setResetNum(0)
                        .setMaxTimes(10)
                        .addHighLight(new HighLight(binder.ivMainBottom)//
                                .setShapeRectangleRound(20)//
                                .setPadding(20)
                                .setOnClickListener(v -> SysToast.showToastShort(context, "社会我凡哥"))
                                .addRelativeGuide(new HighLightGuide(R.layout.layout_newbie_guide_page)//
                                        .setLayoutType(HighLightGuide.LayoutType.Bottom2Top)//
                                        .setLayoutType(HighLightGuide.LayoutType.Right2Left)
                                        .setOnClickListener(null))))

                .addGuidePage(new GuidePage("bbbb")
                        .setResetNum(0)
                        .setMaxTimes(10)
                        .addHighLight(new HighLight(binder.ivMainBottom)//
                                .setShapeRectangleRound(20)//
                                .setPadding(20)
                                .setOnClickListener(v -> SysToast.showToastShort(context, "社会我凡哥牛逼"))
                                .addRelativeGuide(new HighLightGuide(R.layout.layout_newbie_guide_page)//
                                        .setLayoutType(HighLightGuide.LayoutType.Bottom2Top)//
                                        .setLayoutType(HighLightGuide.LayoutType.Left2Right)
                                        .setOnClickListener(null))));
        controller.create();

    }


}
