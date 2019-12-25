package c.g.a.x.module_main.main;

import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.android.utils.NotificationHelper;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityMainBinding;
import c.g.a.x.module_main.main.index.IndexFragment;
import c.g.a.x.module_main.main.mine.MineFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;


@Route(path = Constant.MAIN_ACTIVITY)
public class MainActivity extends MvpActivity<ActivityMainBinding, Presenter> implements Contract.View {

    private final int[] rbIds = new int[]{
            R.id.rb_main_index
            , R.id.rb_main_mine
    };

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }


    @Override
    protected void initView() {
        NotificationHelper.checkEnabledDialog(context);

        MainFragmentStateAdapter adapter = new MainFragmentStateAdapter(this);
        adapter.list.add(new IndexFragment());
        adapter.list.add(new MineFragment());

        binder.vpContainer.setAdapter(adapter);
        binder.vpContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binder.rgMain.check(rbIds[position]);
            }
        });

        binder.rgMain.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < rbIds.length; i++) {
                if (rbIds[i] != checkedId) continue;
                binder.vpContainer.setCurrentItem(i);
                break;
            }
        });
        binder.rgMain.check(rbIds[0]);
        binder.vpContainer.setCurrentItem(0);

//        ImageLoader.loadHead(context, binder.iv1, "http://f.hiphotos.baidu.com/zhidao/pic/item/3c6d55fbb2fb4316984c0f4122a4462309f7d3be.jpg");
//        ImageLoader.load(context, binder.iv2, "http://f.hiphotos.baidu.com/zhidao/pic/item/3c6d55fbb2fb4316984c0f4122a4462309f7d3be.jpg");

//        PlaylistBox box = new PlaylistBox();
//        box.deleteAll();
//        Playlist vo = new Playlist();
//        vo.name = "连佳凡";
//        box.insert(vo);
//        vo = box.select("gdl");
//        Logger.e(vo.id, "======== ", vo.name);


    }


    @Override
    protected void initData() {
    }


    @AfterPermissionGranted(Presenter.REQUEST_CODE_4_DOSOMETHING)
    @Override
    public void doSomeThing() {
    }

}
