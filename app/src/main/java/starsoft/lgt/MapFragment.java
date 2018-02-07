package starsoft.lgt;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import starsoft.lgt.model.demo.ColorUtils;
import starsoft.lgt.model.demo.PinStations;
import starsoft.lgt.model.demo.Routes;
import starsoft.lgt.model.PinStation;
import starsoft.lgt.model.RouteMap;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public static final String TAG = "MapFragment";

    private MapView mMapView;
    private static final int FINE_LOCATION_REQUEST = 0;
    private LocationManager mLocationManager;
    private Location mCurrentLocation;
    private Marker mUserMarker;
    private GoogleMap mGoogleMap;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_fragment_map));
        View view =  inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        ((MainActivity)getActivity()).showHomeAsUp(false);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
            for (PinStation pinStation : PinStations.data) {
                LatLng stationPosition = new LatLng(pinStation.latitude, pinStation.longitude);
                googleMap.addMarker(
                        new MarkerOptions()
                                .position(stationPosition)
                                .title(pinStation.name)
                                .snippet(pinStation.description))
                                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
            }

            List<Integer> colors = new ArrayList<>();
            ColorUtils.injectDemoColors(colors);


            int pos = 0;
            for (RouteMap routeMap : Routes.data) {
                List<LatLng> latLngs = new ArrayList<>();
                PolylineOptions polylineOptions = new PolylineOptions().clickable(false);

                for (int i = 0; i < routeMap.routeStations.size(); i++) {
                    latLngs.add(new LatLng(routeMap.routeStations.get(i).latitude,
                            routeMap.routeStations.get(i).longitude));
                }
                Polyline polyline = googleMap.addPolyline(polylineOptions);
                polyline.setWidth(routeMap.strokeWeight);
                polyline.setPoints(latLngs);

                polyline.setColor(colors.get(pos++));
                if (pos >= colors.size())
                        pos = 0;
            }

            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        loadMapStyle(googleMap);

        LatLng latLng = new LatLng(55.315231, 24.189048);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.setMinZoomPreference(7);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        //----
        final boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d(TAG, "gps enabled: " + (gpsEnabled ? "yes" : "no"));

        if (!gpsEnabled) {
            // Build an alert dialog here that requests that the user enable
            // the location services, then when the user clicks the "OK" button,
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        int permissionStatus = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000, 10, locationListener);
        mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateUserMarker();


    }

    private void loadMapStyle(GoogleMap googleMap) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mCurrentLocation = location;
            updateUserMarker();
            Log.d(TAG, "lat: " + location.getLatitude() + "lng: " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
            updateUserMarker();
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(getContext(), "Provider disabled, id:" + s, Toast.LENGTH_LONG)
                    .show();
        }
    };

    private void updateUserMarker() {
        if (mUserMarker != null) mUserMarker.remove();
        addUserMarker();
    }

    private void addUserMarker() {
        int height = 150;
        int width = 150;
        BitmapDrawable bitmapDrawable =
                (BitmapDrawable) getResources().getDrawable(R.drawable.ic_user_pin, null);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);

        LatLng location =
                new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mUserMarker = mGoogleMap.addMarker(new MarkerOptions().position(location)
                .snippet("Tavo lokacija"));
        mUserMarker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != FINE_LOCATION_REQUEST) return;

        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Location permission denied.");
            return;
        }

        int permissionStatus = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,
                    10, locationListener);
            mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            updateUserMarker();
        }

    }
}
