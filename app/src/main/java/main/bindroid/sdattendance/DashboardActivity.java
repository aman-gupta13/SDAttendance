package main.bindroid.sdattendance;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;

public class DashboardActivity extends AppCompatActivity {

	private ViewPager viewPager;
	private TabLayout tabLayout;
	private TabLayout.Tab tab1;
	private TabLayout.Tab tab2;
	private MyPagerAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dahsboard);
		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		tab1 = tabLayout.newTab();
		tab2 = tabLayout.newTab();

		myAdapter = new MyPagerAdapter(getSupportFragmentManager());

		tabLayout.addTab(tab1);
		tabLayout.addTab(tab2);
		viewPager.setOnPageChangeListener(onPageChangeListener);
		viewPager.setAdapter(myAdapter);
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.setOnTabSelectedListener(
				new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

	}

	OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			viewPager.setCurrentItem(position, true);
			switch (position) {
				case 0 :
					// tabLayout...setTabMode(0);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_dahsboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0 :
					return new AttendenceFragment();
				case 1 :
					return new FindSDianFragment();

			}
			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			switch (position) {
				case 0 :
					return "Attendence";
				case 1 :
					return "Find SDian";

			}

			return super.getPageTitle(position);
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

}
