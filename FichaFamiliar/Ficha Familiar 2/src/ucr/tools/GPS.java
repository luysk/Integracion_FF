package ucr.tools;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;


/**
 * Created by luysk on 09/12/2014.
 */
public class GPS implements LocationListener{

        String ubicacionActual = "";
        int esta_encendido;


        @Override
        public void onLocationChanged(Location ubicacion){
            String latitud,longitud;
            latitud = ""+ubicacion.getLatitude();
            longitud = ""+ubicacion.getLongitude();
            ubicacionActual = latitud+"|"+longitud;
        }

        public String getUbicacionActual(){
            return ubicacionActual;
        }

        public void onProviderDisabled(String mensaje){
            esta_encendido = 0;
        }

        public void onProviderEnabled(String mensaje){
            esta_encendido = 1;
        }

        public void onStatusChanged(String proveedor, int estado ,Bundle extra){


        }




}
