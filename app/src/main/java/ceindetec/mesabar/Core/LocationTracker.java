package ceindetec.mesabar.Core;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationTracker implements LocationListener {

    public LocationTracker(LocationManager locationManager) {

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);

        double latitudUsuario = location.getLatitude();
        double longitudUsuario = location.getLongitude();

        GlobalVars.getGlobalVarsInstance().setPosLatitud(latitudUsuario);
        GlobalVars.getGlobalVarsInstance().setPosLongitud(longitudUsuario);

    }



    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            GlobalVars.getGlobalVarsInstance().setPosLatitud(location.getLatitude());
            GlobalVars.getGlobalVarsInstance().setPosLongitud(location.getLongitude());

        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Log.i("Proveedor: ", "Proveedor Activo");
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Log.i("Proveedor: ", "Proveedor Activo");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Log.i("Proveedor: ", "Proveedor Cambio");
    }

}
