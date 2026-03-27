# Finance Tracker - Android App

A comprehensive Finance Tracker application built with a modular architecture using Fragments and modern Android development practices.

## Features Implemented

- **Modular UI Architecture**: 
    - `MainActivity` serves as a thin container using `FragmentContainerView`.
    - All screens are implemented as `Fragments` for better lifecycle management and modularity.
- **User Authentication**:
    - `LoginActivity` handles user entry.
    - Data passing from `LoginActivity` to `MainActivity` via **Intent Extras**.
- **Dashboard & Transaction Management**:
    - Real-time overview of balance, income, and expenses.
    - `RecyclerView` with a custom adapter for displaying transaction history.
    - **Real-time Search**: Implemented `Filterable` interface in `TransactionAdapter` for instant transaction lookup.
- **Subscription & Debt Tracking**:
    - Dedicated screen for managing active subscriptions and monitoring debts/IOUs.
- **Safe Data Communication**:
    - **Zero Static Variables**: Strictly avoided static fields for state sharing.
    - **Parcelable Objects**: Used `Parcelable` models (`Transaction`, `Subscription`) to pass complex data between Fragments using **Bundles**.
- **Modern Package Organization**:
    - `.activities`: App entry points and containers.
    - `.fragments`: UI logic and screen implementations.
    - `.adapters`: RecyclerView logic.
    - `.models`: Data structures and Parcelable implementations.

## Technical Details

- **Language**: Java / Kotlin
- **Components**: Fragments, RecyclerView, Material Design 3, ConstraintLayout, Parcelable.
- **Communication**: Intent Extras (Activity-to-Activity), Bundles (Fragment-to-Fragment).
