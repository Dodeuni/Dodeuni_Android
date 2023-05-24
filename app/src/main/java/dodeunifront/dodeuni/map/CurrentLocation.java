package dodeunifront.dodeuni.map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.daum.mf.map.api.MapPoint;

import dodeunifront.dodeuni.MainActivity;

public class CurrentLocation {

    LocationManager lm;
    Activity activity;

    public CurrentLocation(LocationManager lm, Activity activity){
        setLm(lm);
        setActivity(activity);
    }

    public class Geocoord{
        double longitude;
        double latitude;
        Geocoord(double longitude, double latitude){
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public double getLongitude(){
            return longitude;
        }

        public double getLatitude(){
            return latitude;
        }
    }

    public Geocoord getCurrentLocation(){
        if (Build.VERSION.SDK_INT < 23 ||
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 가장최근 위치정보 가져오기
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                Geocoord geocoord = new Geocoord(longitude, latitude);

                Log.d("LOCATION CLASS", "위치정보 : " + provider + "\n" +
                        "위도 : " + longitude + "\n" +
                        "경도 : " + latitude);
                return geocoord;
            }
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10000,
                    1,
                    gpsLocationListener);
        } else {
            Toast.makeText(activity, "위치 권한 없음", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void setLm(LocationManager lm){
        this.lm = lm;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setPermission(){
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
        } public void onStatusChanged(String provider, int status, Bundle extras) {

        } public void onProviderEnabled(String provider) {

        } public void onProviderDisabled(String provider) {

        }
    };
}
