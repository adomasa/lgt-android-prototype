package starsoft.lgt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.MapView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import starsoft.lgt.model.demo.PinStations;
import starsoft.lgt.model.demo.Routes;
import starsoft.lgt.model.PinStation;
import starsoft.lgt.model.RouteMap;

/**
 * Loading screen
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseMapView();
        loadJSONResources();

        startActivity(new Intent(this, MainActivity.class));
        // Destroy SplashActivity from backstack
        finish();
    }

    private void initialiseMapView() {
        new Thread(() -> {
            try {
                MapView mapView = new MapView(getApplicationContext());
                mapView.onCreate(null);
                mapView.onPause();
                mapView.onDestroy();
            } catch (Exception ignored){}
        }).start();
    }

    private void loadJSONResources() {
        try {
            Gson gson = new Gson();
            //Stations
            JsonReader jsonReader = getJsonReader(R.raw.stations);
            PinStations.data = gson.fromJson(jsonReader, new TypeToken<Collection<PinStation>>(){}.getType());
            jsonReader.close();

            //Routes
            jsonReader = getJsonReader(R.raw.routes);
            Routes.data = gson.fromJson(jsonReader, new TypeToken<Collection<RouteMap>>(){}.getType());
            jsonReader.close();

        } catch (IOException e) {
            Log.e(getClass().toString(), "Error on loading JSON");
            // Halt application
            finish();
        }
    }

    private JsonReader getJsonReader(int resourceID) {
        return new JsonReader(
                new BufferedReader(
                        new InputStreamReader(getResources().openRawResource(resourceID))
                )
        );
    }
}
