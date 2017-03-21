package com.taxiconductor.Presenter.PresenterHome;

import com.taxiconductor.Interator.InteratorHome.HomeInterator;
import com.taxiconductor.Interator.InteratorHome.HomeInteratorImp;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import com.taxiconductor.View.ViewHome.HomeView;


public class HomePresenterImp implements HomePresenter, HomeInterator.OnHomeFinishedListener {

    private HomeView homeView;
    private HomeInteratorImp homeInteractor;

    public HomePresenterImp(HomeView homeView) {
        this.homeView = homeView;
        this.homeInteractor = new HomeInteratorImp();
    }

    @Override
    public void validateUpdateCoordinates(int id_driver, double latitude, double longitude) {
        if(homeView != null){
        }
        homeInteractor.updateCoordinates(id_driver,latitude, longitude, this);
    }

    @Override
    public void validateListenerPetition(int id_drive) {
        if(homeView != null){
        }
        homeInteractor.listenPetition(id_drive,this);
    }

    @Override
    public void validateUpdateStatus(int id_driver, int status) {
        if(homeView != null){
        }
        homeInteractor.updateStatus(id_driver,status,this);
    }

    @Override
    public void validateDeleteDriver(int id_driver) {
        if(homeView != null){
        }
        homeInteractor.deleteDriver(id_driver,this);
    }

    @Override
    public void validateDeleteDriverPetition(int id_driver) {
        if(homeView != null){
        }
        homeInteractor.deleteDriverPetition(id_driver,this);
    }

    ////////////////////////////////////RETURN//////////////////////////////////////////////////////

    @Override
    public void onSuccessStatic(String message) {
        homeView.messageServiceStatic(message);
    }

    @Override
    public void onSuccessDynamic(String message) {
        homeView.messageServiceConstant(message);
    }

    @Override
    public void onSuccessPetition(ModelStatus petition) {
        if(petition!=null){
            homeView.ServicePetition(petition);
            homeView.executeSoundVibrator(true);
        }
    }

    @Override
    public void onSuccessDeletePetition(String message) {
        if(message!=null){
            homeView.messageDeletePetition(message);
        }
    }

    @Override
    public void onFailedSuccessPetition(String message) {
        homeView.messageFailedPetition(message);
    }

    @Override
    public void codeUpdateStatus(int statusCode, int statusDriver) {
        homeView.codeUpdateStatus(statusCode, statusDriver);
    }

    @Override
    public void codeDeletePetition(int statusCode) {
        homeView.codeDeletePetition(statusCode);
    }
}
