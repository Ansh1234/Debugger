package awesomedroidapps.com.debugger;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

  TabLayout debuggerTabLayout;
  ViewPager debuggerViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    debuggerTabLayout = (TabLayout) findViewById(R.id.debugger_tablayout);
    debuggerViewPager = (ViewPager) findViewById(R.id.debugger_pager);


    String[] debugMenuItems = getResources().getStringArray(R.array.debugger_menu_items);
    for (int i = 0; i < debugMenuItems.length; i++) {
      TabLayout.Tab tab = debuggerTabLayout.newTab();
      tab.setText(debugMenuItems[i]);
      debuggerTabLayout.addTab(tab);
    }
    debuggerTabLayout.setOnTabSelectedListener(this);

    DebuggerPagerAdapter adapter = new DebuggerPagerAdapter(getSupportFragmentManager());
    debuggerViewPager.setAdapter(adapter);
    debuggerViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
        debuggerTabLayout));

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


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

    debuggerViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }
}
