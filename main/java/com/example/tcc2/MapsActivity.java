package com.example.tcc2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude;
    Double longitude;
    Button som, voltar;
    int acabou=1;
    Double latPoint2, lngPoint2;
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();

        Bundle parametros = intent.getExtras();

        if (parametros != null) {

            latitude = parametros.getDouble("latitude");
            longitude = parametros.getDouble("longitude");

        } else {
            latitude=51.6;
            longitude=75.7;

        }

        som = (Button) findViewById(R.id.tocarsom);
        som.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                int localizacao_valida=0;
                latPoint2=latitude*-1;
                lngPoint2=longitude*-1;

                if (latPoint2 >= 23.4767 && latPoint2 <= 23.4773 && lngPoint2>= 46.6319 && lngPoint2<= 46.6324 ) {

                    media = MediaPlayer.create(MapsActivity.this, R.raw.som);
                    localizacao_valida=1;

                }
                if (latPoint2 >= 22.8205 && latPoint2 <= 22.8227 && lngPoint2>= 47.0647 && lngPoint2<= 47.0680 ) {

                    media = MediaPlayer.create(MapsActivity.this, R.raw.feec);
                    localizacao_valida=1;

                }
                if (latPoint2 >= 23.3717 && latPoint2 <= 23.3724 && lngPoint2>= 46.6352 && lngPoint2<= 46.6361 ) {

                    media = MediaPlayer.create(MapsActivity.this, R.raw.cuca);
                    localizacao_valida=1;

                } if(localizacao_valida!=1){

                    media = null;

                }

                if (media != null) {
                    if (acabou == 1) {
                        media.start();
                        acabou = 0;
                        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                acabou = 1;
                            }
                        });
                    }
                } else {
                    Toast.makeText(MapsActivity.this, "A sua localização não possui áudio disponível",Toast.LENGTH_SHORT).show();
                }

            }
        });

        voltar = (Button) findViewById(R.id.voltar);
        voltar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                Intent voltar_tela_principal = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(voltar_tela_principal);
                if (media != null ) {
                    media.pause();
                }
            }

            });

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
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}