//package c.g.a.x.lib_support.android.utils;
//
//import android.content.Context;
//import android.media.AudioAttributes;
//import android.media.AudioManager;
//import android.media.SoundPool;
//import android.os.Build;
//import android.util.SparseArray;
//import android.util.SparseIntArray;
//
//import static android.os.Build.VERSION_CODES.LOLLIPOP;
//
///**
// * Created by Administrator on 2018/7/13.
// * 适合播放声音短，文件小
// * 可以同时播放多种音频
// * 消耗资源较小
// */
//public final class SoundMnger {
//
//    private Context context;
//
//    private SoundPool soundPool;
//
//    private SparseIntArray rawid_soundid_map;
//
//    private SparseArray<SoundInfo> soundid_info_map;
//
//    private static volatile SoundMnger soundMnger;
//
//    public static SoundMnger getInstance(Context context) {
//        if (soundMnger == null) {
//            synchronized (SoundMnger.class) {
//                if (soundMnger == null) {
//                    soundMnger = new SoundMnger(context, 5);
//                }
//            }
//        }
//        return soundMnger;
//    }
//
//    private SoundMnger(Context context, int size) {
//        this.context = context.getApplicationContext();
//
//        rawid_soundid_map = new SparseIntArray(size);
//        soundid_info_map = new SparseArray<>(size);
//
//        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
//            SoundPool.Builder builder = new SoundPool.Builder();
//
//            builder.setMaxStreams(size);        //传入音频的数量
//
//            //AudioAttributes是一个封装音频各种属性的类
//            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
//            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
//
//            builder.setAudioAttributes(attrBuilder.build());
//
//            soundPool = builder.build();
//        } else {
//            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
//            soundPool = new SoundPool(size, AudioManager.STREAM_MUSIC, 0);
//        }
//
//        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
//            SoundInfo info = soundid_info_map.get(sampleId);
//            if (info == null) return;
//            info.prepare = true;
//
//            if (info.play) playBySoundId(info.soundId);
//        });
//    }
//
//
//    public void load2Play(int rawId) {
//        load(rawId);
//        playByRawId(rawId);
//    }
//
//    public synchronized void load(int rawId) {
//
//        int soundId = rawid_soundid_map.get(rawId);
//        SoundInfo info = soundid_info_map.get(soundId);
//
//        if (info != null) return;
//
//        info = new SoundInfo();
//        info.rawId = rawId;
//        //第一个参数Context,第二个参数资源Id，第三个参数优先级
//        info.soundId = soundPool.load(context, rawId, 0);
//
//        rawid_soundid_map.put(rawId, info.soundId);
//        soundid_info_map.put(info.soundId, info);
//    }
//
//    public void playByRawId(int rawId) {
//        Integer soundId = rawid_soundid_map.get(rawId);
//        playBySoundId(soundId);
//    }
//
//    private synchronized void playBySoundId(Integer soundId) {
//        SoundInfo info = soundid_info_map.get(soundId);
//
//        if (info == null) return;
//
//        info.play = true;
//
//        if (!info.prepare) return;
//
//        //第一个参数id，即传入池中的顺序，
//        // 第二个和第三个参数为左右声道，
//        // 第四个参数为优先级，
//        // 第五个是否循环播放，0不循环，-1循环
//        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放
//        info.streamId = soundPool.play(info.soundId, 1, 1, 0, 0, 1);
//    }
//
//    public synchronized void unloadByRawId(Integer rawId) {
//        int soundId = rawid_soundid_map.get(rawId);
//        soundPool.unload(soundId);
//        soundid_info_map.remove(soundId);
//        rawid_soundid_map.delete(rawId);
//    }
//
//
//    public synchronized void unloadAll() {
//        for (int i = 0; i < soundid_info_map.size(); i++) {
//            soundPool.unload(soundid_info_map.keyAt(i));
//        }
//        rawid_soundid_map.clear();
//        soundid_info_map.clear();
//    }
//
//    public void release() {
//        unloadAll();
//        soundPool.release();
//        soundPool = null;
//    }
//
//    public class SoundInfo {
//        int rawId;
//        int soundId;
//        int streamId;
//
//        volatile boolean prepare;
//        volatile boolean play;
//    }
//}
