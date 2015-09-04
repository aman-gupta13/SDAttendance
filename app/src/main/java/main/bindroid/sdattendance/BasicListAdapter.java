package main.bindroid.sdattendance;

/**
 * Created by vikassinghsuriyal on 9/4/15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BasicListAdapter extends RecyclerView.Adapter<CustomViewHolder> {

	private List<FeedItem> feedItemList;
	private Context mContext;

	public BasicListAdapter(Context context, List<FeedItem> feedItemList) {
		this.feedItemList = feedItemList;
		this.mContext = context;
	}

	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.card_view, null);

		CustomViewHolder viewHolder = new CustomViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(CustomViewHolder holder, int position) {
		FeedItem feedItem = feedItemList.get(position);
		if (feedItem != null) {
			holder.mobile.setText(feedItem.getEmpMobile());
			holder.dept.setText(feedItem.getEmpDept());
			holder.name.setText(feedItem.getEmpName());
			holder.seat.setText(feedItem.getEmpSeat());
		}

	}

	@Override
	public int getItemCount() {
		return (null != feedItemList ? feedItemList.size() : 0);
	}
}
