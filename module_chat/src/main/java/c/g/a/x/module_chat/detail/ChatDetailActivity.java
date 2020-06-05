package c.g.a.x.module_chat.detail;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.module_chat.R;
import c.g.a.x.module_chat.databinding.ActivityChatDetailBinding;

//这里被注释的部分是消息界面的逻辑功能  解开进行相关修改即可
@Route(path = Constant.LOGIN_ACTIVITY)
public class ChatDetailActivity extends MvpActivity<ActivityChatDetailBinding, Presenter> implements Contract.View {


    private KeyboardType keyboardTypeNow = KeyboardType.NONE;

    @Override
    protected int layoutResID() {
        return R.layout.activity_chat_detail;
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }


    @Override
    protected void initView() {
    }


    @Override
    protected void initData() {
    }


    @Override
    protected void onStop() {
        AndroidUtils.hideSoftInput(this);
        super.onStop();
    }

    //
//    @Override
//    protected void onDestroy() {//取消对服务器发送来的消息的订阅
//        RxBus.removeDisposable0(ChatDetailActivity.this.toString());
//        super.onDestroy();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
    //先关闭软键盘和自定义键盘后才能关闭界面
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (isKeyboardOtherVisibility()) {
//                changeKeyboard(KeyboardType.NONE);
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void listenerMsg() {//订阅服务器发来的消息
//        RxBus.register0(ChatDetailActivity.this.toString(), Message.class, message -> {
//            Message.BodyBean body = message.getBody();
//            //客户端 发送的消息 成功失败
//            if (body.getReadType() == 0 && message.getSource().equals(MQTTConfigInfo.getInstance().topicSelf)) {
//                //更新客户端消息状态为发送成功或失败 这里因为是引用传递，所以自己发送的消息和服务端状态更改的消息是同一个对象，所以直接更新界面即可
//                adapter.notifyDataSetChanged();
//                return;
//            }
//
//
////                服务端 发送的消息
//            if (body.getReadType() == 0 && message.getSource().equals(MQTTConfigInfo.getInstance().targetTopic)) {
////                    将服务端消息添加到消息列表中
//                addTime(message);//根据两条消息之间的时间差 判断是否要在界面上展示消息发送时间
//
//                adapter.addData(message);
//                adapter.notifyDataSetChanged();
//                //            收到服务端消息后回复给服务端一条消息 告知 客户端已读 这个逻辑需要具体问题具体分析
//                connection.mService.publish(Message.createReadMsg());
//                return;
//            }
////下边两个判断分别是 两端发来的 你的消息我已收到并且阅读 的状态消息 具体逻辑具体分析吧
//            //客户端 发送的 已读消息
//            if (body.getReadType() == 1 && message.getSource().equals(MQTTConfigInfo.getInstance().topicSelf)) {
//                return;
//            }
//
//            //服务端 发送的 已读消息
    ////根据服务端发来的消息已读和服务端发来的他收到的消息的最新msgid 将客户端界面上所有客户端消息根据对应msgid之上（比这条消息更旧的消息）的未读状态改为已读
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
    ////下拉加载 数据库中或服务端中保存的消息记录的上50条消息记录
//            loadOldDatas();
//            smartRefreshLayout.finishRefresh();
//        });
//
//        mRealListView = findViewById(R.id.lv_chat);
//        adapter = new ChatDetailAdapter(this, mRealListView);
//        mRealListView.setAdapter(adapter);
//        mRealListView.setOnTouchListener((v, event) -> {//如果用户触摸消息列表将所有软键盘都关掉
//            changeKeyboard(KeyboardType.NONE);
//            return false;
//        });
//
//    }
//
//
//    private void initChatKeyBoardFace() {//点击emoji表情
//        cb_keyboard_face = findViewById(R.id.cb_keyboard_face);
//        cb_keyboard_face.setOnClickListener(v -> {
////重复点击emoji表情按钮会在弹出软键盘和表情键盘来回切换
//            if (keyboardTypeNow != KeyboardType.EMOJI) {
//                changeKeyboard(KeyboardType.EMOJI);
//            } else {
//                changeKeyboard(KeyboardType.TEXT);
//            }
    ////如果是点击了对应的软键盘那么消息列表自动滑动到最新一条数据上
//            mRealListView.setSelection(adapter.getCount() - 1);
//        });
//    }
//
//    private void initChatKeyBoardMore() {//逻辑同上 更多按钮
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
//        eedt_keyboard.setOnClickListener(v -> {//弹出软键盘
//            changeKeyboard(KeyboardType.TEXT);
//
//            mRealListView.setSelection(adapter.getCount() - 1);
//        });
//    }
//
//    private void initChatKeyBoardSubmit() {//点击发送按钮 发送消息
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
////先将数据加到消息列表上 再根据服务端消息状态修改状态
//        adapter.addData(msg);
//        adapter.notifyDataSetChanged();
//发送到服务器上
//        connection.mService.publish(msg);
//    }
//
////用你当前发送的消息和最近一条消息的时间来判断是否展示一下消息发送时间
//    private void addTime(Message msg) {
//        Message last = null;
//        for (int i = adapter.getCount() - 1; i >= 0; i--) {
//            Object object = adapter.getItem(i);
//            if (object instanceof Message) {
//                last = (Message) object;
//                break;
//            }
//        }
//        if (last == null) {//界面上一条消息也没有则展示一下发送时间
//            adapter.addData(DateHelper.getDateMnger(msg.getBody().getCreateTime()).getString(DateHelper.Pattern.PATTERN_D2_T2_1));
//        } else {//根据聊天数据间隔 展示不同样式的时间格式
//            String timeStr = createTime(msg.getBody().getCreateTime(), last.getBody().getCreateTime());
//            if (timeStr != null) {
//                adapter.addData(timeStr);
//            }
//        }
//    }
//
//    private void loadOldDatas() {//加载更旧的数据50条
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
////添加时间
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
// 滑动到对应位置
//        mRealListView.setSelection(pos);
//    }
//
//    private String createTime(long now, long lst) {
//        long time = Math.abs(lst - now);
    ////大于一天时间展示日期时间
//        if (time >= DateHelper.TimeValue.DAY_MILLIS) {//显示日期时间
//            return DateHelper.getDateMnger(now).getString(DateHelper.Pattern.PATTERN_D2_T2_1);
//        } else if (time >= (DateHelper.TimeValue.MIN_MILLIS * 10)) {//显示时间
    ////超过十分钟展示时间
//            return DateHelper.getDateMnger(now).getString(DateHelper.Pattern.PATTERN_T2_1);
//        } else {
//
//        }
//    十分钟内什么也不展示
//        return null;
//    }
// //弹出不同软键盘或受其软键盘
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
    enum KeyboardType {
        NONE, TEXT, EMOJI, MORE
    }
}
