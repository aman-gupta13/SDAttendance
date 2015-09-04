package main.bindroid.sdattendance;

/**
 * Created by vikassinghsuriyal on 9/4/15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import main.bindroid.sdattendance.AttendenceListAdapter.CustomViewHolder1;

public class AttendenceListAdapter
		extends
			RecyclerView.Adapter<CustomViewHolder1> {

	private List<AttendenceRowItem> feedItemList;
	private Context mContext;

	public AttendenceListAdapter(Context context,
			List<AttendenceRowItem> feedItemList) {
		this.feedItemList = feedItemList;
		this.mContext = context;
	}

	@Override
	public CustomViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.attendence_card_view, null);

		CustomViewHolder1 viewHolder = new CustomViewHolder1(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(CustomViewHolder1 holder, int position) {
		AttendenceRowItem feedItem = feedItemList.get(position);
		if (feedItem != null) {
			holder.loginDate.setText(feedItem.getDate());
			holder.loginTime.setText("Login : " + feedItem.getLoginTime());
			holder.logoutTime.setText("Logout : " + feedItem.getLogoutTime());
			holder.noOfHours.setText("Hours : " + feedItem.getNoOfHours());

		}

	}

	@Override
	public int getItemCount() {
		return (null != feedItemList ? feedItemList.size() : 0);
	}

	public class CustomViewHolder1 extends RecyclerView.ViewHolder {

		public TextView loginDate;
		public TextView loginTime;
		public TextView logoutTime;
		public TextView noOfHours;

		public CustomViewHolder1(View view) {
			super(view);
			loginDate = (TextView) view.findViewById(R.id.loginDate);
			loginTime = (TextView) view.findViewById(R.id.logintime);
			logoutTime = (TextView) view.findViewById(R.id.logoutTime);
			noOfHours = (TextView) view.findViewById(R.id.noOfHours);
		}
	}

}
