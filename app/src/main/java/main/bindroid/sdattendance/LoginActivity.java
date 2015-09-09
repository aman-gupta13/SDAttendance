package main.bindroid.sdattendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import main.bindroid.sdattendance.beans.SDEmployee;
import main.bindroid.sdattendance.utills.CommonUtils;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

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
			startActivity(new Intent(LoginActivity.this,
					DashboardActivity.class));
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

	public void onRegisterClick(View view) {
		final AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Register Your Details");
		View v = LayoutInflater.from(this).inflate(R.layout.layout_register,
				null);
		final TextInputLayout nameTil = (TextInputLayout) v
				.findViewById(R.id.name_et_til);
		final TextInputLayout codeTil = (TextInputLayout) v
				.findViewById(R.id.code_et_til);
		final TextInputLayout deptTil = (TextInputLayout) v
				.findViewById(R.id.dept_et_til);
		final TextInputLayout seatTil = (TextInputLayout) v
				.findViewById(R.id.seat_et_til);
		// TextView submit = (TextView) v.findViewById(R.id.registerTv);
		builder.setCancelable(false);
		builder.setNegativeButton("Register",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (TextUtils.isEmpty(nameTil.getEditText().getText()
								.toString())) {
							nameTil.setError("Please enter your Name");
						} else if (TextUtils.isEmpty(codeTil.getEditText()
								.getText().toString())) {
							codeTil.setError("Please enter your Code");
						} else if (codeTil.getEditText().getText().toString()
								.length() < 4
								&& codeTil.getEditText().getText().toString()
										.length() > 6) {
							codeTil.setError("Please enter a valid Code");
						} else if (TextUtils.isEmpty(deptTil.getEditText()
								.getText().toString())) {
							deptTil.setError("Please enter your Department");
						} else if (TextUtils.isEmpty(seatTil.getEditText()
								.getText().toString())) {
							seatTil.setError("Please enter your Seat No.");
						} else {
							showProgressbar();
							dialog.dismiss();
							if (CommonUtils
									.isConnectingToInternet(getApplicationContext())) {
								ParseQuery<ParseObject> query = ParseQuery
										.getQuery("SDEmployee");
								query.whereEqualTo("EmpCode", codeTil
										.getEditText().getText().toString());
								query.findInBackground(new FindCallback<ParseObject>() {

									@Override
									public void done(List<ParseObject> objects,
											ParseException e) {
										if (e == null && objects.size() == 0) {
											ParseObject newEmp = new ParseObject(
													"SDEmployee");
											ParseACL acl = new ParseACL(
													ParseUser.getCurrentUser());
											acl.setPublicReadAccess(true);
											acl.setPublicWriteAccess(true);
											newEmp.setACL(acl);
											newEmp.put("EmpCode", codeTil
													.getEditText().getText()
													.toString());
											newEmp.put("EmpDepartment", deptTil
													.getEditText().getText()
													.toString());
											newEmp.put("EmpName", nameTil
													.getEditText().getText()
													.toString());
											newEmp.put("EmpSeat", seatTil
													.getEditText().getText()
													.toString());
											newEmp.saveInBackground(new SaveCallback() {

												@Override
												public void done(
														ParseException e) {
													if (e == null) {
														showToastMessage("Registeration Successful");
														SDEmployee employee = new SDEmployee();
														employee.setEmpCode(codeTil
																.getEditText()
																.getText()
																.toString());
														employee.setEmpName(nameTil
																.getEditText()
																.getText()
																.toString());
														employee.setEmpSeat(seatTil
																.getEditText()
																.getText()
																.toString());
														employee.setEmpFloor("");
														employee.setEmpDepartment(deptTil
																.getEditText()
																.getText()
																.toString());
														employee.setEmpUnit("");
														employee.setEmpTeam("");
														CommonUtils
																.setLoggedInUser(
																		getApplicationContext(),
																		employee);
														startActivity(new Intent(
																LoginActivity.this,
																DashboardActivity.class));
														finish();
													}
												}
											});
										} else {
											if (objects.size() > 0)
												showToastMessage("You are already registered.");
										}
									}
								});
							} else {
								showToastMessage("No Internet Connection.");
							}
						}
					}
				});
		builder.setView(v);
		builder.show();
	}

	private void showStartUpAnimation() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;
		int heightAnimation = 0;
		if (height < 900) {
			heightAnimation = -200;
		} else if (height >= 900 && height < 1500) {
			heightAnimation = -300;
		} else if (height >= 1500) {
			heightAnimation = -400;
		}
		snapdealImageView.animate().translationYBy(heightAnimation)
				.setDuration(2000).setStartDelay(1000).start();
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
					public void
							done(List<ParseObject> objects, ParseException e) {
						Log.d("Parser", "inside done");
						if (e == null && objects != null && objects.size() > 0) {
							showToastMessage("Welcome To Snapdeal "
									+ objects.get(0).getString("EmpName"));
							SDEmployee employee = new SDEmployee();
							employee.setEmpCode(objects.get(0).getString(
									"EmpCode"));
							employee.setEmpName(objects.get(0).getString(
									"EmpName"));
							employee.setEmpSeat(objects.get(0).getString(
									"EmpSeat"));
							employee.setEmpFloor(objects.get(0).getString(
									"EmpFloor"));
							employee.setEmpDepartment(objects.get(0).getString(
									"EmpDepartment"));
							employee.setEmpUnit(objects.get(0).getString(
									"EmpUnit"));
							employee.setEmpTeam(objects.get(0).getString(
									"EmpTeam"));
							CommonUtils.setLoggedInUser(
									getApplicationContext(), employee);
							startActivity(new Intent(LoginActivity.this,
									DashboardActivity.class));
							finish();
						} else {
							showToastMessage("Oops!! I don't know you!! Sorry!!");
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
