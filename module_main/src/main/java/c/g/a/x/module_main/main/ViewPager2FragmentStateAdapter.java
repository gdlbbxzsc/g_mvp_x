package c.g.a.x.module_main.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2FragmentStateAdapter extends FragmentStateAdapter {

    List<Fragment> list = new ArrayList<>(4);

    public ViewPager2FragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }
}