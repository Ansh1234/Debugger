package awesomedroidapps.com.debugger;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/**
 * @author anshul.jain on 2/8/2016.
 */
public class DebuggerWindow extends Service {

  WindowManager.LayoutParams params;
  WindowManager windowManager;
  Button debuggerView;

  @Override
  public void onCreate() {
    super.onCreate();

  }

  @Override
  public int onStartCommand(Intent intent, int flag, int startId) {

    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

    params = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT, 200,
        WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
        PixelFormat.TRANSLUCENT);

    debuggerView = new Button(this);
    debuggerView.setText("Anshul");
    windowManager.addView(debuggerView, params);

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
    return START_STICKY;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }


  @Override
  public void onDestroy(){
    super.onDestroy();
    if(debuggerView!=null){
      windowManager.removeView(debuggerView);
    }
  }
}
