package com.taxiconductor.View.ViewTravels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taxiconductor.Presenter.PresenterTravels.TravelsPresenterImp;
import com.taxiconductor.R;
import com.taxiconductor.RetrofitAPI.Model.ModelTravels;
import com.taxiconductor.Utils.Util;
import com.taxiconductor.View.ViewTravels.Adapters.AdapterTravels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TravelsActivity extends AppCompatActivity implements TravelsView {

    private TextView tv_date;
    public ProgressDialog progressDialog;
    public TravelsPresenterImp presenter;
    public ListView lv_travels;
    private AdapterTravels adapterTravels;
    private static int id_driver;
    private SharedPreferences preferences;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);
        preferences =  getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        presenter = new TravelsPresenterImp(this);
        tv_date = (TextView) findViewById(R.id.textViewDate);
        lv_travels = (ListView) findViewById(R.id.listViewTravels);
        tv_date.setText(getCurrentDate());

        if(savedInstanceState == null){
            id_driver = Util.getIdDriverPrefs(preferences);

        }
        presenter.validateTravels(id_driver,getCurrentDate());

    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        Log.e("DATE",strDate);
        return strDate;
    }

    @Override
    public void showProgress() {
        this.progressDialog.setTitle("Por favor espere...");
        this.progressDialog.show();
    }

    @Override
    public void hideProgress() {
        this.progressDialog.dismiss();
    }

    @Override
    public void errorGetTravels(int statusCode) {
        if(statusCode >= 500){
            presenter.validateTravels(id_driver,getCurrentDate());
        }
    }

    @Override
    public void getTravels(ArrayList<ModelTravels> arrayList) {
        adapterTravels = new AdapterTravels(TravelsActivity.this,arrayList);
        lv_travels.setAdapter(adapterTravels);
    }

    @Override
    public void notExistTravels(String message) {
        Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show();
    }
}