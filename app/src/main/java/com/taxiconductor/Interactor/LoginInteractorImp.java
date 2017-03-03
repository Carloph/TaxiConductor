package com.taxiconductor.Interactor;

import android.text.TextUtils;
import android.util.Log;
import com.taxiconductor.RetrofitAPI.APIService;
import com.taxiconductor.RetrofitAPI.APIClient;
import com.taxiconductor.RetrofitAPI.Login.ModelStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by carlos on 02/03/17.
 */
public class LoginInteractorImp implements LoginInteractor {

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener){
        // Mock login. I'm creating a handler to delay the answer a couple of seconds

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

                            Log.e("onResponse", "" + response.body().getMessage());

                            if(response.body().getStatus().equals("1")) {

                                String id_user = response.body().getSesion().getIDCHOFER();

                                listener.onSuccess(id_user);

                                //listener.onMessageService(response.body().getMessage());

                            }else if(response.body().getStatus().equals("2")) {

                                listener.onMessageService(response.body().getMessage());

                            }else if(response.body().getStatus().equals("3")){

                                listener.onMessageService(response.body().getMessage());

                            }else {

                            }
                        }

                        @Override
                        public void onFailure(Call<ModelStatus> call, Throwable t) {
                            Log.e("onFailure", t.toString());
                        }
                    });
                }
    }

    @Override
    public void sessionValidate(String id_driver, final OnLoginFinishedListener listener) {

        APIService service = APIClient.getClient().create(APIService.class);
        Call<ModelStatus> userCall = service.verificationSession(id_driver);

        userCall.enqueue(new Callback<ModelStatus>() {
            @Override
            public void onResponse(Call<ModelStatus> call, Response<ModelStatus> response) {

                Log.e("onResponse", "" + response.body().getMessage());

                if(response.body().getStatus().equals("1")) {

                    listener.onSuccessFinally(response.body().getStatus());
                    listener.onMessageService(response.body().getMessage());

                }else if(response.body().getStatus().equals("2")) {

                  //  String id_user = response.body().getSesion().getIDCHOFER();
                    listener.onSuccessFinally(response.body().getStatus());
                    listener.onMessageService(response.body().getMessage());

                }else if(response.body().getStatus().equals("3")){

                    listener.onMessageService(response.body().getMessage());

                }else {

                }
            }

            @Override
            public void onFailure(Call<ModelStatus> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }


}