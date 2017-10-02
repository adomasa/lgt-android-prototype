package starsoft.litrail_android.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import starsoft.litrail_android.R;
import starsoft.litrail_android.Model.SavedRoute;

import java.util.List;

public class SavedRoutesRecyclerViewAdapter extends RecyclerView.Adapter<SavedRoutesRecyclerViewAdapter.ViewHolder> {

    private final List<SavedRoute> mValues;
    private final SavedRoutesFragment.OnListFragmentInteractionListener mListener;

    public SavedRoutesRecyclerViewAdapter(List<SavedRoute> items, SavedRoutesFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_routes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.route = mValues.get(position);
        holder.departureLocation.setText(mValues.get(position).departureStation);
        holder.arrivalLocation.setText(mValues.get(position).arrivalStation);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.route);
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
        public final TextView departureLocation;
        public final TextView arrivalLocation;
        public SavedRoute route;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            departureLocation = (TextView) view.findViewById(R.id.messageDateTextView);
            departureLocation = (TextView) view.findViewById(R.id.departureLocationTextView);
            arrivalLocation = (TextView) view.findViewById(R.id.arrivalLocationTextView);
        }

    }
}
