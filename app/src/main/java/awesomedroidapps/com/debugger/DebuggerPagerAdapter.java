package awesomedroidapps.com.debugger;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import awesomedroidapps.com.debugger.fragments.CpuFragment;
import awesomedroidapps.com.debugger.fragments.MemoryFragment;
import awesomedroidapps.com.debugger.fragments.NetworkFragment;

/**
 * @author anshul.jain on 2/8/2016.
 */
public class DebuggerPagerAdapter extends FragmentPagerAdapter  {

  public DebuggerPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {

    switch (position){
      case 0:
        return MemoryFragment.getInstance();
      case 1:
        return NetworkFragment.getInstance();
      case 2:
        return CpuFragment.getInstance();
    }
    return null;
  }

  @Override
  public int getCount() {
    return 3;
  }
}
