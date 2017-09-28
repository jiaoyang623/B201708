package guru.ioio.charlie.utils;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;

import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 8/25/17.
 * add at
 */

public class AtHelper implements AtTextWatcher.OnAddAtListener {
    private EditText mInput;
    private AtTextWatcher mWatcher;
    private List<IAtName> mList = new ArrayList<>();

    public AtHelper(EditText input) {
        mInput = input;
        mWatcher = new AtTextWatcher();
        mWatcher.setOnAddAdListener(this);
        mInput.addTextChangedListener(mWatcher);
    }

    @Override
    public void onAddAt() {

    }

    public void add(IAtName name) {
        if (name != null && !mList.contains(name)) {
            mList.add(name);
        }
    }


    private void cleanUnused() {
        String text = mInput.getText().toString();
        if (text.trim().length() > 0 && mList.size() > 0) {
            for (int i = mList.size() - 1; i != -1; i--) {
                IAtName name = mList.get(i);
                if (!text.contains(name.getName() + ' ')) {
                    mList.remove(i);
                }
            }
        }
    }

    public String encode() {
        cleanUnused();
        String text = mInput.getText().toString();
        if (text.trim().length() == 0 || mList.size() == 0) {
            return text;
        }

        for (IAtName name : mList) {
            text = text.replace(name.getName() + " ", "<at " + name.getAttributes() + ">" + name.getName() + "</at> ");
        }

        return text;
    }

    public static Spanned decode(String text) {
        return Html.fromHtml(text, null, new Html.TagHandler() {
            private String uid = "";
            private int start = 0;

            @Override
            public void handleTag(boolean opening, String tag, Editable output, Attributes attributes) {
                if ("at".equals(tag)) {
                    if (opening) {
                        uid = attributes.getValue("uid");
                        start = output.length();
                    } else {
                        final String fuid = uid;
                        output.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                System.out.println(fuid);
                            }
                        }, start, output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        });
    }


    public interface IAtName {
        String getName();

        String getAttributes();
    }
}
