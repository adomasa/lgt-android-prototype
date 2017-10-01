package starsoft.litrail_android.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import starsoft.litrail_android.R;


public class TimetableSearchFragment extends Fragment {
    public static final String TAG = "TimetambleSearchFragment";

    ArrayAdapter<String> stationsAdapter;
    Calendar departureDate;
    AlertDialog.Builder dialogBuilder;
    AlertDialog locationDialog;


    private OnFragmentInteractionListener mListener;

    public TimetableSearchFragment() {
    }

    public static TimetableSearchFragment newInstance() {
        TimetableSearchFragment fragment = new TimetableSearchFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        Log.d("FRAGMENTS", "newInstance " + TAG);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        Log.d("FRAGMENTS", "onCreate " + TAG);
        //demo DB stočių duomenys
        List<String> stations = new ArrayList<>();
        stations.add("Vilnius");
        stations.add("Kaunas");
        stations.add("Šiauliai");
        stations.add("Klaipėda");
        stations.add("Panevėžys");
        stations.add("Radviliškis");
        stationsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stations);
        departureDate = Calendar.getInstance();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationDialog != null)
            locationDialog.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Tvarkaraščių paieška");
        Log.d("FRAGMENTS", "onCreateView " + TAG);
        View view = inflater.inflate(R.layout.fragment_timetable_search, container, false);
        final Button departureStationButton = (Button) view.findViewById(R.id.buttonDeparture);
        final Button arrivalStationButton = (Button) view.findViewById(R.id.buttonDestination);
        final Button searchButton = (Button) view.findViewById(R.id.buttonSearch);
        final CalendarView departureDatePicker = (CalendarView) view.findViewById(R.id.calendarView);
        // paspaustas išvykimo stočių mygtukas, rodantis sąrašą vietų
        departureStationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                departureStationButton.getBackground().setColorFilter(ContextCompat.getColor(v.getContext(), R.color.text), PorterDuff.Mode.SRC_ATOP);
                popUpLocationPicker(getResources().getString(R.string.select_departure_station), stationsAdapter, departureStationButton);
            }
        });
        // paspaustas atvykimo stočių mygtukas, rodantis sąrašą vietų
        arrivalStationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                arrivalStationButton.getBackground().setColorFilter(ContextCompat.getColor(v.getContext(), R.color.text), PorterDuff.Mode.SRC_ATOP);
                popUpLocationPicker(getResources().getString(R.string.select_arrival_station), stationsAdapter, arrivalStationButton);
            }
        });

        // paspaustas paieškos mygtukas, pateikiantis paieškos užklausą kontroleriui
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isInputValid(v.getContext(), departureStationButton, arrivalStationButton)) {
                    Toast.makeText(v.getContext(), "Klaida. Nenurodytas pilnas maršrutas.", Toast.LENGTH_SHORT).show();
                }
                else if (departureStationButton.getText().equals(arrivalStationButton.getText())) {
                    Toast.makeText(v.getContext(), "Tokiam maršrutui traukinių nereikia!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle args = new Bundle();
                    args.putString("DEPARTURE_STATION", departureStationButton.getText().toString());
                    args.putString("ARRIVAL_STATION", arrivalStationButton.getText().toString());

                    onButtonPressed(Uri.parse("SavedRoutesFragment"), args);
                }
            }
        });

        // Pakeista data datos pasirinkimo widget'e
        departureDatePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                departureDate.set(year, month, dayOfMonth);
                Toast.makeText(getContext(),
                        "Data: " + year + " " + month + " " + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri, Bundle args) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri, args);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("FRAGMENTS", "onAttach " + TAG);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("FRAGMENTS", "onDetach " + TAG);
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri, Bundle bundle);
    }


    private void popUpLocationPicker(String title, final ArrayAdapter<String> adapter, final Button button) {
        dialogBuilder = dialogBuilder != null ? dialogBuilder : new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(title);
        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // pakeičia mygtuko tekstą į pasirinktą
                button.setText(adapter.getItem(item));
            }
        });
        locationDialog = dialogBuilder.create();
        locationDialog.show();
    }

    private boolean isInputValid(Context c, Button button1, Button button2) {
        boolean status = true;

        if (button1.getText().equals(getResources().getString(R.string.undefined_place))) {
            status = false;
            button1.getBackground().setColorFilter(ContextCompat.getColor(c, R.color.error), PorterDuff.Mode.SRC_ATOP);
        }
        if (button2.getText().equals(getResources().getString(R.string.undefined_place))) {
            status = false;
            button2.getBackground().setColorFilter(ContextCompat.getColor(c, R.color.error), PorterDuff.Mode.SRC_ATOP);
        }

        return status;
    }
}
