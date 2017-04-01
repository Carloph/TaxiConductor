package com.taxiconductor.View.ViewHome;

import com.taxiconductor.RetrofitAPI.Model.ModelStatus;

/**
 * Created by carlos on 05/03/17.
 */

public interface HomeView {

    void messageServiceStatic(String message);
    void messageServiceConstant(String message);
    void ServicePetition(ModelStatus petition);
    void executeSoundVibrator(boolean start);
    void messageDeletePetition(String message);
    void messageFailedPetition(String message);
    void codeUpdateStatus(int statusCode, int statusDriver);
    void codeDeletePetition(int statusCode);
    void codeDeleteDriver(int statusCode);
    void codeInsertHistoryTravel(int statusCode);
}


