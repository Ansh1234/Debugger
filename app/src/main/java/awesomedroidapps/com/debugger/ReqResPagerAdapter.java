package awesomedroidapps.com.debugger;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import awesomedroidapps.com.debugger.fragments.RequestFragment;
import awesomedroidapps.com.debugger.fragments.ResponseFragment;
import awesomedroidapps.com.debugger.views.CpuView;
import awesomedroidapps.com.debugger.views.MemoryView;
import awesomedroidapps.com.debugger.views.NetworkView;

/**
 * @author anshul.jain on 2/8/2016.
 */
public class ReqResPagerAdapter extends FragmentPagerAdapter {


  public ReqResPagerAdapter(FragmentManager fm) {
    super(fm);
  }


  @Override
  public Fragment getItem(int position) {

    switch (position){
      case 0:
        return RequestFragment.newInstance();
      case 1:
        return ResponseFragment.newInstance();
    }
    return null;
  }


  @Override
  public int getCount() {
    return 2;
  }

}
