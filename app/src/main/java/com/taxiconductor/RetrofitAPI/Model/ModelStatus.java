package com.taxiconductor.RetrofitAPI.Model;

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
    private ModelCredentials sesion;

    @SerializedName("petition")
    private ModelPetition petition;

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

    public ModelCredentials getSesion() {
        return sesion;
    }

    public void setSesion(ModelCredentials sesion) {
        this.sesion = sesion;
    }

    public ModelPetition getPetition() {
        return petition;
    }

    public void setPetition(ModelPetition petition) {
        this.petition = petition;
    }
}