package com.healthyfriends.healthyfriends;

import java.util.Iterator;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PhotosFragment extends Fragment {
	
	public PhotosFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);

        System.out.println("----- PhotosFragment -----");
        
        TextView titleText = (TextView) rootView.findViewById(R.id.txtLabel);
        EditText editText1 = (EditText) rootView.findViewById(R.id.editText1);
        EditText editText2 = (EditText) rootView.findViewById(R.id.editText2);
        EditText editText3 = (EditText) rootView.findViewById(R.id.editText3);
        EditText editText4 = (EditText) rootView.findViewById(R.id.editText4);
        Button submitButton = (Button) rootView.findViewById(R.id.buttonSubmit);
        
        titleText.setVisibility(View.GONE);
        editText1.setVisibility(View.GONE);
        editText2.setVisibility(View.GONE);
        editText3.setVisibility(View.GONE);
        editText4.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);

        //Button mScan = (Button) findViewById(R.id.BarcodeScan);
      
        Firebase myref = new Firebase("https://glowing-torch-7379.firebaseio.com/Item");
	    myref.child("10000000").child("Value").setValue(10);
        final Button button = (Button) rootView.findViewById(R.id.barcodeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                //intent.putExtra("SCAN_MODE", "ALL_CODE_TYPE");
                startActivityForResult(intent, 0);
            }
        });

        
        return rootView;
    }
	
	 public void onActivityResult(int requestCode, int resultCode, final Intent intent) {
		 
     	final TextView titleText = (TextView) getActivity().findViewById(R.id.txtLabel);
         final EditText editText1 = (EditText) getActivity().findViewById(R.id.editText1);
         final EditText editText2 = (EditText) getActivity().findViewById(R.id.editText2);
         final EditText editText3 = (EditText) getActivity().findViewById(R.id.editText3);
         final EditText editText4 = (EditText) getActivity().findViewById(R.id.editText4);
         final Button submitButton = (Button) getActivity().findViewById(R.id.buttonSubmit);
		 
		 System.out.println("PhotosFragment: onActivityResult");
	        if (requestCode == 0) {
	            if (resultCode == getActivity().RESULT_OK) {
	                final String contents = intent.getStringExtra("SCAN_RESULT");

	            	Firebase initialref = new Firebase("https://glowing-torch-7379.firebaseio.com/Item/");
	            	// IF data not found, allow user to input
	            	
	            	initialref.child(contents).addListenerForSingleValueEvent(new ValueEventListener() {
	            	    public void onDataChange(DataSnapshot snapshot) {
	            	        if (snapshot.getValue() != null) {
	            	        	TextView titleText = (TextView) getActivity().findViewById(R.id.txtLabel);
	            	        	String text = "Already Exists";
	            	        	Iterable<DataSnapshot> iter = snapshot.getChildren();
	            	        	String arr[] = {"Calories", "Fat(grams)", "Name", "Points", "Sugar(grams)"};
	            	        	int i = 0;
	            	        	String item = "";
	            	        	int points = 0;
	            	        	while(iter.iterator().hasNext()){
	            	        		Object temp = iter.iterator().next().getValue();
	            	        		System.out.println(temp.toString());
	            	        		text = text + "\n" + arr[i] + " : " + temp.toString();
	            	        		if(i == 2) item = temp.toString();
	            	        		if(i == 3) points = Integer.parseInt(temp.toString());
	            	        		i++;
	            	        	}
	            	        	titleText.setText(text);
	    		                titleText.setVisibility(View.VISIBLE);
	    		                titleText.setGravity(Gravity.CENTER_HORIZONTAL);
	    		                addScore(item,points);
	    		                
	            	        	// Add in the amount of servings
	            	        	//final EditText editText1 = (EditText) getActivity().findViewById(R.id.editText1);
	            	        } else {
	    		                
	    		                titleText.setVisibility(View.VISIBLE);
	    		                editText1.setVisibility(View.VISIBLE);
	    		                editText2.setVisibility(View.VISIBLE);
	    		                editText3.setVisibility(View.VISIBLE);
	    		                editText4.setVisibility(View.VISIBLE);
	    		                submitButton.setVisibility(View.VISIBLE);
	    		            	
	    		            	
	    		                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	    		                // contents - the UPC barcode number
	    		       		 	System.out.println("Contents: " + contents);
	    		       		 	titleText.setText("Input Data for New Item " + contents);
	    		                Log.d("CONTENTS", contents);
	    		                Log.d("FORMAT", format);
	    		               
	    		                //LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	    		                
	    		                
	    		                submitButton.setOnClickListener(new View.OnClickListener() {
	    		                    public void onClick(View v) {
	    		                    	System.out.println("Onclick before the if");
	    	
	    		                    	
	    		                  
	    		                    	if(!editText1.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty() && !editText3.getText().toString().isEmpty() && !editText4.getText().toString().isEmpty()){
	    	
	    			    	                String name = editText1.getText().toString();
	    			    	                int calories = Integer.parseInt(editText2.getText().toString());
	    			    	                int sugar = Integer.parseInt(editText3.getText().toString());
	    			    	                int fat = Integer.parseInt(editText4.getText().toString());
	    		                    		
	    		                    		Firebase myref = new Firebase("https://glowing-torch-7379.firebaseio.com/Item/"+contents);
	    			                    	System.out.println("https://glowing-torch-7379.firebaseio.com/Item"+contents);
	    			                    	System.out.println("Name: "+name);
	    			                    	System.out.println("Fat: "+fat);
	    			                    	System.out.println("Calories "+calories);
	    			                    	System.out.println("Sugar: "+sugar);
	    			                    
	    			                    	myref.child("Name").setValue(name);
	    			                    	myref.child("Fat").setValue(fat);
	    			                    	myref.child("Calories").setValue(calories);
	    			                    	myref.child("Sugar").setValue(fat);
	    			                    	myref.child("Points").setValue(2);
	    			             
	    			                    	addScore(name, 2);
	    			                    	
	    			                    	titleText.setText("Item Submitted");
	    			                    	//titleText.setTextSize(22.0f);
	    		    		                titleText.setGravity(Gravity.CENTER_HORIZONTAL);

	    			                    	editText1.setVisibility(View.GONE);
	    		    		                editText2.setVisibility(View.GONE);
	    		    		                editText3.setVisibility(View.GONE);
	    		    		                editText4.setVisibility(View.GONE);
	    		    		                submitButton.setVisibility(View.GONE);
	    		    		                	    		                    	
	    		                    	}
	    	
	    		                    }
	    		                });
	    		                
	            	        }
	            	    }
	            	    public void onCancelled(FirebaseError arg0) {
	            	    	
	            	    }
	            	});


	
	            } else if (resultCode == getActivity().RESULT_CANCELED) {
	                // Handle cancel
	                Log.d("Result", "Cancel");
	            }
	        }
	 }
	 
	 
	 public void addScore(final String item, final int points){
		System.out.println(item);
		System.out.println(points);
		
    	Global global = Global.getInstance();
    	String username = global.getData();
		 
		 //final Firebase userref = new Firebase("https://glowing-torch-7379.firebaseio.com/User/facebook:10154861802315106");
    	System.out.println("https://glowing-torch-7379.firebaseio.com/User/"+username);
		 final Firebase userref = new Firebase("https://glowing-torch-7379.firebaseio.com/User/"+username);
		 userref.child("item").child(item).setValue(points);
		 userref.child("score").addListenerForSingleValueEvent(new ValueEventListener() {
			  @Override
			  public void onDataChange(DataSnapshot snapshot) {
				  int totalScore = snapshot.getValue(Integer.class);
				  System.out.println("totalScore: "+totalScore);
				  totalScore += points;
				  userref.child("score").setValue(totalScore);
				  System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
			  }
			  @Override public void onCancelled(FirebaseError error) { }
			});
		 
	 }

}
