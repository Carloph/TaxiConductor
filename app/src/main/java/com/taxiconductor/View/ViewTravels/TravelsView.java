package com.taxiconductor.View.ViewTravels;

import com.taxiconductor.RetrofitAPI.Model.ModelTravels;

import java.util.ArrayList;

/**
 * Created by carlos on 31/03/17.
 */

public interface TravelsView {

    void showProgress();

    void hideProgress();

    void errorGetTravels(int statusCode);

    void getTravels(ArrayList<ModelTravels> arrayList);

    void notExistTravels(String message);

}
