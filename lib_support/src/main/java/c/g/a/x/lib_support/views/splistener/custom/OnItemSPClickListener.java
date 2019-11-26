package c.g.a.x.lib_support.views.splistener.custom;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2017/9/18.
 */


public abstract class OnItemSPClickListener extends BaseSPListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!canClick()) return;
        onItemClickSucc(parent, view, position, id);
    }

    public abstract void onItemClickSucc(AdapterView<?> parent, View view, int position, long id);
}
