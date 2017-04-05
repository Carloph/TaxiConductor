package com.taxiconductor.View.ViewLogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taxiconductor.Presenter.PresenterLogin.LoginPresenterImp;
import com.taxiconductor.R;
import com.taxiconductor.View.ViewHome.HomeActivity;


public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    public EditText edt_user;
    public EditText edt_password;
    public Button btn_login;
    public ProgressDialog progressDialog;
    private LoginPresenterImp presenter;

    private SharedPreferences preferences;


    private static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    public static int id_driver_global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermissionLocation();

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);
        edt_user = (EditText) findViewById(R.id.editText_usuario);
        edt_password = (EditText) findViewById(R.id.editText_password);
        btn_login = (Button) findViewById(R.id.button_login);

        presenter = new LoginPresenterImp(this);

        btn_login.setOnClickListener(this);

    }

    /////////////////// METODOS DE VENIDA
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
    public void setUsernameError() {
        edt_user.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        edt_password.setError(getString(R.string.password_error));
    }

    @Override
    public void setMessageService(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
    }

    //// METODOS DE VENIDA VALIDADOR ID_CHOFER

    @Override
    public void validator(int id_driver) {
        id_driver_global = id_driver;
        presenter.validateSesion(id_driver);
    }

    @Override
    public void navigateToHome(String status) {
        if (status.equals("2")) {
            presenter.validateInsertDriver(id_driver_global, 0, 0, 0);
        }
    }

    @Override
    public void validatorInsertDriver(int statusCode) {
        if (statusCode==200){
            String user = edt_user.getText().toString();
            saveOnPreferences(user, id_driver_global);
            Intent intent_home = new Intent(this, HomeActivity.class);
            intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent_home);
            Toast.makeText(getApplication(), "Bienvenido", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplication(),"Verifique su conexión a internet, intente ingresar de nuevo",Toast.LENGTH_LONG).show();
        }
    }

    public void checkPermissionLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(LoginActivity.this, permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                presenter.validateCredentials(edt_user.getText().toString(), edt_password.getText().toString());
                break;
        }
    }

    private void saveOnPreferences(String user, int id_driver) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", user);
        editor.putInt("id_driver", id_driver);
        editor.apply();
    }
}
