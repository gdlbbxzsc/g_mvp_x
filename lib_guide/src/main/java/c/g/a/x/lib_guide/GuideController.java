package c.g.a.x.lib_guide;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import c.g.a.x.lib_support.android.utils.AndroidUtils;

public final class GuideController {

    private Activity activity;
    private Fragment fragment;

    private FrameLayout mParentView;
    private PaintLayout paintLayout;

    private List<GuidePage> guidePageList = new ArrayList<>(3);
    private int current = -1;//当前页
    private GuidePage currentGuidePage;

    private boolean isShowing;

    private View.OnClickListener onDismissListener;

    SharedPreferences sharedPreferences;

    public GuideController(Activity activity) {
        this.activity = activity;
        mParentView = activity.findViewById(android.R.id.content);

        sharedPreferences = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public GuideController(Fragment fragment) {
        this(fragment.getActivity());
        this.fragment = fragment;
    }

    public void create() {
        if (isShowing) return;
        isShowing = true;
        mParentView.post(new Runnable() {
            @Override
            public void run() {
                current = -1;
                currentGuidePage = null;

                addListenerFragment();
                showNxt();
            }
        });
    }

    public void dismiss() {
        removeListenerFragment();
        current = -1;
        currentGuidePage = null;
        if (paintLayout != null) paintLayout.dismiss();
        paintLayout = null;
        guidePageList.clear();
        isShowing = false;
    }

    public void showNxt() {
        current++;
        if (current >= guidePageList.size()) {
            dismiss();
            return;
        }
        showGuidePage(true);
    }

    public void showPre() {
        current--;
        if (current < 0) {
            current = 0;
            return;
        }
        showGuidePage(false);
    }

    private void showGuidePage(boolean b) {
        String preKey = //
                activity.getClass().getSimpleName() +//
                        "_" +//
                        (fragment == null ? "" : fragment.getClass().getSimpleName()) +//
                        "_";//

        if (!b && currentGuidePage != null) {//上一张时，应该把当前一张的计数器减一
            currentGuidePage.reduceTimes(sharedPreferences, preKey);
        }

        currentGuidePage = guidePageList.get(current);
        
        String versionName;
        try {
            versionName = AndroidUtils.getVersionName(activity);
        } catch (Exception e) {
            e.printStackTrace();
            versionName = "";
        }
        currentGuidePage.reset(sharedPreferences, preKey, versionName);

        if (b) {//上一张后计数器不用再计数。
            if (!currentGuidePage.canShow(sharedPreferences, preKey)) {
                showNxt();
                return;
            }
            currentGuidePage.addTimes(sharedPreferences, preKey);
        }

        if (paintLayout == null) {
            paintLayout = new PaintLayout(activity, this);
            mParentView.addView(paintLayout, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        paintLayout.setGuidePage(currentGuidePage);

        isShowing = true;
    }

    private void addListenerFragment() {
        if (fragment == null) return;
        if (!fragment.isAdded()) return;
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        FragmentStateListener fragmentStateListener = (FragmentStateListener) fragmentManager.findFragmentByTag(FragmentStateListener.class.getSimpleName());
        if (fragmentStateListener == null) {
            fragmentStateListener = new FragmentStateListener();
            fragmentManager.beginTransaction().add(fragmentStateListener, FragmentStateListener.class.getSimpleName()).commitAllowingStateLoss();
        }
        fragmentStateListener.fragmentLifecycleChangeListener = new FragmentLifecycleChangeListener() {
            @Override
            public void onFragmentLifecycleChange(FragmentLifecycleState state) {
                switch (state) {
                    case DestroyView: {
                        dismiss();
                    }
                    break;
                }
            }
        };
    }

    private void removeListenerFragment() {
        if (fragment == null) return;
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        FragmentStateListener fragmentStateListener = (FragmentStateListener) fragmentManager.findFragmentByTag(FragmentStateListener.class.getSimpleName());
        if (fragmentStateListener == null) return;
        fragmentManager.beginTransaction().remove(fragmentStateListener).commitAllowingStateLoss();
    }


    public View.OnClickListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(View.OnClickListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    View.OnClickListener _onTouchCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentGuidePage == null) return;
            if (!currentGuidePage.isTouchCancel()) return;

            View.OnClickListener listener = currentGuidePage.getOnTouchCancelListener();
            if (listener != null) listener.onClick(v);

            showNxt();
        }
    };

    public GuideController addGuidePage(GuidePage guidePage) {
        this.guidePageList.add(guidePage);
        return this;
    }

    private class FragmentStateListener extends Fragment {

        FragmentLifecycleChangeListener fragmentLifecycleChangeListener;

        @Override
        public void onStart() {
            super.onStart();
            if (fragmentLifecycleChangeListener != null)
                fragmentLifecycleChangeListener.onFragmentLifecycleChange(FragmentLifecycleState.Start);
        }

        @Override
        public void onStop() {
            super.onStop();
            if (fragmentLifecycleChangeListener != null)
                fragmentLifecycleChangeListener.onFragmentLifecycleChange(FragmentLifecycleState.Stop);
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            if (fragmentLifecycleChangeListener != null)
                fragmentLifecycleChangeListener.onFragmentLifecycleChange(FragmentLifecycleState.DestroyView);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (fragmentLifecycleChangeListener != null)
                fragmentLifecycleChangeListener.onFragmentLifecycleChange(FragmentLifecycleState.Destroy);
        }
    }

    interface FragmentLifecycleChangeListener {
        void onFragmentLifecycleChange(FragmentLifecycleState state);
    }

    enum FragmentLifecycleState {
        Start, Stop, DestroyView, Destroy
    }

}
