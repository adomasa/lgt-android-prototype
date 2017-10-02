package starsoft.litrail_android.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import starsoft.litrail_android.Model.Message;
import starsoft.litrail_android.Model.Messages;
import starsoft.litrail_android.R;
import starsoft.litrail_android.Model.SavedRoute;

import java.util.List;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder> {

    private final List<Message> mValues;
    private final MessagesFragment.OnListFragmentInteractionListener mListener;

    public MessagesRecyclerViewAdapter(List<Message> items, MessagesFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.message = mValues.get(position);
        holder.titleTextview.setText(mValues.get(position).title);
        holder.contentTextview.setText(mValues.get(position).content);
        holder.dateTextview.setText(mValues.get(position).date);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.message);
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
        public final TextView titleTextview;
        public final TextView contentTextview;
        public final TextView dateTextview;
        public Message message;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleTextview = (TextView) view.findViewById(R.id.messageTitleTextView);
            contentTextview = (TextView) view.findViewById(R.id.messageContentTextView);
            dateTextview = (TextView) view.findViewById(R.id.messageDateTextView);
        }

    }
}
