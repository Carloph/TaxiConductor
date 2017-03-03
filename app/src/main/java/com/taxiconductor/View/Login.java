package com.taxiconductor.View;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.taxiconductor.Presenter.LoginPresenterImp;
import com.taxiconductor.R;


public class Login extends AppCompatActivity implements LoginView{

    public EditText edt_user;
    public EditText edt_password;
    public Button btn_login;
    public ProgressBar progressBar;

    private LoginPresenterImp presenter;

    private static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;

    public static String id_driver_global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermissionLocation();
        progressBar = (ProgressBar) findViewById(R.id.progress);
        edt_user  = (EditText)findViewById(R.id.editText_usuario);
        edt_password = (EditText)findViewById(R.id.editText_password);
        btn_login = (Button)findViewById(R.id.button_login);

        presenter = new LoginPresenterImp(this);




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    presenter.validateCredentials(edt_user.getText().toString(), edt_password.getText().toString());

            }
        });

    }


    /////////////////// METODOS DE VENIDA
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        edt_user.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        edt_password.setError(getString(R.string.password_error));
    }

    @Override
    public void setMessageService(String message) {
        Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show();
    }

    //// METODOS DE VENIDA VALIDADOR ID_CHOFER

    @Override
    public void validator(String id_driver) {
        id_driver_global = id_driver;
        presenter.validateSesion(id_driver);
    }

    @Override
    public void navigateToHome(String status) {

        if(status.equals("2")){
            Intent intent_home = new Intent(this,Home.class);
            intent_home.putExtra("ID_CHOFER",id_driver_global);
            startActivity(intent_home);
            finish();
        }
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public void checkPermissionLocation(){
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(Login.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(Login.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(Login.this, permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
            }
        }
    }

}
