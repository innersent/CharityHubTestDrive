package com.example.charityhubmainpagefragmenttest2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        Announcement announcment = new Announcement("https://mail.kz/images/912f0e1537799e5a9e6b697c11424631-slider.jpg", "девятка", "", "https://www.meme-arsenal.com/memes/df2292c6c154dbedf5b22ab0fc54c755.jpg", "Просьба анон пж", 0, 0, false);
//        mDatabase.push().setValue(announcment);
    }

    public void init(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Announcements");
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
    }

    public void openHome(View view){
        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.home_page);
    }
    public void openChat(View view){
        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.chat_page);
    }
    public void openCreate(View view){
        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.create_page);
    }
    public void openSearch(View view){
        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.search_page);
    }
    public void openProfile(View view){
        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.profile_page);
    }

    public void openMenu(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }
}