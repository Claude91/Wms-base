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


    private static MediaPlayUtils mInstance;


    public static MediaPlayUtils getInstance() {
        if (mInstance == null) {
            synchronized (MediaPlayUtils.class) {
                if (mInstance == null) {
                    mInstance = new MediaPlayUtils();
                }
            }
        }
        return mInstance;
    }

    private static MediaPlayer errorPlay;

    private boolean openErrorPlay;


    public void playError(Context context) {
        if (!isOpenErrorPlay()) {
            return;
        }
        if (errorPlay == null) {
            errorPlay = MediaPlayer.create(context, R.raw.error_1);
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


    public boolean isOpenErrorPlay() {
        return openErrorPlay;
    }

    public void setOpenErrorPlay(boolean openErrorPlay) {
        this.openErrorPlay = openErrorPlay;
    }


}
