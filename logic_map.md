# Finance Tracker - Logic Map

| Requirement ID | Class Name | Function Name | Description |
| :--- | :--- | :--- | :--- |
| **F1: Modular UI** | `MainActivity` | `onCreate` | Serves as a thin container; initializes the entry `DashboardFragment` into `fragment_container`. |
| **F2: Activity Communication** | `LoginActivity` | `onClickListener` | Uses `Intent.putExtra("USER_EMAIL", email)` to pass data to `MainActivity` without using static variables. |
| **F3: Fragment Communication** | `DashboardFragment` | `onTransactionClick` | Creates a new `Bundle`, puts a `Parcelable` Transaction object, and passes it to `TransactionDetailFragment`. |
| **F4: Search Logic** | `TransactionAdapter` | `getFilter` | Implements `Filterable` interface to provide real-time filtering of the transaction list based on title or category. |
| **F5: Data Model Implementation** | `Transaction` | `writeToParcel` | Implements `Parcelable` to enable complex data exchange between Fragments through Bundles, maintaining high performance and type safety. |
