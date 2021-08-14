package com.reactnativeoverlayapps;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = OverlayAppsModule.NAME)
public class OverlayAppsModule extends ReactContextBaseJavaModule {
    public static final String NAME = "OverlayApps";

    static final String ACTION_FOREGROUND_SERVICE_START = "com.voximplant.foregroundservice.service_start";
    static final String ACTION_FOREGROUND_SERVICE_STOP = "com.voximplant.foregroundservice.service_stop";

    static final String NOTIFICATION_CONFIG = "com.voximplant.foregroundservice.notif_config";

    static final String ERROR_INVALID_CONFIG = "ERROR_INVALID_CONFIG";
    static final String ERROR_SERVICE_ERROR = "ERROR_SERVICE_ERROR";
    static final String ERROR_ANDROID_VERSION = "ERROR_ANDROID_VERSION";


    public OverlayAppsModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void createNotificationChannel(ReadableMap channelConfig, Promise promise) {
      if (channelConfig == null) {
        promise.reject(ERROR_INVALID_CONFIG, "VIForegroundService: Channel config is invalid");
        return;
      }
      NotificationHelper.getInstance(getReactApplicationContext()).createNotificationChannel(channelConfig, promise);
    }

    @ReactMethod
    public void showOverlay(ReadableMap notificationConfig, Promise promise) {
      if (notificationConfig == null) {
        promise.reject(ERROR_INVALID_CONFIG, "VIForegroundService: Notification config is invalid");
        return;
      }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (!notificationConfig.hasKey("channelId")) {
          promise.reject(ERROR_INVALID_CONFIG, "VIForegroundService: channelId is required");
          return;
        }
      }

      if (!notificationConfig.hasKey("id")) {
        promise.reject(ERROR_INVALID_CONFIG , "VIForegroundService: id is required");
        return;
      }

      if (!notificationConfig.hasKey("icon")) {
        promise.reject(ERROR_INVALID_CONFIG, "VIForegroundService: icon is required");
        return;
      }

      if (!notificationConfig.hasKey("title")) {
        promise.reject(ERROR_INVALID_CONFIG, "VIForegroundService: title is reqired");
        return;
      }

      if (!notificationConfig.hasKey("text")) {
        promise.reject(ERROR_INVALID_CONFIG, "VIForegroundService: text is required");
        return;
      }

      Intent intent = new Intent(getReactApplicationContext(), OverlayAppsService.class);
      intent.setAction(ACTION_FOREGROUND_SERVICE_START);
      intent.putExtra(NOTIFICATION_CONFIG, Arguments.toBundle(notificationConfig));
      ComponentName componentName = getReactApplicationContext().startService(intent);
      if (componentName != null) {
        promise.resolve(null);
      } else {
        promise.reject(ERROR_SERVICE_ERROR, "VIForegroundService: Foreground service is not started");
      }
    }

  @ReactMethod
  public void hideOverlay(Promise promise) {
    Intent intent = new Intent(getReactApplicationContext(), OverlayAppsService.class);
    intent.setAction(ACTION_FOREGROUND_SERVICE_STOP);
    boolean stopped = getReactApplicationContext().stopService(intent);
    if (stopped) {
      promise.resolve(null);
    } else {
      promise.reject(ERROR_SERVICE_ERROR, "VIForegroundService: Foreground service failed to stop");
    }
  }
}
