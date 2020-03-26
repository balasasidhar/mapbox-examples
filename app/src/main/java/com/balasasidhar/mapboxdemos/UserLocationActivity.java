package com.balasasidhar.mapboxdemos;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserLocationActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, OnSuccessListener<LocationSettingsResponse>, OnFailureListener {

    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private LocationRequest mLocationRequest;

    @BindView(R.id.mapView)
    MapView mMapView;

    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(getApplicationContext(), BuildConfig.MAPBOX_API_KEY);

        setContentView(R.layout.activity_user_location);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("User Location");

        mMapView.onSaveInstanceState(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> checkPermissions());
    }

    private void checkPermissions() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            checkLocationSettings();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void checkLocationSettings() {
        createLocationRequest();
        SettingsClient client = LocationServices.getSettingsClient(this);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this);
        task.addOnFailureListener(this);
    }

    private void enableLocationComponent() {
        LocationComponent locationComponent = mapboxMap.getLocationComponent();
        locationComponent.activateLocationComponent(LocationComponentActivationOptions
                .builder(this, mapboxMap.getStyle()).build());
        locationComponent.setLocationComponentEnabled(true);
        locationComponent.setCameraMode(CameraMode.TRACKING);
        locationComponent.setRenderMode(RenderMode.COMPASS);
        getUserLocation();
    }

    private void getUserLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(14)
//                        .tilt(20)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
            }
        });
    }


    @Override
    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
        enableLocationComponent();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof ResolvableApiException) {
            try {
                ResolvableApiException resolvable = (ResolvableApiException) e;
                resolvable.startResolutionForResult(this,
                        REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException sendEx) {
                // Ignore the error.
            }
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
//        ignore
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            checkPermissions();
        } else {
            Toast.makeText(getApplicationContext(), "Permission's denied", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) {
            checkLocationSettings();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
