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
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.taxiconductor.Constants;
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

    private Timer timer;
    private TimerTask timerTask;
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

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED);

            Location location = locationManager.getLastKnownLocation(bestProvider);
            if(location!=null){
                onLocationChanged(location);
                this.driver_latitude = location.getLatitude();
                this.driver_longitude = location.getLongitude();
            }
            locationManager.requestLocationUpdates(bestProvider, 3000, 0, this);

            if(this.timerTask == null){

                this.timer = new Timer();

                this.timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        presenterHome.validateUpdateCoordinates(id_driver, driver_latitude, driver_longitude);
                    }
                };
                this.timer.scheduleAtFixedRate(this.timerTask, 0, 3000);
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try{
            Log.e("ESTATUS: ", "DETENIENDO ACTUALIZACIÓN DE COORDENADAS");
            this.timerTask.cancel();
            this.timerTask = null;
            Intent localIntent = new Intent(Constants.ACTION_PROGRESS_EXIT);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onLocationChanged(Location location) {
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