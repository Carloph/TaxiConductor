package com.taxiconductor.RetrofitAPI.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by carlos on 02/03/17.
 */

public class ModelStatus {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sesion")
    @Expose
    private ModelIDDriver sesion;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelIDDriver getSesion() {
        return sesion;
    }

    public void setSesion(ModelIDDriver sesion) {
        this.sesion = sesion;
    }

}