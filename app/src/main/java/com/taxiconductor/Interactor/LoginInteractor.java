package com.taxiconductor.Interactor;

import java.io.UnsupportedEncodingException;

/**
 * Created by carlos on 02/03/17.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess(String id_driver);

        void onMessageService(String message);

        void onSuccessFinally(String status);
    }

    void login(String username, String password, OnLoginFinishedListener listener) throws UnsupportedEncodingException;

    void sessionValidate(String id_driver,OnLoginFinishedListener listener);

}