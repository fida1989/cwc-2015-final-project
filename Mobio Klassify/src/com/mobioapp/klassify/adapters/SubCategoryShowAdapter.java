package com.mobioapp.klassify.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobioapp.klassify.R;
import com.mobioapp.klassify.models.NewSubCategory;

public class SubCategoryShowAdapter extends BaseAdapter {

	private Activity activity;
	List<NewSubCategory> SubCategory = new ArrayList<NewSubCategory>();
	LayoutInflater inflater;


	ImageView imgGiftImage;
	Button btnAccept, btnReject;
	TextView tvDetails;
	int index;

	ProgressDialog pDialog;

	ArrayList<Notification> notificationList;

	private String user_id, buddy_id, item_id, ack_time, ack_date;
	Dialog dialog;

	public SubCategoryShowAdapter(Activity a, List<NewSubCategory> SubCategory) {
		activity = a;
		this.SubCategory = SubCategory;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	

	}

	public int getCount() {
		return SubCategory.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		View view;
		final ViewHolder holder;
		if (convertView == null) {
			view = inflater.inflate(R.layout.sub_single_list_show, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.cat_row_textView1);

			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(SubCategory.get(position).getName());

		return view;
	}

	static class ViewHolder {
		TextView name, eventDes;

	}
}