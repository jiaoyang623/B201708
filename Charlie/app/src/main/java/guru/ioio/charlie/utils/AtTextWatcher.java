package guru.ioio.charlie.utils;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;

/**
 * Created by daniel on 8/11/17.
 * for changing '@xxx'
 */

public class AtTextWatcher implements TextWatcher {
    private boolean isDeletingSpace = false;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        isDeletingSpace = count == 1 && after == 0 && s.charAt(start) == ' ';
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isDeletingSpace && !(start > 1 && s.charAt(start - 1) == ' ')) {
            String txt = s.subSequence(0, start - 1).toString();
            int index = txt.indexOf('@', txt.lastIndexOf(' '));
            if (index != -1 && s instanceof SpannableStringBuilder) {
                ((SpannableStringBuilder) s).delete(index, start);
            }
        }

        if (mListener != null && before == 0 && count == 1 && s.charAt(start) == '@') {
            mListener.onAddAt();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private OnAddAtListener mListener;

    public AtTextWatcher setOnAddAdListener(OnAddAtListener l) {
        mListener = l;
        return this;
    }

    public interface OnAddAtListener {
        void onAddAt();
    }
}
