package com.waymaps.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waymaps.app.R;
import com.waymaps.data.model.PhoneNumber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyPhoneRecyclerViewAdapter extends RecyclerView.Adapter<MyPhoneRecyclerViewAdapter.ViewHolder> {

    private final List<PhoneNumber> mValues;

    public MyPhoneRecyclerViewAdapter(List<PhoneNumber> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_phone_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.item_number)
        TextView mIdView;

        @BindView(R.id.item_content)
        TextView mContentView;

        public PhoneNumber mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this,view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public List<PhoneNumber> getmValues() {
        return mValues;
    }
}
