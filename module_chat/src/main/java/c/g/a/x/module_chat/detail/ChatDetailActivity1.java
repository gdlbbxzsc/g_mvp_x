//package com.pbph.module_mqtt.activity;
//
//import android.app.Activity;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ListView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.pbph.module_mqtt.R;
//import com.pbph.module_mqtt.bean.Message;
//import com.pbph.module_mqtt.db.DBMnger;
//import com.pbph.module_mqtt.db.impl.MsgRecordDao;
//import com.pbph.module_mqtt.db.impl.MsgRecordVo;
//import com.pbph.module_mqtt.http.http.HttpAction;
//import com.pbph.module_mqtt.http.model.reponse.GetClientParamResponse;
//import com.pbph.module_mqtt.http.model.reponse.GetMqttUserDetailsResponse;
//import com.pbph.module_mqtt.http.model.request.GetClientParamRequest;
//import com.pbph.module_mqtt.http.model.request.GetMqttUserDetailsRequest;
//import com.pbph.module_mqtt.http.rxjava2.filterobserver.BaseObserver;
//import com.pbph.module_mqtt.mqtt.MQTTConfigInfo;
//import com.pbph.module_mqtt.mqtt.MyMqttService;
//import com.pbph.module_mqtt.mqtt.MyMqttServiceConnection;
//import com.pbph.module_mqtt.utils.AndroidUtils;
//import com.pbph.module_mqtt.utils.DateHelper;
//import com.pbph.module_mqtt.utils.RxBus;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.header.ClassicsHeader;
//
//import java.util.List;
//
//import c.g.a.x.module_chat.detail.MoreFragment;
//import c.g.a.x.module_chat.detail.adapter.ChatDetailAdapter;
//import io.github.rockerhieu.emojicon.EmojiconGridFragment;
//import io.github.rockerhieu.emojicon.EmojiconsFragment;
//import io.github.rockerhieu.emojicon.emoji.Emojicon;
//
//
//public class ChatDetailActivity extends AppCompatActivity implements
//        EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener, MoreFragment.OnImageChooseListener {
//
//    private Context context;
//    private InputMethodManager inputMethodManager;
//
//
//
//    public SmartRefreshLayout smartRefreshLayout;
//    private ListView mRealListView;
//    private ChatDetailAdapter adapter;
//
//
//    private CheckBox cb_keyboard_face;
//    private CheckBox cb_keyboard_more;
//    private EditText eedt_keyboard;
//    private Button btn_keyboard;
//
//    private FrameLayout rl_other_keyboard;
//
//    private EmojiconsFragment emojiconsFragment;
//    private MoreFragment moreFragment;
//
//
//    MyMqttServiceConnection connection = new MyMqttServiceConnection();
//    private KeyboardType keyboardTypeNow = KeyboardType.NONE;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = this;
//
//        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        setContentView(R.layout.activity_chat_detail);
//
//        initListView();
//
//        initChatKeyBoardFace();
//        initChatKeyBoardMore();
//        initChatKeyBoardEditText();
//        initChatKeyBoardSubmit();
//
//
//        rl_other_keyboard = findViewById(R.id.rl_other_keyboard);
//        emojiconsFragment = EmojiconsFragment.newInstance(true);
//        moreFragment = MoreFragment.newInstance();
//        moreFragment.setListener(this);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.rl_other_keyboard, emojiconsFragment)
//                .hide(emojiconsFragment)
//                .add(R.id.rl_other_keyboard, moreFragment)
//                .hide(moreFragment)
//                .commit();
//
//        listenerMsg();
//
//        getClientParam();
//
////        TimerTask task = new TimerTask() {
////            @Override
////            public void run() {
////                for (int i = 0; i < 100; i++) {
////                    Message msg = Message.createSendMsg(String.valueOf(i), 1);
////                    adapter.addData(msg);
////                    handler.sendEmptyMessage(1);
////                    connection.mService.publish(msg);
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////
////            }
////        };
////        Timer timer = new Timer();
////        timer.schedule(task, 5000);
//
////    Handler handler = new Handler() {
////        @Override
////        public void handleMessage(@NonNull android.os.Message msg) {
////            super.handleMessage(msg);
////            adapter.notifyDataSetChanged();
////        }
////    };
//
//    }
//
//    void getClientParam() {
//
//        GetClientParamRequest<GetClientParamResponse> request = new GetClientParamRequest<>();
//        request.username = "13333333333";
//
//        HttpAction.<GetClientParamResponse>context(context).post(request).progress().subscribe(new BaseObserver<GetClientParamResponse>(context) {
//
//            @Override
//            public void onNextDo(GetClientParamResponse vo) {
//                GetClientParamResponse.DataBean data = vo.getData();
//                MQTTConfigInfo.getInstance().setData(data);
//                bindService(new Intent(context, MyMqttService.class), connection, Service.BIND_AUTO_CREATE);
//
//            }
//        });
//    }
//
//    void GetMqttUserDetails() {
//
//        GetMqttUserDetailsRequest<GetMqttUserDetailsResponse> request = new GetMqttUserDetailsRequest<>();
//
//        request.topic = "ddddd";
//
//        HttpAction.<GetMqttUserDetailsResponse>context(context).post(request).progress().subscribe(new BaseObserver<GetMqttUserDetailsResponse>(context) {
//
//            @Override
//            public void onNextDo(GetMqttUserDetailsResponse vo) {
//
//                if (vo.getCode() != 200) {
//                    return;
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onEmojiconClicked(Emojicon emojicon) {
//        EmojiconsFragment.input(eedt_keyboard, emojicon);
//    }
//
//    @Override
//    public void onEmojiconBackspaceClicked(View v) {
//        EmojiconsFragment.backspace(eedt_keyboard);
//    }
//
//    @Override
//    public void onImageChoose(String url) {
//        sendMyMsg(Message.createSendMsg(url, 2));
//    }
//
//    @Override
//    protected void onStop() {
//        AndroidUtils.hideSoftInput(this);
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        RxBus.removeDisposable0(ChatDetailActivity.this.toString());
//        super.onDestroy();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (isKeyboardOtherVisibility()) {
//                changeKeyboard(KeyboardType.NONE);
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void listenerMsg() {
//        RxBus.register0(ChatDetailActivity.this.toString(), Message.class, message -> {
//            Message.BodyBean body = message.getBody();
//            //客户端 发送的消息 成功失败
//            if (body.getReadType() == 0 && message.getSource().equals(MQTTConfigInfo.getInstance().topicSelf)) {
//                //更新客户端消息状态为发送成功或失败
//                adapter.notifyDataSetChanged();
//                return;
//            }
//
//
////                服务端 发送的消息
//            if (body.getReadType() == 0 && message.getSource().equals(MQTTConfigInfo.getInstance().targetTopic)) {
////                    将服务端消息添加到消息列表中
//                addTime(message);
//
//                adapter.addData(message);
//                adapter.notifyDataSetChanged();
//                //            收到服务端消息后回复给服务端 客户端已读
//                connection.mService.publish(Message.createReadMsg());
//                return;
//            }
//
//            //客户端 发送的 已读消息
//            if (body.getReadType() == 1 && message.getSource().equals(MQTTConfigInfo.getInstance().topicSelf)) {
//                return;
//            }
//
//            //服务端 发送的 已读消息
//            if (body.getReadType() == 1 && message.getSource().equals(MQTTConfigInfo.getInstance().targetTopic)) {
//                String sid = message.getBody().getMsgId();
////                    将这条消息以前的所有消息都设为已读
//
//                boolean jump = true;
//
//                for (int i = adapter.getCount() - 1; i >= 0; i--) {
//                    Object obj = adapter.getItem(i);
//
//                    if (!(obj instanceof Message)) continue;
//
//                    Message temp = (Message) obj;
//                    Message.BodyBean tbody = temp.getBody();
//                    //这个判断如下：首先要找到服务端msgid和消息列表中的msgid相等的那条数据才开始未读转已读，否则不进行未读转已读修改
//                    if (jump) {
//                        if (!tbody.getMsgId().equals(sid)) continue;
//                        jump = false;
//                    }
//
//                    if (!temp.getSource().equals(MQTTConfigInfo.getInstance().topicSelf))
//                        continue;
//
//
//                    if (tbody.getReadType() != 0) continue;
//                    tbody.setReadType(1);
//
//                }
//                adapter.notifyDataSetChanged();
//                return;
//            }
//        });
//    }
//
//    private void initListView() {
//        smartRefreshLayout = findViewById(R.id.refreshLayout);
//        smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
//        smartRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
//        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(context));
//        smartRefreshLayout.setHeaderHeight(60);
//
//        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
//            loadOldDatas();
//            smartRefreshLayout.finishRefresh();
//        });
//
//        mRealListView = findViewById(R.id.lv_chat);
//        adapter = new ChatDetailAdapter(this, mRealListView);
//        mRealListView.setAdapter(adapter);
//        mRealListView.setOnTouchListener((v, event) -> {
//            changeKeyboard(KeyboardType.NONE);
//            return false;
//        });
//
//    }
//
//
//    private void initChatKeyBoardFace() {
//        cb_keyboard_face = findViewById(R.id.cb_keyboard_face);
//        cb_keyboard_face.setOnClickListener(v -> {
//
//            if (keyboardTypeNow != KeyboardType.EMOJI) {
//                changeKeyboard(KeyboardType.EMOJI);
//            } else {
//                changeKeyboard(KeyboardType.TEXT);
//            }
//            mRealListView.setSelection(adapter.getCount() - 1);
//        });
//    }
//
//    private void initChatKeyBoardMore() {
//        cb_keyboard_more = findViewById(R.id.cb_keyboard_more);
//        cb_keyboard_more.setOnClickListener(v -> {
//            if (keyboardTypeNow != KeyboardType.MORE) {
//                changeKeyboard(KeyboardType.MORE);
//            } else {
//                changeKeyboard(KeyboardType.TEXT);
//            }
//            mRealListView.setSelection(adapter.getCount() - 1);
//        });
//    }
//
//    private void initChatKeyBoardEditText() {
//        eedt_keyboard = findViewById(R.id.eedt_keyboard);
//        eedt_keyboard.setOnClickListener(v -> {
//            changeKeyboard(KeyboardType.TEXT);
//
//            mRealListView.setSelection(adapter.getCount() - 1);
//        });
//    }
//
//    private void initChatKeyBoardSubmit() {
//        btn_keyboard = findViewById(R.id.btn_keyboard);
//        btn_keyboard.setOnClickListener(v -> {
//            String content = eedt_keyboard.getText().toString();
//            if (TextUtils.isEmpty(content)) return;
//
//            eedt_keyboard.setText(null);
//
//            sendMyMsg(Message.createSendMsg(content, 1));
//        });
//    }
//
//
//
//
//    private void sendMyMsg(Message msg) {
//        addTime(msg);
//
//        adapter.addData(msg);
//        adapter.notifyDataSetChanged();
//
//        connection.mService.publish(msg);
//    }
//
//
//    private void addTime(Message msg) {
//        Message last = null;
//        for (int i = adapter.getCount() - 1; i >= 0; i--) {
//            Object object = adapter.getItem(i);
//            if (object instanceof Message) {
//                last = (Message) object;
//                break;
//            }
//        }
//        if (last == null) {
//            adapter.addData(DateHelper.getDateMnger(msg.getBody().getCreateTime()).getString(DateHelper.Pattern.PATTERN_D2_T2_1));
//        } else {
//            String timeStr = createTime(msg.getBody().getCreateTime(), last.getBody().getCreateTime());
//            if (timeStr != null) {
//                adapter.addData(timeStr);
//            }
//        }
//    }
//
//    private void loadOldDatas() {
//        Message fst = null;
//        for (int i = 0; i < adapter.getCount(); i++) {
//            Object object = adapter.getItem(i);
//            if (object instanceof Message) {
//                fst = (Message) object;
//                break;
//            }
//        }
//        MsgRecordDao dao = DBMnger.getInstance(context).msgRecordDao();
//        List<MsgRecordVo> list;
//        if (fst == null) {
//            list = dao.selectPage();
//        } else {
//            list = dao.selectPage(fst.getBody().getMsgId());
//        }
//
//        if (list == null || list.isEmpty()) return;
//
//        int pos = 0;
//        for (int i = 0; i < list.size(); i++) {
//            MsgRecordVo vo = list.get(i);
//
//            Message message = Message.createMessage(vo);
//
//            if (adapter.getCount() > 0) {
//                Object object = adapter.getItem(0);
//                if (object instanceof Message) {
//                    Message zero = (Message) object;
//
//                    String timeStr = createTime(zero.getBody().getCreateTime(), message.getBody().getCreateTime());
//                    if (timeStr != null) {
//                        adapter.addData(0, timeStr);
//                        pos++;
//                    }
//                }
//            }
//            adapter.addData(0, message);
//            pos++;
//        }
//        adapter.notifyDataSetChanged();
//
//        mRealListView.setSelection(pos);
//    }
//
//    private String createTime(long now, long lst) {
//        long time = Math.abs(lst - now);
//        if (time >= DateHelper.TimeValue.DAY_MILLIS) {//显示日期时间
//            return DateHelper.getDateMnger(now).getString(DateHelper.Pattern.PATTERN_D2_T2_1);
//        } else if (time >= (DateHelper.TimeValue.MIN_MILLIS * 10)) {//显示时间
//            return DateHelper.getDateMnger(now).getString(DateHelper.Pattern.PATTERN_T2_1);
//        } else {
//
//        }
//        return null;
//    }
//
//    private void changeKeyboard(KeyboardType keyboardType) {
//        switch (keyboardType) {
//            case NONE:
//                keyboardEmojiVisibility(false);
//                keyboardMoreVisibility(false);
//
//                keyboardTextVisibility(false);
//                keyboardOtherVisibility(false);
//                break;
//            case TEXT:
//                keyboardEmojiVisibility(false);
//                keyboardMoreVisibility(false);
//
//                keyboardOtherVisibility(false);
//                keyboardTextVisibility(true);
//                break;
//            default:
//                keyboardTextVisibility(false);
//                keyboardOtherVisibility(true);
//
//                switch (keyboardType) {
//                    case EMOJI:
//                        keyboardMoreVisibility(false);
//                        keyboardEmojiVisibility(true);
//                        break;
//                    case MORE:
//                        keyboardEmojiVisibility(false);
//                        keyboardMoreVisibility(true);
//                        break;
//                }
//                break;
//        }
//
//        keyboardTypeNow = keyboardType;
//    }
//
//    private void keyboardTextVisibility(boolean b) {
//        if (b) {
//            // 显示软键盘
//            eedt_keyboard.requestFocus();
//            inputMethodManager.showSoftInput(eedt_keyboard, 0);
//
//        } else {
//            // 隐藏软键盘
//            Activity activity = (Activity) context;
//            if (inputMethodManager.isActive() && activity.getCurrentFocus() != null) {
//                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//            }
//        }
//    }
//
//    private void keyboardOtherVisibility(boolean b) {
//        if (b) {
//            if (!isKeyboardOtherVisibility()) {
//                new Handler().postDelayed(() -> rl_other_keyboard.setVisibility(View.VISIBLE), 50);
//            }
//        } else {
//            rl_other_keyboard.setVisibility(View.GONE);
//        }
//    }
//
//
//    private void keyboardEmojiVisibility(boolean b) {
//        cb_keyboard_face.setChecked(b);
//        if (b) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .show(emojiconsFragment)
//                    .commit();
//        } else {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .hide(emojiconsFragment)
//                    .commit();
//        }
//    }
//
//    private void keyboardMoreVisibility(boolean b) {
//        cb_keyboard_more.setChecked(b);
//        if (b) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .show(moreFragment)
//                    .commit();
//        } else {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .hide(moreFragment)
//                    .commit();
//        }
//    }
//
//
//    public final boolean isKeyboardOtherVisibility() {
//        return rl_other_keyboard.getVisibility() == View.VISIBLE;
//    }
//
//    enum KeyboardType {
//        NONE, TEXT, EMOJI, MORE
//    }
//}
