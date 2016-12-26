package cooleye.eot.kotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by cool on 16-11-25.
 */
class RidingDb : SQLiteOpenHelper {
    constructor(context: Context) : super(context, "Riding", null, 1) {
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}