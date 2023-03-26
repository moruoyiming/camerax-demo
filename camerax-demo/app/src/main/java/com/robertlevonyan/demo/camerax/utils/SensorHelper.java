package com.robertlevonyan.demo.camerax.utils;

import android.app.Activity;
import android.util.Log;
import android.view.OrientationEventListener;

import java.lang.ref.WeakReference;

/**
 * 监听重力系统传感器的变化
 */
public class SensorHelper {
    private static final String TAG = SensorHelper.class.getSimpleName();
    private OrientationEventListener mLandOrientationListener;
    private WeakReference<Activity> mActivityWeakRef;
    private boolean isPortLock = false;
    private boolean isLandLock = false;

    public SensorHelper(final Activity activity, onOrientationChangedListener callBack) {
        this.mActivityWeakRef = new WeakReference(activity);
        this.mLandOrientationListener = new OrientationEventListener(activity, 3) {
            public void onOrientationChanged(int orientation) {
                if (orientation < 100 && orientation > 80) {//"转到了横屏"
                    if (!SensorHelper.this.isLandLock) {
                        isLandLock = true;
                        isPortLock = false;
                        if (callBack != null) {
                            callBack.onOrientationChanged(1);
                        }
                    }
                } else if (orientation < 280 && orientation > 260) {
                    if (!SensorHelper.this.isLandLock) {
                        isLandLock = true;
                        isPortLock = false;
                        if (callBack != null) {
                            callBack.onOrientationChanged(1);
                        }
                    }
                } else if (orientation < 10 || orientation > 350) {
                    if (!SensorHelper.this.isPortLock) {
                        isPortLock = true;
                        isLandLock = false;
                        if (callBack != null) {
                            callBack.onOrientationChanged(0);
                        }
                    }
                } else if (orientation < 190 && orientation > 170) {
                    if (!SensorHelper.this.isPortLock) {
                        isPortLock = true;
                        isLandLock = false;
                        if (callBack != null) {
                            callBack.onOrientationChanged(0);
                        }
                    }
                }
            }
        };
    }

    //禁用切换屏幕的开关
    public void disable() {
        Log.e(TAG, "disable");
        this.mLandOrientationListener.disable();
    }

    //开启横竖屏切换的开关
    public void enable() {
        this.mLandOrientationListener.enable();
    }

    //设置竖屏是否上锁，true锁定屏幕,fanle解锁
    public void setPortLock(boolean lockFlag) {
        this.isPortLock = lockFlag;
    }

    //设置横屏是否锁定，true锁定，false解锁
    public void setLandLock(boolean isLandLock) {
        this.isLandLock = isLandLock;
    }

    public interface onOrientationChangedListener {
        void onOrientationChanged(int orientation);
    }
}