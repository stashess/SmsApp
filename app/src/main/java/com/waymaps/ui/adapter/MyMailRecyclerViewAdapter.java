package com.waymaps.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waymaps.app.R;
import com.waymaps.data.model.Mail;
import com.waymaps.data.model.PhoneNumber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMailRecyclerViewAdapter extends RecyclerView.Adapter<MyMailRecyclerViewAdapter.ViewHolder>{

    private List<Mail> mValues;

    public MyMailRecyclerViewAdapter(List<Mail> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getMail());
        switch (mValues.get(position).getStatus()){
            case Mail.Statuses.VERIFIED :
                holder.mContentView.setTextColor(Color.GREEN);
                break;
            case Mail.Statuses.FAILED :
                holder.mContentView.setTextColor(Color.RED);
                break;
            case Mail.Statuses.NOT_CHECKED :
            default:
                holder.mContentView.setTextColor(Color.parseColor("#424242"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends MyViewHolder {
        public final View mView;

        @BindView(R.id.foreground_view)
        View foregroundView;

        @BindView(R.id.background_view)
        View backGroundView;

        @BindView(R.id.mail_id)
        TextView mIdView;

        @BindView(R.id.mail_mail)
        TextView mContentView;

        public Mail mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this,view);
        }

        @Override
        public View getForegroundView() {
            return foregroundView;
        }

        @Override
        public View getBackgroundView() {
            return backGroundView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public List<Mail> getmValues() {
        return mValues;
    }
}
