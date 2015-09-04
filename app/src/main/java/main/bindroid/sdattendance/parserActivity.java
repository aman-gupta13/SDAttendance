/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package main.bindroid.sdattendance;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class parserActivity extends Activity implements View.OnClickListener {

	private RecyclerView mRecyclerView;
	private List<FeedItem> list;
	private BasicListAdapter adapter;
	private EditText field;
	private String search = "Name";
	private Button searchBy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_layout);
		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		list = new ArrayList<FeedItem>();
		adapter = new BasicListAdapter(this, list);
		initWidget();

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

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void initWidget() {
		field = (EditText) findViewById(R.id.editText);
		field.setClickable(false);
		Button find = (Button) findViewById(R.id.find);
		searchBy = (Button) findViewById(R.id.searchby);
		searchBy.setText("Search by Name");
		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(adapter);
		find.setOnClickListener(this);
		searchBy.setOnClickListener(this);

	}

	private void parse(String fieldName, String value) {
		/*
		 * final ParseObject testObject = new ParseObject("TestObject");
		 * testObject.put("foo", "bar"); testObject.saveInBackground(new
		 * SaveCallback() {
		 *
		 * @Override public void done(com.parse.ParseException e) {
		 *
		 * if (e == null) { // Saved successfully. testObject.getObjectId(); }
		 * else { // The save failed.
		 *
		 * }
		 *
		 * }
		 *
		 * });
		 */

		ParseQuery<ParseObject> query = ParseQuery.getQuery("SDEmployee");
		query.whereEqualTo(fieldName, value);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				if (e == null) {
					Toast.makeText(parserActivity.this,
							"size is " + objects.size(), Toast.LENGTH_SHORT)
							.show();
					for (int i = 0; i < objects.size(); i++) {

						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("SDEmployee");
						query.getInBackground(objects.get(i).getObjectId(),
								new GetCallback<ParseObject>() {

									@Override
									public void done(ParseObject arg0,
											ParseException arg1) {
										// TODO Auto-generated method stub

										if (arg1 == null) {
											FeedItem item = new FeedItem();
											item.setEmpName(arg0
													.getString("EmpName"));
											list.add(item);
											adapter.notifyDataSetChanged();

										} else {

										}
									}
								});

					}
					// Log.d("score", "Retrieved " + scoreList.size() +
					// " scores");
				} else {
					// Log.d("score", "Error: " + e.getMessage());
				}

			}

		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.find :
				if (search.equalsIgnoreCase("Name"))
					parse("EmpName", field.getText().toString());
				else if (search.equalsIgnoreCase("Employee Id"))
					parse("EmpCode", field.getText().toString());
				break;

			case R.id.searchby :
				final Dialog dia = new Dialog(this);
				dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dia.setContentView(R.layout.filter_dialog_layout);
				RadioGroup radioGroup = (RadioGroup) dia
						.findViewById(R.id.radioCategory);

				radioGroup
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								field.setClickable(true);
								switch (checkedId) {
									case R.id.radioName :
										search = "Name";

										break;
									case R.id.radioId :
										search = "Employee Id";
										break;
								}
								searchBy.setText("Search by " + search);
							}
						});
				dia.show();
				break;
		}
	}
}
