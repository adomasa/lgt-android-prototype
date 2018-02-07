package starsoft.lgt.adapters;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import starsoft.lgt.R;
import starsoft.lgt.model.SavedRoute;
import starsoft.lgt.utils.OnFragmentInteractionListener;

import java.util.List;

public class SavedRoutesAdapter extends RecyclerView.Adapter<SavedRoutesAdapter.SavedRoutesViewHolder> {

    private final List<SavedRoute> mValues;
    private final OnFragmentInteractionListener mFragmentListener;

    public SavedRoutesAdapter(List<SavedRoute> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mFragmentListener = listener;
    }

    @Override
    public SavedRoutesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(starsoft.lgt.R.layout.fragment_routes, parent, false);
        final SavedRoutesViewHolder holder = new SavedRoutesViewHolder(view);

        holder.itemView.setOnClickListener((View v) -> {
            if (mFragmentListener == null) return;

            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Bundle args = new Bundle();
            Resources resources = holder.itemView.getResources();

            String action = resources.getString(R.string.action_open_timetable);
            String departureStationKey = resources.getString(R.string.key_departure_station);
            String arrivalStationKey = resources.getString(R.string.key_arrival_station);

            args.putString(departureStationKey, holder.mRoute.departureStation);
            args.putString(arrivalStationKey, holder.mRoute.arrivalStation);
            mFragmentListener.onFragmentInteraction(action, args);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final SavedRoutesViewHolder holder, int position) {
        holder.mRoute = mValues.get(position);
        holder.mDepartureLocation.setText(mValues.get(position).departureStation);
        holder.mArrivalLocation.setText(mValues.get(position).arrivalStation);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class SavedRoutesViewHolder extends RecyclerView.ViewHolder {
        TextView mDepartureLocation;
        TextView mArrivalLocation;
        SavedRoute mRoute;

        private SavedRoutesViewHolder(View view) {
            super(view);
            mDepartureLocation = view.findViewById(starsoft.lgt.R.id.messageDate);
            mArrivalLocation = view.findViewById(starsoft.lgt.R.id.arrivalLocation);
        }

    }
}
