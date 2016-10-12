package com.akseltorgard.devcalc;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * http://stackoverflow.com/a/27552576
 */
class SoundPoolManager {

    private static SoundPool soundPool;

    static SoundPool getSoundPool() {
        if (soundPool == null) {
            createSoundPool();
        }

        return soundPool;
    }

    private static void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private static void createOldSoundPool(){
        soundPool = new SoundPool(5, AudioManager.STREAM_NOTIFICATION,0);
    }
}