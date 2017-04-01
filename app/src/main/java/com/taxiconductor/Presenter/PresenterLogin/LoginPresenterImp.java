package com.taxiconductor.Presenter.PresenterLogin;

import com.taxiconductor.Interator.InteratorLogin.LoginInterator;
import com.taxiconductor.Interator.InteratorLogin.LoginInteratorImp;
import com.taxiconductor.View.ViewLogin.LoginView;



public class LoginPresenterImp implements LoginPresenter, LoginInterator.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteratorImp loginInteractor;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteratorImp();
    }

    ////////////////////METODOS DE IDA

    @Override public void validateCredentials(String username, String password){
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(username, password, this);
    }


    @Override
    public void validateSesion(int id_drive) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.sessionValidate(id_drive,this);
    }

    @Override
    public void validateInsertDriver(int id_driver, double latitude, double longitude, int status) {
        if(loginView != null){
            loginView.showProgress();
        }
        loginInteractor.insertDriver(id_driver, latitude, longitude, status, this);
    }

    /////////////////////03-10 15:31:04.096 31808-31821/? E/art: Failed sending reply to debugger: Broken pipe


    @Override public void onDestroy() {
        loginView = null;
    }

    /////////////////// METODOS DE VUELTA LOGIN

    @Override public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }
    @Override public void onSuccess(int id_driver) {
        if (loginView != null) {
           // loginView.navigateToHome(id_driver);
            loginView.validator(id_driver);
            loginView.hideProgress();
        }
    }

    @Override
    public void onMessageService(String message) {
        loginView.setMessageService(message);
        loginView.hideProgress();
    }

    @Override
    public void onSuccessFinally(String status) {
        loginView.navigateToHome(status);
        loginView.hideProgress();

    }

    @Override
    public void codeValidatorDeleteDriver(int statusCode) {
        loginView.validatorInsertDriver(statusCode);
        loginView.hideProgress();

    }
}
