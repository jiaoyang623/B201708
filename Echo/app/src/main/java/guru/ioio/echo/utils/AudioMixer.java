package guru.ioio.echo.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by daniel on 10/9/17.
 * for mix audio
 */

public class AudioMixer {
    private float mPercentA = 0;
    private float mPercentB = 0;
    private MediaPlayer mPlayerA;
    private MediaPlayer mPlayerB;
    private Context mContext;
    private boolean mIsReadyA = false;
    private boolean mIsReadyB = false;
    private boolean mRequestStart = false;

    public AudioMixer(Context context) {
        mContext = context;
    }


    public void setAudioA(String path) {
        mPlayerA = setAudio(path, mPlayerA, mPercentA);
        mIsReadyA = false;
    }

    public void setAudioB(String path) {
        mPlayerB = setAudio(path, mPlayerB, mPercentB);
        mIsReadyB = false;
    }

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mp == mPlayerA) {
                mIsReadyA = true;
            } else {
                mIsReadyB = true;
            }

            if (mIsReadyA && mIsReadyB && mRequestStart) {
                mPlayerA.start();
                mPlayerB.start();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            restart(mPlayerA);
            restart(mPlayerB);
        }
    };

    private void restart(MediaPlayer player) {

    }

    private MediaPlayer setAudio(String path, MediaPlayer player, float percent) {
        if (player == null) {
            player = MediaPlayer.create(mContext, Uri.parse("File://" + path));
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(mOnPreparedListener);
            player.setOnCompletionListener(mOnCompletionListener);
        } else {
            player.reset();
            try {
                player.setDataSource(mContext, Uri.parse("File://" + path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        player.setVolume(percent, percent);
        player.prepareAsync();

        return player;
    }

    public void play() {
        mRequestStart = true;
        if (mIsReadyA && mIsReadyB) {
            mPlayerA.start();
            mPlayerB.start();
        }
    }

    public void pause() {
        mRequestStart = false;
        if (mIsReadyA && mIsReadyB) {
            mPlayerA.pause();
            mPlayerB.pause();
        }
    }

    public void release() {
        mRequestStart = false;
        if (mPlayerA != null) {
            mPlayerA.release();
            mPlayerA = null;
            mIsReadyA = false;
        }

        if (mPlayerB != null) {
            mPlayerB.release();
            mPlayerB = null;
            mIsReadyB = false;
        }
    }

    /**
     * @param percent from 0.0 to 1.0
     */
    public void setPercentA(float percent) {
        mPercentA = Math.max(0, Math.min(1, percent));
        if (mPlayerA != null) {
            mPlayerA.setVolume(mPercentA, mPercentA);
        }
    }

    /**
     * @param percent from 0.0 to 1.0
     */
    public void setPercentB(float percent) {
        mPercentB = Math.max(0, Math.min(1, percent));
        if (mPlayerB != null) {
            mPlayerB.setVolume(mPercentB, mPercentB);
        }
    }
}
