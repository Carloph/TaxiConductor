package com.taxiconductor.Presenter.PresenterTravels;

import com.taxiconductor.Interator.InteratorTravels.TravelsInterator;
import com.taxiconductor.Interator.InteratorTravels.TravelsInteratorImp;
import com.taxiconductor.RetrofitAPI.Model.ModelTravels;
import com.taxiconductor.View.ViewTravels.TravelsView;

import java.util.ArrayList;

/**
 * Created by carlos on 31/03/17.
 */

public class TravelsPresenterImp implements TravelsPresenter, TravelsInterator.OnTravelsListener {

    private TravelsView travelsView;
    private TravelsInteratorImp travelsInteratorImp;

    public TravelsPresenterImp(TravelsView travelsView){
        this.travelsView =  travelsView;
        this.travelsInteratorImp = new TravelsInteratorImp();
    }

    @Override
    public void validateTravels(int id_drive, String date) {

        if(travelsView!=null){
            travelsView.showProgress();
        }
        travelsInteratorImp.getTravels(id_drive,date,this);
    }

    @Override
    public void errorValidatorGetTravels(int statusCode) {
        travelsView.errorGetTravels(statusCode);
        travelsView.hideProgress();
    }

    @Override
    public void messageGetTravels(String message) {
        travelsView.notExistTravels(message);
        travelsView.hideProgress();

    }

    @Override
    public void listTravels(ArrayList<ModelTravels> arrayList) {
        travelsView.getTravels(arrayList);
        travelsView.hideProgress();
    }

}
