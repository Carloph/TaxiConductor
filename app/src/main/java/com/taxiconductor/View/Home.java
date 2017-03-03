package com.taxiconductor.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.taxiconductor.R;

public class Home extends AppCompatActivity {

    public TextView tv_iddriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras =  getIntent().getExtras();
        tv_iddriver = (TextView) findViewById(R.id.textView_id_driver);
        tv_iddriver.setText(extras.getString("ID_CHOFER"));
    }
}
