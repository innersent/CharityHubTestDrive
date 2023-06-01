package com.example.charityhubmainpagefragmenttest2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.fragmentmainpage.Announcment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {
    private int savedScrollPosition = RecyclerView.NO_POSITION;


    DatabaseReference mDataBase;
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Announcement> announcements;
    ArrayList<String> keys;
    RecyclerViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        loadProduct();
        getUrlAnnouncement();

        Announcement announcement = new Announcement("https://drasler.ru/wp-content/uploads/2019/05/%D0%A7%D0%B5%D0%BB%D0%BE%D0%B2%D0%B5%D0%BA-%D0%B1%D0%B5%D0%B7-%D0%BB%D0%B8%D1%86%D0%B0-%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B0-%D0%B8-%D1%84%D0%BE%D1%82%D0%BE-027.jpg", "anon777", "", "https://spectrum.ieee.org/media-library/less-than-p-greater-than-the-ibm-pc-introduced-in-august-1981-helped-reassure-corporate-customers-that-personal-computing-was-serious-less-than-p-greater-than.jpg?id=27044507", "PC anon pls", 0, 0, false);
//        mDataBase.push().setValue(announcement);
        return view;
    }

    public void init(View view) {
        mDataBase = FirebaseDatabase.getInstance().getReference("Announcements");
        rv = view.findViewById(R.id.rvHome);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        announcements = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new RecyclerViewAdapter(announcements, getContext());

//        linearLayoutManager.setReverseLayout(true);
    }

    private String getCurrentTimestamp() {
        // Получите текущую дату и время
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }


    public void loadProduct() {
        rv.setAdapter(adapter);
    }

    public void getUrlAnnouncement() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Announcement> newAnnouncements = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Announcement ancmnt = ds.getValue(Announcement.class);
                    newAnnouncements.add(ancmnt);
//                    ancmnt.setTimeStamp(getCurrentTimestamp());
                    String announcementKey = ds.getKey();
//                    mDataBase.child(announcementKey).child("timeStamp").setValue(getCurrentTimestamp());
//                    announcements.add(ancmnt);
                    keys.add(ds.getKey());
                }

                announcements.clear();
                announcements.addAll(newAnnouncements);
                adapter.notifyDataSetChanged();

                if(savedScrollPosition != RecyclerView.NO_POSITION){
                    linearLayoutManager.scrollToPosition(savedScrollPosition);
                    savedScrollPosition = RecyclerView.NO_POSITION;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(valueEventListener);
    }

//    public String getTimeAgo(String timestamp) {
//        if (timestamp == null) {
//            return "";
//        }
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        format.setTimeZone(TimeZone.getTimeZone("RU"));
//
//        try {
//            Date pastDate = format.parse(timestamp);
//            Date currentDate = new Date();
//
//            long timeDiff = currentDate.getTime() - pastDate.getTime();
//
//            long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff);
//            long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff);
//            long hours = TimeUnit.MILLISECONDS.toHours(timeDiff);
//            long days = TimeUnit.MILLISECONDS.toDays(timeDiff);
//
//            if (seconds < 60) {
//                return seconds + " секунд назад";
//            } else if (minutes < 60) {
//                return minutes + " минут назад";
//            } else if (hours < 24) {
//                return hours + " часов назад";
//            } else if (days < 7) {
//                return days + " дней назад";
//            } else {
//                SimpleDateFormat formatOutput = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
//                return formatOutput.format(pastDate) + " г.";
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }



    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AnnouncementViewHolder> {
        private ArrayList<Announcement> adapterAnnouncements;

        private Context context;

        public RecyclerViewAdapter(ArrayList<Announcement> adapterAnnouncements, Context context) {
            this.adapterAnnouncements = adapterAnnouncements;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.announc_item, parent, false);
            AnnouncementViewHolder avh = new AnnouncementViewHolder(v);
            return avh;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.AnnouncementViewHolder holder, int position) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

                    Announcement selectedAnnouncement = adapterAnnouncements.get(position);
                    Announcement selectedAnnouncementTitle = adapterAnnouncements.get(position);
                    Announcement selectedAnnouncementParticipateCount = adapterAnnouncements.get(position);

                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, AnnouncementsDetailsActivity.class);

                    intent.putExtra("selectedAnnouncement", selectedAnnouncement.getMain_announcement());
                    intent.putExtra("selectedAnnouncementTitle", selectedAnnouncementTitle.getAnnouncement_title());
                    intent.putExtra("selectedAnnouncementParticipateCount", selectedAnnouncementParticipateCount.getAnnouncement_votes());
//                    intent.putExtra()
                    context.startActivity(intent);
                }
            });

//            String timeStamp =  String.valueOf(adapterAnnouncements.get(position).getTimeStamp());
//            String timeAgo = getTimeAgo(timeStamp);

//            holder.announcement_time.setText(timeAgo);
            holder.nickName.setText(adapterAnnouncements.get(position).getNickName());
            holder.announcement_title.setText(adapterAnnouncements.get(position).getAnnouncement_title());
            holder.announcement_votes.setText(Integer.toString(adapterAnnouncements.get(position).getAnnouncement_votes()));
            holder.announcement_comments_count.setText(Integer.toString(adapterAnnouncements.get(position).getAnnouncement_comments_count()));
            Picasso.get().load(adapterAnnouncements.get(position).getProfile_avatar()).into(holder.profile_avatar);
            Picasso.get().load(adapterAnnouncements.get(position).getMain_announcement()).into(holder.main_announcement);

            if (adapterAnnouncements.get(position).isVoted == true) {
                holder.participate_button.setImageResource(R.drawable.man_icon_blue);
                holder.announcement_votes.setTextColor(Color.parseColor("#0096D7"));
            } else if(adapterAnnouncements.get(position).isVoted == false){
                holder.participate_button.setImageResource(R.drawable.man_icon);
                holder.announcement_votes.setTextColor(Color.parseColor("#000000"));
            }
        }

        @Override
        public int getItemCount() {
            return adapterAnnouncements.size();
        }

        class AnnouncementViewHolder extends RecyclerView.ViewHolder {
            TextView nickName, announcement_time, announcement_title, announcement_votes, announcement_comments_count;
            ImageView profile_avatar, main_announcement, participate_button;

            public AnnouncementViewHolder(@NonNull View itemView) {
                super(itemView);

                nickName = itemView.findViewById(R.id.nickName);
                announcement_time = itemView.findViewById(R.id.announcement_time);
                announcement_title = itemView.findViewById(R.id.announcement_title);
                announcement_votes = itemView.findViewById(R.id.announcement_votes);
                announcement_comments_count = itemView.findViewById(R.id.announcement_comments_count);
                profile_avatar = itemView.findViewById(R.id.profile_avatar);
                main_announcement = itemView.findViewById(R.id.main_announcement);
                participate_button = itemView.findViewById(R.id.imageView6);


                participate_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int positionIndex = getAdapterPosition();
                        mDataBase = FirebaseDatabase.getInstance().getReference("Announcements").child(keys.get(positionIndex));
                        Announcement announcement = adapterAnnouncements.get(positionIndex);
                        if (announcement.isVoted == false) {
                            mDataBase.child("announcement_votes").setValue(announcement.announcement_votes + 1);
                            mDataBase.child("isVoted").setValue(true);
                            announcement.isVoted = true;
                            participate_button.setImageResource(R.drawable.man_icon_blue);
                            announcement_votes.setTextColor(Color.parseColor("#0096D7"));
                        } else if (announcement.isVoted == true) {
                            mDataBase.child("announcement_votes").setValue(announcement.announcement_votes - 1);
                            mDataBase.child("isVoted").setValue(false);
                            announcement.isVoted = false;
                            participate_button.setImageResource(R.drawable.man_icon);
                            announcement_votes.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });
            }
        }
    }
}