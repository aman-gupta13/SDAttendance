package main.bindroid.sdattendance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Dev.Dhar on 04/09/15.
 */
public class FloorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.floor_layout);
		FloorView floorView = (FloorView) findViewById(R.id.floor);

		floorView.setLocation(LocationMap.getInstance()
				.getLocation(getIntent().getStringExtra("SeatNumber")));

	}

}
