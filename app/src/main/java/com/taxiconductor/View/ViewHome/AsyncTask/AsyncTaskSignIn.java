package com.taxiconductor.View.ViewHome.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.taxiconductor.RetrofitAPI.APIClient;
import com.taxiconductor.RetrofitAPI.APIService;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import com.taxiconductor.View.ViewHome.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by carlos on 31/03/17.
 */

    public class AsyncTaskSignIn extends AsyncTask<String, Void, Integer> {
    private AsyncResponseValidator delegate = null;
    private HomeActivity activity;
    public Integer res;
    public int id_driver;
    private ProgressDialog p;


    public interface AsyncResponseValidator {
        void processFinish(Integer output);
    }

    public AsyncTaskSignIn(HomeActivity activity,AsyncResponseValidator delegate, int id_driver) {
        this.activity = activity;
        this.delegate = delegate;
        this.id_driver = id_driver;
        this.p=new ProgressDialog(activity);


    }
    @Override
    protected void onPreExecute() {
        p.setMessage("Cerrando sesión");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected void onPostExecute(Integer result) {
            p.dismiss();
            delegate.processFinish(result);
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {

            APIService service =  APIClient.getClient().create(APIService.class);
            Call<ModelStatus> userCall = service.deleteDriverSession(id_driver);
            userCall.enqueue(new Callback<ModelStatus>() {
                @Override
                public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {
                    if (response.isSuccessful()){
                        if(response.body().getStatus().equals("1")){
                            res = response.code();
                        }else if(response.body().getStatus().equals("2")){
                            res = response.code();
                        }
                    }else {
                        Log.e("ERROR", ""+ response.code());
                        String.valueOf(response.code());
                    }
                }
                @Override
                public void onFailure(Call<ModelStatus> call, Throwable t) {
                    Log.e("ERROR", ""+ "delete driver"+t);
                }
            });


            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
        return res;
    }


}