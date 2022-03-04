package com.example.androidlesson5databasesecondtask.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidlesson5databasesecondtask.models.TrafficSign

class TSDatabase(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.VERSION), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE ${Constant.TABLE}(${Constant.ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.NAME} TEXT NOT NULL,${Constant.ABOUT} TEXT NOT NULL,${Constant.IMAGE_PATH} TEXT NOT NULL,${Constant.CATEGORY} TEXT NOT NULL,${Constant.LIKE} INTEGER NOT NULL)"

        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun insertSign(trafficSign: TrafficSign) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.NAME, trafficSign.name)
        contentValues.put(Constant.ABOUT, trafficSign.definition)
        contentValues.put(Constant.IMAGE_PATH, trafficSign.imagePath)
        contentValues.put(Constant.CATEGORY, trafficSign.category)
        contentValues.put(Constant.LIKE, trafficSign.like)
        database.insert(Constant.TABLE, null, contentValues)
        database.close()
    }

    override fun getAllSigns(query: String): ArrayList<TrafficSign> {
        val trafficSignList = ArrayList<TrafficSign>()
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val trafficSign = TrafficSign(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5))
                trafficSignList.add(trafficSign)
            } while (cursor.moveToNext())
        }
        return trafficSignList
    }

    override fun deleteSign(trafficSign: TrafficSign) {
        val database = readableDatabase
        database.delete(Constant.TABLE, "${Constant.ID} = ?", arrayOf("${trafficSign.id}"))
        database.close()
    }

    override fun updateSign(trafficSign: TrafficSign) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.ID, trafficSign.id)
        contentValues.put(Constant.NAME, trafficSign.name)
        contentValues.put(Constant.ABOUT, trafficSign.definition)
        contentValues.put(Constant.IMAGE_PATH, trafficSign.imagePath)
        contentValues.put(Constant.CATEGORY, trafficSign.category)
        contentValues.put(Constant.LIKE, trafficSign.like)
        database.update(Constant.TABLE,
            contentValues,
            "${Constant.ID} = ?",
            arrayOf("${trafficSign.id}"))
    }

    override fun getSignById(id: Int): TrafficSign {
        val database = this.readableDatabase
        val cursor = database.query(Constant.TABLE,
            arrayOf(Constant.ID,
                Constant.NAME,
                Constant.ABOUT,
                Constant.IMAGE_PATH,
                Constant.CATEGORY,
                Constant.LIKE),
            "${Constant.ID} = ?",
            arrayOf("$id"),
            null,
            null,
            null)
        cursor?.moveToFirst()

        return TrafficSign(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5))
    }

    @SuppressLint("Recycle")
    override fun getLastRowID(): Int {
        val database = this.readableDatabase
        val query = "SELECT * FROM ${Constant.TABLE} ORDER BY ${Constant.ID} DESC LIMIT 1"
        val cursor = database.rawQuery(query, null)
        cursor.moveToLast()
        val trafficSign = TrafficSign(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5))
        return trafficSign.id!!
    }


}