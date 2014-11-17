package com.healthyfriends.healthyfriends;

import java.util.ArrayList;

import com.healthyfriends.items.CustomProfileAdapter;
import com.healthyfriends.items.ProfileItem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HomeFragment extends Fragment {
	
	ArrayList<ProfileItem> profileArray = new ArrayList<ProfileItem>();
	CustomProfileAdapter adapter;
	String[][] Entries;
	
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        System.out.println("----- HomeFragment -----");
        
        int numEntries = 5;
        Entries = new String[numEntries][2];
        
        /*
        for(int i = 0; i < numEntries; i++)
        {
        	
        	Entries[i][0] = "Calories";
        	Entries[i][1] = "1000";
        	String itemName = Entries[i][0];
        	profileArray.add(new ProfileItem(itemName, Entries[i]));
        	
        }
        */
        Global g = Global.getInstance();
        
        System.out.println("GLOBAL :"+g.getData());
        
        String itemName;
        Entries[0][0] = "Scan Points";
    	Entries[0][1] = "1000";
    	itemName = Entries[0][0];
    	profileArray.add(new ProfileItem(itemName, Entries[0]));
        Entries[1][0] = "Home";
    	Entries[1][1] = "400";
    	itemName = Entries[1][0];
    	profileArray.add(new ProfileItem(itemName, Entries[1]));
        Entries[2][0] = "Calories";
    	Entries[2][1] = "250";
    	itemName = Entries[2][0];
    	profileArray.add(new ProfileItem(itemName, Entries[2]));
        Entries[3][0] = "Total Sugar";
    	Entries[3][1] = "100";
    	itemName = Entries[3][0];
    	profileArray.add(new ProfileItem(itemName, Entries[3]));
        Entries[4][0] = "Total Fat";
    	Entries[4][1] = "500";
    	itemName = Entries[4][0];
    	profileArray.add(new ProfileItem(itemName, Entries[4]));
        
    	
    	// add data in custom adapter
			adapter = new CustomProfileAdapter(getActivity(), R.layout.profile_list_row, profileArray);
	        ListView dataList = (ListView) rootView.findViewById(R.id.listviewid);
	        dataList.setAdapter(adapter);
        
         
        return rootView;
    }
}
