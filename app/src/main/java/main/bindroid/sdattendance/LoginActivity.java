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
	private View mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		snapdealImageView = (ImageView) findViewById(R.id.sdlogo);
		mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinear);
		getInText = (TextView) findViewById(R.id.getInTv);
		empCodeET = (EditText) findViewById(R.id.empCodeET);
		mProgressBar = (View) findViewById(R.id.materialLoader);

		getInText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validateDataFromDB();
			}
		});
		snapdealImageView.animate().translationYBy(-400).setDuration(2000)
				.setStartDelay(1000).start();
		mainLinearLayout.animate().alpha(1).setDuration(4000)
				.setStartDelay(1000).start();
	}

	public void validateDataFromDB() {
		showProgressbar();
		if (TextUtils.isEmpty(empCodeET.getText().toString().trim())) {

			showToastMessage("Oops!! You missed something!!");
		} else {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("SDEmployee");
			query.whereEqualTo("EmpCode", empCodeET.getText().toString());
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					Log.d("Parser", "inside done");

					if (e == null && objects != null && objects.size() > 0) {

						showToastMessage("Welcome To Snapdeal "
								+ objects.get(0).getString("EmpName"));
						startActivity(new Intent(LoginActivity.this,
								DashboardActivity.class));
					} else {

						showToastMessage("Oops!! I don't know you!! Sorry!!");
					}
				}
			});
		}
	}

	private void showToastMessage(String message) {
		Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
		hideProgressbar();
	}

	private void showProgressbar() {
		mProgressBar.setVisibility(View.VISIBLE);
		getInText.setClickable(false);

	}

	private void hideProgressbar() {
		mProgressBar.setVisibility(View.GONE);
		getInText.setClickable(true);

	}

}
