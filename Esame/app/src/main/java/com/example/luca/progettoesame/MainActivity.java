package com.example.luca.progettoesame;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.SensorManager.getOrientation;
import static android.hardware.SensorManager.getRotationMatrix;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGravitySensor;
    private Sensor mGeomagneticSensor;

    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;
    private static final float DEFAULT_ZOOM = 15f;

    private TextView azimuthTv;
    private TextView GPS;
    private LocationManager locationManager;
    private TextView nome;


    float[] gravity = new float[3];
    float[] geomagnetic = new float[3];

    private ArrayList<Citta> cities;
    private double latitudine;
    private double longitudine;
    private ArrayDeque<Float> codaAzim = new ArrayDeque<>();
    private final int MAX_NUM_AZIM = 25;
    private static String TAG = MainActivity.class.getSimpleName();
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private Polyline line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGeomagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        azimuthTv = findViewById(R.id.azimuth);
        nome = (TextView) findViewById(R.id.nome);
        GPS = findViewById(R.id.GPS);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mNavItems.add(new NavItem("Capitali", "Elenco capitali", R.drawable.onu, MySQLiteHelper.COUNTRY_CODE_CAPITALI));
        mNavItems.add(new NavItem("Italia", "Elenco città italiane", R.drawable.italia, "ITA"));
        mNavItems.add(new NavItem("Francia", "Elenco città francesi", R.drawable.francia, "FRA"));
        mNavItems.add(new NavItem("Germania", "Elenco città tedesche", R.drawable.germany, "GER"));
        mNavItems.add(new NavItem("Giappone", "Elenco città giapponesi", R.drawable.japan, "JAP"));
        mNavItems.add(new NavItem("Regno Unito", "Elenco città inglesi", R.drawable.uk, "GBR"));
        mNavItems.add(new NavItem("Stati Uniti d'America", "Elenco città americane", R.drawable.usa, "USA"));


        changeCities(MySQLiteHelper.COUNTRY_CODE_CAPITALI, this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems, this);
        mDrawerList.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                latitudine = location.getLatitude();
                longitudine = location.getLongitude();
                GPS.setText(MessageFormat.format("{0}, {1}",
                        String.valueOf(latitudine),
                        String.valueOf(longitudine)));
                updatePolyline();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        getLocationPermission();
        checkLocationPermission();
        checkLocationPermission1();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher_background, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(TAG, "onDrawerClosed: ");

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkLocationPermission1() {
        String permission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGeomagneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void selectItemFromDrawer(int position) {
        Fragment fragment = new PreferencesFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.equals(mGravitySensor)) {
            gravity = event.values;
            updateScreen();
        } else if (event.sensor.equals(mGeomagneticSensor)) {
            geomagnetic = event.values;
            updateScreen();
        }
    }

    public void updateScreen() {
        float[] R = new float[16];
        getRotationMatrix(R, null, gravity, geomagnetic);
        float[] angles = new float[3];
        getOrientation(R, angles);
        codaAzim.addFirst(angles[0]);
        if (codaAzim.size() > MAX_NUM_AZIM) {
            codaAzim.removeLast();
        }

        azimuthTv.setText(String.valueOf(Math.toDegrees(getAzimuth())));
        Citta citta = findCity();
        if (citta != null) {
            nome.setText(citta.getNomeCitta());
        }

    }

    public float getAzimuth() {
        float sum = 0;
        for (Float x : codaAzim) {
            sum += x;
        }
        return sum / codaAzim.size();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Vector3D gps_to_xyz(double lat, double longg, double radius) {
        lat = Math.toRadians(90 - lat);
        longg = Math.toRadians(longg);
        double x = radius * Math.sin(lat) * Math.cos(longg);
        double y = radius * Math.sin(lat) * Math.cos(longg);
        double z = radius * Math.cos(lat);
        Vector3D v3 = new Vector3D(x, y, z);
        return v3;
    }

    public Citta findCity() {
        double azimuth = getAzimuth();
        Vector3D earth = new Vector3D(0, 0, 0);
        Vector3D user = gps_to_xyz(latitudine, longitudine, 1);
        double minAngle = Math.toRadians(5);
        ArrayList<Citta> cittaValide = new ArrayList<>();
        Citta city = null;
        for (Citta x : cities) {
            Vector3D cityPos = gps_to_xyz(x.getLatitudine(), x.getLongitudine(), 1);
            Vector3D locPos = new Vector3D(-Math.cos(azimuth), Math.sin(azimuth), 0);
            Rotation quat_rot_lat = new Rotation(new Vector3D(0, 1, 0), latitudine);
            Rotation quat_rot_long = new Rotation(new Vector3D(0, 0, 1), longitudine);
            Vector3D gloDir = quat_rot_long.applyTo(quat_rot_lat.applyTo(locPos));
            Vector3D normUser = Vector3D.crossProduct(user, cityPos);
            Vector3D normCity = Vector3D.crossProduct(user, gloDir);
            double angle = Vector3D.angle(normUser, normCity);
            if (angle < minAngle) {
                minAngle = angle;
                city = x;
                //cittaValide.add(x);
            }
        }
        //cittaValide.sort(new Comparator<Citta>() {
        //@Override
        //public int compare(Citta o1, Citta o2) {
        //return o1.getPopolazione() - o2.getPopolazione();
        //  }
        //});
        return city;
    }


    //-------------------------GOOGLE MAPS CODE---------------------------------------------------------//

    public void changeCities(String countryCode, Context context) {
        MySQLiteHelper helper = MySQLiteHelper.getInstance(context);
        cities = helper.elencoCitta(countryCode);
    }


    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void onRequestPermissionResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResult) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResult.length > 0) {
                    for (int i = 0; i < grantResult.length; i++) {
                        if (grantResult[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            break;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "on Complete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        } else {
                            Log.d(TAG, "on Complete: current location is null");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: security Exception: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            Log.d("GPS", Double.valueOf(latitudine).toString());
            Log.d("GPS", Double.valueOf(longitudine).toString());

            line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(0, 0), new LatLng(40.7, -74.0))
                    .width(5)
                    .color(Color.RED));
            line.setGeodesic(true);
        }
    }

    private void updatePolyline() {
        if (line !=null){
            line.remove();
            LatLng startPoint = new LatLng(latitudine, longitudine);

            LatLng endPoint = new LatLng(45, 45);
            line = mMap.addPolyline(new PolylineOptions()
                    .add(startPoint, endPoint)
                    .width(5)
                    .color(Color.RED));
            line.setGeodesic(true);
            Location start = new Location("");
            start.setLatitude(latitudine);
            start.setLongitude(longitudine);

            Location end = new Location("");
            end.setLatitude(45);
            end.setLongitude(45);
            Log.d("Distance", Double.valueOf(start.distanceTo(end)).toString());
        }

    }
}


