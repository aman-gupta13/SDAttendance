package main.bindroid.sdattendance;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import main.bindroid.sdattendance.utills.CommonUtils;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link AttendenceFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link AttendenceFragment#newInstance} factory method to create an instance
 * of this fragment.
 */
public class AttendenceFragment extends Fragment {

	private List<ParseObject> mObjects;
	private Switch toggle;

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
	public static AttendenceFragment newInstance(String param1, String param2) {
		AttendenceFragment fragment = new AttendenceFragment();
		Bundle args = new Bundle();

		fragment.setArguments(args);
		return fragment;
	}

	public AttendenceFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callNetworkRequestForData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_attendence, container,
				false);
		toggle = (Switch) view.findViewById(R.id.autoToggle);
		return view;
	}

	private void callNetworkRequestForData() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SDLoginData");
		Log.d("empcode", CommonUtils.getLoggedInUser(getActivity())
				.getEmpCode());
		query.whereEqualTo("EmpCode", CommonUtils
				.getLoggedInUser(getActivity()).getEmpCode());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				Log.d("object size", "" + objects.size());
				if (e == null && objects.size() > 0) {
					mObjects = objects;
					// Todo: update your recycler adapter
				} else {
					// Todo: no data fetched
				}
			}
		});
	}

}
