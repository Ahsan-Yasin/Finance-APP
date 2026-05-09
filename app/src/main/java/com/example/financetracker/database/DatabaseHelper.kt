package com.example.financetracker.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.financetracker.models.TransactionEntity
import com.example.financetracker.models.Subscription
import com.example.financetracker.models.Debt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseHelper(context: Context) : 
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "FinanceTrackerDB.db"
        private const val DATABASE_VERSION = 3 

        const val TABLE_CATEGORIES = "categories"
        const val COL_CAT_ID = "ca_id"
        const val COL_CAT_NAME = "ca_name"

        const val TABLE_TRANSACTIONS = "transactions"
        const val COL_TRANS_ID = "tr_id"
        const val COL_TRANS_TITLE = "tr_title"
        const val COL_TRANS_AMOUNT = "tr_amount"
        const val COL_TRANS_CAT_ID = "tr_category_id"

        const val TABLE_SUBSCRIPTIONS = "subscriptions"
        const val COL_SUB_ID = "sub_id"
        const val COL_SUB_NAME = "sub_name"
        const val COL_SUB_AMOUNT = "sub_amount"
        const val COL_SUB_DUE_DATE = "sub_due_date"
        const val COL_SUB_CYCLE = "sub_cycle"
        const val COL_SUB_ICON = "sub_icon"

        const val TABLE_DEBTS = "debts"
        const val COL_DEBT_ID = "debt_id"
        const val COL_DEBT_NAME = "debt_name"
        const val COL_DEBT_AMOUNT = "debt_amount"
        const val COL_DEBT_DESC = "debt_desc"
        const val COL_DEBT_TYPE = "debt_type" 
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_CATEGORIES ($COL_CAT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_CAT_NAME TEXT NOT NULL)")
        db.execSQL("CREATE TABLE $TABLE_TRANSACTIONS ($COL_TRANS_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TRANS_TITLE TEXT NOT NULL, $COL_TRANS_AMOUNT REAL NOT NULL, $COL_TRANS_CAT_ID INTEGER, FOREIGN KEY($COL_TRANS_CAT_ID) REFERENCES $TABLE_CATEGORIES($COL_CAT_ID) ON DELETE SET NULL)")
        db.execSQL("CREATE TABLE $TABLE_SUBSCRIPTIONS ($COL_SUB_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_SUB_NAME TEXT NOT NULL, $COL_SUB_AMOUNT REAL NOT NULL, $COL_SUB_DUE_DATE TEXT NOT NULL, $COL_SUB_CYCLE TEXT NOT NULL, $COL_SUB_ICON TEXT NOT NULL)")
        db.execSQL("CREATE TABLE $TABLE_DEBTS ($COL_DEBT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_DEBT_NAME TEXT NOT NULL, $COL_DEBT_AMOUNT REAL NOT NULL, $COL_DEBT_DESC TEXT, $COL_DEBT_TYPE TEXT NOT NULL)")
        
        db.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME) VALUES ('General')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUBSCRIPTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEBTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        onCreate(db)
    }

    fun resetDatabase() {
        val db = writableDatabase
        onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION)
    }

    // --- SUBSCRIPTION CRUD ---
    suspend fun insertSubscription(sub: Subscription): Long = withContext(Dispatchers.IO) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_SUB_NAME, sub.getName())
            put(COL_SUB_AMOUNT, sub.getAmount())
            put(COL_SUB_DUE_DATE, sub.getDueDate())
            put(COL_SUB_CYCLE, sub.getBillingCycle())
            put(COL_SUB_ICON, sub.getIcon())
        }
        db.insert(TABLE_SUBSCRIPTIONS, null, values)
    }

    suspend fun getAllSubscriptions(): List<Subscription> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val cursor = db.query(TABLE_SUBSCRIPTIONS, null, null, null, null, null, "$COL_SUB_ID DESC")
        val list = mutableListOf<Subscription>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SUB_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_SUB_NAME))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_SUB_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COL_SUB_DUE_DATE))
                val cycle = cursor.getString(cursor.getColumnIndexOrThrow(COL_SUB_CYCLE))
                val icon = cursor.getString(cursor.getColumnIndexOrThrow(COL_SUB_ICON))
                list.add(Subscription(id.toString(), name, amount, date, cycle, icon))
            } while (cursor.moveToNext())
        }
        cursor.close()
        list
    }

    // --- DEBT CRUD ---
    suspend fun insertDebt(name: String, amount: Double, desc: String, type: String): Long = withContext(Dispatchers.IO) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_DEBT_NAME, name)
            put(COL_DEBT_AMOUNT, amount)
            put(COL_DEBT_DESC, desc)
            put(COL_DEBT_TYPE, type)
        }
        db.insert(TABLE_DEBTS, null, values)
    }

    suspend fun getAllDebts(): List<Debt> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val cursor = db.query(TABLE_DEBTS, null, null, null, null, null, "$COL_DEBT_ID DESC")
        val list = mutableListOf<Debt>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_DEBT_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_DEBT_NAME))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_DEBT_AMOUNT))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(COL_DEBT_DESC))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COL_DEBT_TYPE))
                list.add(Debt(id.toString(), name, amount, desc, type))
            } while (cursor.moveToNext())
        }
        cursor.close()
        list
    }

    suspend fun getTotalDebt(): Double = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COL_DEBT_AMOUNT) FROM $TABLE_DEBTS", null)
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        total
    }

    // --- TRANSACTION CRUD ---
    suspend fun insertTransaction(title: String, amount: Double, categoryId: Int): Long = withContext(Dispatchers.IO) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_TRANS_TITLE, title)
            put(COL_TRANS_AMOUNT, amount)
            put(COL_TRANS_CAT_ID, categoryId)
        }
        db.insert(TABLE_TRANSACTIONS, null, values)
    }

    suspend fun getAllTransactions(orderBy: String = "$COL_TRANS_ID DESC"): List<TransactionEntity> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val cursor = db.query(TABLE_TRANSACTIONS, null, null, null, null, null, orderBy)
        val list = mutableListOf<TransactionEntity>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANS_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANS_TITLE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TRANS_AMOUNT))
                val catId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANS_CAT_ID))
                list.add(TransactionEntity(id, title, amount, catId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        list
    }

    // F5 dynamic SQL with LIKE
    suspend fun searchTransactionsByTitle(query: String): List<TransactionEntity> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val selection = "$COL_TRANS_TITLE LIKE ?"
        val selectionArgs = arrayOf("%${query}%")
        val cursor = db.query(TABLE_TRANSACTIONS, null, selection, selectionArgs, null, null, "$COL_TRANS_ID DESC")
        val list = mutableListOf<TransactionEntity>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANS_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANS_TITLE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TRANS_AMOUNT))
                val catId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANS_CAT_ID))
                list.add(TransactionEntity(id, title, amount, catId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        list
    }
}
