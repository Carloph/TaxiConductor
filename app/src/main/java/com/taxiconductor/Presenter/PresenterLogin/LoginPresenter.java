package com.taxiconductor.Presenter.PresenterLogin;

import java.io.UnsupportedEncodingException;


public interface LoginPresenter {

    void validateCredentials(String username, String password) throws UnsupportedEncodingException;

    void validateSesion(int id_drive);

    void validateInsertDriver(int id_driver, double latitude, double longitude, int status);

    void onDestroy();
}