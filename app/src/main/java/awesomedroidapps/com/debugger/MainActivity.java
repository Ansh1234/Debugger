package awesomedroidapps.com.debugger;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.jnetpcap.Pcap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import awesomedroidapps.com.debugger.jni.MyPcap;
import awesomedroidapps.com.debugger.utils.ApplicationUtils;
import awesomedroidapps.com.debugger.utils.FileUtils;
import awesomedroidapps.com.debugger.utils.NativeController;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


  public static final ArrayList arrayList = new ArrayList();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    System.loadLibrary("jnetpcap");
    System.loadLibrary("custompcap");

   PacketCaptureHandling.handleCapture(this);


    Button startVpnServiceBtn = (Button) findViewById(R.id.start_vpn_service);
    startVpnServiceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!DebuggerService.isServiceRunning()) {
          Intent intent = VpnService.prepare(getApplicationContext());
          if (intent != null) {
            startActivityForResult(intent, 0);
          } else {
            onActivityResult(0, RESULT_OK, null);
          }
        }
      }
    });

    Button startServiceBtn = (Button) findViewById(R.id.start_service);
    startServiceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!DebuggerService.isServiceRunning()) {
          Intent intent = new Intent(MainActivity.this, DebuggerService.class);
          startService(intent);
        }
      }
    });

    Button stopServiceBtn = (Button) findViewById(R.id.stop_servive);
    stopServiceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (DebuggerService.isServiceRunning()) {
          Intent intent = new Intent(MainActivity.this, DebuggerService.class);
          stopService(intent);
        }
      }
    });

    Button ramUsageBtn = (Button) findViewById(R.id.ram_usage);
    ramUsageBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new Thread(new Runnable() {
          @Override
          public void run() {
            for (int i = 0; i < 5000000; i++) {
              arrayList.add("ANshul");
            }

          }
        }).start();

      }
    });

    Button justRamUsageBtn = (Button) findViewById(R.id.ram_usage_simple);
    justRamUsageBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        DeleteActivity.getMemoryInformation(MainActivity.this);
        NativeController.returnRunningProcesses("com.eterno");
      }
    });

    ApplicationUtils.getInstalledApplications(this);

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {

    //debuggerViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      Intent intent = new Intent(this, DebuggerVpnService.class);

      intent.putExtra("address", "54.169.181.12");
      intent.putExtra("port", "6767");
      intent.putExtra("secret", "secret message");
      startService(intent);
    }
  }



}
