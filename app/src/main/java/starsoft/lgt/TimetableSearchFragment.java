package starsoft.lgt;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import starsoft.lgt.utils.OnFragmentInteractionListener;

import static android.app.Activity.RESULT_OK;

public class TimetableSearchFragment extends Fragment {
    public static final String TAG = "TimetambleSearchFragment";

    ArrayAdapter<String> mStationsAdapter;
    Calendar mDepartureDate;
    AlertDialog.Builder mLocationDialogBuilder;
    AlertDialog mLocationDialog;

    private Button mDepartureStationButton;
    private Button mArrivalStationButton;
    private Button mSearchButton;
    private CalendarView mDepartureDatePicker;
    private ImageButton mMicButton;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private OnFragmentInteractionListener mListener;

    View.OnClickListener searchButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isInputValid(mDepartureStationButton) || !isInputValid(mArrivalStationButton)) {
                promptError("Klaida: nenurodytas pilnas maršrutas.");
            } else if (mDepartureStationButton.getText().equals(mArrivalStationButton.getText())) {
                promptError("Klaida: blogas maršruto formatas.");
            } else {
                Bundle args = new Bundle();
                args.putString("DEPARTURE_STATION", mDepartureStationButton.getText().toString());
                args.putString("ARRIVAL_STATION", mArrivalStationButton.getText().toString());
                args.putString("DEPARTURE_DATE", String.format("%1$tY-%1$tm-%1$td", mDepartureDate));
                openTimetableFragment("action_timetable", args);
            }
        }

        private boolean isInputValid(Button button) {
            // If location wasn't selected, button's value is equal to @string/undefined_place
            if (!(button.getText().equals(getResources().getString(R.string.undefined_place))))
                return true;
            // Set error color filter on button
            button.getBackground().setColorFilter(
                    ContextCompat.getColor(getContext(), R.color.error), PorterDuff.Mode.SRC_ATOP);
            return false;
        }

        private void promptError(String message)
        {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }

    };

    private void clearColorFilter(Button button) {
        button.getBackground().setColorFilter(
                ContextCompat.getColor(getContext(), R.color.text), PorterDuff.Mode.SRC_ATOP);
    }

    public static TimetableSearchFragment newInstance() {
        return new TimetableSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //demo DB stočių duomenys
        List<String> stations = new ArrayList<>();
        stations.add("Vilnius");
        stations.add("Kaunas");
        stations.add("Šiauliai");
        stations.add("Klaipėda");
        stations.add("Panevėžys");
        stations.add("Radviliškis");
        mStationsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, stations);
        mDepartureDate = Calendar.getInstance();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationDialog != null)
            mLocationDialog.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Tvarkaraščių paieška");
        View view = inflater.inflate(R.layout.fragment_timetable_search, container, false);
        mDepartureStationButton = view.findViewById(R.id.buttonDeparture);
        mArrivalStationButton = view.findViewById(R.id.buttonDestination);
        mSearchButton = view.findViewById(R.id.buttonSearch);
        mDepartureDatePicker = view.findViewById(R.id.calendarView);
        mMicButton = view.findViewById(R.id.micButton);

        ((MainActivity)getActivity()).showHomeAsUp(false);

        mMicButton.setOnClickListener(v -> promptSpeechInput());

        // paspaustas išvykimo stočių mygtukas, rodantis sąrašą vietų
        mDepartureStationButton.setOnClickListener(v -> {
            clearColorFilter(mDepartureStationButton);
            showLocationDialog(getResources().getString(R.string.select_departure_station),
                    mStationsAdapter, mDepartureStationButton);
        });
        // paspaustas atvykimo stočių mygtukas, rodantis sąrašą vietų
        mArrivalStationButton.setOnClickListener(v -> {
            clearColorFilter(mArrivalStationButton);
            showLocationDialog(getResources().getString(R.string.select_arrival_station),
                    mStationsAdapter, mArrivalStationButton);
        });

        // paspaustas paieškos mygtukas, pateikiantis paieškos užklausą kontroleriui
        mSearchButton.setOnClickListener(searchButtonListener);

        // Pakeista data datos pasirinkimo widget'e
        mDepartureDatePicker.setOnDateChangeListener(
                (view1, year, month, dayOfMonth) -> mDepartureDate.set(year, month, dayOfMonth));
        return view;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQ_CODE_SPEECH_INPUT) return;

        if (resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String speechInput = result.get(0);
            if (!speechInput.startsWith("Noriu nuvykti iš")) {
                Toast.makeText(getContext(), "Neatpažinta įvestis", Toast.LENGTH_SHORT).show();
                return;
            }
            speechInput = speechInput.replace("Noriu nuvykti iš ", "");
            String[] splitInput = speechInput.split(" ");
            String from = splitInput[0];
            if (!splitInput[1].equals("į")) {
                Toast.makeText(getContext(), "Neatpažinta įvestis", Toast.LENGTH_SHORT).show();
                return;
            }
            String to = splitInput[2];

            String parsedFrom = parseSpeechLocation(from);
            String parsedTo = parseSpeechLocation(to);
            if (parsedFrom.equals(parsedTo)) {
                Toast.makeText(getContext(), "Tam nereikia traukinio", Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle args = new Bundle();
            args.putString("DEPARTURE_STATION", parsedFrom);
            args.putString("ARRIVAL_STATION", parsedTo);
            args.putString("DEPARTURE_DATE", String.format("%1$tY-%1$tm-%1$td", mDepartureDate));
            openTimetableFragment(getString(R.string.action_open_timetable), args);

        }
    }

    /**
     * Demo parser
     * @param data - location name
     * @return parsed location name
     */
    private String parseSpeechLocation(String data) {
        if (data.startsWith("Šiaul"))
            return "Šiauliai";
        else if (data.startsWith("Vilni"))
            return "Vilnius";
        else if (data.startsWith("Klaipėd"))
            return "Klaipėda";

        return "Default";
    }


    public void openTimetableFragment(String action, Bundle args) {
        if (mListener != null) {
            mListener.onFragmentInteraction(action, args);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void showLocationDialog(String title, ArrayAdapter<String> adapter, Button button) {
        if (mLocationDialogBuilder == null)
            mLocationDialogBuilder = new AlertDialog.Builder(getContext());

        mLocationDialogBuilder.setTitle(title)
                .setAdapter(adapter, (dialog, item) -> button.setText(adapter.getItem(item)))
                .setNegativeButton(R.string.dialog_cancel,
                        (dialogInterface, i) -> dialogInterface.dismiss());
        mLocationDialog = mLocationDialogBuilder.create();
        mLocationDialog.show();
    }
}
