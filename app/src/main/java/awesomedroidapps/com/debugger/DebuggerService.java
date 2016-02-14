package awesomedroidapps.com.debugger;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import awesomedroidapps.com.debugger.utils.MemoryUtils;
import awesomedroidapps.com.debugger.utils.NativeController;

/**
 * @author anshul.jain on 2/8/2016.
 */
public class DebuggerService extends AccessibilityService {

  private static boolean serviceRunning = false;

  public static boolean isServiceRunning() {
    return serviceRunning;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flag, int startId) {
    DebuggerLayout.getInstance().drawLayout(this);
    serviceRunning = true;
    return START_STICKY;
  }

  @Override
  protected void onServiceConnected() {
    super.onServiceConnected();

    //Configure these here for compatibility with API 13 and below.
    AccessibilityServiceInfo config = new AccessibilityServiceInfo();
    config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
    config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

    if (Build.VERSION.SDK_INT >= 16)
    //Just in case this helps
    {
      config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
    }

    setServiceInfo(config);
  }

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event) {
    EventsController.handleEvent(this, event);
  }


  @Override
  public void onInterrupt() {
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    serviceRunning = false;
  }


}
