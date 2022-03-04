package com.example.androidlesson5databasesecondtask.models

object CheckNotEmpty {
    fun empty(text: String): Boolean {
        var bool = false
        for (c in text) {
            if (c != ' ') {
                bool = true
                break
            }
        }
        return bool
    }
}