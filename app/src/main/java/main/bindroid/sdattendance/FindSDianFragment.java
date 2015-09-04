package main.bindroid.sdattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the interface to handle interaction events. Use the
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

	private TextView searchBy;

	String searchByText;

	private List<ParseObject> obj;
	private View mProgressBar;
	private TextView find;

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
		field.addTextChangedListener(this);
		field.setClickable(false);
		find = (TextView) getView().findViewById(R.id.find);
		searchBy = (TextView) getView().findViewById(R.id.searchby);

		mProgressBar = (View) getView().findViewById(R.id.materialLoader);

		mRecyclerView = (RecyclerView) getView()
				.findViewById(R.id.recyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter.setOnClickListener(this);
		mRecyclerView.setAdapter(adapter);
		find.setOnClickListener(this);
		searchBy.setOnClickListener(this);
		searchByText = SearchByFragment.EMP_CODE;
		setSearchTitle();
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
		query.setLimit(1000);
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
				if (searchByText.equalsIgnoreCase(SearchByFragment.NAME))

					findByName(obj, field.getText().toString());
				else if (searchByText
						.equalsIgnoreCase(SearchByFragment.EMP_CODE))
					findById(obj, field.getText().toString());
				else if (searchByText
						.equalsIgnoreCase(SearchByFragment.DEPARTMENT))
					findByDept(obj, field.getText().toString());
				break;

			case R.id.searchby :
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				Bundle bundle = new Bundle();
				bundle.putString(SearchByFragment.SEARCH_BY_KEY, searchByText);
				SearchByFragment searchByFragment = new SearchByFragment();
				searchByFragment.setArguments(bundle);
				searchByFragment.setTargetFragment(this, REQUEST_ID);
				searchByFragment.show(fragmentManager, "searchBy");
				break;
			case R.id.mainView :

				Intent intent = new Intent(getActivity(), FloorActivity.class);
				intent.putExtra("SeatNumber", view.getTag().toString());
				startActivity(intent);
				break;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && requestCode == REQUEST_ID) {

			searchByText = data.getExtras().getString(
					SearchByFragment.SEARCH_BY_KEY);
			list.clear();
			adapter.notifyDataSetChanged();

			setSearchTitle();
		}
	}

	private void setSearchTitle() {
		if (searchByText.equals(SearchByFragment.EMP_CODE)) {
			field.getText().clear();
			field.setInputType(InputType.TYPE_CLASS_NUMBER);
		} else {
			field.getText().clear();
			field.setInputType(InputType.TYPE_CLASS_TEXT);
		}

		searchBy.setText(getSortFilterTitle("Search By" + "\n", searchByText));

	}

	private CharSequence getSortFilterTitle(String title, String words) {

		SpannableString sb = new SpannableString(title);
		sb.setSpan(new TextAppearanceSpan(getActivity(),
				R.style.sort_filer_title), 0, title.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		String displayName = words.toUpperCase();
		SpannableString filterWord = new SpannableString(displayName);
		filterWord.setSpan(new TextAppearanceSpan(getActivity(),
				R.style.selected_sort_filter), 0, displayName.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		return TextUtils.concat(sb, filterWord);
	}

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
			if (searchByText.equalsIgnoreCase(SearchByFragment.NAME))
				findByName(obj, field.getText().toString());
			else if (searchByText.equalsIgnoreCase(SearchByFragment.EMP_CODE))
				findById(obj, field.getText().toString());
			else if (searchByText.equalsIgnoreCase(SearchByFragment.DEPARTMENT))
				findByDept(obj, field.getText().toString());

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
				item.setEmpEmail("xxx@snapdeal.com");
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
			if (code.startsWith(str)) {
				FeedItem item = new FeedItem();
				item.setEmpName(userObject.getString("EmpName"));
				item.setEmpId(userObject.getString("EmpCode"));
				item.setEmpDept(userObject.getString("EmpDepartment"));
				item.setEmpEmail("xxx@snapdeal.com");
				item.setEmpSeat(userObject.getString("EmpSeat"));
				item.setEmpMobile("+911244330082");
				list.add(item);
				adapter.notifyDataSetChanged();
			}

		}

	}

	private void findByDept(List<ParseObject> objects, String str) {
		for (int i = 0; i < objects.size(); i++) {
			ParseObject userObject = objects.get(i);
			String dept = userObject.getString("EmpDepartment");
			if (dept.toLowerCase().contains(str.toLowerCase())) {
				FeedItem item = new FeedItem();
				item.setEmpName(userObject.getString("EmpName"));
				item.setEmpId(userObject.getString("EmpCode"));
				item.setEmpDept(userObject.getString("EmpDepartment"));
				item.setEmpEmail("xxx@snapdeal.com");
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
