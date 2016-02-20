package awesomedroidapps.com.debugger;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * @author anshul.jain on 2/20/2016.
 */
public class ReqResActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

  ViewPager reqResViewPager;
  TabLayout reqResTabLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reqres);

    Toolbar toolbar = (Toolbar) findViewById(R.id.activity_reqres_toolbar);
    setSupportActionBar(toolbar);

    reqResViewPager = (ViewPager) findViewById(R.id.reqres_viewpager);

    reqResTabLayout = (TabLayout) findViewById(R.id.reqres_tablayout);
    TabLayout.Tab requestTab =  reqResTabLayout.newTab();
    requestTab.setText("Request");
    reqResTabLayout.addTab(requestTab);

    TabLayout.Tab responseTab = reqResTabLayout.newTab();
    responseTab.setText("Response");
    reqResTabLayout.addTab(responseTab);

    reqResTabLayout.setOnTabSelectedListener(this);

    ReqResPagerAdapter adapter = new ReqResPagerAdapter(getSupportFragmentManager());
    reqResViewPager.setAdapter(adapter);

    reqResViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    reqResViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }

}
