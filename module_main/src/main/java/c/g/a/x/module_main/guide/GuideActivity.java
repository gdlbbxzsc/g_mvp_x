package c.g.a.x.module_main.guide;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_sp.AccountSpHelper;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityGuideBinding;


@Route(path = Constant.GUIDE_ACTIVITY)
public class GuideActivity extends MvpActivity<ActivityGuideBinding, Presenter> implements Contract.View {

    private final int[] resIds = {R.drawable.left_arrow_black, R.drawable.left_arrow_white};

    @Override
    protected int layoutResID() {
        return R.layout.activity_guide;
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
        binder.btnGuide.setVisibility(View.INVISIBLE);
        binder.btnGuide.setEnabled(false);
        binder.btnGuide.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                new AccountSpHelper().putFirstUse(false);
                ARouter.getInstance().build(Constant.MAIN_ACTIVITY).navigation();
                finish();
            }
        });

//        binder.vfGuide.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(View v) {
//            }
//
//            @Override
//            public void onViewDetachedFromWindow(View v) {
//            }
//        });
    }


    @Override
    protected void initData() {
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < resIds.length; i++) {
            ImageView iv = new ImageView(context);
            iv.setBackgroundResource(resIds[i]);
            binder.vfGuide.addView(iv);

            RadioButton rb = (RadioButton) inflater.inflate(R.layout.rb_guide, null);
            rb.setId(i);
            binder.rgGuide.addView(rb);
            RadioGroup.LayoutParams layoutParams = (RadioGroup.LayoutParams) rb.getLayoutParams();
            layoutParams.width = layoutParams.height = 20;
            layoutParams.setMargins(20, 0, 20, 0);//4个参数按顺序分别是左上右下
            rb.setLayoutParams(layoutParams);
        }
        binder.rgGuide.check(0);
    }

    private float startX;
    private int indicatorPos = 0;
    private final int max = resIds.length - 1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startX = event.getX();
            }
            break;
            case MotionEvent.ACTION_UP: {
                if (event.getX() - startX > 100) { //向右滑动
                    if (indicatorPos <= 0) break;

                    binder.vfGuide.setOutAnimation(this, R.anim.guide_right_out);
                    binder.vfGuide.setInAnimation(this, R.anim.guide_left_in);
                    binder.vfGuide.showNext();
                    indicatorPos--;
                } else if (startX - event.getX() > 100) {  //向左滑动
                    if (indicatorPos >= max) break;

                    binder.vfGuide.setOutAnimation(this, R.anim.guide_left_out);
                    binder.vfGuide.setInAnimation(this, R.anim.guide_right_in);
                    binder.vfGuide.showPrevious();
                    indicatorPos++;
                }

                binder.rgGuide.check(indicatorPos);

                if (indicatorPos == max) {
                    binder.btnGuide.setVisibility(View.VISIBLE);
                    binder.btnGuide.setEnabled(true);
                } else {
                    binder.btnGuide.setVisibility(View.INVISIBLE);
                    binder.btnGuide.setEnabled(false);
                }
            }
            break;
        }
        return super.onTouchEvent(event);
    }
}
