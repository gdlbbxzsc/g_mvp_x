//package c.g.a.x.lib_support.views.pop;
//
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//
//import c.g.a.x.lib_support.R;
//import c.g.a.x.lib_support.views.adapter.v2.abslistview.DataAdapter;
//import c.g.a.x.lib_support.views.splistener.custom.OnItemSPClickListener;
//import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;
//
//
//public class CommonChooseListPop extends PopupWindow implements PopupWindow.OnDismissListener {
//
//
//
//    public final ListView listView;
//    public final DataAdapter adapter;
//
//
//    final OnItemChooseListener listener;
//
//
//    public CommonChooseListPop(Context context, final OnItemChooseListener listener, Class viewHolder) {
//        super(context);
//        this.listener = listener;
//
////        setBackgroundDrawable(new BitmapDrawable());
//        setBackgroundDrawable(new ColorDrawable(0x88323232));
//
//        setWidth(LayoutParams.MATCH_PARENT);
//        setHeight(LayoutParams.MATCH_PARENT);
//
//        setFocusable(true);
//        setOutsideTouchable(true);
//
//        setOnDismissListener(this);
//
//        View view =  LayoutInflater.from(context).inflate(R.layout.pop_common_choose_list, null);
//
//        setContentView(view);
//
//        LinearLayout layout = view.findViewById(R.id.linearLayout);
//        layout.setOnClickListener(new OnSPClickListener() {
//            @Override
//            public void onClickSucc(View v) {
//                dismiss();
//            }
//        });
//
//        listView = view.findViewById(R.id.listView);
//
//        adapter = new DataAdapter(context, listView, viewHolder).singleChoice();
//
//
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new OnItemSPClickListener() {
//            @Override
//            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
//                adapter.choiceHelper.putChoiceNotify(position);
//
//                Object obj = adapter.getItem(position);
//                listener.onItemChoose(obj);
//
//                dismiss();
//            }
//        });
//        update();
//    }
//
//    public void show(View view) {
//        if (adapter.getCount() <= 0) return;
//
////        showAsDropDown(view, 0, 0);
//        showAtLocation(view, Gravity.CENTER, 0, 0);
////            showAsDropDown(view, 0, -view.getHeight());
//
////        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
////        lp.alpha = 0.4f;
////        context.getWindow().setAttributes(lp);
//    }
//
//    @Override
//    public void onDismiss() {
//    }
//
//    public interface OnItemChooseListener {
//        void onItemChoose(Object obj);
//    }
//
//}
