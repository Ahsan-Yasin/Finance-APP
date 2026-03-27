package com.example.financetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Goal implements Parcelable {
    private String title;
    private String targetDate;
    private double currentAmount;
    private double targetAmount;
    private String iconType;

    public Goal(String title, String targetDate, double currentAmount, double targetAmount, String iconType) {
        this.title = title;
        this.targetDate = targetDate;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
        this.iconType = iconType;
    }

    protected Goal(Parcel in) {
        title = in.readString();
        targetDate = in.readString();
        currentAmount = in.readDouble();
        targetAmount = in.readDouble();
        iconType = in.readString();
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

    public String getTitle() { return title; }
    public String getTargetDate() { return targetDate; }
    public double getCurrentAmount() { return currentAmount; }
    public double getTargetAmount() { return targetAmount; }
    public String getIconType() { return iconType; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(targetDate);
        dest.writeDouble(currentAmount);
        dest.writeDouble(targetAmount);
        dest.writeString(iconType);
    }
}
