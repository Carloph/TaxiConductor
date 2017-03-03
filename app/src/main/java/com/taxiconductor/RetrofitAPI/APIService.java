package com.taxiconductor.RetrofitAPI;

import com.taxiconductor.RetrofitAPI.Login.ModelStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by carlos on 02/03/17.
 */

public interface APIService {

    @GET("login.php")
    Call<ModelStatus> verificationLogin(@Query("USUARIO") String user,
                                        @Query("CONTRASENIA") String password);

    @GET("validation_session.php")
    Call<ModelStatus> verificationSession(@Query("ID_CHOFER") String id_driver);

}
