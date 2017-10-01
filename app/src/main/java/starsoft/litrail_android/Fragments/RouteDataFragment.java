package starsoft.litrail_android.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import starsoft.litrail_android.Model.RouteTime;
import starsoft.litrail_android.R;
import starsoft.litrail_android.Model.RouteTimes;

public class RouteDataFragment extends Fragment {

    public static final String TAG = "RouteDataFragment";
    public String departureLocation;
    public String arrivalLocation;
    public TextView departureLocationTextView;
    public TextView arrivalLocationTextView;
    private OnListFragmentInteractionListener mListener;

    public RouteDataFragment() {
    }

    public static RouteDataFragment newInstance() {
        RouteDataFragment fragment = new RouteDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_data, container, false);
        departureLocationTextView = (TextView) view.findViewById(R.id.departureLocationTextView);
        arrivalLocationTextView = (TextView) view.findViewById(R.id.arrivalLocationTextView);

        Bundle bundle = getArguments();
        if (bundle.getString("DEPARTURE_STATION") != null)
            departureLocation = bundle.getString("DEPARTURE_STATION");
        departureLocationTextView.setText(departureLocation);

        if (bundle.getString("ARRIVAL_STATION") != null)
            arrivalLocation = bundle.getString("ARRIVAL_STATION");

        arrivalLocationTextView.setText(arrivalLocation);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listTime);
        recyclerView.setAdapter(new RouteDataRecyclerViewAdapter(RouteTimes.ITEMS, mListener));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RouteTime item);
    }
}
