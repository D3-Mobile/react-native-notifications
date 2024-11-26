package com.wix.reactnativenotifications.core;

import com.facebook.react.bridge.ReactContext;

public interface AppLifecycleFacade {

    interface AppVisibilityListener {
        void onAppVisible();
        void onAppNotVisible();
    }

    boolean isReactInitialized();
    ReactContext getRunningReactContext();
    boolean isAppVisible();
    boolean isAppDestroyed();
    boolean isAppStarted();
    void addVisibilityListener(AppVisibilityListener listener);
    void removeVisibilityListener(AppVisibilityListener listener);
}
