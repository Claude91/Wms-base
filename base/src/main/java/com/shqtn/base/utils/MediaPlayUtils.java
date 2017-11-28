package com.shqtn.base.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.shqtn.base.R;

/**
 * @author ql
 *         创建时间:2017/11/28
 *         描述:
 */

public class MediaPlayUtils {
    private static MediaPlayer errorPlay;

    public static void playError(Context context) {
        if (errorPlay == null) {
            errorPlay = MediaPlayer.create(context, R.raw.error);
            errorPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    errorPlay.stop();
                    errorPlay.release();
                    errorPlay = null;
                }
            });
        } else {

        }
        if (errorPlay.isPlaying()) {
            errorPlay.seekTo(0);
        } else {
            errorPlay.start();
        }

    }

}
