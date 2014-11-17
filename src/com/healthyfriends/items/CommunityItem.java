package com.healthyfriends.items;

public class CommunityItem implements Comparable<CommunityItem>{

    public String title;
    public String[] details;
    
    public int sortType;
   
    public CommunityItem(String title, String[] details) {
           super();
           this.title = title;
           this.details = details;
    }
    public String getTitle() {
           return title;
    }
    public void setTitle(String title) {
           this.title = title;
    }
    public String[] getDetails() {
           return details;
    }
    public void setDetails(String[] details) {
           this.details = details;
    }
    
    public int getType() {
    	return sortType;
    }
    public void setType(int sortType) {
    	this.sortType = sortType;
    }
  
	@Override
	public int compareTo(CommunityItem c) {
		// 0 = title
 	    // 1 = quantity
		
		
 	    // 2 = checkout individual
 	    // 3 = member Id
 	    // 4 = checkout date
		// 5 = due date
    	int lastCmp;
    	switch(sortType) {
		case 0:
			lastCmp = details[0].compareTo(c.details[0]);
			return(lastCmp);
		case 1:
			lastCmp = details[1].compareTo(c.details[1]);
			return(lastCmp);
  	   	case 2:
  	   		lastCmp = details[2].compareTo(c.details[2]);
  	   		return(lastCmp);
  	   	case 3:
  	   		lastCmp = details[3].compareTo(c.details[3]);
  	   		return(lastCmp);
  	  	case 4:
  	  		lastCmp = details[4].compareTo(c.details[4]);
  	  		return(lastCmp);
  	  	case 5:
  	  		lastCmp = details[5].compareTo(c.details[5]);
  	  		return(lastCmp);
		}
		return 0;
	}

}