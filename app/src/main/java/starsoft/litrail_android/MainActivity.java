package starsoft.litrail_android;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import starsoft.litrail_android.Fragments.MapFragment;
import starsoft.litrail_android.Fragments.RouteDataFragment;
import starsoft.litrail_android.Fragments.SavedRoutesFragment;
import starsoft.litrail_android.Fragments.TimetableSearchFragment;
import starsoft.litrail_android.Model.SavedRoute;

public class MainActivity extends AppCompatActivity implements TimetableSearchFragment.OnFragmentInteractionListener, SavedRoutesFragment.OnListFragmentInteractionListener {

    private Fragment timetableSearchFragment;
    private Fragment savedRoutesFragment;
    private Fragment mapFragment;

    private String currentFragmentTAG;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content, timetableSearchFragment);
                    fragmentTransaction.addToBackStack(currentFragmentTAG);
                    fragmentTransaction.commit();
                    currentFragmentTAG = TimetableSearchFragment.TAG;
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.content, savedRoutesFragment);
                    fragmentTransaction.addToBackStack(currentFragmentTAG);
                    fragmentTransaction.commit();
                    currentFragmentTAG = SavedRoutesFragment.TAG;
                    return true;
                case R.id.navigation_map:
                    fragmentTransaction.replace(R.id.content, mapFragment);
                    fragmentTransaction.addToBackStack(currentFragmentTAG);
                    fragmentTransaction.commit();
                    currentFragmentTAG = MapFragment.TAG;
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
        // rodomi navigacijos elementų pavadinimai
        BottomNavigationViewExpander.disableShiftMode(navigation);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        timetableSearchFragment = TimetableSearchFragment.newInstance();
        savedRoutesFragment = SavedRoutesFragment.newInstance();
        mapFragment = MapFragment.newInstance();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, timetableSearchFragment).commit();
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
//            case R.id.action_settings:
//                return true;
            case R.id.action_report:
                return true;
            case R.id.about_us:
                popUpAboutUsDialog();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void popUpAboutUsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Apie mus");
        dialogBuilder.setMessage("Litrail-android prototipas. \n" +
                "Komandos Starsoft projektinis darbas.");
        dialogBuilder.setPositiveButton("Uždaryti langą", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.create().show();
    }

    @Override
    public void onFragmentInteraction(Uri uri, Bundle args) {
        String operation = uri.toString();
        if (operation.equals("SavedRoutesFragment")) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            RouteDataFragment routeDataFragment = RouteDataFragment.newInstance();
            routeDataFragment.setArguments(args);
            fragmentTransaction.replace(R.id.content, routeDataFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onListFragmentInteraction(SavedRoute route) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        Bundle args = new Bundle();
        args.putString("DEPARTURE_STATION", route.departureStation);
//        Log.d("BUNDLE", "DEPARTURE: " + route.departureStation);
        args.putString("ARRIVAL_STATION", route.arrivalStation);
//        Log.d("BUNDLE", "ARRIVAL: " + route.arrivalStation);
        RouteDataFragment routeDataFragment = RouteDataFragment.newInstance();
        routeDataFragment.setArguments(args);
        fragmentTransaction.replace(R.id.content, routeDataFragment);
        fragmentTransaction.commit();
    }
}
