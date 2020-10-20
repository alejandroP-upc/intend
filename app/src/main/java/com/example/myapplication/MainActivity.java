package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText latitud, longitud;
    Button enviar, visualizar;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitud = (EditText) findViewById(R.id.latitud);
        longitud = (EditText) findViewById(R.id.longitud);
        enviar = (Button) findViewById(R.id.enviar);
        visualizar = (Button) findViewById(R.id.visualizar);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        enviar.setOnClickListener(this);
        visualizar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enviar:
               // Toast.makeText(MainActivity.this, "latitud"+"+", Toast.LENGTH_SHORT).show();

                if (ActivityCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                    //location_view.setText("when permission granted");

                } else {

                    //location_view.setText("when permission denied");
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

                break;

            case R.id.visualizar:

                Toast.makeText(MainActivity.this, "llegue", Toast.LENGTH_SHORT).show();

                // Create a Uri from an intent string. Use the result to create an Intent.
                //Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+latitud.getText().toString()+","+latitud.getText().toString());

                String geo="geo:"+latitud.getText().toString()+","+longitud.getText().toString();
                Log.e("AQUI ES" ,geo);
                Toast.makeText(MainActivity.this, geo, Toast.LENGTH_SHORT).show();
                Uri gmmIntentUri = Uri.parse(geo);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;


        }

    }

    public void getLocation() {
       // Toast.makeText(MainActivity.this, "aqui"+"+", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(MainActivity.this,
                                        Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                   // Toast.makeText(MainActivity.this, "latitud"+"+", Toast.LENGTH_SHORT).show();
                                    latitud.setText("" + (double) addresses.get(0).getLatitude());
                                    longitud.setText("" + (double) addresses.get(0).getLongitude());


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (ActivityCompat.checkSelfPermission(MainActivity.this
                                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    getLocation();
                                }
                            }
                        }
                    });
        }
    }
}