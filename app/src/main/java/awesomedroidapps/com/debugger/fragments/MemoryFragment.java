package awesomedroidapps.com.debugger.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import awesomedroidapps.com.debugger.R;

/**
 * @author anshul.jain on 2/8/2016.
 */
public class MemoryFragment extends Fragment {

  public static Fragment getInstance() {
    MemoryFragment fragment = new MemoryFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
    super.onCreateView(inflater, viewGroup, bundle);
    View rootView = inflater.inflate(R.layout.debugger_memory_layout, null);
    return rootView;
  }
}
