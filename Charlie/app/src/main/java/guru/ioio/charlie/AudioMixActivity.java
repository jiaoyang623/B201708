package guru.ioio.charlie;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.File;

import guru.ioio.charlie.databinding.ActivityAudioMixBinding;

/**
 * Created by daniel on 9/28/17.
 * test for audio mix
 */

public class AudioMixActivity extends Activity implements SoundPool.OnLoadCompleteListener {
    private ActivityAudioMixBinding mBinding;
    private SoundPool mSoundPool;
    private int m0, m1;
    private boolean isStarted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_audio_mix);
        mBinding.setPresenter(this);
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        m0 = mSoundPool.load(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "test" + File.separator + "m0.mp3", 1);
        m1 = mSoundPool.load(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "test" + File.separator + "m1.mp3", 1);
    }

    public boolean play() {
        if (!isStarted) {
            mSoundPool.play(m0, 1, 1, 1, 0, 1);
            mSoundPool.play(m1, 1, 1, 1, 0, 1);
            isStarted = true;
        } else {
            mSoundPool.resume(m0);
            mSoundPool.resume(m1);
        }

        return true;
    }

    public boolean pause() {
        if (isStarted) {
            mSoundPool.pause(m0);
            mSoundPool.pause(m1);
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        mSoundPool.release();
        super.onDestroy();
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        isStarted = false;
    }

}
