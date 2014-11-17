package com.healthyfriends.items;

import java.util.ArrayList;

import com.healthyfriends.healthyfriends.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomProfileAdapter extends ArrayAdapter<ProfileItem> {
	Context context;
    int layoutResourceId;
    LinearLayout linearMain;
    ArrayList<ProfileItem> data = new ArrayList<ProfileItem>();

    public CustomProfileAdapter(Context context, int layoutResourceId,
                  ArrayList<ProfileItem> data) {
           super(context, layoutResourceId, data);
           this.layoutResourceId = layoutResourceId;
           this.context = context;
           this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;

       if (convertView == null) {
              LayoutInflater inflater = ((Activity) context).getLayoutInflater();
              row = inflater.inflate(layoutResourceId, parent, false);
              //Make sure the textview exists in this xml
       } else {
              row = convertView;
       }
  

       ProfileItem myItem = data.get(position);
       
       
       ImageView itemImage = (ImageView) row.findViewById(R.id.itemImage);
       if(myItem.details[0].equals("Home"))
       {
           itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home));
       }
       else if(myItem.details[0].equals("Calories"))
       {
           itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_whats_hot));
       }
       else if(myItem.details[0].equals("Scan Points"))
       {
           itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_photos));
       }
       else if(myItem.details[0].equals("Total Sugar"))
       {
           itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pages));
       }
       else if(myItem.details[0].equals("Total Fat"))
       {
           itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_people));
       }
       
       TextView authorLabel = (TextView) row.findViewById(R.id.itemTitle);
       authorLabel.setText(myItem.details[0]);
       TextView checkoutIndividual = (TextView) row.findViewById(R.id.itemQuantity);
       checkoutIndividual.setText(myItem.details[1]);

       return row;
    }
    
    public void filter(int type) {
    	// 0 = title
    	// 1 = quantity
    	for(int i=0; i<data.size(); i++) {
 		   data.get(i).setType(type);
 		   for(int j=i+1; j<data.size(); j++) {
 			   if (data.get(i).compareTo(data.get(j))>0) {
 				   String tempIsbn = data.get(j).getTitle();
 				   String[] tempDetails = data.get(j).getDetails();
 				   data.get(j).setTitle(data.get(i).getTitle());
 				   data.get(j).setDetails(data.get(i).getDetails());
 				   data.get(i).setTitle(tempIsbn);
 				   data.get(i).setDetails(tempDetails);
 			   }
 		   }
 	   }
    }
}
