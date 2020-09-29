package com.example.filip.mytirecenter.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.mytirecenter.MainActivity;
import com.example.filip.mytirecenter.utils.MyTireCenterDatabaseHelper;
import com.example.filip.mytirecenter.R;
import com.example.filip.mytirecenter.fragments.TireCentersListFragment.TireCenterAdapter;
import com.example.filip.mytirecenter.model.TireCenter;
import com.example.filip.mytirecenter.widget.LineItemDecoration;

import java.util.ArrayList;

/**
 *  The Fragment to show the research of tire centers
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class SearchTireCentersFragment extends Fragment {
    /**
     * The Tag for this Fragment
     */
    public static final String TAG = SearchTireCentersFragment.class.getSimpleName();

    //The ArrayList containing tire centers
    private ArrayList<TireCenter> mModel;

    //The Adapter for the TireCenter model
    private TireCenterAdapter mAdapter;

    //The MainActivity
    private MainActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_search_tire_center, container, false);
        Toolbar mToolbar = layout.findViewById(R.id.my_toolbar);
        mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setTitle(R.string.fragment_tire_center_search_title);
        mToolbar.inflateMenu(R.menu.fragment_tire_center_search);
        // The RecyclerListView
        RecyclerView mRecyclerView = layout.findViewById(R.id.tire_center_recycler_view);
        mModel = new ArrayList<>(MyTireCenterDatabaseHelper.getMyTireCenterDatabase(mActivity).getAllTireCenters());
        // Set the layout
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        // The adapter
        mAdapter = new TireCenterAdapter(mModel);
        mAdapter.setOnTireCenterListener(mActivity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new LineItemDecoration());
        mRecyclerView.setHasFixedSize(true);
        // We get the reference to the SearchView
        MenuItem searchItem = mToolbar.getMenu().findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Query Text Submit! " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Query Text Changed! ");
                if (newText.length() > 0) {
                    mModel.clear();
                    mModel.addAll(MyTireCenterDatabaseHelper.getMyTireCenterDatabase(mActivity).searchTireCenter(newText));
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
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
