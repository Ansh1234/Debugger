package awesomedroidapps.com.debugger;

import android.app.usage.UsageEvents;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.view.accessibility.AccessibilityEvent;

import java.util.List;

import awesomedroidapps.com.debugger.utils.MemoryUtils;
import awesomedroidapps.com.debugger.utils.NativeController;

/**
 * @author anshul.jain on 2/14/2016.
 */
public class EventsController {

  public static void handleEvent(Context context, AccessibilityEvent event) {

    DebuggerObject object = new DebuggerObject();

    if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

      object.className = event.getClassName().toString();
      object.packageName = event.getPackageName().toString();

      ComponentName componentName = new ComponentName(
          event.getPackageName().toString(),
          event.getClassName().toString()
      );

      ActivityInfo activityInfo = tryGetActivity(context, componentName);

      boolean isActivity = activityInfo != null;

      if (isActivity) {
        List<Integer> runningProcesses = NativeController.returnRunningProcesses(event
            .getPackageName().toString());
        object.pids = runningProcesses;
        int pids[] = new int[object.pids.size()];
        for(int i=0;i<pids.length;i++){
          pids[i] = object.pids.get(i);
        }
        object.totalRamUsed = MemoryUtils.getRam(context,pids);
        DebuggerLayout.getInstance().updateLayout(context, object);
      }

    }
  }

  private static ActivityInfo tryGetActivity(Context context, ComponentName componentName) {
    try {
      return context.getPackageManager().getActivityInfo(componentName, 0);
    } catch (PackageManager.NameNotFoundException e) {
      return null;
    }
  }

}
