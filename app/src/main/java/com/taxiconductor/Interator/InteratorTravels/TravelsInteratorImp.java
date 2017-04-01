package com.taxiconductor.Interator.InteratorTravels;

import android.util.Log;

import com.taxiconductor.RetrofitAPI.APIClient;
import com.taxiconductor.RetrofitAPI.APIService;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import com.taxiconductor.RetrofitAPI.Model.ModelTravels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by carlos on 31/03/17.
 */

public class TravelsInteratorImp implements  TravelsInterator{
    @Override
    public void getTravels(int id_driver, String date, final OnTravelsListener onTravelsListener) {

        try {
            APIService service = APIClient.getClient().create(APIService.class);
            Call<ModelStatus> userCall = service.getHistoryTravel(id_driver,date);
            userCall.enqueue(new Callback<ModelStatus>() {
                @Override
                public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                    if (response.isSuccessful()) {

                        if(response.body().getStatus().equals("1")){
                            ArrayList<ModelTravels> arrayList = new ArrayList<>();
                            arrayList.add(response.body().getTravel());
                            onTravelsListener.listTravels(arrayList);
                        }else if(response.body().getStatus().equals("2")){
                            onTravelsListener.messageGetTravels(response.body().getMessage());
                        }else{

                        }
                    }
                    else {
                        onTravelsListener.errorValidatorGetTravels(response.code());
                        Log.e("ERROR", ""+ response.code());
                    }
                }

                @Override
                public void onFailure(Call<ModelStatus> call, Throwable t) {
                    Log.e("ERROR", ""+ "escuchar peticion"+t);
                }
            });
        }catch (Exception e){
            Log.e("ERROR", ""+e);
        }

    }
}
