package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Found extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private List<Upload> mUploadsFull;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        searchText = findViewById(R.id.search_text);
        mUploadsFull = new ArrayList<>();

        mStorage = FirebaseStorage.getInstance();

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(Found.this,mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(Found.this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                String date = "",email="",url="",name="",number="",key = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    /*Object op = postSnapshot.getValue();
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);

                    mAdapter = new ImageAdapter(Found.this,mUploads);
                    mRecyclerView.setAdapter(mAdapter);*/
                    key = postSnapshot.getKey();

                    Map<String, Object> map = (Map<String, Object>) postSnapshot.getValue();


                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        //System.out.println(entry.getKey() + "/" + entry.getValue());

                        Object obj = entry.getValue();
                        //System.out.println(entry.getKey());
                        Gson gson = new Gson();
                        String json = gson.toJson(obj);

                        if (obj != null) {
                            try {
                                JSONObject myObjToConvert = new JSONObject(json);
                                name = myObjToConvert.getString("name");
                                email = myObjToConvert.getString("email");
                                date = myObjToConvert.getString("date");
                                url = myObjToConvert.getString("imageUrl");
                                number = myObjToConvert.getString("number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Upload upload = new Upload(name,url,number,email,date);
                        upload.setKey(entry.getKey());
                        upload.setId(key);
                        mUploads.add(upload);

                        //mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                }
                //mUploadsFull.clear();
                mUploadsFull.addAll(mUploads);
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Found.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());

            }
        });
    }

    private void filter(String text){
        ArrayList<Upload>  filteredList = new ArrayList<>();
        for (Upload item : mUploadsFull){
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mUploads.clear();
        mUploads.addAll(filteredList);
        mAdapter.filterList(filteredList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Upload selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        DatabaseReference dtRef = FirebaseDatabase.getInstance().getReference("uploads/"+selectedItem.getId()+"/"+selectedKey);
        dtRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Upload up = dataSnapshot.getValue(Upload.class);
                //String name, String imageUrl, String contcactNumber, String email, String date
                Intent intent = new Intent(Found.this,ItemDescription.class);
                intent.putExtra("name",up.getName());
                intent.putExtra("url",up.getImageUrl());
                intent.putExtra("number",up.getNumber());
                intent.putExtra("email",up.getEmail());
                intent.putExtra("date",up.getDate());
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*Intent intent = new Intent(Found.this,ItemDescription.class);
        intent.putExtra("name",selectedItem.getName());
        intent.putExtra("url",selectedItem.getImageUrl());
        intent.putExtra("number",selectedItem.getNumber());
        intent.putExtra("email",selectedItem.getEmail());
        intent.putExtra("date",selectedItem.getDate());
        startActivity(intent);*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
