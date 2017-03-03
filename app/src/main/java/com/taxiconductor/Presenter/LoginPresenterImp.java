package com.taxiconductor.Presenter;

import com.taxiconductor.Interactor.LoginInteractor;
import com.taxiconductor.Interactor.LoginInteractorImp;
import com.taxiconductor.View.LoginView;


/**
 * Created by carlos on 02/03/17.
 */

public class LoginPresenterImp implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractorImp loginInteractor;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImp();
    }

    ////////////////////METODO DE IDA LOGIN

    @Override public void validateCredentials(String username, String password){
        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(username, password, this);
    }

    ///////////////////METODO DE IDA VALIDADOR ID_CHOFER

    @Override
    public void validateSesion(String id_drive) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.sessionValidate(id_drive,this);
    }

    /////////////////////


    @Override public void onDestroy() {
        loginView = null;
    }

    /////////////////// METODOS DE VENIDA LOGIN

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
    @Override public void onSuccess(String id_driver) {
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

    /////////////////////METODOS DE VENIDA VALIDADOR ID_CHOFER

    @Override
    public void onSuccessFinally(String status) {
        loginView.navigateToHome(status);
    }


}