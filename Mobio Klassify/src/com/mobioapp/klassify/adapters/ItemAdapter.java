package com.mobioapp.klassify.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobioapp.klassify.R;
import com.mobioapp.klassify.models.Item;
import com.squareup.picasso.Picasso;

public class ItemAdapter extends ArrayAdapter<Item> {
	private Context context;
	private Item item;
    public ItemAdapter(Context context, ArrayList<Item> users) {
       super(context, 0, users);
       this.context = context;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
       // Get the data item for this position
    	
       item = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_item, parent, false);
       }
       // Lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.item_textView1);
       TextView tvPrice = (TextView) convertView.findViewById(R.id.item_textView2);
       ImageView tvImage = (ImageView)convertView.findViewById(R.id.item_imageView1);
      
       // Populate the data into the template view using the data object
       tvName.setText(item.getTitle());
       tvPrice.setText(item.getPrice());
       Picasso.with(context).load(item.getImage()).placeholder(R.drawable.ic_launcher)
       .error(R.drawable.ic_launcher).into(tvImage);
       
   
       
       // Return the completed view to render on screen
       return convertView;
   }
}