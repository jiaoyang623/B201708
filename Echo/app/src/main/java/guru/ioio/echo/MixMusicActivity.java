package guru.ioio.echo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import guru.ioio.echo.databinding.ActivityMixMusicBinding;

/**
 * Created by daniel on 10/9/17.
 * for mix music
 */

public class MixMusicActivity extends Activity {
    private ActivityMixMusicBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mix_music);
        mBinding.setPresenter(this);
    }

    public boolean play() {
        return true;
    }

    public boolean pause() {
        return true;
    }
}
