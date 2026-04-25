package com.example.financetracker.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.financetracker.models.TransactionEntity
import com.example.financetracker.models.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseHelper(context: Context) : 
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "FinanceTrackerDB.db"
        private const val DATABASE_VERSION = 2 // Updated version for new table

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
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createCategoriesTable = """
            CREATE TABLE $TABLE_CATEGORIES (
                $COL_CAT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_CAT_NAME TEXT NOT NULL
            )
        """.trimIndent()

        val createTransactionsTable = """
            CREATE TABLE $TABLE_TRANSACTIONS (
                $COL_TRANS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TRANS_TITLE TEXT NOT NULL,
                $COL_TRANS_AMOUNT REAL NOT NULL,
                $COL_TRANS_CAT_ID INTEGER,
                FOREIGN KEY($COL_TRANS_CAT_ID) REFERENCES $TABLE_CATEGORIES($COL_CAT_ID) ON DELETE SET NULL
            )
        """.trimIndent()

        val createSubscriptionsTable = """
            CREATE TABLE $TABLE_SUBSCRIPTIONS (
                $COL_SUB_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_SUB_NAME TEXT NOT NULL,
                $COL_SUB_AMOUNT REAL NOT NULL,
                $COL_SUB_DUE_DATE TEXT NOT NULL,
                $COL_SUB_CYCLE TEXT NOT NULL,
                $COL_SUB_ICON TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createCategoriesTable)
        db.execSQL(createTransactionsTable)
        db.execSQL(createSubscriptionsTable)
        
        db.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME) VALUES ('General')")
        db.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME) VALUES ('Food')")
        db.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME) VALUES ('Transport')")
        db.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME) VALUES ('Shopping')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            val createSubscriptionsTable = """
                CREATE TABLE $TABLE_SUBSCRIPTIONS (
                    $COL_SUB_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COL_SUB_NAME TEXT NOT NULL,
                    $COL_SUB_AMOUNT REAL NOT NULL,
                    $COL_SUB_DUE_DATE TEXT NOT NULL,
                    $COL_SUB_CYCLE TEXT NOT NULL,
                    $COL_SUB_ICON TEXT NOT NULL
                )
            """.trimIndent()
            db.execSQL(createSubscriptionsTable)
        }
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
        parseTransactionsCursor(cursor)
    }

    suspend fun searchTransactionsByTitle(query: String): List<TransactionEntity> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val selection = "$COL_TRANS_TITLE LIKE ?"
        val selectionArgs = arrayOf("%${query}%")
        val cursor = db.query(TABLE_TRANSACTIONS, null, selection, selectionArgs, null, null, "$COL_TRANS_ID DESC")
        parseTransactionsCursor(cursor)
    }

    private fun parseTransactionsCursor(cursor: Cursor): List<TransactionEntity> {
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
        return list
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
}
