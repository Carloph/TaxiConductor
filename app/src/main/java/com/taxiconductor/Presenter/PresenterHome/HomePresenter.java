package com.taxiconductor.Presenter.PresenterHome;


public interface HomePresenter {

    void validateUpdateCoordinates(int id_driver, double latitude, double longitude);
    void validateListenerPetition(int id_drive);
    void validateUpdateStatus(int id_driver, int status);
    void validateDeleteDriver(int id_driver);
    void validateDeleteDriverPetition(int id_driver);
}
