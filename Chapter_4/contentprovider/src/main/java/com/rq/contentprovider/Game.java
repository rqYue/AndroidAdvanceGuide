package com.rq.contentprovider;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Game implements Parcelable {

    public String gameName;

    public String gameDescribe;

    public Game(String gameName, String gameDescribe) {
        this.gameName = gameName;
        this.gameDescribe = gameDescribe;
    }

    protected Game(Parcel in) {
        gameName = in.readString();
        gameDescribe = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(gameName);
        parcel.writeString(gameDescribe);
    }
}
