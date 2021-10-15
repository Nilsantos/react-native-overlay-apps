package com.reactnativeoverlayapps;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;


@ReactModule(name = OverlayAppsModule.NAME)
public class OverlayAppsModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;
    public static final String NAME = "OverlayApps";
    private ReactApplicationContext reactContext;
    private ServiceConnection mServiceConnection;
    Promise askPermissionPromise;
    OverlayAppsService mService;
    Intent serviceIntent;

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {
        if (resultCode == activity.RESULT_OK) {
          this.askPermissionPromise.resolve(true);
        }
        else {
          this.askPermissionPromise.resolve(false);
        }
      }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    public OverlayAppsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void askPermission(Promise promise) {
      this.askPermissionPromise = promise;

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this.getReactApplicationContext())) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.reactContext.getPackageName()));
        reactContext.startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE, null);
      }
      else {
        promise.resolve(true);
      }
    }

    @ReactMethod
    public void showOverlay() {
      createFloatingWidget();
    }

    @ReactMethod
    public void hideOverlay() {
      this.reactContext.unbindService(mServiceConnection);
      this.reactContext.stopService(serviceIntent);
      serviceIntent = null;
    }

    @ReactMethod
    public void setText(String text) {
      if(mService != null) {
        Log.i("text", text);
        mService.setText(text);
      }
    }

    public void createFloatingWidget() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this.getReactApplicationContext())) {
        startFloatingWidgetService();
      }
    }

    private void startFloatingWidgetService() {
      if(serviceIntent == null) {
        mServiceConnection = new ServiceConnection() {
          @Override
          public void onServiceConnected(ComponentName name, IBinder service) {
            OverlayAppsService.LocalBinder mLocalBinder = (OverlayAppsService.LocalBinder) service;
            mService = mLocalBinder.getInstance();
          }

          @Override
          public void onServiceDisconnected(ComponentName name) {

          }
        };

        serviceIntent = new Intent(this.reactContext, OverlayAppsService.class);
        this.reactContext.bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        this.reactContext.startService(serviceIntent);
      }
    }
  }

