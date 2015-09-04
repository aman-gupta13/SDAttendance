package main.bindroid.sdattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link FindSDianFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link FindSDianFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class FindSDianFragment extends Fragment {

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
		return inflater.inflate(R.layout.fragment_find_sdian, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		Bundle bundle = new Bundle();
		bundle.putString(SearchByFragment.SEARCH_BY_KEY, SearchByFragment.NAME);
		SearchByFragment searchByFragment = new SearchByFragment();
		searchByFragment.setArguments(bundle);
		searchByFragment.setTargetFragment(this, REQUEST_ID);
		searchByFragment.show(fragmentManager, "searchBy");
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
