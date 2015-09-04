package main.bindroid.sdattendance;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import main.bindroid.sdattendance.utills.CommonUtils;

public class DashboardActivity extends AppCompatActivity {

	private static final int REQUEST_ENABLE_BT = 1234;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private BeaconManager mBeaconManager;
	private Region mRegion;
	private TabLayout.Tab tab1;
	private TabLayout.Tab tab2;
	private MyPagerAdapter myAdapter;
	private TextView nameTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dahsboard);

		// doing beacon work here
		mRegion = new Region(Globals.REGION + "_", Globals.PROXIMITY_UUID,
				Globals.MAJOR, Globals.MINOR);
		// Configure mBeaconManager.
		mBeaconManager = new BeaconManager(this);
		// Default values are 5s of scanning and 25s of waiting time to save CPU
		// cycles.
		// In order for this demo to be more responsive and immediate we lower
		// down those values.
//		mBeaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);
//		mBeaconManager
//				.setMonitoringListener(new BeaconManager.MonitoringListener() {
//
//					@Override
//					public void onEnteredRegion(final Region region,
//							List<Beacon> beacons) {
//						// Todo: do something when region entered
//					}
//
//					@Override
//					public void onExitedRegion(final Region region) {
//						// Todo: do something when region exited
//					}
//
//				});
		// starting beacon service here if bluetooth is on and never started
		if (!getSharedPreferences("SDAttendance", Context.MODE_PRIVATE)
				.getBoolean("service", false)) {
			startBeaconService();
		}

		tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		nameTextView = (TextView) findViewById(R.id.nameTextView);
		nameTextView.setText(CommonUtils.getLoggedInUser(
				getApplicationContext()).getEmpName());
		tab1 = tabLayout.newTab();
		tab2 = tabLayout.newTab();
		myAdapter = new MyPagerAdapter(getSupportFragmentManager());
		tabLayout.addTab(tab1);
		tabLayout.addTab(tab2);
		viewPager.setOnPageChangeListener(onPageChangeListener);
		viewPager.setAdapter(myAdapter);
		tabLayout.setupWithViewPager(viewPager);
		tabLayout
				.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(
						viewPager));
	}

	OnTabChangeListener tabChangeListener = new OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {

		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		// Check if device supports Bluetooth Low Energy.
		if (!mBeaconManager.hasBluetooth()) {
			Toast.makeText(this, getString(R.string.device_no_ble),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mBeaconManager.hasBluetooth()) {
			mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {

				@Override
				public void onServiceReady() {
					try {
						mBeaconManager.startMonitoring(mRegion);
					} catch (RemoteException e) {
						Log.d("DashboardActivity",
								"Error while starting monitoring");
					}
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		mBeaconManager.disconnect();
		super.onDestroy();
	}

	@Override
	protected void
			onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				startBeaconService();
			} else {
				Toast.makeText(this, getString(R.string.bluetooth_not_enabled),
						Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private boolean checkBluetooth() {
		// Check if device supports Bluetooth Low Energy.
		if (!mBeaconManager.hasBluetooth()) {
			Toast.makeText(this, getString(R.string.device_no_ble),
					Toast.LENGTH_LONG).show();
			return false;
		}
		// If Bluetooth is not enabled, let user enable it.
		if (!mBeaconManager.isBluetoothEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			return true;
		}
		return false;
	}

	private void startBeaconService() {
		if (checkBluetooth()) {
			startService(new Intent(DashboardActivity.this, MyService.class));
			getSharedPreferences("SDAttendance", Context.MODE_PRIVATE).edit()
					.putBoolean("service", true).commit();
			Toast.makeText(DashboardActivity.this,
					getString(R.string.service_started), Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void stopBeaconService() {
		stopService(new Intent(DashboardActivity.this, MyService.class));
		Toast.makeText(DashboardActivity.this,
				getString(R.string.service_stopped), Toast.LENGTH_SHORT).show();
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
