package com.taxiconductor.Presenter.PresenterLogin;

import com.taxiconductor.Interator.InteratorLogin.LoginInterator;
import com.taxiconductor.Interator.InteratorLogin.LoginInteratorImp;
import com.taxiconductor.View.ViewLogin.LoginView;



public class LoginPresenterImp implements LoginPresenter, LoginInterator.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteratorImp loginInteractor;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteratorImp();
    }

    ////////////////////METODOS DE IDA

    @Override public void validateCredentials(String username, String password){
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(username, password, this);
    }


    @Override
    public void validateSesion(int id_drive) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.sessionValidate(id_drive,this);
    }

    @Override
    public void validateInsertDriver(int id_driver, double latitude, double longitude, int status) {
        if(loginView != null){
            loginView.showProgress();
        }
        loginInteractor.insertDriver(id_driver, latitude, longitude, status, this);
    }

    /////////////////////03-10 15:31:04.096 31808-31821/? E/art: Failed sending reply to debugger: Broken pipe


    @Override public void onDestroy() {
        loginView = null;
    }

    /////////////////// METODOS DE VUELTA LOGIN

    @Override public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }
    @Override public void onSuccess(int id_driver) {
        if (loginView != null) {
           // loginView.navigateToHome(id_driver);
            loginView.validator(id_driver);
            loginView.hideProgress();
        }
    }

    @Override
    public void onMessageService(String message) {
        loginView.setMessageService(message);
        loginView.hideProgress();
    }

    @Override
    public void onSuccessFinally(String status) {
        loginView.navigateToHome(status);
    }


}


/* FATAL EXCEPTION: main
                                                                 Process: com.taxiconductor, PID: 6131
                                                                 java.lang.RuntimeException: Unable to get provider com.google.firebase.provider.FirebaseInitProvider: java.lang.ClassNotFoundException: Didn't find class "com.google.firebase.provider.FirebaseInitProvider" on path: DexPathList[[zip file "/data/app/com.taxiconductor-2.apk"],nativeLibraryDirectories=[/data/app-lib/com.taxiconductor-2, /vendor/lib, /data/cust/lib, /system/lib]]
                                                                     at android.app.ActivityThread.installProvider(ActivityThread.java:5087)
                                                                     at android.app.ActivityThread.installContentProviders(ActivityThread.java:4673)
                                                                     at android.app.ActivityThread.handleBindApplication(ActivityThread.java:4613)
                                                                     at android.app.ActivityThread.access$1800(ActivityThread.java:141)
                                                                     at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1298)
                                                                     at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                     at android.os.Looper.loop(Looper.java:136)
                                                                     at android.app.ActivityThread.main(ActivityThread.java:5336)
                                                                     at java.lang.reflect.Method.invokeNative(Native Method)
                                                                     at java.lang.reflect.Method.invoke(Method.java:515)
                                                                     at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:871)
                                                                     at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:687)
                                                                     at dalvik.system.NativeStart.main(Native Method)
                                                                  Caused by: java.lang.ClassNotFoundException: Didn't find class "com.google.firebase.provider.FirebaseInitProvider" on path: DexPathList[[zip file "/data/app/com.taxiconductor-2.apk"],nativeLibraryDirectories=[/data/app-lib/com.taxiconductor-2, /vendor/lib, /data/cust/lib, /system/lib]]
                                                                     at dalvik.system.BaseDexClassLoader.findClass(BaseDexClassLoader.java:56)
                                                                     at java.lang.ClassLoader.loadClass(ClassLoader.java:497)
                                                                     at java.lang.ClassLoader.loadClass(ClassLoader.java:457)
                                                                     at android.app.ActivityThread.installProvider(ActivityThread.java:5072)
                                                                     at android.app.ActivityThread.installContentProviders(ActivityThread.java:4673) 
                                                                     at android.app.ActivityThread.handleBindApplication(ActivityThread.java:4613) 
                                                                     at android.app.ActivityThread.access$1800(ActivityThread.java:141) 
                                                                     at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1298) 
                                                                     at android.os.Handler.dispatchMessage(Handler.java:102) 
                                                                     at android.os.Looper.loop(Looper.java:136) 
                                                                     at android.app.ActivityThread.main(ActivityThread.java:5336) 
                                                                     at java.lang.reflect.Method.invokeNative(Native Method) 
                                                                     at java.lang.reflect.Method.invoke(Method.java:515) 
                                                                     at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:871) 
                                                                     at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:687) 
                                                                     at dalvik.system.NativeStart.main(Native Method) 
*/