package awesomedroidapps.com.debugger;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author anshul.jain on 2/13/2016.
 */
public class MyLinearLayout extends LinearLayout {
  public MyLinearLayout(Context context) {
    super(context);
  }
  public MyLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }
  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    Log.d("MYLINEARLayout", "MyLinearLayout");
    return true; // Tell children not to use this event
  }
}
