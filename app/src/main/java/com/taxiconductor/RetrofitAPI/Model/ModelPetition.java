package com.taxiconductor.RetrofitAPI.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by carlos on 08/03/17.
 */

public class ModelPetition {

    @SerializedName("LATITUD_CLIENTE")
    @Expose
    private double latitude_client;

    @SerializedName("LONGITUD_CLIENTE")
    @Expose
    private double longitude_client;

    @SerializedName("LATITUD_DESTINO")
    @Expose
    private double latitude_destin;

    @SerializedName("LONGITUD_DESTINO")
    @Expose
    private double longitude_destin;

    @SerializedName("MENSAJE")
    @Expose
    private String message;

    public double getLatitude_client() {
        return latitude_client;
    }

    public void setLatitude_client(double latitude_client) {
        this.latitude_client = latitude_client;
    }

    public double getLongitude_client() {
        return longitude_client;
    }

    public void setLongitude_client(int longitude_client) {
        this.longitude_client = longitude_client;
    }

    public double getLatitude_destin() {
        return latitude_destin;
    }

    public void setLatitude_destin(int latitude_destin) {
        this.latitude_destin = latitude_destin;
    }

    public double getLongitude_destin() {
        return longitude_destin;
    }

    public void setLongitude_destin(int longitude_destin) {
        this.longitude_destin = longitude_destin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
