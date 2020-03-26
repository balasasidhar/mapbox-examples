package com.balasasidhar.mapboxdemos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.mapView)
    MapView mMapView;

    SymbolManager mSymbolManager;

    private LatLng latLng = new LatLng(12.9807722, 77.5865957);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), BuildConfig.MAPBOX_API_KEY);

        setContentView(R.layout.activity_marker);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Marker");

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {

            style.addImage("MARKER_ICON", BitmapFactory.decodeResource(MarkerActivity.this.getResources(), R.drawable.marker));

            mSymbolManager = new SymbolManager(mMapView, mapboxMap, style);
            mSymbolManager.create(new SymbolOptions()
                    .withLatLng(latLng)
                    .withIconImage("MARKER_ICON")
                    .withIconSize(.5f));

            CameraPosition position = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(14)
                    .tilt(20)
                    .build();

            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
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
