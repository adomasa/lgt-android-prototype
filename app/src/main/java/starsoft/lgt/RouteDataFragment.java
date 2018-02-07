package starsoft.lgt;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import starsoft.lgt.adapters.RouteDataAdapter;
import starsoft.lgt.model.demo.RouteTimeDemo;
import starsoft.lgt.utils.OnFragmentInteractionListener;

public class RouteDataFragment extends Fragment {

    public final String TAG = this.getClass().getSimpleName();

    String mDepartureLocation;
    String mArrivalLocation;
    String mDepartureDate;
    TextView mDepartureLocationTextView;
    TextView mDepartureDateTextView;
    TextView mArrivalLocationTextView;
    OnFragmentInteractionListener mFragmentInteractionListener;


    public static RouteDataFragment newInstance() {
        return new RouteDataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_data, container, false);
        mDepartureLocationTextView = (TextView) view.findViewById(R.id.departureLocation);
        mArrivalLocationTextView = (TextView) view.findViewById(R.id.arrivalLocation);
        mDepartureDateTextView = (TextView) view.findViewById(R.id.departureDate);

        ((MainActivity)getActivity()).showHomeAsUp(true);

        getActivity().setTitle("Maršruto tvarkaraštis");
        ((MainActivity) getActivity()).menu.findItem(R.id.action_favorite).setVisible(true);

        Bundle bundle = getArguments();
        if (bundle.getString("DEPARTURE_STATION") != null)
            mDepartureLocation = bundle.getString("DEPARTURE_STATION");


        if (bundle.getString("ARRIVAL_STATION") != null)
            mArrivalLocation = bundle.getString("ARRIVAL_STATION");

        if (bundle.getString("DEPARTURE_DATE") != null)
            mDepartureDate = bundle.getString("DEPARTURE_DATE");
        else
        {
            mDepartureDate = String.format("%1$tY-%1$tm-%1$td", Calendar.getInstance());
        }

        mDepartureLocationTextView.setText(mDepartureLocation);
        mArrivalLocationTextView.setText(mArrivalLocation);
        mDepartureDateTextView.setText(mDepartureDate);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listTime);
        recyclerView.setAdapter(new RouteDataAdapter(RouteTimeDemo.ITEMS, mFragmentInteractionListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteractionListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).menu.findItem(R.id.action_favorite).setVisible(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteractionListener = null;
    }
}
