package org.alextan.android.exits.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.alextan.android.exits.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = getApplicationContext();
        activity = this;
        Button check_permission = (Button)findViewById(R.id.btn_check);
        Button request_permission = (Button)findViewById(R.id.btn_request);
        Button start = (Button)findViewById(R.id.btn_start);
        check_permission.setOnClickListener(this);
        request_permission.setOnClickListener(this);
        start.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        view = v;

        int id = v.getId();
        switch (id){
            case R.id.btn_check:
                if (checkPermission()) {

                    Snackbar.make(view,"Permission already granted.",Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(view,"Please request permission.",Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_request:
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    Snackbar.make(view,"Permission already granted.",Snackbar.LENGTH_LONG).show();

                }
                break;
            case R.id.btn_start:
                Log.d("TAG", "lcicked");
                startActivity(new Intent(getApplicationContext(), FormActivity.class));
                break;
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION)){

            Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Snackbar.make(view,"Permission Granted, Now you can access location data.",Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }
}
