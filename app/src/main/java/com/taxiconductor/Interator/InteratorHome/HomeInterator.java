package com.taxiconductor.Interator.InteratorHome;

import com.taxiconductor.RetrofitAPI.Model.ModelStatus;

public interface HomeInterator {

    interface OnHomeFinishedListener{

        void onSuccessStatic(String message);
        void onSuccessDynamic(String message);
        void onSuccessPetition(ModelStatus petition);
        void onSuccessDeletePetition(String message);
        void onFailedSuccessPetition(String message);
        void codeUpdateStatus(int statusCode, int statusDriver);
        void codeDeletePetition(int statusCode);
        void codeDeleteDriver(int statusCode);
        void codeInsertHistoryTravel(int statusCode);
    }

    void insertDriver(int id_driver, double latitude, double longitude, int status, final OnHomeFinishedListener listener);

    void updateCoordinates(int id_driver,double latitude, double longitude, final OnHomeFinishedListener  listener);

    void listenPetition(int id_drive,final OnHomeFinishedListener  listener);

    void updateStatus(int id_driver, int status, final OnHomeFinishedListener  listener);

    void deleteDriver(int id_driver,final OnHomeFinishedListener  listener);

    void deleteDriverPetition(int id_driver, final OnHomeFinishedListener listener);

    void insertDriverHistoryTravel(int id_driver, String origin, String destination, String date, OnHomeFinishedListener listener);

}
