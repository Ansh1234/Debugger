package awesomedroidapps.com.debugger;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author anshul.jain on 2/14/2016.
 */
public class DebuggerLayout implements TabLayout.OnTabSelectedListener {

  private LinearLayout debuggerView;
  private TabLayout debuggerTabLayout;
  private NonSwipeableViewPager debuggerViewPager;
  private TextView packageNameTextView, activityNameTextView;
  private WindowManager.LayoutParams params;
  private WindowManager windowManager;
  private static DebuggerLayout layout;

  public static DebuggerLayout getInstance() {
    if (layout == null) {
      layout = new DebuggerLayout();
    }
    return layout;
  }

  public void updateLayout(Context context,DebuggerObject object) {

    if (packageNameTextView != null) {
      packageNameTextView.setText(object.className);
    }
    if (activityNameTextView != null) {
      activityNameTextView.setText(Integer.toString(object.totalRamUsed));
    }
  }

  public void drawLayout(Context context) {
    windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    params = getParams();
    getDebuggerView(context);
    windowManager.addView(debuggerView, params);
  }

  public View getDebuggerView(Context context) {

    params = getParams();

    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService
        (Context.LAYOUT_INFLATER_SERVICE);
    debuggerView = (LinearLayout) layoutInflater.inflate(R.layout.activity_debugger, null);
    packageNameTextView = (TextView) debuggerView.findViewById(R.id.package_name);
    activityNameTextView = (TextView) debuggerView.findViewById(R.id.activity_name);

    debuggerTabLayout = (TabLayout) debuggerView.findViewById(R.id.debugger_tablayout);
    debuggerViewPager = (NonSwipeableViewPager) debuggerView.findViewById(R.id.debugger_pager);
    String[] debugMenuItems = context.getResources().getStringArray(R.array.debugger_menu_items);
    for (int i = 0; i < debugMenuItems.length; i++) {
      TabLayout.Tab tab = debuggerTabLayout.newTab();
      tab.setText(debugMenuItems[i]);
      debuggerTabLayout.addTab(tab);
    }
    debuggerTabLayout.setOnTabSelectedListener(this);
    DebuggerPagerAdapter adapter = new DebuggerPagerAdapter(context);
    debuggerViewPager.setAdapter(adapter);

    debuggerViewPager.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("ONE", "ONE");
      }
    });
    debuggerView.setOnTouchListener(new View.OnTouchListener() {
      private int initialX;
      private int initialY;
      private float initialTouchX;
      private float initialTouchY;

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            initialX = params.x;
            initialY = params.y;
            initialTouchX = event.getRawX();
            initialTouchY = event.getRawY();
            return true;
          case MotionEvent.ACTION_UP:
            return true;
          case MotionEvent.ACTION_MOVE:
            params.x = initialX
                + (int) (event.getRawX() - initialTouchX);
            params.y = initialY
                + (int) (event.getRawY() - initialTouchY);
            windowManager.updateViewLayout(debuggerView, params);
            return true;
        }
        return false;
      }
    });
    return debuggerView;
  }

  public WindowManager.LayoutParams getParams() {
    return new WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT, 300,
        WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
        PixelFormat.TRANSLUCENT);
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    debuggerViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }

}
