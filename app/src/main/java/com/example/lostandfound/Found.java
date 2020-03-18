package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Found extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    /*Object op = postSnapshot.getValue();
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);

                    mAdapter = new ImageAdapter(Found.this,mUploads);
                    mRecyclerView.setAdapter(mAdapter);*/

                    Map<String, Object> map = (Map<String, Object>) postSnapshot.getValue();
                    String date = "",email="",url="",name="",number="";

                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        //System.out.println(entry.getKey() + "/" + entry.getValue());
                        Object obj = entry.getValue();
                        Gson gson = new Gson();
                        String json = gson.toJson(obj);

                        if (obj != null) {
                            try {
                                JSONObject myObjToConvert = new JSONObject(json);
                                name = myObjToConvert.getString("name");
                                email = myObjToConvert.getString("email");
                                date = myObjToConvert.getString("date");
                                url = myObjToConvert.getString("imageUrl");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Upload upload = new Upload(name,url,number,email,date);
                        mUploads.add(upload);

                        mAdapter = new ImageAdapter(Found.this,mUploads);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Found.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
