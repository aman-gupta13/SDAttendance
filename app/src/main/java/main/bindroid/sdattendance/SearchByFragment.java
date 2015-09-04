package main.bindroid.sdattendance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import main.bindroid.sdattendance.SingleChoiceAdapter.OnRadioSelected;

/**
 * Created by nitishshrivastava on 9/4/15.
 */
public class SearchByFragment extends DialogFragment
		implements
			OnRadioSelected {

	private ListView searchBy;
	private SingleChoiceAdapter singleChoiceAdapter;
	public static String SEARCH_BY_KEY = "search_by_key";
	public static String EMP_CODE = "EmpCode";
	public static String NAME = "Name";
	public static String DEPARTMENT = "Department";

	private String[] optionSearchBy = {EMP_CODE, NAME, DEPARTMENT};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_search_by, container,
				false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		searchBy = (ListView) getView().findViewById(R.id.searchBy);
		singleChoiceAdapter = new SingleChoiceAdapter(getActivity(), this,
				optionSearchBy);
		Bundle bundle = getArguments();
		if (bundle != null) {
			String key = bundle.getString(SEARCH_BY_KEY);
			for (int i = 0; i < optionSearchBy.length; i++) {
				if (optionSearchBy[i].equalsIgnoreCase(key)) {
					singleChoiceAdapter.setSelectedIndex(i);
				}
			}
		}

		searchBy.setAdapter(singleChoiceAdapter);
	}

	//

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle("Search By:");
		return dialog;

	}

	@Override
	public void onRadioSelected(int selectedPosition) {

		if (getTargetFragment() != null) {
			Intent intent = new Intent();
			intent.putExtra(SEARCH_BY_KEY, optionSearchBy[selectedPosition]);
			getTargetFragment().onActivityResult(getTargetRequestCode(),
					Activity.RESULT_OK, intent);
		}

		dismiss();
	}

}
