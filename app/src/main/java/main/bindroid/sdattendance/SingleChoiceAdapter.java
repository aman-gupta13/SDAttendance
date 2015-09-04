package main.bindroid.sdattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

/**
 * Created by nitishshrivastava on 9/4/15.
 */
public class SingleChoiceAdapter extends BaseAdapter
		implements
			OnClickListener {

	private String selectedItem;
	private int selectedIndex = 0;
	private String[] options;
	private OnRadioSelected onRadioSelected;
	private LayoutInflater inflater;
	private Context mContext;

	public SingleChoiceAdapter(Context mContext,
			OnRadioSelected onRadioSelected, String[] options) {
		this.options = options;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
		this.onRadioSelected = onRadioSelected;

	}

	void setSelectedIndex(int position) {
		selectedIndex = position;
	}

	@Override
	public int getCount() {
		return options.length;
	}

	@Override
	public Object getItem(int i) {
		return options[i];
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(final int i, View view, ViewGroup viewGroup) {
		ViewObserver viewObserver;
		if (view == null) {
			viewObserver = new ViewObserver();
			view = inflater.inflate(R.layout.material_search_by_row, null);
			viewObserver.radioOption = (RadioButton) view
					.findViewById(R.id.search_by_radio_button);
			viewObserver.radioOption.setOnClickListener(this);
			view.setTag(viewObserver);

		} else {
			viewObserver = (ViewObserver) view.getTag();
		}

		viewObserver.radioOption.setText(options[i]);
		viewObserver.radioOption.setTag(R.id.search_by_radio_button, i);

		if (i == selectedIndex) {
			viewObserver.radioOption.setChecked(true);
		} else {
			viewObserver.radioOption.setChecked(false);
		}

		return view;
	}

	@Override
	public void onClick(View view) {
		selectedIndex = Integer
				.parseInt("" + view.getTag(R.id.search_by_radio_button));

		notifyDataSetChanged();
		onRadioSelected.onRadioSelected(selectedIndex);
	}

	private class ViewObserver {

		RadioButton radioOption;
	}

	public interface OnRadioSelected {

		public void onRadioSelected(int selectedPosition);
	}
}
