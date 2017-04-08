package com.taxiconductor.View.ViewHome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.taxiconductor.Constants;
import com.taxiconductor.DirectionMaps.DirectionFinder;
import com.taxiconductor.DirectionMaps.DirectionFinderListener;
import com.taxiconductor.DirectionMaps.Route;
import com.taxiconductor.Presenter.PresenterHome.HomePresenterImp;
import com.taxiconductor.R;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import com.taxiconductor.Utils.Util;
import com.taxiconductor.View.ViewHome.HomeServices.HomeLocationService;
import com.taxiconductor.View.ViewLogin.LoginActivity;
import com.taxiconductor.View.ViewTravels.TravelsActivity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener, HomeView, DirectionFinderListener, CompoundButton.OnCheckedChangeListener {

    public GoogleMap mMap;
    public SupportMapFragment mapFragment;
    private HomePresenterImp presenterHome;

    public double driver_latitude;
    public double driver_longitude;
    private static int id_driver;
    private static int counter;
    private int saved_state;

    private TextView tv_id_drive;
    private Button btn_status_petition;
    private Button btn_status_occupied;
    private TextView tv_distance;
    private TextView tv_duration;
    private TextView tv_message;
    private static AlertDialog dialog;

    public TimerTask mTimerTask;
    final Handler handler = new Handler();
    public Timer t = new Timer();
    public int nCounter = 0;

    public TimerTask mTimerTaskPetition;
    final Handler handlerPetition = new Handler();
    public Timer tPetition = new Timer();
    public int nCounterPetition = 0;

    public static ModelStatus coordinates_global;
    public static String user;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ToggleButton zoom;

    private static LocationManager locationManager;
    private  static Location location;
    private static Criteria criteria;
    private static String bestProvider;
    private static LatLng latLng;

    public boolean validator_occupied;

    public static String coordinates_driver;
    public static String coordinates_origin;
    public static String coordinates_destination;

    private SharedPreferences preferences;

    public String history_origin = "";
    public String history_destination = "";

    Intent intentUpdateCoordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenterHome = new HomePresenterImp(this);
        preferences =  getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        id_driver = Util.getIdDriverPrefs(preferences);
        user = Util.getUserPrefs(preferences);

        tv_id_drive = (TextView) findViewById(R.id.textView_id_driver);
        btn_status_petition = (Button) findViewById(R.id.button_status_petition);
        btn_status_occupied = (Button) findViewById(R.id.button_status_occupied);
        tv_distance = (TextView) findViewById(R.id.textView_distance);
        tv_duration = (TextView) findViewById(R.id.texView_duration);
        tv_message = (TextView) findViewById(R.id.textView_message);
        zoom = (ToggleButton) findViewById(R.id.zoom);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_status_petition.setOnClickListener(this);
        btn_status_occupied.setOnClickListener(this);
        zoom.setOnCheckedChangeListener(this);

        intentUpdateCoordinates = new Intent(getApplicationContext(), HomeLocationService.class);

        tv_id_drive.setText("Usted ha ingresado cómo usuario: " + user);
        if(savedInstanceState == null){
            counter = 0;
            saved_state = 0;
            validator_occupied = true;
            zoom.setChecked(true);
        }
        // Filtro de acciones que serán alertadas
        IntentFilter filter = new IntentFilter(
                Constants.ACTION_RUN_ISERVICE);
        filter.addAction(Constants.ACTION_PROGRESS_EXIT);

        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver =
                new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;
        mMap.setMyLocationEnabled(true);
    
    }

    public void getLocalization(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        location = locationManager.getLastKnownLocation(bestProvider);
        if(location!=null){
            onLocationChanged(location);
            driver_latitude = location.getLatitude();
            driver_longitude = location.getLongitude();
            latLng = new LatLng(driver_latitude, driver_longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
        locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_status_petition:

                if (counter == 0) {
                        startListenerPetition();
                        startService(intentUpdateCoordinates);
                        saved_state=1;
                        counter++;
                        mMap.clear();
                        tv_distance.setText("0 km");
                        tv_duration.setText("0 min");
                        presenterHome.validateUpdateStatus(id_driver, 1);
                        btn_status_petition.setBackgroundColor(Color.GREEN);
                        btn_status_occupied.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGrey));
                        validator_occupied = true;
                        Toast.makeText(getApplication(), "Ahora está disponible, espere una solicitud de viaje", Toast.LENGTH_SHORT).show();

                } else if (counter == 1) {

                    Toast.makeText(getApplication(), "No puede cambiar de estado hasta que tenga una solicitud de viaje", Toast.LENGTH_SHORT).show();

                } else if (counter == 2) {

                    saved_state = 3;
                    counter++;
                    presenterHome.validateUpdateStatus(id_driver, 3);
                    btn_status_petition.setBackgroundColor(Color.BLUE);
                    Toast.makeText(getApplication(), "Esperando a que el pasajero aborde la unidad", Toast.LENGTH_SHORT).show();

                } else if (counter == 3) {

                    saved_state=4;
                    coordinates_origin = coordinates_global.getPetition().getLatitude_client() + "," + coordinates_global.getPetition().getLongitude_client();
                    coordinates_destination = coordinates_global.getPetition().getLatitude_destin() + "," + coordinates_global.getPetition().getLongitude_destin();
                    sendRequest(coordinates_origin, coordinates_destination);
                    counter++;
                    mapFragment.getMapAsync(HomeActivity.this);
                    tv_message.setText("");
                    presenterHome.validateUpdateStatus(id_driver, 4);
                    btn_status_petition.setBackgroundColor(Color.RED);
                    presenterHome.validateDeleteDriverPetition(id_driver);
                    presenterHome.validateInserHistorTravel(id_driver,history_origin,history_destination,getCurrentDate());
                    Toast.makeText(getApplication(), "El pasajero ha abordado el taxi, estás dirigiéndote a su destino", Toast.LENGTH_SHORT).show();
                }
                else if(counter == 4){
                    saved_state = 5;
                    counter = 0;
                    history_origin = "";
                    history_destination = "";
                    mMap.clear();
                    btn_status_petition.setBackgroundColor(Color.RED);
                    Toast.makeText(getApplication(),"Ha terminado el viaje, pulse el botón para estar disponible",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.button_status_occupied:

                if(validator_occupied){
                    if(counter == 0 || counter == 1){
                    saved_state = 6;
                    btn_status_petition.setBackgroundColor(ContextCompat.getColor(getApplication(),R.color.colorGrey));
                    btn_status_occupied.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorBlack));
                    validator_occupied = false;
                    counter = 0;
                    presenterHome.validateUpdateStatus(id_driver,5);
                    stopListenerPetition();
                    Toast.makeText(getApplication(),"Modo ocupado activado",Toast.LENGTH_SHORT).show();
                    }
                    else if(counter == 2 || counter == 3 || counter == 4){

                        Toast.makeText(getApplication(),"Tiene un viaje asignado",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    saved_state = 6;
                    btn_status_occupied.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGrey));
                    validator_occupied = true;
                    presenterHome.validateUpdateStatus(id_driver,0);
                    stopListenerPetition();
                    stopService(intentUpdateCoordinates);
                    Toast.makeText(getApplication(),"Modo ocupado desactivado",Toast.LENGTH_LONG).show();
                }
                break;

            default:
                Toast.makeText(this,"Button not finded",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void messageServiceStatic(String message) {
        Log.e("MessageServiceStatic", message);
    }

    @Override
    public void messageServiceConstant(String message) {
        Log.e("MessageServiceDynamic", message);
    }

    @Override
    public void messageDeletePetition(String message) {
        if (message.equals("1")){
            startListenerPetition();
            Log.e("OkDeletePetition", message);
        }else{
            Log.e("FailedDeletePetition", message);
        }
    }

    @Override
    public void messageFailedPetition(String message) {
        Log.e("MessagePetition", message);
    }

    @Override
    public void codeUpdateStatus(int statusCode, int statusDriver) {
        if(statusDriver == 0){
            if(statusCode!= 200){
                presenterHome.validateUpdateStatus(id_driver, 0);
                Log.e("Error", "en cambiar a estado 0 en servidor");

            }
        }
        else if(statusDriver ==  1){
            if (statusCode != 200){
                Log.e("Error", "en cambiar a estado 1 en servidor");
                presenterHome.validateUpdateStatus(id_driver, 1);
            }
        }
        else if(statusDriver ==  2){
            if(statusCode != 200){
                Log.e("Error", "en cambiar a estado 2 en servidor");
                presenterHome.validateUpdateStatus(id_driver, 2);

            }
        }
        else if(statusDriver == 3){
            if(statusCode != 200){
                Log.e("Error", "en cambiar a estado 3 en servidor");

                presenterHome.validateUpdateStatus(id_driver, 3);
            }
        }
        else if(statusDriver == 4){
            if(statusCode != 200){
                Log.e("Error", "en cambiar a estado 4 en servidor");
                presenterHome.validateUpdateStatus(id_driver, 4);
            }
        }
    }

    @Override
    public void codeDeletePetition(int statusCode) {
        if(statusCode!=200){
            Log.e("Error", "en eliminar petición del servidor");
            presenterHome.validateDeleteDriverPetition(id_driver);
        }
    }

    @Override
    public void codeDeleteDriver(int statusCode) {
        if(statusCode==200){
            saved_state=7;
            stopListenerPetition();
            stopUpdateZoom();
            stopService(intentUpdateCoordinates);
            preferences.edit().clear().apply();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(getApplication(),"Ha cerrado sesión correctamente",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplication(),"Parece que hubo un error al cerrar sesión, inténtalo de nuevo",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void codeInsertHistoryTravel(int statusCode) {
        if(statusCode == 200){
            Toast.makeText(getApplication(),"Viaje registrado en el historial correctamente",Toast.LENGTH_LONG).show();
        }else{
            presenterHome.validateInserHistorTravel(id_driver,history_origin,history_destination,getCurrentDate());
        }
    }

    @Override
    public void ServicePetition(final ModelStatus petition) {
        if (petition != null) {
            stopListenerPetition();
            if(zoom.isChecked()){
                zoom.setChecked(false);
            }
            coordinates_global = petition;
            if (dialog != null) {
                dialog = null;
            }
            dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Petición de viaje");
            dialog.setMessage("Hay una petición de viaje entrante");
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int buttonId) {
                            saved_state=2;
                            counter++;
                            tv_message.setText(petition.getPetition().getMessage());
                            coordinates_driver  = String.valueOf(driver_latitude)+ ","+ String.valueOf(driver_longitude);
                            coordinates_origin = driver_latitude + "," + driver_longitude;
                            coordinates_destination = coordinates_global.getPetition().getLatitude_client() + "," + coordinates_global.getPetition().getLongitude_client();
                            sendRequest(coordinates_origin, coordinates_destination);
                            btn_status_petition.setBackgroundColor(Color.YELLOW);
                            presenterHome.validateUpdateStatus(id_driver,2);
                            Toast.makeText(getApplication(),"Conductor en camino...",Toast.LENGTH_SHORT).show();
                        }
                    });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int buttonId) {
                            counter=1;
                            presenterHome.validateDeleteDriverPetition(id_driver);
                            Toast.makeText(getApplication(),"Ha cancelado el viaje solicitado",Toast.LENGTH_SHORT).show();
                        }
                    });
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            dialog.show();
        }
    }

    @Override
    public void executeSoundVibrator(boolean start) {
        if (start) {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            MediaPlayer mp = MediaPlayer.create(this, R.raw.alert);
            mp.start();
            v.vibrate(2000);
        }
    }

    private void sendRequest(String origin, String destination) {


        if (origin.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese la dirección de origen!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese la dirección de destino!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            new DirectionFinder(this, origin, destination).execute();
           // animateCamera(origin, destination);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {

        try {
            polylinePaths = new ArrayList<>();
            originMarkers = new ArrayList<>();
            destinationMarkers = new ArrayList<>();

            for (Route route : routes) {
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 13));
                ((TextView) findViewById(R.id.texView_duration)).setText(route.duration.text);
                ((TextView) findViewById(R.id.textView_distance)).setText(route.distance.text);

                history_origin = route.startAddress;
                history_destination = route.endAddress;

                originMarkers.add(mMap.addMarker(new MarkerOptions()
                        //             .title(nombre)
                        .position(route.startLocation)));
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .title(route.endAddress)
                        .position(route.endLocation)));

                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.BLUE).
                        width(10);

                for (int i = 0; i < route.points.size(); i++)
                    polylineOptions.add(route.points.get(i));

                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
            Double origin_lat,origin_lng;
            Double destination_lat,destination_lng;

            String cadena[] = coordinates_origin.split(",");
            origin_lat = Double.parseDouble(cadena[0]);
            origin_lng = Double.parseDouble(cadena[1]);

            String cadena2[] = coordinates_destination.split(",");
            destination_lat = Double.parseDouble(cadena2[0]);
            destination_lng = Double.parseDouble(cadena2[1]);

            LatLng var_origen = new LatLng(origin_lat,origin_lng);
            LatLng var_destino = new LatLng(destination_lat,destination_lng);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(var_origen);
            builder.include(var_destino);
            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
            mMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
                public void onCancel() {
                }

                public void onFinish() {
                    CameraUpdate zout = CameraUpdateFactory.zoomBy(-1.0f);
                    mMap.animateCamera(zout);
                }
            });


        } catch (Exception e) {
            Toast.makeText(getApplication(), "Hubo un error al obtener la ubicación " + e, Toast.LENGTH_LONG).show();
        }

    }

    public void startUpdateZoom() {
        if(this.mTimerTask==null){
            this.mTimerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        nCounter++;
                        getLocalization();
                    }
                });
            }
            };

            // public void schedule (TimerTask task, long delay, long period)
            this.t.schedule(this.mTimerTask, 0, 3000);  //
        }
    }

    public void startListenerPetition() {
        if(this.mTimerTaskPetition==null){
         this.mTimerTaskPetition = new TimerTask() {
            public void run() {
                handlerPetition.post(new Runnable() {
                    public void run() {
                        nCounterPetition++;
                        presenterHome.validateListenerPetition(id_driver);
                    }
                });
            }
        };
        this.tPetition.schedule(this.mTimerTaskPetition, 0, 3000);  //
        }

    }

    public void stopUpdateZoom() {
        if (this.mTimerTask != null) {
            this.mTimerTask.cancel();
            this.mTimerTask = null;
        }
    }

    public void stopListenerPetition() {
        if (this.mTimerTaskPetition != null) {
            Log.e("timerTask", "petitions cancel");
            this.mTimerTaskPetition.cancel();
            this.mTimerTaskPetition = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_logout:
                presenterHome.validateDeleteDriver(id_driver);
                return true;
            case R.id.action_travels:
                Intent intent_travels = new Intent(this, TravelsActivity.class);
                startActivity(intent_travels);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showProgress(){

        progressDialog = ProgressDialog.show(this, "", "Cerrando sesión...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hideProgress()
    {
        Toast.makeText(getApplication(),"Ha cerrado sesión correctamente",Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intentUpdateCoordinates = new Intent(getApplicationContext(), HomeLocationService.class);
        saved_state=7;
        stopListenerPetition();
        stopUpdateZoom();
        stopService(intentUpdateCoordinates);
        presenterHome.validateDeleteDriver(id_driver);
        preferences.edit().clear().apply();
        showProgress();
        hideProgress();
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("validator_occupied", validator_occupied);
        if(saved_state==0){
            outState.putInt("saved_state",saved_state);
            outState.putInt("id_driver",id_driver);
            outState.putInt("counter",counter);
            outState.putString("user", user);
        }
        else if(saved_state==1){
            outState.putInt("saved_state",saved_state);
            outState.putInt("id_driver",id_driver);
            outState.putInt("counter", counter);
            outState.putString("user", user);
        }
        else if(saved_state==2){
            outState.putInt("saved_state",saved_state);
            outState.putInt("id_driver", id_driver);
            outState.putInt("counter", counter);
            outState.putString("user", user);
            outState.putString("coordinates_driver", coordinates_driver);
            outState.putString("coordinates_origin", coordinates_origin);
            outState.putString("coordinates_destination", coordinates_destination);

        }
        else if(saved_state==3){
            outState.putInt("saved_state",saved_state);
            outState.putInt("id_driver",id_driver);
            outState.putInt("counter",counter);
            outState.putString("user", user);
            outState.putString("coordinates_origin", coordinates_origin);
            outState.putString("coordinates_destination", coordinates_destination);

        }
        else if(saved_state==4){
            outState.putInt("saved_state",saved_state);
            outState.putInt("id_driver", id_driver);
            outState.putInt("counter", counter);
            outState.putString("user", user);
            outState.putString("coordinates_origin", coordinates_origin);
            outState.putString("coordinates_destination", coordinates_destination);
        }else if(saved_state == 5){
            outState.putInt("saved_state",saved_state);
            outState.putInt("id_driver",id_driver);
            outState.putInt("counter", counter);
            outState.putString("user", user);

        }
        else if(saved_state == 6){
            outState.putInt("saved_state",saved_state);
            outState.putInt("id_driver",id_driver);
            outState.putInt("counter", counter);
            outState.putString("user", user);
            outState.putBoolean("validator_occupied", validator_occupied);
        }
        else if(saved_state==7){
            outState.clear();
        }
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        saved_state = savedInstanceState.getInt("saved_state");
        counter= savedInstanceState.getInt("counter");
        id_driver = savedInstanceState.getInt("id_driver");
        user = savedInstanceState.getString("user");
        validator_occupied = savedInstanceState.getBoolean("validator_occupied");

        if(saved_state == 0){

        }
        else if(saved_state==1){
            btn_status_petition.setBackgroundColor(Color.GREEN);
        }
        else if(saved_state==2){
            Log.e(">>>>","se supone que está en estado 1>>>>>>>>>>>>>>>>>>>>>>");
            coordinates_origin = savedInstanceState.getString("coordinates_origin");
            coordinates_destination = savedInstanceState.getString("coordinates_destination");
            sendRequest(coordinates_origin,coordinates_destination);
            btn_status_petition.setBackgroundColor(Color.YELLOW);
        }
        else if(saved_state==3){

            coordinates_origin = savedInstanceState.getString("coordinates_origin");
            coordinates_destination = savedInstanceState.getString("coordinates_destination");
            sendRequest(coordinates_origin,coordinates_destination);
            btn_status_petition.setBackgroundColor(Color.BLUE);

        }else if(saved_state==4){

            coordinates_origin = savedInstanceState.getString("coordinates_origin");
            coordinates_destination = savedInstanceState.getString("coordinates_destination");
            sendRequest(coordinates_origin, coordinates_destination);
            btn_status_petition.setBackgroundColor(Color.RED);
        }
        else if(saved_state == 5){
            btn_status_petition.setBackgroundColor(ContextCompat.getColor(getApplication(),R.color.colorGrey));
            btn_status_occupied.setBackgroundColor(ContextCompat.getColor(getApplication(),R.color.colorGrey));
        }
        else if(saved_state == 6){

            if(validator_occupied){
                if(counter == 0 || counter == 1){
                    btn_status_occupied.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGrey));
                    validator_occupied = true;
                    stopListenerPetition();
                }
            }else {
                btn_status_petition.setBackgroundColor(ContextCompat.getColor(getApplication(),R.color.colorGrey));
                btn_status_occupied.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorBlack));
                validator_occupied = false;
                stopListenerPetition();
            }
        }
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        Log.e("DATE",strDate);
        return strDate;
    }



    @Override
    public void onLocationChanged(Location location) {
        //locationManager.removeUpdates(this);
        driver_latitude = location.getLatitude();
        driver_longitude = location.getLongitude();
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.zoom:
                if (isChecked) {
                    zoom.setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(),"Foco de cámara activado",Toast.LENGTH_SHORT).show();
                    startUpdateZoom();

                } else {
                    zoom.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGrey));
                    Toast.makeText(getApplicationContext(),"Foco de cámara desactivado",Toast.LENGTH_SHORT).show();
                    stopUpdateZoom();
                }
                break;
        }
    }


    private class ResponseReceiver extends BroadcastReceiver {

        // Sin instancias
        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.ACTION_PROGRESS_EXIT:
                    Log.e("SERVICE","Se ha detenido el servicio");
                    break;
            }
        }
    }

}
