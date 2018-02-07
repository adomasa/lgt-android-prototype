package starsoft.lgt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import starsoft.lgt.model.RouteTime;
import starsoft.lgt.utils.OnFragmentInteractionListener;

import java.util.List;

public class RouteDataAdapter extends RecyclerView.Adapter<RouteDataAdapter.RouteDataViewHolder> {

    private List<RouteTime> mValues;
    private OnFragmentInteractionListener mFragmentListener;

    public RouteDataAdapter(List<RouteTime> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mFragmentListener = listener;
    }

    @Override
    public RouteDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(starsoft.lgt.R.layout.fragment_route_time, parent, false);
        final RouteDataViewHolder holder = new RouteDataViewHolder(view);

        holder.itemView.setOnClickListener(v -> {
            if (mFragmentListener == null) return;
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            //Todo: add interaction here
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RouteDataViewHolder holder, int position) {
        holder.mRouteTime = mValues.get(position);
        holder.mDepartureTime.setText(mValues.get(position).departureTime);
        holder.mArrivalTime.setText(mValues.get(position).arrivalTime);
        holder.mDuration.setText(mValues.get(position).duration);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class RouteDataViewHolder extends RecyclerView.ViewHolder {
        TextView mDepartureTime;
        TextView mArrivalTime;
        TextView mDuration;
        RouteTime mRouteTime;

        private RouteDataViewHolder(View view) {
            super(view);
            mDepartureTime = view.findViewById(starsoft.lgt.R.id.departureTimeTextView);
            mArrivalTime = view.findViewById(starsoft.lgt.R.id.arrivalTimeTextView);
            mDuration = view.findViewById(starsoft.lgt.R.id.durationTextView);
        }

    }
}
