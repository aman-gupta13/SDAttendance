package main.bindroid.sdattendance;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vikassinghsuriyal on 9/4/15.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

	public TextView name;
	public TextView email;
	public TextView mobile;
	public TextView seat;

	public CustomViewHolder(View view) {
		super(view);
		name = (TextView) view.findViewById(R.id.person_name);
		email = (TextView) view.findViewById(R.id.person_email);
		mobile = (TextView) view.findViewById(R.id.person_mobile);
		seat = (TextView) view.findViewById(R.id.person_seat);

	}
}