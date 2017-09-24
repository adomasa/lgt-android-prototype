package starsoft.litrail_android;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_map:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Button departureStationButton = (Button) findViewById(R.id.buttonDeparture);
        final Button arrivalStationButton = (Button) findViewById(R.id.buttonDestination);
        final Button searchButton = (Button) findViewById(R.id.buttonSearch);
        final CalendarView departureDatePicker = (CalendarView) findViewById(R.id.calendarView);

        final Calendar departureDate = Calendar.getInstance();
        final Resources resources = getResources();

        // rodomi navigacijos elementų pavadinimai
        BottomNavigationViewExpander.disableShiftMode(navigation);

        //demo DB stočių duomenys
        List<String> stations = new ArrayList<>();
        stations.add("Vilnius");
        stations.add("Kaunas");
        stations.add("Šiauliai");
        stations.add("Klaipėda");
        stations.add("Panevėžys");
        stations.add("Radviliškis");
        final ArrayAdapter<String> stationsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stations);

        // paspaustas išvykimo stočių mygtukas, rodantis sąrašą vietų
        departureStationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popUpLocationPicker(resources.getString(R.string.select_departure_station), stationsAdapter, departureStationButton);
            }
        });
        // paspaustas atvykimo stočių mygtukas, rodantis sąrašą vietų
        arrivalStationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popUpLocationPicker(resources.getString(R.string.select_arrival_station), stationsAdapter, arrivalStationButton);
            }
        });

        // paspaustas paieškos mygtukas, pateikiantis paieškos užklausą kontroleriui
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Query to DB", Toast.LENGTH_SHORT).show();
            }
        });

        // Pakeista data datos pasirinkimo widget'e
        departureDatePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                departureDate.set(year, month, dayOfMonth);
                Toast.makeText(getApplicationContext(),
                        "Data: " + year + " " + month + " " + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Inicializuojamas viršutinis meniu langas
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    // paspaustas mygtukas viršutiniame meniu lange
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.action_report:
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void popUpLocationPicker(String title, final ArrayAdapter<String> adapter, final Button button) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(title);
        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // pakeičia mygtuko tekstą į pasirinktą
                button.setText(adapter.getItem(item));

            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

}
