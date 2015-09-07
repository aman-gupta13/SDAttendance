package main.bindroid.sdattendance;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vikassinghsuriyal on 9/4/15.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

	public TextView name;
	public TextView dept;
	public TextView mobile;
	public TextView seat;
	public TextView email;
	public TextView ext;
	public TextView id;

	public CustomViewHolder(View view) {
		super(view);
		name = (TextView) view.findViewById(R.id.person_name);
		dept = (TextView) view.findViewById(R.id.person_department);
		mobile = (TextView) view.findViewById(R.id.person_mobile);
		seat = (TextView) view.findViewById(R.id.person_seat);
		email = (TextView) view.findViewById(R.id.person_email);
		ext = (TextView) view.findViewById(R.id.person_ext);
		id = (TextView) view.findViewById(R.id.emp_code);
	}
}