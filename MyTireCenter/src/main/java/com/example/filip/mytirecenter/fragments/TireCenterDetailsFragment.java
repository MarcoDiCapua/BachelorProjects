package com.example.filip.mytirecenter.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filip.mytirecenter.utils.FavouriteTireCentersManager;
import com.example.filip.mytirecenter.MainActivity;
import com.example.filip.mytirecenter.utils.MyTireCenterDatabaseHelper;
import com.example.filip.mytirecenter.R;
import com.example.filip.mytirecenter.model.Tire;
import com.example.filip.mytirecenter.model.TireCenter;
import com.example.filip.mytirecenter.widget.LineItemDecoration;
import com.example.filip.mytirecenter.widget.DecoratedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * The Fragment to show the tire center details
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class TireCenterDetailsFragment extends Fragment {

    /**
     * The Tag for this Fragment
     */
    public static final String TAG = TireCenterDetailsFragment.class.getSimpleName();
    // The image that shows if a tire center is favourite or not
    private ImageView mFavourite;
    // The boolean that tells if a tire center is favourite or not
    private boolean isFavourite = false;
    // The tire center
    private TireCenter mTireCenter;
    // The MainActivity
    private MainActivity mActivity;

    /**
     * Empty constructor
     */
    public TireCenterDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * The creation of the Fragment itself
     *
     * @param tireCenter The tire center
     * @return the Fragment just created
     */
    public static TireCenterDetailsFragment newInstance(TireCenter tireCenter) {
        TireCenterDetailsFragment fragment = new TireCenterDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(MainActivity.TIRE_CENTER_SELECTED_KEY, tireCenter);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_tire_center_details, container, false);
        Toolbar mToolbar = layout.findViewById(R.id.my_toolbar);
        mToolbar.inflateMenu(R.menu.fragment_tire_center_list);
        mActivity.setSupportActionBar(mToolbar);
        if (!mActivity.isTwoPane()) {
            if (mActivity.getSupportActionBar() != null) {
                mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        TextView mTextViewName = layout.findViewById(R.id.textViewName);
        TextView mTextViewAddress = layout.findViewById(R.id.textViewAddress);
        TextView mTextViewPhone = layout.findViewById(R.id.textViewPhone);
        if (getArguments() != null) {
            mTireCenter = getArguments().getParcelable(MainActivity.TIRE_CENTER_SELECTED_KEY);
            Resources res = getResources();
            String name = res.getString(R.string.fragment_tire_center_details_name);
            String address = res.getString(R.string.fragment_tire_center_details_address);
            String phone = res.getString(R.string.fragment_tire_center_details_telephone);
            mTextViewName.setText(name + mTireCenter.getName());
            mTextViewAddress.setText(address + mTireCenter.getAddress());
            mTextViewPhone.setText(phone + mTireCenter.getTelephoneNumber());
        }
        mFavourite = layout.findViewById(R.id.favouriteImageView);
        List<TireCenter> list = FavouriteTireCentersManager.get(mActivity).getFavouriteTireCenters();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(mTireCenter)) {
                isFavourite = true;
                break;
            }
        }
        if (isFavourite) {
            mFavourite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            mFavourite.setImageResource(android.R.drawable.btn_star_big_off);
        }
        mFavourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isFavourite) {
                    Log.d(TAG, "removeFromFavourite");
                    Toast.makeText(getActivity(), R.string.removed_from_favourite, Toast.LENGTH_SHORT)
                            .show();
                    FavouriteTireCentersManager.get(mActivity).removeFavourite(mTireCenter);
                    mFavourite.setImageResource(android.R.drawable.btn_star_big_off);
                    isFavourite = false;
                } else {
                    Log.d(TAG, "addToFavourite");
                    Toast.makeText(getActivity(), R.string.added_to_favourite, Toast.LENGTH_SHORT)
                            .show();
                    FavouriteTireCentersManager.get(mActivity).addFavourite(mTireCenter);
                    mFavourite.setImageResource(android.R.drawable.btn_star_big_on);
                    isFavourite = true;
                }
            }
        });

        RecyclerView mRecyclerView = layout.findViewById(R.id.tire_recycler_view);
        ArrayList<Tire> mModel = new ArrayList<>(MyTireCenterDatabaseHelper.getMyTireCenterDatabase(mActivity).searchTires(mTireCenter.getId()));
        // set the layout
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        // The adapter
        TireAdapter mAdapter = new TireAdapter(mModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new LineItemDecoration());
        mRecyclerView.setHasFixedSize(true);

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

    // This is the ViewHolder to manage item for the TireCenter
    private final static class TireViewHolder extends RecyclerView.ViewHolder {

        // The brand of the tire
        private TextView mTireBrand;

        // The type of the tire
        private TextView mTireType;

        // The model of the tire
        private TextView mTireModel;

        // The three sizes of the tire
        private DecoratedTextView mTireSize;

        // The price of the tire
        private TextView mTirePrice;

        /**
         * Init the view
         *
         * @param itemView the itemView
         */
        private TireViewHolder(View itemView) {
            super(itemView);
            mTireBrand = itemView.findViewById(R.id.tire_brand);
            mTireType = itemView.findViewById(R.id.tire_type);
            mTireModel = itemView.findViewById(R.id.tire_model);
            mTireSize = itemView.findViewById(R.id.tire_size);
            mTirePrice = itemView.findViewById(R.id.tire_price);
        }

        /**
         * This method binds the model to the View
         *
         * @param tire The model to map
         */
        @SuppressLint("SetTextI18n")
        public void bind(Tire tire) {
            mTireBrand.setText(tire.getBrand());
            mTireType.setText(tire.getType());
            mTireModel.setText(tire.getModel());
            mTireSize.setText(tire.getAspectRatio() + "/" + tire.getSectionWidth() + "/"
                    + tire.getRimDiameter());
            mTirePrice.setText(tire.getPrice() + " €");
        }

    }

    // This is the Adapter that has the responsibility to access the model, create the ViewHolder
    // and bind data
    private final static class TireAdapter extends RecyclerView.Adapter<TireCenterDetailsFragment.TireViewHolder> {

        //The model for the Adapter
        private final List<Tire> mModel;

        /**
         * Creates a TireCenterAdapter for the given model
         *
         * @param model The model for this Adapter
         */
        private TireAdapter(final List<Tire> model) {
            this.mModel = model;
        }

        @Override
        public TireCenterDetailsFragment.TireViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // We inflate the data and return the related ViewHolder
            final View layout = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tire_layout, viewGroup, false);
            return new TireCenterDetailsFragment.TireViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(TireCenterDetailsFragment.TireViewHolder tireViewHolder, int position) {
            tireViewHolder.bind(mModel.get(position));
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }

    }


}
