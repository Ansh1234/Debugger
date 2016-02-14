package awesomedroidapps.com.debugger.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.util.Log;

import java.util.List;

/**
 * @author anshul.jain on 2/13/2016.
 */
public class MemoryUtils {

  public static void getMemInfo(Context context) {
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    activityManager.getMemoryInfo(mi);
    long availableMegs = mi.availMem / 1048576L;

//Percentage can be calculated for API 16+
    long percentAvail = mi.availMem / mi.totalMem;

    Log.d("availableMegs ", availableMegs + "");
    Log.d("percentAvail", percentAvail + "");
  }

  public static void getProcessInfo(Context context, String packageName) {

    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
        .ACTIVITY_SERVICE);
    PackageManager packageManager = context.getPackageManager();
    final List<ActivityManager.RunningAppProcessInfo> runningProcesses =
        activityManager.getRunningAppProcesses();
    final List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager
        .getRunningServices(100);
    System.out.println("Size of running process is " + runningServiceInfos.size());
    for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
      Log.d("processNames ", serviceInfo.service.toString());

      int size = getRam(context, new int[]{serviceInfo.pid});
      Log.d("memoryInfo ", " " + size + " M.B.");
      CharSequence appName = null;

    }
  }

  public static int getRam(Context context, int[] pids) {
    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
        .ACTIVITY_SERVICE);
    Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(pids);

    int size = 0;

    for(int i=0;i<memoryInfos.length;i++){

      size = size + memoryInfos[i].getTotalPrivateClean() + memoryInfos[i]
          .getTotalPrivateDirty();
    }
    return size==0 ? size : (size / 1024);
  }

}
