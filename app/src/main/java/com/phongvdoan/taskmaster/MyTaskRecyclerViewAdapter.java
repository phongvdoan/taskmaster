package com.phongvdoan.taskmaster;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).title);
//        holder.mBodyView.setText(mValues.get(position).body);
        holder.mStatusView.setText(mValues.get(position).state);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements TaskListener {
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

        @Override
        public void taskClick(int position) {

        }
    }

    public interface TaskListener{
        void taskClick(int position);
    }
}
