package com.taxiconductor.View.ViewHome.HomeServices;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.taxiconductor.Presenter.PresenterHome.HomePresenterImp;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import com.taxiconductor.Utils.Util;
import com.taxiconductor.View.ViewHome.HomeView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sandoval on 30/03/2017.
 */

public class HomeLocationService extends Service implements LocationListener, HomeView{

    private HomePresenterImp presenterHome;

    TimerTask timerTask;
    private LocationManager locationManager;
    private SharedPreferences preferences;

    int id_driver;
    public double driver_latitude;
    public double driver_longitude;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        presenterHome = new HomePresenterImp(this);
        preferences =  getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        this.id_driver = Util.getIdDriverPrefs(preferences);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            Timer timer = new Timer();

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.e("ESTATUS: ", "ACTUALIZANDO COORDENADAS");
                    getLocalization();
                    Log.e("LO QUE VA A ENVIAR: ","Latitud: "+
                            driver_latitude+" Longitud: "+
                            driver_longitude+"ID DE CONDUCTOR: "+
                            String.valueOf(id_driver));
                    presenterHome.validateUpdateCoordinates(id_driver, driver_latitude, driver_longitude);
                }
            };

            timer.scheduleAtFixedRate(timerTask, 0, 3000);
        }

        catch (Exception e){e.printStackTrace();}

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        try{
            Log.e("ESTATUS: ", "DETENIENDO ACTUALIZACIÓN DE COORDENADAS");
            this.timerTask.cancel();
            this.timerTask = null;
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        this.driver_latitude = location.getLatitude();
        this.driver_longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void getLocalization(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true).toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        Location location = locationManager.getLastKnownLocation(bestProvider);
        if(location!=null){
            onLocationChanged(location);
            this.driver_latitude = location.getLatitude();
            this.driver_longitude = location.getLongitude();
        }
        else{
            locationManager.requestLocationUpdates(bestProvider, 3000, 0, this);
        }
    }

    @Override
    public void messageServiceStatic(String message) {

    }

    @Override
    public void messageServiceConstant(String message) {

    }

    @Override
    public void ServicePetition(ModelStatus petition) {

    }

    @Override
    public void executeSoundVibrator(boolean start) {

    }

    @Override
    public void messageDeletePetition(String message) {

    }

    @Override
    public void messageFailedPetition(String message) {

    }

    @Override
    public void codeUpdateStatus(int statusCode, int statusDriver) {

    }

    @Override
    public void codeDeletePetition(int statusCode) {

    }

    @Override
    public void codeDeleteDriver(int statusCode) {

    }

    @Override
    public void codeInsertHistoryTravel(int statusCode) {

    }
}
