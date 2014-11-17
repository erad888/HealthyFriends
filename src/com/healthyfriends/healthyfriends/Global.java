package com.healthyfriends.healthyfriends;

public class Global{
	   private static Global instance;
	 
	   // Global variable
	   private String data;
	 
	   // Restrict the constructor from being instantiated
	   private Global(){}
	 
	   public void setData(String d){
	     this.data=d;
	   }
	   public String getData(){
	     return this.data;
	   }
	 
	   public static synchronized Global getInstance(){
	     if(instance==null){
	       instance=new Global();
	     }
	     return instance;
	   }
	}