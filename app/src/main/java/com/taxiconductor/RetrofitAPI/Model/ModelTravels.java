package com.taxiconductor.RetrofitAPI.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by carlos on 31/03/17.
 */

public class ModelTravels {

    @SerializedName("ORIGEN")
    @Expose
    private String origin;

    @SerializedName("DESTINO")
    @Expose
    private String destination;

    @SerializedName("FECHA")
    @Expose
    private String date;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
