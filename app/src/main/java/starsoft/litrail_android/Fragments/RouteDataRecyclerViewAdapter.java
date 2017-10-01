package starsoft.litrail_android.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import starsoft.litrail_android.Fragments.RouteDataFragment.OnListFragmentInteractionListener;
import starsoft.litrail_android.Model.RouteTime;
import starsoft.litrail_android.R;

import java.util.List;

public class RouteDataRecyclerViewAdapter extends RecyclerView.Adapter<RouteDataRecyclerViewAdapter.ViewHolder> {

    private final List<RouteTime> mValues;
    private final OnListFragmentInteractionListener mListener;

    public RouteDataRecyclerViewAdapter(List<RouteTime> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_route_time, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.routeTime = mValues.get(position);
        holder.departureTime.setText(mValues.get(position).departureTime);
        holder.arrivalTime.setText(mValues.get(position).arrivalTime);
        holder.duration.setText(mValues.get(position).duration);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.routeTime);
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
        public final TextView departureTime;
        public final TextView arrivalTime;
        public final TextView duration;
        public RouteTime routeTime;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            departureTime = (TextView) view.findViewById(R.id.departureTimeTextView);
            arrivalTime = (TextView) view.findViewById(R.id.arrivalTimeTextView);
            duration = (TextView) view.findViewById(R.id.durationTextView);

        }

    }
}
