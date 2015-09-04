package main.bindroid.sdattendance;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link AttendenceFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link AttendenceFragment#newInstance} factory method to create an instance
 * of this fragment.
 */
public class AttendenceFragment extends Fragment {

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_attendence, container, false);
	}

}
