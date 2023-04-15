package com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.view;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainFormActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_form_layout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMapView);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        address = findViewById(R.id.addressName);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng ltlng=new LatLng(43.738392,-79.266258);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                ltlng, 16f);
        googleMap.animateCamera(cameraUpdate);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(getAddress(latLng));
            googleMap.clear();
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    latLng, 15);
            googleMap.animateCamera(location);
            googleMap.addMarker(markerOptions);
            address.setText(getAddress(latLng));
        });
    }

    private String getAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            System.out.println(address);
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return  "Nothing Found";
        }
    }

}
