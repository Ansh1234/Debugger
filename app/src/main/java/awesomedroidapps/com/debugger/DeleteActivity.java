package awesomedroidapps.com.debugger;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import awesomedroidapps.com.debugger.utils.MemoryUtils;

/**
 * @author anshul.jain on 2/13/2016.
 */
public class DeleteActivity extends Activity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.delete);


    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(this);
    MyLinearLayout l1 = (MyLinearLayout) findViewById(R.id.inner_layout);
    l1.setOnClickListener(this);
    MyLinearLayout l2 = (MyLinearLayout) findViewById(R.id.outer_layout);
    l2.setOnClickListener(this);

  }

      public static void getMemoryInformation(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
            .ACTIVITY_SERVICE);
        PackageManager packageManager = context.getPackageManager();
        final List<ActivityManager.RunningAppProcessInfo>
            runningProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
          String name = processInfo.processName;
          int pid = processInfo.pid;
          int ramSize = MemoryUtils.getRam(context, new int[]{pid});
          System.out.println("Ram size is " + ramSize);
        }
      }

  @Override
  public void onClick(View v) {
    String TAG = "TAG";
    String message = "Message";

    switch (v.getId()) {
      case R.id.button:
        message = "button";
        break;
      case R.id.inner_layout:
        message = "inner layout";
        break;
      case R.id.outer_layout:
        message = "outer_layout";
        break;
    }
    Log.d(TAG, message);
  }
}
