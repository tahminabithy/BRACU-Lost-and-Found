package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ItemDescription extends AppCompatActivity {
    TextView itemName,emailAdd,contactNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        /*
                intent.putExtra("name",up.getName());
                intent.putExtra("url",up.getImageUrl());
                intent.putExtra("number",up.getNumber());
                intent.putExtra("email",up.getEmail());
                intent.putExtra("date",up.getDate());
         */
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String url = intent.getStringExtra("url");
        String number = intent.getStringExtra("number");
        String email = intent.getStringExtra("email");
        String date = intent.getStringExtra("date");

        itemName = findViewById(R.id.item_name_ind);
        emailAdd = findViewById(R.id.email_ind);
        contactNo = findViewById(R.id.number_ind);

        itemName.setText(name);
        emailAdd.setText(email);
        contactNo.setText(number);

    }
}
