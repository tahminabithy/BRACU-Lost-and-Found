package com.example.lostandfound;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mContactNumber;
    private String mEmail;
    private String mDate;

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
    public void setContactNumber(String number){
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

}
