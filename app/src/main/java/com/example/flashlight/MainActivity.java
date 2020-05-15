package com.example.flashlight;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton on,off;
    Boolean OnFlash=false;
    Boolean hasFlashLight=false;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        off = findViewById(R.id.Off);
        BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        assert batteryManager != null;
        hasFlashLight = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        off.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    if (hasFlashLight) {
                        if (OnFlash) {
                            turnOff();
                            Changebutton();
                        } else {
                            turnOn();
                            Changebutton();
                        }
                    }
                } else {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Permission")
                            .setMessage("Allow this to access Flash light").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS}, 2);


                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
                }
            }
        });


    }
    private void Changebutton() {
        if(OnFlash)
        {
            off.setBackgroundResource(R.drawable.on);
        }
        else {
            off.setBackgroundResource(R.drawable.off);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void turnOff() {

                CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                try {

                    assert cameraManager != null;
                    String cameraid = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraid, false);
                    OnFlash=false;
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }




    @RequiresApi(api = Build.VERSION_CODES.M)
    private void turnOn() {

                CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                try {

                    assert cameraManager != null;
                    String cameraid = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraid, true);
                    OnFlash=true;
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    break;
                } else {
                    finish();

                }



        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting:
                startActivity(new Intent(getApplicationContext(),Settings.class));
                break;
            case  R.id.follow:
                startActivity(new Intent(getApplicationContext(),Settings.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
