package com.taxiconductor.RetrofitAPI.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by carlos on 02/03/17.
 */

public class ModelCredentials {

    @SerializedName("ID_CHOFER")
    @Expose
    private int iDCHOFER;

    public int getIDCHOFER() {
        return iDCHOFER;
    }

    public void setIDCHOFER(int iDCHOFER) {
        this.iDCHOFER = iDCHOFER;
    }

}