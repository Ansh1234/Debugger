package awesomedroidapps.com.debugger.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;

import java.util.List;

/**
 * @author anshul.jain on 2/14/2016.
 */
public class ApplicationUtils {

  public static void getInstalledApplications(Context context){

    final PackageManager packageManager = context.getPackageManager();
    List<ApplicationInfo> installedApplications =
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

    System.out.println("number of installed applications are "+installedApplications.size());
    for (ApplicationInfo appInfo : installedApplications)
    {
      int uid = appInfo.uid;

      if(appInfo.packageName.equals("com.eterno")){

        long totalBytes = TrafficStats.getUidRxBytes(uid)+TrafficStats.getUidTxBytes(uid);
        System.out.println("Total bytes consumed by Dailyhunt are "+((totalBytes/1024)/1024)+ " " +
            "MB ");
      }
      //Log.d("OUTPUT", "Package name : " + appInfo.packageName);
      //Log.d("OUTPUT", "Name: " + appInfo.loadLabel(packageManager));
    }
  }
}
