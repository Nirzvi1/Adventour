package com.adventour.adventour;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.adventour.adventour.data.Challenge;
import com.adventour.adventour.data.Pair;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Explore extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Challenge> nearby;
    private HashMap<Marker, Challenge> markToCh = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_explore);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("GPS Error", "No permissions for GPS Signal!");
            return;
        }

        final Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 12.0f));

        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title("Your Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.blueflag)));

        Database.read("challenges", new Callback() {
            @Override
            public void receive(Object o) {
                Set<Map.Entry<String, Object>> entries = ((HashMap<String, Object>) o).entrySet();
                for (Map.Entry<String, Object> challenge : entries) {
                    Challenge c = new Challenge((HashMap<String, Object>) challenge.getValue());
                    Pair<Double> p = c.getLocation();
                    if (Math.abs(p.first - loc.getLatitude()) < 0.1
                            && Math.abs(p.second - loc.getLongitude()) < 0.1
                            && !Home.u.getCompleted().contains(c.getChallengeId())) {
                        Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(p.first, p.second)).title(c.getActivity()).snippet("Votes: " + c.getVotes() + "   Difficulty: " + c.getDifficulty()));
                        markToCh.put(m, c);
                    }
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (markToCh.containsKey(marker)) {
                            ChallengeActivity.ch = markToCh.get(marker);
                            Intent i = new Intent(Explore.this, ChallengeActivity.class);
                            startActivity(i);
                        }
                        return false;
                    }
                });
            }
        });



    }
}
