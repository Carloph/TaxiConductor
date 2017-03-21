package com.taxiconductor.Interator.InteratorLogin;


import com.taxiconductor.Interator.InteratorHome.HomeInterator;

public interface LoginInterator {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess(int id_driver);

        void onMessageService(String message);

        void onSuccessFinally(String status);

        void codeValidatorDeleteDriver(int statusCode);
    }

    void login(String username, String password, OnLoginFinishedListener listener);

    void sessionValidate(int id_driver,OnLoginFinishedListener listener);

    void insertDriver(int id_driver, double latitude, double longitude, int status, final OnLoginFinishedListener listener);


}