package com.example.loginapp_programdesign.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.small.a20190105.R;

public class LocationTestActivity extends AppCompatActivity {
    private double locationX = 0.0;
    private double locationY  = 0.0;
    boolean gpsON = true;
    LocationManager mlocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);
        mlocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);//設置允許產生資費
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = mlocationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1
            );
        }else{
            Location location = mlocationManager.getLastKnownLocation(provider);
            Location lastloc = mlocationManager.getLastKnownLocation(provider);
            if (lastloc!=null)
                updateLocation(location, lastloc);
            mlocationManager.requestLocationUpdates(provider, 3000, 0, locationListener);
        }

    }
    private void updateLocation(Location location, Location lastloc) {
        if (location != null) {
            locationX = location.getLatitude();
            locationY  = location.getLongitude();
        } else if (lastloc!=null){
            locationX = lastloc.getLatitude();
            locationY  = lastloc.getLongitude();
        }
        else {
            locationX = 0.0;
            locationY = 0.0;
        }
        //背景執行時關閉顯示地點
        if(gpsON == true){
            Toast.makeText(LocationTestActivity.this, "" + "x:" + locationX  + " y:" + locationY , Toast.LENGTH_SHORT).show();
        }
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateLocation(location, null);

        }
        public void onProviderDisabled(String provider){
            updateLocation(null, null);
        }
        public void onProviderEnabled(String provider){

        }
        public void onStatusChanged(String provider, int status,Bundle extras){

        }
    };

}
