package com.example.lostandfound;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mContactNumber;
    private String mEmail;
    private String mDate;
    private String mKey;
    private String mId;
    public Upload(){

    }

    public Upload(String name, String imageUrl, String contcactNumber, String email, String date){
        if (name.trim().equals("")){
            name = "No name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mContactNumber = contcactNumber;
        mEmail = email;
        mDate = date;
    }

    public String getName(){
        return mName;
    }
    public void setName(String name){
        mName = name;
    }

    public String getImageUrl(){
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }

    public String getNumber(){
        return  mContactNumber;
    }
    public void setNumber(String number){
        mContactNumber = number;
    }

    public String getEmail(){
        return  mEmail;
    }
    public void setEmail(String email){
        mEmail = email;
    }

    public String getDate(){
        return  mDate;
    }
    public void setDate(String date){
        mDate = date;
    }

    @Exclude
    public String getKey(){
        return mKey;
    }
    @Exclude
    public void setKey(String key){
        mKey = key;
    }

    @Exclude
    public String getId(){
        return mId;
    }
    @Exclude
    public void setId(String id){
        mId = id;
    }
}
