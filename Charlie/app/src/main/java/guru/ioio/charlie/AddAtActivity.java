package guru.ioio.charlie;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;

import guru.ioio.charlie.databinding.ActivityAddAtBinding;
import guru.ioio.charlie.utils.AtHelper;

/**
 * Created by daniel on 8/25/17.
 * for add at
 */

public class AddAtActivity extends Activity {
    public ObservableField<String> result = new ObservableField<>();
    private ActivityAddAtBinding mBinding;
    private AtHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_at);
        mBinding.setPresenter(this);
        mHelper = new AtHelper(mBinding.input);
        mBinding.input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String encode = mHelper.encode();
                result.set(encode);
                mBinding.result.setText(AtHelper.decode(encode));
            }
        });
    }

    public boolean onAddClick() {
        AtHelper.IAtName name = new AtHelper.IAtName() {
            @Override
            public String getName() {
                return "@abc";
            }

            @Override
            public String getAttributes() {
                return "uid='123'";
            }
        };

        mHelper.add(name);
        mBinding.input.getText().insert(mBinding.input.getSelectionStart(), name.getName() + " ");


        return true;
    }

}
