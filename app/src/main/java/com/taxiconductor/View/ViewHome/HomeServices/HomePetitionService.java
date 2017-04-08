package com.taxiconductor.View.ViewHome.HomeServices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.taxiconductor.Presenter.PresenterHome.HomePresenterImp;
import com.taxiconductor.R;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import com.taxiconductor.Utils.Util;
import com.taxiconductor.View.ViewHome.HomeActivity;
import com.taxiconductor.View.ViewHome.HomeView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sandoval on 08/04/2017.
 */

public class HomePetitionService extends Service implements HomeView {

    private static final String TAG = "Petición recibida";
    int id_driver;

    private Timer timer;
    private TimerTask timerTask;
    private SharedPreferences preferences;

    private HomePresenterImp presenterHome;


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
    public int onStartCommand(final Intent intent, int flags, int startId) {

        if(this.timerTask == null){

            this.timer = new Timer();

            this.timerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.e("ESTATUS: ", "ESCUCHANDO PETICIONES");
                    presenterHome.validateListenerPetition(id_driver);
                }
            };
            this.timer.scheduleAtFixedRate(this.timerTask, 0, 3000);
        }
        return START_STICKY;
    }

    @Override
    public void messageServiceStatic(String message) {

    }

    @Override
    public void messageServiceConstant(String message) {

    }

    @Override
    public void ServicePetition(ModelStatus petition) {
        if(petition!=null){
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getBaseContext())
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("NOTIFICACIÓN")
                    .setContentText("Tiene una solicitud de servicio")
                    .setWhen(System.currentTimeMillis());

            Intent targetIntent = new Intent(getBaseContext(),
                    HomeActivity.class);

            PendingIntent contentIntent = PendingIntent.getActivity(
                    getBaseContext(), 0, targetIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            builder.setAutoCancel(true);

            nManager.notify(12345, builder.build());
            this.timerTask.cancel();
            this.timerTask = null;
        }

    }

    @Override
    public void onDestroy() {
        try{
            Log.e("ESTATUS: ", "DETENIENDO ESCUCHA DE PETICIONES");
            this.timerTask.cancel();
            this.timerTask = null;
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void executeSoundVibrator(boolean start) {
        if (start) {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            MediaPlayer mp = MediaPlayer.create(this, R.raw.alert);
            mp.start();
            v.vibrate(1500);
        }
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
