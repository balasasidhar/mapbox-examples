package com.balasasidhar.mapboxdemos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String MAPBOX_API_KEY = BuildConfig.MAPBOX_API_KEY;

    @BindView(R.id.mapView)
    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(getApplicationContext(), MAPBOX_API_KEY);
        setContentView(R.layout.activity_simple_map);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Simple Map");

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {

        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


}
