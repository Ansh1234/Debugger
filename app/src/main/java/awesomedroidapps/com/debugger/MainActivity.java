package awesomedroidapps.com.debugger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import awesomedroidapps.com.debugger.utils.NativeController;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {



  public  static final ArrayList arrayList = new ArrayList();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    for(int i=0;i<500000;i++){
      arrayList.add("ANshul");
    }
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

    Button ramUsageBtn = (Button)findViewById(R.id.ram_usage);
    ramUsageBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new Thread(new Runnable() {
          @Override
          public void run() {
            for(int i=0;i<500000;i++){
              arrayList.add("ANshul");
            }
          }
        }).start();

        DeleteActivity.getMemoryInformation(MainActivity.this);
      }
    });

    Button justRamUsageBtn = (Button)findViewById(R.id.ram_usage_simple);
    justRamUsageBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        DeleteActivity.getMemoryInformation(MainActivity.this);
        NativeController.returnRunningProcesses("com.eterno");
      }
    });


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
}
