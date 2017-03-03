package com.taxiconductor.View;

/**
 * Created by carlos on 02/03/17.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome(String id_driver);

    void setMessageService(String message);

    void validator(String status);

}
