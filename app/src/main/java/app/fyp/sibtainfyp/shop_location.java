package app.fyp.sibtainfyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class shop_location extends AppCompatActivity implements OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    public Button savloc;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap map;
    public double lat,lon;
    Location location;

    //new implementataion
    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double latt =0, lonn=0;
    global_var globalVariable = (global_var) getApplication();


    //Inent Things :  Start

    String owner_name,shop_name,owner_email,ownervalidated_pass;

    //Intent Things : End
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_location);
        savloc = (Button) findViewById(R.id.saveloc);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();



        //Login text Clicked
        savloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("saveloc ","save loc btn Text Clicked");
//                Intent intent=new Intent(getApplicationContext(),Dashboard.class);
//                startActivity(intent);
//                finish();
            }
        });
        //Login text Clicked
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //Getting intent Extra Data

        owner_name = getIntent().getExtras().getString("ShopOwner");
        shop_name = getIntent().getExtras().getString("ShopName");
        owner_email = getIntent().getExtras().getString("OwnerEmail");

        Toast.makeText(getApplicationContext(),owner_name+ "" +shop_name+ " "+owner_email,Toast.LENGTH_SHORT).show();


        //Gettng intent Extra Data


    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
//                                    Log.v("lat",location.getLatitude()+"");
//                                    Log.v("lon",location.getLongitude()+"");
                                    if (map == null) {
                                        // check if map is created successfully or not
                                        if (map == null) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }
                                    map.setMyLocationEnabled(true); //Nullpointer exception.........
                                    LatLng myLatLng = new LatLng(location.getLatitude(),
                                            location.getLongitude());

                                    CameraPosition myPosition = new CameraPosition.Builder()
                                            .target(myLatLng).zoom(17).bearing(90).tilt(30).build();
                                    map.animateCamera(
                                            CameraUpdateFactory.newCameraPosition(myPosition));
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.v("lat",mLastLocation.getLatitude()+"");
//            Log.v("lon",mLastLocation.getLongitude()+"");

            globalVariable.setLatitude(mLastLocation.getLatitude()+"");
            Log.v("loc",globalVariable.getLatitude());
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

    private void buildGoogleApiClient() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);

        } else {
            Toast.makeText(this, "Persmission Error", Toast.LENGTH_LONG).show();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        //add this here:
        buildGoogleApiClient();


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();
                map.addMarker(new MarkerOptions().position(latLng).title("New Marker on click"));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
        //LatLng loc = new LatLng(lat, lng);
        //mMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lonn = mLastLocation.getLongitude();

            LatLng loc = new LatLng(lat, lonn);
            map.addMarker(new MarkerOptions().position(loc).title("New Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




}
