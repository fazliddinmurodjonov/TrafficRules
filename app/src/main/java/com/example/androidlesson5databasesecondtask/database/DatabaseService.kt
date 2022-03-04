package com.example.androidlesson5databasesecondtask.database

import com.example.androidlesson5databasesecondtask.models.TrafficSign

interface DatabaseService {
    fun insertSign(trafficSign: TrafficSign)
    fun deleteSign(trafficSign: TrafficSign)
    fun updateSign(trafficSign: TrafficSign)
    fun getSignById(id: Int): TrafficSign
    fun getAllSigns(query: String): ArrayList<TrafficSign>
    fun getLastRowID(): Int

}