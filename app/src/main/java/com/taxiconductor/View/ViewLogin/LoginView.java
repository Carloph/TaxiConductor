package com.taxiconductor.View.ViewLogin;

/**
 * Created by carlos on 02/03/17.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome(String status);

    void validatorInsertDriver(int statusCode);

    void setMessageService(String message);

    void validator(int id_driver);

}
