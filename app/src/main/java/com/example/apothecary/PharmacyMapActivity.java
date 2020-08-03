package com.example.apothecary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.apothecary.models.pharmacies.Datum;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PharmacyMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    Datum datum ;

    GoogleMap googleMap;
    MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        datum = (Datum) intent.getSerializableExtra("EXTRA_PHARMACYDATA");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {

        }

        //Edit the following as per you needs
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //

        LatLng placeLocation = new LatLng(datum.getLatitude(),datum.getLongitude()); //Make them global
        Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
                .title(datum.getPharmName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLocation,16F));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14F));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);
        // googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);
    }
}
