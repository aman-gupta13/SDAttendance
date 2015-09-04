/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package main.bindroid.sdattendance;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class parser extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		/*final ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(com.parse.ParseException e) {

				if (e == null) {
					// Saved successfully.
					testObject.getObjectId();
				} else {
					// The save failed.

				}

			}

		});*/

		ParseQuery<ParseObject> query = ParseQuery.getQuery("SDEmployee");
		query.whereEqualTo("EmpCode", "9634");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects,
					ParseException e) {

				if (e == null) {
					Toast.makeText(parser.this, "size is " + objects.size(),
							Toast.LENGTH_SHORT)
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

											String playerName = arg0
													.getString("EmpName");
											Toast.makeText(parser.this, playerName,
													Toast.LENGTH_LONG).show();

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
}
