package com.taxiconductor.Interator.InteratorHome;

import android.util.Log;

import com.taxiconductor.RetrofitAPI.APIClient;
import com.taxiconductor.RetrofitAPI.APIService;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeInteratorImp implements HomeInterator {

    @Override
    public void insertDriver(final int id_driver, final double latitude, final double longitude, final int status, final OnHomeFinishedListener listener) {
    try{

        APIService service = APIClient.getClient().create(APIService.class);
        Call<ModelStatus> userCall = service.insertLocation(id_driver,latitude,longitude,status);
        userCall.enqueue(new Callback<ModelStatus>() {

            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {

                if (response.isSuccessful()) {

                    if(response.body().getStatus().equals("1")){
                        listener.onSuccessStatic(response.body().getMessage());
                    }else if(response.body().getStatus().equals("2")){
                        listener.onSuccessStatic(response.body().getMessage());
                    }
                }else{
                    listener.onFailedSuccessPetition("Error en llamar la petición insert driver");
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {
                Log.e("ERROR", ""+ "insert driver"+t);
            }
        });
    }catch (Exception e){
        Log.e("ERROR", ""+e);
    }

    }

    @Override
    public void updateCoordinates(final int id_driver, double latitude, double longitude, final OnHomeFinishedListener listener) {
    try{
        APIService service =  APIClient.getClient().create(APIService.class);
        Call<ModelStatus> userCall =  service.updateCoordinates(id_driver,latitude,longitude);
        userCall.enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()) {

                    if(response.body().getStatus().equals("1")){
                        listener.onSuccessDynamic(response.body().getMessage());
                        Log.e("OK", "Coordanadas actualizadas "+ response.code());

                    }else if(response.body().getStatus().equals("2")){
                        listener.onSuccessDynamic(response.body().getMessage());
                    }
                }else{
                    Log.e("ERROR", ""+ response.code());
                    listener.onFailedSuccessPetition("Error en llamar la petición update coordinates");
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {
                    //listener.onSuccessDynamic(t.getMessage());
                Log.e("ERROR", ""+ "update coordinates"+t);

            }
        });
    }catch (Exception e){
        Log.e("ERROR", ""+e);
    }
    }

    @Override
    public void listenPetition(final int id_driver, final OnHomeFinishedListener listener) {
    try {
        APIService service = APIClient.getClient().create(APIService.class);
        Call<ModelStatus> userCall = service.getPetitions(id_driver);
        userCall.enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()) {

                    if(response.body().getStatus().equals("1")){

                        ModelStatus petition;
                        petition = response.body();

                        listener.onSuccessPetition(petition);
                        listener.onSuccessDynamic(response.body().getMessage());

                    }else if(response.body().getStatus().equals("2")){

                        listener.onSuccessStatic(response.body().getMessage());
                    }else{

                    }
                }
                else {
                    Log.e("ERROR", ""+ response.code());
                    listener.onFailedSuccessPetition("Error en llamar la petición listen petition");
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {
                Log.e("ERROR", ""+ "escuchar peticion"+t);
            }
        });
    }catch (Exception e){
        Log.e("ERROR", ""+e);
    }
    }

    @Override
    public void updateStatus(final int id_driver, final int status, final OnHomeFinishedListener listener) {
    try {

        APIService service  = APIClient.getClient().create(APIService.class);
        Call<ModelStatus>userCall = service.updateStatus(id_driver,status);
        userCall.enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()){

                    if(response.body().getStatus().equals("1")){
                        listener.onSuccessStatic(response.body().getMessage());
                    }else if(response.body().getStatus().equals("2")){
                        listener.onSuccessStatic(response.body().getMessage());
                    }

                }else {
                    Log.e("ERROR", ""+ response.code());
                    listener.codeUpdateStatus(response.code(),status);
                    listener.onFailedSuccessPetition("Error en llamar la petición update status");
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {
                Log.e("ERROR", ""+ "update estatus"+t);
            }
        });
    }catch (Exception e){
        Log.e("ERROR", ""+e);
    }
    }

    @Override
    public void deleteDriver(final int id_driver, final OnHomeFinishedListener listener) {
    try{

        APIService service =  APIClient.getClient().create(APIService.class);
        Call<ModelStatus> userCall = service.deleteDriverSession(id_driver);
        userCall.enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                if (response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                        //listener.onSuccessStatic(response.body().getMessage());
                        listener.codeDeleteDriver(response.code());
                    }else if(response.body().getStatus().equals("2")){
                        //listener.onSuccessStatic(response.body().getMessage());
                        listener.codeDeleteDriver(response.code());
                    }
                }else {
                    Log.e("ERROR", ""+ response.code());
                    //listener.onFailedSuccessPetition("Error en llamar la petición delete driver");
                    listener.codeDeleteDriver(response.code());
                }

            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {
                    Log.e("ERROR", ""+ "delete driver"+t);
            }
        });
    }catch (Exception e){
        Log.e("ERROR", ""+e);
    }
    }

    @Override
    public void deleteDriverPetition(final int id_driver,final OnHomeFinishedListener listener) {
    try {

        APIService service =  APIClient.getClient().create(APIService.class);
        Call<ModelStatus> userCall =  service.deletePetition(id_driver);
        userCall.enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {

                if (response.isSuccessful()){

                    if(response.body().getStatus().equals("1")){
                        listener.onSuccessDeletePetition(response.body().getStatus());
                    }else {
                        listener.onSuccessDeletePetition(response.body().getMessage());
                    }
                }else {
                    Log.e("ERROR", ""+ response.code());
                    listener.codeDeletePetition(response.code());
                    listener.onFailedSuccessPetition("Error en llamar la petición delete driver");
                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {
                Log.e("ERROR", ""+ "delete driver petition"+t);
            }
        });
    }catch (Exception e){
        Log.e("ERROR", ""+e);
    }
    }

    @Override
    public void insertDriverHistoryTravel(int id_driver, String origin, String destination, String date,final OnHomeFinishedListener listener) {
        try{

            APIService service = APIClient.getClient().create(APIService.class);
            Call<ModelStatus> userCall = service.insertHistoryTravel(id_driver,origin,destination,date);
            userCall.enqueue(new Callback<ModelStatus>() {

                @Override
                public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {

                    if (response.isSuccessful()) {

                        if(response.body().getStatus().equals("1")){
                            //listener.onSuccessStatic(response.body().getMessage());
                            Log.e("1",response.body().getMessage());
                            listener.codeInsertHistoryTravel(response.code());

                        }else if(response.body().getStatus().equals("2")){
                            //listener.onSuccessStatic(response.body().getMessage());
                            Log.e("2",response.body().getMessage());
                            listener.codeInsertHistoryTravel(response.code());

                        }
                    }else{
                        //listener.onFailedSuccessPetition("");
                        Log.e("1","Error en llamar la petición insert history travel");
                        listener.codeInsertHistoryTravel(response.code());
                    }
                }

                @Override
                public void onFailure(Call<ModelStatus> call, Throwable t) {
                    Log.e("ERROR", ""+ "insert driver"+t);
                }
            });
        }catch (Exception e){
            Log.e("ERROR", ""+e);
        }

    }

}
