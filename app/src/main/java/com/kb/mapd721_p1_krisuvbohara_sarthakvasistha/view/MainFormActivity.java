package com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.view;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.R;
import com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.database.MapDatabase;
import com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.database.MapEntity;
import com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.service.ChatService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainFormActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView address;
    Button button;
    EditText name;

    Double lat;
    Double lng;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_form_layout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMapView);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        address = findViewById(R.id.addressName);
        name = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.main_form_button);

        button.setOnClickListener(view -> {
            String value = name.getText().toString();
            String add = name.getText().toString();

            if(value.matches("")){
                getAllTodos(view);
                Toast.makeText(this, "Name Required",
                        Toast.LENGTH_LONG).show();
            }else if(lat == null){
                Toast.makeText(this, "Location Not Set",
                        Toast.LENGTH_LONG).show();
            }else{
                try {
                    MapEntity entity = new MapEntity(value, add,lat,lng);
                    InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
                    insertAsyncTask.execute(entity);
                    getAllTodos(view);

                }catch (IllegalArgumentException e){
                    Log.e(e.toString(),"s");
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng ltlng=new LatLng(43.738392,-79.266258);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                ltlng, 16f);
        googleMap.animateCamera(cameraUpdate);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMapClickListener(latLng -> {
            Intent intent = new Intent(MainFormActivity.this, ChatService.class);
//            intent.putExtras(data);
            MainFormActivity.this.startService(intent);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(getAddress(latLng));
            googleMap.clear();
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    latLng, 15);
            googleMap.animateCamera(location);
            googleMap.addMarker(markerOptions);
            address.setText(getAddress(latLng));
            lat = latLng.latitude;
            lng = latLng.longitude;
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

    public void getAllTodos(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<MapEntity> todoList = MapDatabase.getInstance(getApplicationContext())
                        .mapDao().getAll();
                System.out.println( todoList.size());
            }
        });
            thread.start();
    }

    class InsertAsyncTask extends AsyncTask<MapEntity, Void, Void> {
    @Override
    protected Void doInBackground(MapEntity... model) {

        MapDatabase.getInstance(getApplicationContext()).mapDao().insert(model[0]);
        return null;
    }
    }


}

