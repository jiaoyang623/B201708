package guru.ioio.charlie;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import guru.ioio.charlie.databinding.ActivityAddAtBinding;

/**
 * Created by daniel on 8/25/17.
 * for add at
 */

public class AddAtActivity extends Activity {
    private ActivityAddAtBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_at);
        mBinding.setPresenter(this);
    }
}
