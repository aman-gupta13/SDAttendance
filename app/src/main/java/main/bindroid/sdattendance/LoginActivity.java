package main.bindroid.sdattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

	private ImageView snapdealImageView;
	private LinearLayout mainLinearLayout;
	private TextView getInText;
	private EditText empCodeET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		snapdealImageView = (ImageView) findViewById(R.id.sdlogo);
		mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinear);
		getInText = (TextView) findViewById(R.id.getInTv);
		empCodeET = (EditText) findViewById(R.id.empCodeET);
		getInText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validateDataFromDB();
			}
		});
		snapdealImageView.animate().translationYBy(-500).setDuration(2000)
				.setStartDelay(2000).start();
		mainLinearLayout.animate().alpha(1).setDuration(4000)
				.setStartDelay(2000).start();
	}

	public void validateDataFromDB() {
		if (TextUtils.isEmpty(empCodeET.getText().toString().trim())) {
			Toast.makeText(LoginActivity.this, "Oops!! You missed something!!",
					Toast.LENGTH_LONG).show();
		} else {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("SDEmployee");
			query.whereEqualTo("EmpCode", empCodeET.getText().toString());
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					Log.d("Parser", "inside done");

					if (e == null && objects != null && objects.size() > 0) {
						Toast.makeText(
								LoginActivity.this,
								"Welcome To Snapdeal "
										+ objects.get(0).getString("EmpName"),
								Toast.LENGTH_LONG).show();
						startActivity(new Intent(LoginActivity.this,
								DashboardActivity.class));
					} else {
						Toast.makeText(LoginActivity.this,
								"Oops!! I don't know you!! Sorry!!",
								Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}

}
