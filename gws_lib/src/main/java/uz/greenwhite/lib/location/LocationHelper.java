package uz.greenwhite.lib.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import uz.greenwhite.lib.GWSLIB;

public class LocationHelper {

    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    final int waitTime = 1000 * 20;
    private Handler mHandler = new Handler();

    public static boolean getOneLocation(Context context, LocationResult result) {
        return (new LocationHelper()).getLocation(context, result);
    }

    public boolean getLocation(Context context, LocationResult result) {
        locationResult = result;
        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            GWSLIB.log(e);
            return false;
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            GWSLIB.log(e);
            return false;
        }

        if (!gps_enabled && !network_enabled) {
            GWSLIB.log("gps " + gps_enabled + " and network " + network_enabled);
            return false;
        }
        if (gps_enabled)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);

        if (network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

        mHandler.removeCallbacks(GetLastLocation); // удаляем GetLastLocation
        mHandler.postDelayed(GetLastLocation, waitTime);
        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            mHandler.removeCallbacks(GetLastLocation);// (отключаем таймер)
            lm.removeUpdates(this);// отписываемся от провайдеров гео данных
            lm.removeUpdates(locationListenerNetwork);
            locationResult.gotLocation(location);// вызываем callback метод,
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (gps_enabled)// если включен GPS просто выходим. Ждем обновления данных
                return;
            mHandler.removeCallbacks(GetLastLocation);// отписываемся от отложенного
            lm.removeUpdates(this);// отписываемся от провайдеров гео данных
            lm.removeUpdates(locationListenerGps);
            locationResult.gotLocation(location);// вызываем callback метод,
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private Runnable GetLastLocation = new Runnable() {
        @Override
        public void run() {
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);

            Location net_loc = null, gps_loc = null;
            if (gps_enabled)
                gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (network_enabled)
                net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime())// выбираем тот, который
                    locationResult.gotLocation(gps_loc);
                else
                    locationResult.gotLocation(net_loc);
                return;
            }

            if (gps_loc != null) {
                locationResult.gotLocation(gps_loc);
                return;
            }
            if (net_loc != null) {
                locationResult.gotLocation(net_loc);
                return;
            }
            locationResult.gotLocation(null);
        }
    };

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}
