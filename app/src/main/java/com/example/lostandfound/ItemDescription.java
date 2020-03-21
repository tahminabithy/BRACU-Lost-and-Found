package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemDescription extends AppCompatActivity {
    TextView itemName,emailAdd,contactNo,dateNo;
    ImageView imageView;
    String name,url,number,email,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        url = intent.getStringExtra("url");
        number = intent.getStringExtra("number");
        email = intent.getStringExtra("email");
        date = intent.getStringExtra("date");

        itemName = findViewById(R.id.item_name_ind);
        emailAdd = findViewById(R.id.email_ind);
        contactNo = findViewById(R.id.number_ind);
        dateNo = findViewById(R.id.date_ind);
        imageView = findViewById(R.id.image_view_upload);

        itemName.setText(name);
        emailAdd.setText(email);
        contactNo.setText(number);
        dateNo.setText(date);
        Picasso.get()
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageView);

    }

    public void openDialer(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }

    public void openGmail(View view){
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={email};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"I think this lost item is mine!");
        intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
        intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }
}
