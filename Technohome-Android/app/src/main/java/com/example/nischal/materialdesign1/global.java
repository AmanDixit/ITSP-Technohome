package com.example.nischal.materialdesign1;

/**
 * Created by UTKARSH on 24-06-2015.
 */


public class global {

        public static global instance;

        // Global variable
    public static String value;
        public String data;

        // Restrict the constructor from being instantiated
        //public global(){}

        public void setData(String d){
            this.data=d;
        }
    public void copy(String d){
        value=d;
        String a=value;
        data=a;
    }
        public String getData(){
            return this.data;
        }

        public static synchronized global getInstance(){
            if(instance==null){
                instance=new global();
            }
            return instance;
        }
    }


