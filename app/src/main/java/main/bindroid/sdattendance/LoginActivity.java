package main.bindroid.sdattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

	private ImageView snapdealImageView;
	private LinearLayout mainLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		snapdealImageView = (ImageView) findViewById(R.id.sdlogo);
		mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinear);
		findViewById(R.id.getInTv).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,
						DashboardActivity.class));
			}
		});
		snapdealImageView.animate().translationYBy(-500).setDuration(2000).setStartDelay(2000)
				.start();
		mainLinearLayout.animate().alpha(1).setDuration(4000).setStartDelay(2000).start();
	}

}
