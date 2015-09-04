package main.bindroid.sdattendance;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the interface to handle interaction events. Use the
 * {@link FindSDianFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class FindSDianFragment extends Fragment
		implements
			View.OnClickListener {

	private RecyclerView mRecyclerView;
	private List<FeedItem> list;
	private BasicListAdapter adapter;
	private EditText field;
	private String search = "Name";
	private Button searchBy;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		list = new ArrayList<FeedItem>();
		adapter = new BasicListAdapter(getActivity(), list);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initWidget();
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		Bundle bundle = new Bundle();
		bundle.putString(SearchByFragment.SEARCH_BY_KEY, SearchByFragment.NAME);
		SearchByFragment searchByFragment = new SearchByFragment();
		searchByFragment.setArguments(bundle);
		searchByFragment.setTargetFragment(this, REQUEST_ID);
		searchByFragment.show(fragmentManager, "searchBy");
	}

	private int REQUEST_ID = 101;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment AttendenceFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static FindSDianFragment newInstance(String param1, String param2) {
		FindSDianFragment fragment = new FindSDianFragment();
		Bundle args = new Bundle();

		fragment.setArguments(args);
		return fragment;
	}

	public FindSDianFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.filter_layout, container, false);
	}

	private void initWidget() {
		field = (EditText) getView().findViewById(R.id.editText);
		field.setClickable(false);
		Button find = (Button) getView().findViewById(R.id.find);
		searchBy = (Button) getView().findViewById(R.id.searchby);

		searchBy.setText("Search by Name");
		mRecyclerView = (RecyclerView) getView()
				.findViewById(R.id.recyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
									item.setEmpName(arg0.getString("EmpName"));
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
				final Dialog dia = new Dialog(getActivity());
				dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dia.setContentView(R.layout.filter_dialog_layout);
				RadioButton nameButton, employeeIdButton;
				nameButton = (RadioButton) dia.findViewById(R.id.radioName);
				employeeIdButton = (RadioButton) dia.findViewById(R.id.radioId);
				if (search != null && search.equalsIgnoreCase("name")) {
					nameButton.setChecked(true);
					employeeIdButton.setChecked(false);
				} else
					if (search != null
							&& search.equalsIgnoreCase("Employee Id")) {
					nameButton.setChecked(false);
					employeeIdButton.setChecked(true);
				}
				RadioGroup radioGroup = (RadioGroup) dia
						.findViewById(R.id.radioCategory);

				radioGroup.setOnCheckedChangeListener(
						new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								field.setClickable(true);
								switch (checkedId) {
									case R.id.radioName :
										search = "Name";
										field.setInputType(
												InputType.TYPE_CLASS_TEXT);
										break;
									case R.id.radioId :
										field.setInputType(
												InputType.TYPE_CLASS_NUMBER);
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && requestCode == REQUEST_ID) {
			Toast.makeText(getActivity(),
					"Result Found::" + data.getExtras()
							.getString(SearchByFragment.SEARCH_BY_KEY),
					Toast.LENGTH_SHORT).show();
		}
	}
}
