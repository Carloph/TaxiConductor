package com.taxiconductor.Interator.InteratorTravels;

import com.taxiconductor.RetrofitAPI.Model.ModelTravels;

import java.util.ArrayList;

/**
 * Created by carlos on 31/03/17.
 */

public interface TravelsInterator {

    interface  OnTravelsListener{

        void errorValidatorGetTravels(int statusCode);
        void messageGetTravels(String message);
        void listTravels(ArrayList<ModelTravels>arrayList);

    }

    void getTravels(int id_driver, String date,OnTravelsListener onTravelsListener);
}
