package com.taxiconductor.RetrofitAPI.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by carlos on 02/03/17.
 */

public class ModelIDDriver {

    @SerializedName("ID_CHOFER")
    @Expose
    private String iDCHOFER;

    public String getIDCHOFER() {
        return iDCHOFER;
    }

    public void setIDCHOFER(String iDCHOFER) {
        this.iDCHOFER = iDCHOFER;
    }

}