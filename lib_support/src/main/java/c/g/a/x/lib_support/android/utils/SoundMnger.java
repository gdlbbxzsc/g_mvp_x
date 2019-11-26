package c.g.a.x.lib_support.android.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by Administrator on 2018/7/13.
 * 适合播放声音短，文件小
 * 可以同时播放多种音频
 * 消耗资源较小
 */
public final class SoundMnger {

    private Context context;

    SoundPool soundPool;

    Map<Integer, Integer> rawid_soundid_map;

    Map<Integer, SoundInfo> soundid_info_map;

    public static SoundMnger getInstance() {
        return InnerInstance.INSTANCE;
    }


    private static class InnerInstance {
        private static SoundMnger INSTANCE = new SoundMnger();
    }

    private SoundMnger() {
    }

    public SoundMnger init(Context applicationcontext) {
        return this.init(applicationcontext, 1);
    }

    public SoundMnger init(Context applicationcontext, int size) {
        this.context = applicationcontext;

        rawid_soundid_map = new HashMap<>(size);
        soundid_info_map = new HashMap<>(size);

        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            SoundPool.Builder builder = new SoundPool.Builder();

            builder.setMaxStreams(size);        //传入音频的数量

            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);

            builder.setAudioAttributes(attrBuilder.build());

            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(size, AudioManager.STREAM_MUSIC, 0);
        }

        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            SoundInfo info = soundid_info_map.get(sampleId);
            if (info == null) return;
            info.prepare = true;

            if (info.play) playBySoundId(info.soundId);
        });
        return this;
    }

    public void load2Play(int rawId) {
        load(rawId);
        playByRawId(rawId);
    }

    public synchronized void load(int rawId) {

        Integer soundId = rawid_soundid_map.get(rawId);
        SoundInfo info = soundid_info_map.get(soundId);

        if (info != null) return;

        info = new SoundInfo();
        info.rawId = rawId;
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        info.soundId = soundPool.load(context, rawId, 0);

        rawid_soundid_map.put(rawId, info.soundId);
        soundid_info_map.put(info.soundId, info);
    }

    public void playByRawId(int rawId) {
        Integer soundId = rawid_soundid_map.get(rawId);
        playBySoundId(soundId);
    }

    private synchronized void playBySoundId(Integer soundId) {
        SoundInfo info = soundid_info_map.get(soundId);

        if (info == null) return;

        info.play = true;

        if (!info.prepare) return;

        //第一个参数id，即传入池中的顺序，
        // 第二个和第三个参数为左右声道，
        // 第四个参数为优先级，
        // 第五个是否循环播放，0不循环，-1循环
        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放
        info.streamId = soundPool.play(info.soundId, 1, 1, 0, 0, 1);
    }

    public synchronized void unloadByRawId(Integer rawId) {
        Integer soundId = rawid_soundid_map.get(rawId);
        if (soundId == null) return;
        soundPool.unload(soundId);
        soundid_info_map.remove(soundId);
        rawid_soundid_map.remove(rawId);
    }


    public synchronized void unloadAll() {
        Iterator<Integer> iterator = soundid_info_map.keySet().iterator();
        while (iterator.hasNext()) {
            Integer id = iterator.next();
            if (id == null) return;
            soundPool.unload(id);
        }
        rawid_soundid_map.clear();
        soundid_info_map.clear();
    }

    public void release() {
        unloadAll();
        soundPool.release();
        soundPool = null;
    }

    public class SoundInfo {

        public int rawId;
        public int soundId;
        public int streamId;

        public volatile boolean prepare;
        public volatile boolean play;

    }
}
