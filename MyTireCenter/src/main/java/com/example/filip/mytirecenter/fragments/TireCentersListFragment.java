package com.example.filip.mytirecenter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.filip.mytirecenter.MainActivity;
import com.example.filip.mytirecenter.utils.MyTireCenterDatabaseHelper;
import com.example.filip.mytirecenter.R;
import com.example.filip.mytirecenter.model.TireCenter;
import com.example.filip.mytirecenter.widget.LineItemDecoration;
import com.example.filip.mytirecenter.widget.DecoratedTextView;
import com.example.filip.mytirecenter.widget.TireCenterTextFAB;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * The Fragment to show the list of all tire centers
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class TireCentersListFragment extends Fragment {

    /**
     * The Tag for this Fragment
     */
    public static final String TAG = TireCentersListFragment.class.getSimpleName();

    // The MainActivity
    private MainActivity mActivity;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_tire_center_recycler_list, container, false);
        Toolbar mToolbar = layout.findViewById(R.id.my_toolbar);
        mToolbar.inflateMenu(R.menu.fragment_tire_center_list);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_map:
                        Log.d(TAG, "Map Selected! ");
                        mActivity.onMiniMapClicked();
                        return true;
                }
                return false;
            }
        });
        RecyclerView mRecyclerView = layout.findViewById(R.id.tire_center_recycler_view);
        ArrayList<TireCenter> mModel = new ArrayList<>(MyTireCenterDatabaseHelper.getMyTireCenterDatabase(mActivity).getAllTireCenters());
        // set the layout
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        // The adapter
        TireCenterAdapter mAdapter = new TireCenterAdapter(mModel);
        mAdapter.setOnTireCenterListener(mActivity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new LineItemDecoration());
        mRecyclerView.setHasFixedSize(true);
        return layout;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_map:
                mActivity.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This is the interface to handle miniMap click
     */
    public interface OnMiniMapListener {

        /**
         * Invoked when the miniMap is selected
         */
        void onMiniMapClicked();

    }

    /**
     * This is the ViewHolder to manage item for the TireCenter
     */
    private final static class TireCenterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // The icon indicator of the tire center
        private TireCenterTextFAB mTireCenterTextFAB;
        // The textView for the tire center name
        private TextView mTireCenterName;
        // The view for the tire center address
        private DecoratedTextView mTireCenterAddress;
        //The listener of the click on the item
        private WeakReference<OnItemClickListener> mOnItemClickListenerRef;

        /**
         * Init the view holder
         *
         * @param itemView The item
         */
        private TireCenterViewHolder(View itemView) {
            super(itemView);
            mTireCenterTextFAB = itemView.findViewById(R.id.tire_center_indicator);
            mTireCenterName = itemView.findViewById(R.id.tire_center_name);
            mTireCenterAddress = itemView.findViewById(R.id.tire_center_address);
            itemView.setOnClickListener(this);
        }

        /**
         * Method used to to register a specific OnItemClickListener
         *
         * @param onItemClickListener The OnItemClickListener
         */
        public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
            this.mOnItemClickListenerRef = new WeakReference<>(onItemClickListener);
        }

        /**
         * This binds the model to the View
         *
         * @param tireCenter The model to map
         */
        public void bind(TireCenter tireCenter) {
            mTireCenterTextFAB.setText(tireCenter.getProvinceCode());
            mTireCenterName.setText(tireCenter.getName());
            mTireCenterAddress.setText(tireCenter.getAddress());
        }

        @Override
        public void onClick(View v) {
            OnItemClickListener listener;
            if (mOnItemClickListenerRef != null && (listener = mOnItemClickListenerRef.get()) != null) {
                listener.onItemClicked(getLayoutPosition());
            }
        }

        /**
         * This is the interface to implement to listen to the click on the item
         */
        public interface OnItemClickListener {

            /**
             * Invoked when a tire center is selected
             *
             * @param position The selected position
             */
            void onItemClicked(int position);

        }
    }

    /**
     * This is the Adapter that has the responsibility to access the model, create the ViewHolder
     * and bind data
     */
    public final static class TireCenterAdapter extends RecyclerView.Adapter<TireCenterViewHolder>
            implements TireCenterViewHolder.OnItemClickListener {

        // The model for the Adapter
        private final List<TireCenter> mModel;

        // The Reference to the Listener for TireCenter selection
        private OnTireCenterListener mOnTireCenterListenerRef;

        /**
         * Creates a TireCenterAdapter for the given model
         *
         * @param model The model for this Adapter
         */
        TireCenterAdapter(final List<TireCenter> model) {
            this.mModel = model;
        }

        /**
         * Method used to register a specific OnTireCenterListener
         *
         * @param onTireCenterListener The OnTireCenterListener
         */
        public void setOnTireCenterListener(final OnTireCenterListener onTireCenterListener) {
            this.mOnTireCenterListenerRef = onTireCenterListener;
        }

        @Override
        public TireCenterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // We inflate the data and return the related ViewHolder
            final View layout = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tire_center_layout, viewGroup, false);
            return new TireCenterViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(TireCenterViewHolder tireCenterViewHolder, int position) {
            tireCenterViewHolder.bind(mModel.get(position));
            tireCenterViewHolder.setOnItemClickListener(this);
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }

        @Override
        public void onItemClicked(int position) {
            if (mOnTireCenterListenerRef != null) {
                mOnTireCenterListenerRef.onTireCenterClicked(mModel.get(position));
            }
        }

        /**
         * This is the interface to implement to listen to the click on the item
         */
        public interface OnTireCenterListener {

            /**
             * Invoked when a tire center is selected
             *
             * @param tireCenter The selected TireCenter
             */
            void onTireCenterClicked(TireCenter tireCenter);

        }
    }
}
