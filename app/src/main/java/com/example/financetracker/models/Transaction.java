package com.example.financetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {
    private String id;
    private String title;
    private String category;
    private String account;
    private double amount;
    private String date;
    private String icon;
    private boolean isIncome;

    public Transaction(String id, String title, String category, String account, double amount, String date, String icon, boolean isIncome) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.icon = icon;
        this.isIncome = isIncome;
    }

    protected Transaction(Parcel in) {
        id = in.readString();
        title = in.readString();
        category = in.readString();
        account = in.readString();
        amount = in.readDouble();
        date = in.readString();
        icon = in.readString();
        isIncome = in.readByte() != 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getAccount() { return account; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getIcon() { return icon; }
    public boolean isIncome() { return isIncome; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(account);
        dest.writeDouble(amount);
        dest.writeString(date);
        dest.writeString(icon);
        dest.writeByte((byte) (isIncome ? 1 : 0));
    }
}
