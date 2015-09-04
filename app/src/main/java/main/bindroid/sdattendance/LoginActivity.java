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

import main.bindroid.sdattendance.beans.SDEmployee;
import main.bindroid.sdattendance.utills.CommonUtils;

public class LoginActivity extends AppCompatActivity
		implements
			OnClickListener {

	private ImageView snapdealImageView;
	private LinearLayout mainLinearLayout;
	private TextView getInText;
	private EditText empCodeET;
	private View mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if (CommonUtils.getLoggedInUser(getApplicationContext()) != null) {
			startActivity(
					new Intent(LoginActivity.this, DashboardActivity.class));
			finish();
		}

		initViews();

	}

	private void initViews() {
		snapdealImageView = (ImageView) findViewById(R.id.sdlogo);
		mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinear);
		getInText = (TextView) findViewById(R.id.getInTv);
		empCodeET = (EditText) findViewById(R.id.empCodeET);
		mProgressBar = (View) findViewById(R.id.materialLoader);

		getInText.setOnClickListener(this);

		showStartUpAnimation();
	}

	private void showStartUpAnimation() {
		snapdealImageView.animate().translationYBy(-400).setDuration(2000)
				.setStartDelay(1000).start();
		mainLinearLayout.animate().alpha(1).setDuration(4000)
				.setStartDelay(1000).start();
	}

	@Override
	public void onClick(View view) {
		CommonUtils.hideKeypad(this, empCodeET);
		validateDataFromDB();
	}

	public void validateDataFromDB() {
		showProgressbar();
		if (TextUtils.isEmpty(empCodeET.getText().toString().trim())) {

			showToastMessage("Oops!! You missed something!!");
		} else {
			if (CommonUtils.isConnectingToInternet(getApplicationContext())) {
				ParseQuery<ParseObject> query = ParseQuery
						.getQuery("SDEmployee");
				query.whereEqualTo("EmpCode", empCodeET.getText().toString());
				query.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> objects,
							ParseException e) {
						Log.d("Parser", "inside done");
						if (e == null && objects != null
								&& objects.size() > 0) {
							showToastMessage("Welcome To Snapdeal "
									+ objects.get(0).getString("EmpName"));
							SDEmployee employee = new SDEmployee();
							employee.setEmpCode(
									objects.get(0).getString("EmpCode"));
							employee.setEmpName(
									objects.get(0).getString("EmpName"));
							employee.setEmpSeat(
									objects.get(0).getString("EmpSeat"));
							employee.setEmpFloor(
									objects.get(0).getString("EmpFloor"));
							employee.setEmpDepartment(
									objects.get(0).getString("EmpDepartment"));
							employee.setEmpUnit(
									objects.get(0).getString("EmpUnit"));
							employee.setEmpTeam(
									objects.get(0).getString("EmpTeam"));
							CommonUtils.setLoggedInUser(getApplicationContext(),
									employee);
							startActivity(new Intent(LoginActivity.this,
									DashboardActivity.class));
							finish();
						} else {
							showToastMessage(
									"Oops!! I don't know you!! Sorry!!");
						}
					}
				});
			} else {
				showToastMessage("Looks like Internet is Off");
			}
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
