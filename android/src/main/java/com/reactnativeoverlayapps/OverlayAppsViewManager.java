package com.reactnativeoverlayapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class OverlayAppsViewManager extends SimpleViewManager<FrameLayout> {

  public static final String REACT_CLASS = "OverlayAppsView";
  ReactApplicationContext mCallerContext;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  public OverlayAppsViewManager(ReactApplicationContext reactContext) {
    mCallerContext = reactContext;
  }

  @NonNull
  @Override
  protected FrameLayout createViewInstance(ThemedReactContext reactContext) {
    LayoutInflater inflater = (LayoutInflater) reactContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.floating_widget_layout, null);
    return frameLayout;
  }
}
