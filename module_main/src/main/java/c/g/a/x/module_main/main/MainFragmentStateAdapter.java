package c.g.a.x.module_main.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

final class MainFragmentStateAdapter extends FragmentStateAdapter {

    List<Integer> rbIds = new ArrayList<>(4);
    private List<Fragment> list = new ArrayList<>(4);

    MainFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
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

    void addTabItem(int rbId, Fragment fragment) {
        rbIds.add(rbId);
        list.add(fragment);
    }
}