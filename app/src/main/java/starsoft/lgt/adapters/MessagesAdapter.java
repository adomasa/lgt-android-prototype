package starsoft.lgt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import starsoft.lgt.R;
import starsoft.lgt.model.Message;
import starsoft.lgt.utils.OnFragmentInteractionListener;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private List<Message> mValues;
    private OnFragmentInteractionListener mFragmentListener;

    public MessagesAdapter(List<Message> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mFragmentListener = listener;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_messages, parent, false);
        final MessagesViewHolder holder = new MessagesViewHolder(view);

        holder.itemView.setOnClickListener(v -> {
            if (mFragmentListener == null) return;
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            //Todo: add interaction here
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {
        holder.mMessage = mValues.get(position);
        holder.mTitleTextview.setText(mValues.get(position).title);
        holder.mContentTextview.setText(mValues.get(position).content);
        holder.mDateTextview.setText(mValues.get(position).date);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextview;
        TextView mContentTextview;
        TextView mDateTextview;
        Message mMessage;

        MessagesViewHolder(View view) {
            super(view);
            mTitleTextview = view.findViewById(R.id.messageTitle);
            mContentTextview = view.findViewById(R.id.departureLabel);
            mDateTextview = view.findViewById(R.id.messageDate);
        }

    }
}
