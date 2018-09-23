package com.waymaps.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waymaps.app.R;
import com.waymaps.data.model.Task;
import com.waymaps.util.DateTimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTasksRecyclerViewAdapter extends RecyclerView.Adapter<MyTasksRecyclerViewAdapter.ViewHolder> {

    List<Task> mValues;

    public MyTasksRecyclerViewAdapter(List<Task> mValues) {
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public MyTasksRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_task_item, viewGroup, false);
        return new MyTasksRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTasksRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mItem = mValues.get(i);
        Context context = viewHolder.receiverView.getContext();

        viewHolder.messageField.setText(viewHolder.mItem.getText());
        switch (viewHolder.mItem.getType()) {
            case Task.Types.PHONE_TO_MAIL:
                viewHolder.typeField.setText(context.getString(R.string.phone_mail));
                break;
            case Task.Types.MAIL_TO_PHONE:
                viewHolder.typeField.setText(context.getString(R.string.mail_phone));
                break;
            case Task.Types.SERVER_TO_PHONE:
                viewHolder.typeField.setText(context.getString(R.string.server_phone));
                break;
            default:
                viewHolder.typeField.setText(context.getString(R.string.undefined));
        }

        if (viewHolder.mItem.getType() == Task.Types.PHONE_TO_MAIL){
            viewHolder.receiverField.setText(context.getString(R.string.selected_mails));
        } else {
            viewHolder.receiverField.setText(viewHolder.mItem.getReceiver());
        }

        if (viewHolder.mItem.getType() == Task.Types.SERVER_TO_PHONE){
            viewHolder.senderField.setText(context.getString(R.string.server));
        } else {
            viewHolder.senderField.setText(viewHolder.mItem.getSender());
        }

        switch (viewHolder.mItem.getStatus()) {
            case Task.Statuses.TO_PROCESS:
                viewHolder.statusField.setText(context.getString(R.string.status_not_processed));
                viewHolder.statusField.setTextColor(Color.BLACK);
                break;
            case Task.Statuses.SUCCESS:
                viewHolder.statusField.setText(context.getString(R.string.status_success));
                viewHolder.statusField.setTextColor(Color.GREEN);
                break;
            case Task.Statuses.FAILED:
                viewHolder.statusField.setText(context.getString(R.string.status_failed));
                viewHolder.statusField.setTextColor(Color.RED);
                break;
            case Task.Statuses.WAITING_FOR_RESPONSE:
                viewHolder.statusField.setText(context.getString(R.string.status_waiting_for_response));
                viewHolder.statusField.setTextColor(Color.BLUE);
                break;
            default:
                viewHolder.statusField.setText(context.getString(R.string.undefined));
                viewHolder.statusField.setTextColor(Color.BLACK);
                break;
        }

        viewHolder.creatingDateField.setText(DateTimeUtil.toFormat(viewHolder.mItem.getReceivedDate(),DateTimeUtil.DATE_FORMAT));

        if (viewHolder.mItem.getLastTryDate() == null){
            viewHolder.statsuDateField.setText(DateTimeUtil.toFormat(viewHolder.mItem.getReceivedDate(),DateTimeUtil.DATE_FORMAT));
        } else {
            viewHolder.statsuDateField.setText(DateTimeUtil.toFormat(viewHolder.mItem.getLastTryDate(),DateTimeUtil.DATE_FORMAT));
        }


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        @BindView(R.id.receiver_view)
        LinearLayout receiverView;

        @BindView(R.id.sender_view)
        LinearLayout senderView;

        @BindView(R.id.status_view)
        LinearLayout statusView;

        @BindView(R.id.type_view)
        LinearLayout typeView;

        @BindView(R.id.message_view)
        LinearLayout messageView;

        @BindView(R.id.creating_date_view)
        LinearLayout creatingDateView;

        @BindView(R.id.status_date_view)
        LinearLayout statusDateView;

        @BindView(R.id.receiver_label)
        TextView receiverLabel;

        @BindView(R.id.sender_label)
        TextView senderLabel;

        @BindView(R.id.status_label)
        TextView statusLabel;

        @BindView(R.id.type_label)
        TextView typeLabel;

        @BindView(R.id.message_label)
        TextView messageLabel;

        @BindView(R.id.creating_date_label)
        TextView creatingDateLabel;

        @BindView(R.id.status_date_label)
        TextView statsuDateLabel;

        @BindView(R.id.receiver_field)
        TextView receiverField;

        @BindView(R.id.sender_field)
        TextView senderField;

        @BindView(R.id.status_field)
        TextView statusField;

        @BindView(R.id.type_field)
        TextView typeField;

        @BindView(R.id.message_field)
        TextView messageField;

        @BindView(R.id.creating_date_field)
        TextView creatingDateField;

        @BindView(R.id.status_date_field)
        TextView statsuDateField;

        Task mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

    }

    public List<Task> getmValues() {
        return mValues;
    }
}
