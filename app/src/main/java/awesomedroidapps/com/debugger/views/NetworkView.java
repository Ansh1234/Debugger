package awesomedroidapps.com.debugger.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import awesomedroidapps.com.debugger.R;

/**
 * @author anshul.jain on 2/8/2016.
 */
public class NetworkView {

  public static View getView(Context context, ViewGroup collection) {

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
        .LAYOUT_INFLATER_SERVICE);
    return inflater.inflate(R.layout.view_debugger_network, collection, false);
  }
}
