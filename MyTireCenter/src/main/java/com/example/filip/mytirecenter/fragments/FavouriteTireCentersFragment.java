package com.example.filip.mytirecenter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.filip.mytirecenter.MainActivity;
import com.example.filip.mytirecenter.R;
import com.example.filip.mytirecenter.model.TireCenter;
import com.example.filip.mytirecenter.utils.FavouriteTireCentersManager;
import com.example.filip.mytirecenter.widget.DecoratedTextView;
import com.example.filip.mytirecenter.widget.LineItemDecoration;
import com.example.filip.mytirecenter.widget.TireCenterTextFAB;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/**
 * The Fragment to show the list of favourite tire centers
 */
public class FavouriteTireCentersFragment extends Fragment {

    /**
     * The Tag for this Fragment
     */
    public static final String TAG = FavouriteTireCentersFragment.class.getSimpleName();

    // The main activity
    private MainActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_tire_center_recycler_list, container, false);
        Toolbar mToolbar = layout.findViewById(R.id.my_toolbar);
        mToolbar.inflateMenu(R.menu.fragment_tire_center_list);
        mToolbar.setTitle(R.string.action_favourite);
        RecyclerView mRecyclerView = layout.findViewById(R.id.tire_center_recycler_view);
        // Set the layout
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "Map Selected! ");
                mActivity.onMiniMapClicked();
                return false;
            }
        });
        // The adapter
        FavouriteTireCentersManager mFavouriteManager = FavouriteTireCentersManager.get(mActivity);
        FavouriteTireCenterAdapter mAdapter = new FavouriteTireCenterAdapter(mFavouriteManager);
        mAdapter.setOnTireCenterListener(mActivity);
        mRecyclerView.addItemDecoration(new LineItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelper(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        // We return the layout
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

    // Interface to implement to be notified of changes in the RecyclerView for swipe and
    //drag & drop
    private interface OnItemTouchListener {

        /**
         * Operation to implement in case od Drag & Drop
         *
         * @param fromPosition The starting position
         * @param toPosition   The ending position
         */
        void onItemMove(int fromPosition, int toPosition);

        /**
         * Operation to implement in case of dismiss
         *
         * @param position The dismissed position
         */
        void onItemDismiss(int position);

    }

    // This is the ViewHolder to manage item for the TireCenter
    private final static class TireCenterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // The textView to show the province code of the tire center
        private TireCenterTextFAB mTireCenterTextFAB;

        // The textView to show the name of the tire center
        private TextView mTireCenterName;

        // The view to show the address of the tire center
        private DecoratedTextView mTireCenterAddress;

        // The listener of the click on the item
        private WeakReference<OnItemClickListener> mOnItemClickListenerRef;

        /**
         * Init the view holder
         *
         * @param itemView the view
         */
        private TireCenterViewHolder(View itemView) {
            super(itemView);
            mTireCenterTextFAB = itemView.findViewById(R.id.tire_center_indicator);
            mTireCenterName = itemView.findViewById(R.id.tire_center_name);
            mTireCenterAddress = itemView.findViewById(R.id.tire_center_address);
            itemView.setOnClickListener(this);
        }

        /**
         * We use this to register a specific OnItemClickListener
         *
         * @param onItemClickListener The OnItemClickListener
         */
        public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
            this.mOnItemClickListenerRef = new WeakReference<>(onItemClickListener);
        }

        /**
         * This method binds the model to the View
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
             * Invoked when we select a tire center
             *
             * @param position The selected position
             */
            void onItemClicked(int position);

        }
    }

    // This is the Adapter that has the responsibility to access the model, create the ViewHolder
    // and bind data
    private final static class FavouriteTireCenterAdapter extends RecyclerView.Adapter<TireCenterViewHolder>
            implements OnItemTouchListener, TireCenterViewHolder.OnItemClickListener {

        // The FavouriteTireCentersManager for the Adapter
        private final FavouriteTireCentersManager mFavouriteManager;

        // The Reference to the Listener for TireCenter selection
        private WeakReference<TireCentersListFragment.TireCenterAdapter.OnTireCenterListener> mOnTireCenterListenerRef;

        /**
         * Creates a TireCenterAdapter for the given FavouriteTireCentersManager
         *
         * @param favouriteManager The FavouriteTireCentersManager for this Adapter
         */
        FavouriteTireCenterAdapter(final FavouriteTireCentersManager favouriteManager) {
            this.mFavouriteManager = favouriteManager;
        }

        /**
         * Method used to register a specific OnTireCenterListener
         *
         * @param onTireCenterListener The OnTireCenterListener
         */
        private void setOnTireCenterListener(final TireCentersListFragment.TireCenterAdapter.OnTireCenterListener onTireCenterListener) {
            this.mOnTireCenterListenerRef = new WeakReference<>(onTireCenterListener);
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
            final List<TireCenter> model = mFavouriteManager.getFavouriteTireCenters();
            tireCenterViewHolder.bind(model.get(position));
            tireCenterViewHolder.setOnItemClickListener(this);
        }

        @Override
        public int getItemCount() {
            final List<TireCenter> model = mFavouriteManager.getFavouriteTireCenters();
            return model.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            final List<TireCenter> model = mFavouriteManager.getFavouriteTireCenters();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(model, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(model, i, i - 1);
                }
            }
            mFavouriteManager.forceFavourite(model);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            final List<TireCenter> model = mFavouriteManager.getFavouriteTireCenters();
            if (mFavouriteManager.removeFavourite(model.get(position))) {
                notifyItemRemoved(position);
            }
        }

        @Override
        public void onItemClicked(int position) {
            TireCentersListFragment.TireCenterAdapter.OnTireCenterListener listener;
            if (mOnTireCenterListenerRef != null && (listener = mOnTireCenterListenerRef.get()) != null) {
                final List<TireCenter> model = mFavouriteManager.getFavouriteTireCenters();
                listener.onTireCenterClicked(model.get(position));
            }
        }
    }

    // Implementation of the ItemTouchHelper
    private class MyItemTouchHelper extends ItemTouchHelper.Callback {

        //The Listener for the events
        private OnItemTouchListener mOnItemTouchListener;

        /**
         * This recognize actions done on touchpad
         *
         * @param onItemTouchListener Interface to implement to be notified of changes in the
         *                            RecyclerView for swipe and drag & drop
         */
        private MyItemTouchHelper(final OnItemTouchListener onItemTouchListener) {
            this.mOnItemTouchListener = onItemTouchListener;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            // We filter the movements for events
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            // Enable long press for drag
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            // Enable swipe for item
            return true;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder src,
                              RecyclerView.ViewHolder dst) {
            mOnItemTouchListener.onItemMove(src.getAdapterPosition(), dst.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mOnItemTouchListener.onItemDismiss(viewHolder.getAdapterPosition());
        }

    }

}
