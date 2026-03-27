package com.example.financetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Subscription implements Parcelable {
    private String id;
    private String name;
    private double amount;
    private String dueDate;
    private String billingCycle;
    private String icon;

    public Subscription(String id, String name, double amount, String dueDate, String billingCycle, String icon) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.dueDate = dueDate;
        this.billingCycle = billingCycle;
        this.icon = icon;
    }

    protected Subscription(Parcel in) {
        id = in.readString();
        name = in.readString();
        amount = in.readDouble();
        dueDate = in.readString();
        billingCycle = in.readString();
        icon = in.readString();
    }

    public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
        @Override
        public Subscription createFromParcel(Parcel in) {
            return new Subscription(in);
        }

        @Override
        public Subscription[] newArray(int size) {
            return new Subscription[size];
        }
    };

    public String getId() { return id; }
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public String getDueDate() { return dueDate; }
    public String getBillingCycle() { return billingCycle; }
    public String getIcon() { return icon; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(amount);
        dest.writeString(dueDate);
        dest.writeString(billingCycle);
        dest.writeString(icon);
    }
}
