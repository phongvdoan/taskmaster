package com.phongvdoan.taskmaster;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.phongvdoan.taskmaster.TaskFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).title);
//        holder.mBodyView.setText(mValues.get(position).body);
        holder.mStatusView.setText(mValues.get(position).state);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Context context = v.getContext();
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected
                // https://stackoverflow.com/questions/21453254/determine-if-context-is-a-specific-activity
                    if(mContext.getClass() == MainActivity.class){
                        Intent taskDetail = new Intent(mContext, TaskDetail.class);
                        System.out.println("holder = " + holder.mItem.id);
                        taskDetail.putExtra("id", holder.mItem.dynamoDBID);
                        taskDetail.putExtra("title", holder.mItem.title);
                        taskDetail.putExtra("body", holder.mItem.body);
                        taskDetail.putExtra("state", holder.mItem.state);

//                    taskDetail.putExtra("description", mValues.get(position).body);
//                    taskDetail.putExtra("status", mValues.get(position).state);
                    context.startActivity(taskDetail);
                    } else {
                        String stringForToast = String.format("%s %s %s", holder.mItem.title, holder.mItem.body, holder.mItem.state);
                        Toast saveToast = Toast.makeText(mContext, stringForToast, Toast.LENGTH_SHORT);
                        saveToast.show();
                    }
                }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
//        public final TextView mBodyView;
        public final TextView mStatusView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
//            mBodyView = (TextView) view.findViewById(R.id.content);
            mStatusView = (TextView) view.findViewById(R.id.status);
        }




        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }

    }

}
