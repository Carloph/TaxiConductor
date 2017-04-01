package com.taxiconductor.RetrofitAPI;

import com.taxiconductor.RetrofitAPI.Model.ModelStatus;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by carlos on 02/03/17.
 */

public interface APIService {

    @GET("login.php")
    Call<ModelStatus> verificationLogin(@Query("USUARIO") String user,
                                        @Query("CONTRASENIA") String password);

    @GET("validation_session.php")
    Call<ModelStatus> verificationSession(@Query("ID_CHOFER") int id_driver);

    @FormUrlEncoded
    @POST("insert_driver_location.php")
    Call<ModelStatus> insertLocation(@Field("ID_CHOFER") int iddriver,
                             @Field("LATITUD") double latitude,
                             @Field("LONGITUD") double longitude,
                             @Field("ESTATUS") int status);

    @FormUrlEncoded
    @POST("update_driver_status.php")
    Call<ModelStatus> updateStatus(@Field("ID_CHOFER") int iddriver,
                           @Field("ESTATUS") int status);

    @FormUrlEncoded
    @POST("delete_driver_session.php")
    Call<ModelStatus> deleteDriverSession(@Field("ID_CHOFER") int iddriver);


    @FormUrlEncoded
    @POST("update_driver_location.php")
    Call<ModelStatus> updateCoordinates(@Field("ID_CHOFER") int iddriver,
                                @Field("LATITUD") double latitude,
                                @Field("LONGITUD") double longitude);

    @GET("get_petitions.php")
    Call<ModelStatus> getPetitions(@Query("ID_CHOFER")int iddriver);

    @FormUrlEncoded
    @POST("delete_driver_petition.php")
    Call<ModelStatus> deletePetition(@Field("ID_CHOFER") int idchofer);

    @FormUrlEncoded
    @POST("insert_history_travel.php")
    Call<ModelStatus> insertHistoryTravel(@Field("ID_CHOFER") int idchofer,
                                          @Field("ORIGEN")String origin,
                                          @Field("DESTINO")String destination,
                                          @Field("FECHA")String Date);

    @GET("get_history_travel_id.php")
    Call<ModelStatus> getHistoryTravel(@Query("ID_CHOFER") int id_driver,
                                       @Query("FECHA") String date);
}
