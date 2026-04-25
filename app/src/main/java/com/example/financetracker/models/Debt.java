package com.example.financetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Debt implements Parcelable {
    private String id;
    private String name;
    private double amount;
    private String description;
    private String type; // "OWE" or "OWED"

    public Debt(String id, String name, double amount, String description, String type) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    protected Debt(Parcel in) {
        id = in.readString();
        name = in.readString();
        amount = in.readDouble();
        description = in.readString();
        type = in.readString();
    }

    public static final Creator<Debt> CREATOR = new Creator<Debt>() {
        @Override
        public Debt createFromParcel(Parcel in) {
            return new Debt(in);
        }

        @Override
        public Debt[] newArray(int size) {
            return new Debt[size];
        }
    };

    public String getId() { return id; }
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getType() { return type; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(amount);
        dest.writeString(description);
        dest.writeString(type);
    }
}
