package starsoft.lgt;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import starsoft.lgt.utils.BottomNavigationViewExpander;
import starsoft.lgt.utils.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements
        OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    public Menu menu;
    private boolean addBookmarkToggle = false;
    private Fragment timetableSearchFragment;
    private Fragment savedRoutesFragment;
    private Fragment mapFragment;
    private Fragment messagesFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = (@NonNull MenuItem item) -> {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content, timetableSearchFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.content, savedRoutesFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_map:
                    fragmentTransaction.replace(R.id.content, mapFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.content, messagesFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                default:
                    return false;
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSupportActionBar(toolbar);
        //show all items titles
        BottomNavigationViewExpander.disableShiftMode(navigation);

        timetableSearchFragment = new TimetableSearchFragment();
        savedRoutesFragment = SavedRoutesFragment.newInstance();
        mapFragment = MapFragment.newInstance();
        messagesFragment = MessagesFragment.newInstance();
        //load default fragment
        getFragmentManager().beginTransaction().add(R.id.content, timetableSearchFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        menu.findItem(R.id.action_favorite).setVisible(false);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                addBookmarkToggle = !addBookmarkToggle;
                if (addBookmarkToggle) {
                    showBookmarkSnackBack();
                } else
                    menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_not_added);
                return true;
            case R.id.action_report:
                return true;
            case R.id.about_us:
                showAboutUsDialog();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showBookmarkSnackBack() {
        menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_added);
        Snackbar snack = Snackbar.make(findViewById(R.id.content),
                "Maršrutas sėkmingai pridėtas prie žymių.", Snackbar.LENGTH_LONG)
                .setAction("Anuliuoti", v -> {
                    addBookmarkToggle = false;
                    menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_not_added);
        });
        snack.show();
    }

    private void showAboutUsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Apie mus");
        dialogBuilder.setMessage("Litrail-android prototipas. \n" +
                "Komandos Starsoft projektinis darbas.");
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setPositiveButton("Uždaryti langą", (dialog, which) -> dialog.dismiss());
        dialogBuilder.create().show();
    }

    @Override
    public void onFragmentInteraction(String action, Bundle args) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment newFragment = null;

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (action.equals("open_timetable")) {
            newFragment = RouteDataFragment.newInstance();
            newFragment.setArguments(args);
        }

        if (newFragment == null) {
            Log.e(TAG, "Fragment interaction action unrecognised: " + action);
            return;
        }

        fragmentTransaction.replace(R.id.content, newFragment)
                .addToBackStack(null)
                .commit();
    }

    public void showHomeAsUp(boolean status) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(status);
    }
}


