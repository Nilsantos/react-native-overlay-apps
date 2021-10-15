package com.reactnativeoverlayapps;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class OverlayAppsService extends Service {
  IBinder binder = new LocalBinder();
  private WindowManager windowManager;
  private View linearLayout;
  TextView textView;

  public class LocalBinder extends Binder {
    public OverlayAppsService getInstance() {
      return OverlayAppsService.this;
    }
  }

  public OverlayAppsService()
  {

  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return binder;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    addFloatingWidgetView(inflater);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (linearLayout != null) {
      windowManager.removeView(linearLayout);
    }
  }

  public void setText(String text) {
    if(linearLayout != null && textView != null) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          textView.setText(text);
        }
      });
    }
  }

  private void addFloatingWidgetView(LayoutInflater inflater) {
    if(linearLayout == null)
    {
      linearLayout = inflater.inflate(R.layout.floating_widget_layout, null);

      final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
      );

      windowManager.addView(linearLayout, params);
      textView = linearLayout.findViewById(R.id.textView);

      try {
        addOnTouchListener(params);
      } catch (Exception e) {

      }
    }
  }

  private void addOnTouchListener(WindowManager.LayoutParams params) {
    linearLayout.setOnTouchListener(new View.OnTouchListener() {
      float dX, dY;

      @Override
      public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            dX = params.x - event.getRawX();
            dY = params.y - event.getRawY();
            break;

          case MotionEvent.ACTION_MOVE:
            params.x = Math.round(event.getRawX() + dX);
            params.y = Math.round(event.getRawY() + dY);
            windowManager.updateViewLayout(v, params);
            break;

          default:
            return false;
        }

        return true;
      }
    });
  }
}
