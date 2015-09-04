package main.bindroid.sdattendance;

import android.app.Dialog;
import android.os.Bundle;
import android.renderscript.ScriptGroup.Input;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;

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
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link FindSDianFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link FindSDianFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class FindSDianFragment extends Fragment
		implements
			View.OnClickListener,
			TextWatcher {

	private RecyclerView mRecyclerView;
	private List<FeedItem> list;
	private BasicListAdapter adapter;
	private EditText field;
	private String search = "Name";
	private Button searchBy;
	private List<ParseObject> obj;
	private View mProgressBar;
	private Button find;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		list = new ArrayList<FeedItem>();
		adapter = new BasicListAdapter(getActivity(), list);
		obj = new ArrayList<>();

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initWidget();
		parse();
	}

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
		field.addTextChangedListener(this);
		field.setClickable(false);
		find = (Button) getView().findViewById(R.id.find);
		searchBy = (Button) getView().findViewById(R.id.searchby);
		mProgressBar = (View) getView().findViewById(R.id.materialLoader);
		searchBy.setText("Search by Name");
		mRecyclerView = (RecyclerView) getView()
				.findViewById(R.id.recyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setAdapter(adapter);
		find.setOnClickListener(this);
		searchBy.setOnClickListener(this);

	}

	private void parse() {
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
		showProgressbar();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SDEmployee");
		/*
		 * if (fieldName.equalsIgnoreCase("EmpName")) { //
		 * query.whereMatches(fieldName, value); } else {
		 * query.whereEqualTo(fieldName, value); }
		 */
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				if (e == null) {
					FindSDianFragment.this.obj = objects;
					hideProgressbar();
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
				list.clear();
				adapter.notifyDataSetChanged();
				if (search.equalsIgnoreCase("Name"))
					findByName(obj, field.getText().toString());
				else if (search.equalsIgnoreCase("Employee Id"))
					findById(obj, field.getText().toString());
				break;

			case R.id.searchby :
				final Dialog dia = new Dialog(getActivity());
				dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dia.setContentView(R.layout.filter_dialog_layout);
				RadioButton nameButton,
				employeeIdButton;
				nameButton = (RadioButton) dia.findViewById(R.id.radioName);
				employeeIdButton = (RadioButton) dia.findViewById(R.id.radioId);
				if (search != null && search.equalsIgnoreCase("name")) {
					nameButton.setChecked(true);
					employeeIdButton.setChecked(false);
				} else if (search != null
						&& search.equalsIgnoreCase("Employee Id")) {
					nameButton.setChecked(false);
					employeeIdButton.setChecked(true);
				}
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
										field.setInputType(InputType.TYPE_CLASS_TEXT);
										break;
									case R.id.radioId :

										field.setInputType(InputType.TYPE_CLASS_NUMBER);

										search = "Employee Id";
										break;
								}
								searchBy.setText("Search by " + search);
								dia.cancel();
							}
						});
				dia.show();
				break;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1,
			int i2) {

	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void afterTextChanged(Editable editable) {
		if (field.getText().toString().length() >= 4) {
			list.clear();
			adapter.notifyDataSetChanged();
			if (search.equalsIgnoreCase("Name"))
				findByName(obj, field.getText().toString());
			else if (search.equalsIgnoreCase("Employee Id"))
				findById(obj, field.getText().toString());
		}

	}

	private void findByName(List<ParseObject> objects, String str) {
		for (int i = 0; i < objects.size(); i++) {
			ParseObject userObject = objects.get(i);
			String name = userObject.getString("EmpName");
			if (name.toLowerCase().contains(str.toLowerCase())) {
				FeedItem item = new FeedItem();
				item.setEmpName(userObject.getString("EmpName"));
				item.setEmpId(userObject.getString("EmpCode"));
				item.setEmpMobile("+911244330082");
				item.setEmpDept(userObject.getString("EmpDepartment"));

				item.setEmpSeat(userObject.getString("EmpSeat"));
				list.add(item);
				adapter.notifyDataSetChanged();
			}

		}
	}

	private void findById(List<ParseObject> objects, String str) {
		for (int i = 0; i < objects.size(); i++) {
			ParseObject userObject = objects.get(i);
			String code = userObject.getString("EmpCode");
			if (code.equalsIgnoreCase(str)) {
				FeedItem item = new FeedItem();
				item.setEmpName(userObject.getString("EmpName"));
				item.setEmpId(userObject.getString("EmpCode"));
				item.setEmpDept(userObject.getString("EmpDepartment"));

				item.setEmpSeat(userObject.getString("EmpSeat"));
				item.setEmpMobile("+911244330082");
				list.add(item);
				adapter.notifyDataSetChanged();
			}

		}

	}

	private void showProgressbar() {
		mProgressBar.setVisibility(View.VISIBLE);
		// getInText.setClickable(false);
		field.setClickable(false);
		searchBy.setClickable(false);
		find.setClickable(false);

	}

	private void hideProgressbar() {
		mProgressBar.setVisibility(View.GONE);
		// getInText.setClickable(true);
		field.setClickable(true);
		searchBy.setClickable(true);
		find.setClickable(true);
	}
}
