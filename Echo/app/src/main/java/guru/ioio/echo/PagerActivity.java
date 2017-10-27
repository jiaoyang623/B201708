package guru.ioio.echo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import guru.ioio.echo.databinding.ActivityPagerBinding;

/**
 * try pager without fragment
 * Created by daniel on 10/27/17.
 */

public class PagerActivity extends Activity {
    private ActivityPagerBinding mBinding;
    private PPAdapter mAdapter = new PPAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pager);
        mBinding.setPresenter(this);

        for (int i = 0; i < 3; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.valueOf(i));
            mAdapter.mList.add(tv);
        }

        mBinding.pager.setAdapter(mAdapter);
    }

    private class PPAdapter extends PagerAdapter {
        private List<View> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mList.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {

        }
    }

}
