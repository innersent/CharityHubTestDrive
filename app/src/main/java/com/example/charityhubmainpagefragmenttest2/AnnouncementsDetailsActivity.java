package com.example.charityhubmainpagefragmenttest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class AnnouncementsDetailsActivity extends AppCompatActivity {
    private ImageView mainAnnouncement;
    private TextView announcementTitle;
    private ImageView participateButton;
    private TextView participateCount;
    private TextView userTelephoneNumber;
    private DatabaseReference mDatabase;
    private ArrayList<String> keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_details);
        init();
        getAnnouncement();
    }

    public void init(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Announcements");
        mainAnnouncement = findViewById(R.id.mainDetails);
        announcementTitle = findViewById(R.id.announcement_title_details);
        participateButton = findViewById(R.id.participate_button_details);
        participateCount = findViewById(R.id.participate_count_details);
        userTelephoneNumber = findViewById(R.id.user_telephone_number_details);
    }

    public void getAnnouncement(){
        Intent intent = getIntent();
//        Announcement selectedAnnouncement = intent.getParcelableExtra("selectedAnnouncement");
        Picasso.get().load(intent.getStringExtra("selectedAnnouncement")).into(mainAnnouncement);
        String title = intent.getStringExtra("selectedAnnouncementTitle");
        announcementTitle.setText(title);
        int count = intent.getIntExtra("selectedAnnouncementParticipateCount", 0);
        participateCount.setText(Integer.toString(count));
        String telephoneNumber = intent.getStringExtra("telephoneNumberText");
        userTelephoneNumber.setText(telephoneNumber);
    }

    public void getUrlAnnouncement(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    keys.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public void participateClick(View view){
//        mDatabase = FirebaseDatabase.getInstance().getReference().child();
    }

    public void backToHome(View view){
        startActivity(new Intent(AnnouncementsDetailsActivity.this, MainActivity.class));
    }


}