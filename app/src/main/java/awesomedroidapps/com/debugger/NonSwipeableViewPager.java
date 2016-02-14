package awesomedroidapps.com.debugger;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author anshul.jain on 2/12/2016.
 */
public class NonSwipeableViewPager extends ViewPager {
  public NonSwipeableViewPager(Context context) {
    super(context);
  }

  public NonSwipeableViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    Log.d("TAG", "intercept touch event" + event.toString());
    // Never allow swiping to switch between pages
    return false;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // Never allow swiping to switch between pages
    Log.d("TAG", "motion event "+ event.toString());
    return false;
  }
}
