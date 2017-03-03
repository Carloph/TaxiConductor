package com.taxiconductor.Presenter;

import java.io.UnsupportedEncodingException;

/**
 * Created by carlos on 02/03/17.
 */

public interface LoginPresenter {
    void validateCredentials(String username, String password) throws UnsupportedEncodingException;

    void validateSesion(String id_drive);


    void onDestroy();
}