package com.example.filip.mytirecenter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.mytirecenter.MainActivity;
import com.example.filip.mytirecenter.R;

/**
 * The Fragment showed when tire center details are empty
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class FragmentEmptyDetails extends Fragment {

    /**
     * The Tag for this Fragment
     */
    public static final String TAG = FragmentEmptyDetails.class.getSimpleName();

    // The MainActivity
    private MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_empty_details, container, false);
        Toolbar mToolbar = layout.findViewById(R.id.my_toolbar);
        mToolbar.inflateMenu(R.menu.fragment_tire_center_list);
        mActivity.setSupportActionBar(mToolbar);
        if (!mActivity.isTwoPane()) {
            if (mActivity.getSupportActionBar() != null) {
                mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }

        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
