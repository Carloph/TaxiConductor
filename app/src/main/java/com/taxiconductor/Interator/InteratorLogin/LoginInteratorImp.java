package com.taxiconductor.Interator.InteratorLogin;

import android.text.TextUtils;
import android.util.Log;
import com.taxiconductor.RetrofitAPI.APIService;
import com.taxiconductor.RetrofitAPI.APIClient;
import com.taxiconductor.RetrofitAPI.Model.ModelStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteratorImp implements LoginInterator {

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener){
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        try{

                boolean error = false;
                if (TextUtils.isEmpty(username)){
                    listener.onUsernameError();
                    error = true;
                    return;
                }
                else if (TextUtils.isEmpty(password)){
                    listener.onPasswordError();
                    error = true;
                    return;
                }
                else{


                    APIService service = APIClient.getClient().create(APIService.class);
                    Call<ModelStatus> userCall = service.verificationLogin(username,password);

                    userCall.enqueue(new Callback<ModelStatus>() {
                        @Override
                        public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {

                            if (response.isSuccessful()){

                                if(response.body().getStatus().equals("1")) {

                                    int id_user = response.body().getSesion().getIDCHOFER();

                                    listener.onSuccess(id_user);


                                }else if(response.body().getStatus().equals("2")) {

                                    listener.onMessageService(response.body().getMessage());

                                }else if(response.body().getStatus().equals("3")){

                                    listener.onMessageService(response.body().getMessage());

                                }else {

                                }
                            }else {
                                listener.onMessageService("Verifique su conexión a internet");
                                Log.e("ERROR", "error en loggin 1");
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelStatus> call, Throwable t) {
                            listener.onMessageService(t.getMessage());
                        }
                    });
                }
        }catch (Exception e){
            Log.e("ERROR", ""+e);
        }
    }

    @Override
    public void sessionValidate(int id_driver, final OnLoginFinishedListener listener) {

        try{

            APIService service = APIClient.getClient().create(APIService.class);
            Call<ModelStatus> userCall = service.verificationSession(id_driver);

            userCall.enqueue(new Callback<ModelStatus>() {
                @Override
                public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {

                    if (response.isSuccessful()){

                        if(response.body().getStatus().equals("1")) {

                            listener.onSuccessFinally(response.body().getStatus());
                            listener.onMessageService(response.body().getMessage());

                        }else if(response.body().getStatus().equals("2")) {

                            listener.onSuccessFinally(response.body().getStatus());
                            //listener.onMessageService(response.body().getMessage());

                        }else if(response.body().getStatus().equals("3")){

                            listener.onMessageService(response.body().getMessage());

                        }else {

                        }
                    }else {
                        listener.onMessageService("Verifique su conexión a internet");
                        Log.e("ERROR", "error en validador session 2");

                    }
                }

                @Override
                public void onFailure(Call<ModelStatus> call, Throwable t) {
                    listener.onMessageService(t.getMessage());
                }
            });
        }catch (Exception e){
            Log.e("ERROR", ""+ e);
        }
    }

    @Override
    public void insertDriver(final int id_driver, final double latitude, final double longitude, final int status, final OnLoginFinishedListener listener) {
        try{

            APIService service = APIClient.getClient().create(APIService.class);
            Call<ModelStatus> userCall = service.insertLocation(id_driver,latitude,longitude,status);
            userCall.enqueue(new Callback<ModelStatus>() {

                @Override
                public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {

                    if (response.isSuccessful()) {

                        if(response.body().getStatus().equals("1")){
                            listener.codeValidatorDeleteDriver(response.code());
                            Log.e("response1", response.body().getMessage());
                        }else if(response.body().getStatus().equals("2")){
                            Log.e("response1", response.body().getMessage());
                        }
                    }else{
                        listener.codeValidatorDeleteDriver(response.code());
                        Log.e("response1", "Error en insertar driver");
                    }
                }

                @Override
                public void onFailure(Call<ModelStatus> call, Throwable t) {
                    Log.e("ERROR", ""+ "insert driver"+t);
                }
            });
        }catch (Exception e){
            Log.e("ERROR", ""+e);
        }

    }
}