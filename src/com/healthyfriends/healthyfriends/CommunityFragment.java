package com.healthyfriends.healthyfriends;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.healthyfriends.items.CustomCommunityAdapter;
import com.healthyfriends.items.CommunityItem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommunityFragment extends Fragment {
	
	ArrayList<CommunityItem> communityArray = new ArrayList<CommunityItem>();
	CustomCommunityAdapter adapter;
	String[] Entries;
	
	public CommunityFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_community, container, false);
         
        System.out.println("----- HomeFragment -----");
        
        
        /*
        for(int i = 0; i < numEntries; i++)
        {
        	
        	Entries[i][0] = "Calories";
        	Entries[i][1] = "1000";
        	String itemName = Entries[i][0];
        	communityArray.add(new CommunityItem(itemName, Entries[i]));
        	
        }
        */
        Global g = Global.getInstance();
        
        System.out.println("GLOBAL :"+g.getData());
        
        Firebase ref = new Firebase("https://glowing-torch-7379.firebaseio.com/User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
            	
            	communityArray = new ArrayList<CommunityItem>();
            	Map<String, Object> value = (Map<String, Object>)snapshot.getValue();
            	System.out.println("Map: "+value.toString());
            	//String score = value.get("score").toString();
            	//String user = value.get();
            	
            	//System.out.println("score :" + score);
            	//System.out.println("user :" + user);

            	String text = "Already Exists";
                Iterable<DataSnapshot> iter = snapshot.getChildren();
            	String arr[] = {"Calories", "Fat(grams)", "Name", "Points", "Sugar(grams)"};
            	int i = 0;
            	String item = "";
            	int points = 0;
            	while(iter.iterator().hasNext()){
            		Map temp = (Map<String, Object>)iter.iterator().next().getValue();
            		System.out.println(temp.toString());
            		String score = temp.get("score").toString();
            		String name = temp.get("username").toString();
            		System.out.println("The name: "+name);
            		System.out.println("The score: "+score);
            		

                    Entries = new String[2];
            		Entries[0] = name;
            		Entries[1] = score;
                	communityArray.add(new CommunityItem(name, Entries));
                	System.out.println("communityArray: "+ communityArray.get(i).getDetails()[0]);

            		//text = text + "\n" + arr[i] + " : " + temp.toString();
            		//if(i == 2) item = temp.toString();
            		//if(i == 3) points = Integer.parseInt(temp.toString());
            		i++;
            	}

                System.out.println("Prepare to display listview");
            	
            	// add data in custom adapter
        		adapter = new CustomCommunityAdapter(getActivity(), R.layout.community_list_row, communityArray);
        	    ListView dataList = (ListView) rootView.findViewById(R.id.listviewid);
            	dataList.setAdapter(adapter);

            	
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        
        
        return rootView;
    }
}
