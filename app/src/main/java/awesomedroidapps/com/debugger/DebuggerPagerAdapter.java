package awesomedroidapps.com.debugger;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import awesomedroidapps.com.debugger.views.CpuView;
import awesomedroidapps.com.debugger.views.MemoryView;
import awesomedroidapps.com.debugger.views.NetworkView;

/**
 * @author anshul.jain on 2/8/2016.
 */
public class DebuggerPagerAdapter extends PagerAdapter {

  private Context context;

  public DebuggerPagerAdapter(Context context) {
    super();
    this.context = context;
  }


  @Override
  public Object instantiateItem(ViewGroup collection, int position) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = null;
    switch (position){
      case 0:
        view = MemoryView.getView(context, collection);
        break;
      case 1:
        view = NetworkView.getView(context, collection);
        break;
      case 2:
        view = CpuView.getView(context, collection);
        break;
    }

    collection.addView(view);
    return view;
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view==object;
  }

  @Override
  public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }
}
