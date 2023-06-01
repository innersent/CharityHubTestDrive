package com.example.charityhubmainpagefragmenttest2;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Announcement implements Serializable {
    public String profile_avatar, nickName, announcement_time, main_announcement, announcement_title, timeStamp, userNumberTelephone;
    public int announcement_votes, announcement_comments_count;
    public boolean isVoted;

    public Announcement(){}

    public Announcement(String profile_avatar, String nickName, String announcement_time, String main_announcement, String announcement_title, int announcement_votes, int announcement_comments_count, boolean isVoted) {
        this.profile_avatar = profile_avatar;
        this.nickName = nickName;
        this.announcement_time = announcement_time;
        this.main_announcement = main_announcement;
        this.announcement_title = announcement_title;
        this.announcement_votes = announcement_votes;
        this.announcement_comments_count = announcement_comments_count;
        this.isVoted = isVoted;
    }

    public Announcement(String main_announcement, String announcement_title, String userNumberTelephone){
        this.main_announcement = main_announcement;
        this.announcement_title = announcement_title;
        this.userNumberTelephone = userNumberTelephone;
    }

    public String getProfile_avatar() {
        return profile_avatar;
    }

    public void setProfile_avatar(String profile_avatar) {
        this.profile_avatar = profile_avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAnnouncement_time() {
        return announcement_time;
    }

    public void setAnnouncement_time(String announcement_time) {
        this.announcement_time = announcement_time;
    }

    public String getMain_announcement() {
        return main_announcement;
    }

    public void setMain_announcement(String main_announcement) {
        this.main_announcement = main_announcement;
    }

    public String getAnnouncement_title() {
        return announcement_title;
    }

    public void setAnnouncement_title(String announcement_title) {
        this.announcement_title = announcement_title;
    }

    public int getAnnouncement_votes() {
        return announcement_votes;
    }

    public void setAnnouncement_votes(int announcement_votes) {
        this.announcement_votes = announcement_votes;
    }

    public int getAnnouncement_comments_count() {
        return announcement_comments_count;
    }

    public void setAnnouncement_comments_count(int announcement_comments_count) {
        this.announcement_comments_count = announcement_comments_count;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public String getTimeStamp(){
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }

    public String serializeToString() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    public static Announcement deserializeFromString(String serializedString) throws IOException, ClassNotFoundException {
        byte[] bytes = Base64.decode(serializedString, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Announcement announcement = (Announcement) objectInputStream.readObject();
        objectInputStream.close();
        return announcement;
    }
}
