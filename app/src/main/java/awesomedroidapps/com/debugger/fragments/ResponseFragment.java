package awesomedroidapps.com.debugger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import awesomedroidapps.com.debugger.R;

/**
 * @author anshul.jain on 2/20/2016.
 */
public class ResponseFragment extends Fragment {

  public static ResponseFragment newInstance() {
    ResponseFragment f = new ResponseFragment();
    return f;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_response, container, false);
    return root;
  }

}
