package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    TextView nameText;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        nameText = findViewById(R.id.name);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            //Uri personPhoto = acct.getPhotoUrl();
            //System.out.println(personPhoto);
            //Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);

            nameText.setText(personName);
        }
    }

    public void goToLost(View view){
        Intent intent = new Intent(HomeScreen.this,Lost.class);
        startActivity(intent);
    }

    public void goToFound(View view){
        Intent intent = new Intent(HomeScreen.this,Found.class);
        startActivity(intent);
    }

    public void SignOutButton(View view) {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Signed Out Succesfully",Toast.LENGTH_SHORT).show();
                        //finish();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeScreen.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
