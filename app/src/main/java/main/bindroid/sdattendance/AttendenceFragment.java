package main.bindroid.sdattendance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.bindroid.sdattendance.utills.CommonUtils;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the method interface to handle interaction events. Use the
 * {@link AttendenceFragment#newInstance} factory method to create an instance
 * of this fragment.
 */
public class AttendenceFragment extends Fragment {

	private RecyclerView recyclerView;
	private AttendenceListAdapter adapter;
	private List<AttendenceRowItem> list;
	private Switch toggle;
	private View mProgressBar;
	private AttendenceTogleStateListener attendenceTogleStateListener;

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
	public static AttendenceFragment newInstance(
			AttendenceTogleStateListener attendenceTogleStateListener,
			String param1, String param2) {
		AttendenceFragment fragment = new AttendenceFragment(
				attendenceTogleStateListener);
		Bundle args = new Bundle();

		fragment.setArguments(args);
		return fragment;
	}

	public AttendenceFragment(
			AttendenceTogleStateListener attendenceTogleStateListener) {
		// Required empty public constructor
		this.attendenceTogleStateListener = attendenceTogleStateListener;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView = (RecyclerView) getView().findViewById(
				R.id.recyclerviewAttendence);
		mProgressBar = (View) getView().findViewById(R.id.materialLoader);
		LayoutManager layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);

		list = new ArrayList<AttendenceRowItem>();
		adapter = new AttendenceListAdapter(getActivity(), list);
		recyclerView.setAdapter(adapter);
		ParseQuery.getQuery("SDLoginData").fromLocalDatastore()
				.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void
							done(List<ParseObject> objects, ParseException e) {
						if (e == null && objects.size() > 0) {
							for (int i = 0; i < objects.size(); i++) {
								ParseObject userObject = objects.get(i);
								Log.d("Parse Obj Date",
										"" + userObject.getCreatedAt());
								AttendenceRowItem item = new AttendenceRowItem();
								item.setDate(DateFormat.getDateInstance(
										DateFormat.LONG).format(
										userObject.getCreatedAt())
										+ " ("
										+ new SimpleDateFormat("EE")
												.format(userObject
														.getCreatedAt()) + ")");
								item.setLoginTime(userObject.getCreatedAt()
										.getHours()
										+ ":"
										+ userObject.getCreatedAt()
												.getMinutes());
								item.setLogoutTime(userObject.getUpdatedAt()
										.getHours()
										+ ":"
										+ userObject.getUpdatedAt()
												.getMinutes());
								item.setNoOfHours(userObject
										.getString("WorkHours"));
								list.clear();
								list.add(item);
							}
							Collections.sort(list);
							adapter.notifyDataSetChanged();
						} else
							mProgressBar.setVisibility(View.VISIBLE);
					}
				});
		callNetworkRequestForData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_attendence, container,
				false);
		toggle = (Switch) view.findViewById(R.id.autoToggle);
		toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean b) {
				attendenceTogleStateListener.onTogleStateChange(b);
			}
		});
		if (getActivity().getSharedPreferences("SDAttendance",
				Context.MODE_PRIVATE).getBoolean("service", false)) {
			toggle.setChecked(true);
		}
		return view;
	}

	private void callNetworkRequestForData() {

		ParseQuery<ParseObject> query = ParseQuery
				.getQuery("SDLoginData")
				.whereEqualTo("EmpCode",
						CommonUtils.getLoggedInUser(getActivity()).getEmpCode());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				Log.d("object size", "" + objects.size());
				ParseObject.pinAllInBackground(objects);
				mProgressBar.setVisibility(View.GONE);
				if (e == null && objects.size() > 0) {
					// Todo: update your recycler adapter
					for (int i = 0; i < objects.size(); i++) {
						ParseObject userObject = objects.get(i);
						Log.d("Parse Obj Date", "" + userObject.getCreatedAt());
						AttendenceRowItem item = new AttendenceRowItem();
						item.setDate(DateFormat
								.getDateInstance(DateFormat.LONG).format(
										userObject.getCreatedAt()));
						// + " ("
						// + new SimpleDateFormat("EE").format(userObject
						// .getCreatedAt()) + ")");
						item.setLoginTime(userObject.getCreatedAt().getHours()
								+ ":" + userObject.getCreatedAt().getMinutes());
						item.setLogoutTime(userObject.getUpdatedAt().getHours()
								+ ":" + userObject.getUpdatedAt().getMinutes());
						item.setNoOfHours(userObject.getString("WorkHours"));
						list.clear();
						list.add(item);
					}
					Collections.sort(list);
					adapter.notifyDataSetChanged();
				} else {
					// Todo: no data fetched
					Toast.makeText(getActivity(),
							"Sorry!! We don't have any records for you!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public interface AttendenceTogleStateListener {

		public void onTogleStateChange(boolean isOn);
	}

}
