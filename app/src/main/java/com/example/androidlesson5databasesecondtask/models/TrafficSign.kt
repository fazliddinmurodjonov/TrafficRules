package com.example.androidlesson5databasesecondtask.models

class TrafficSign {
    var id: Int? = null
    var name: String? = null
    var definition: String? = null
    var imagePath: String? = null
    var category: String? = null
    var like: Int? = null




    constructor()

    constructor(
        name: String?,
        definition: String?,
        imagePath: String?,
        category: String?,
        like: Int?,
    ) {
        this.name = name
        this.definition = definition
        this.imagePath = imagePath
        this.category = category
        this.like = like
    }

    constructor(
        id: Int?,
        name: String?,
        definition: String?,
        imagePath: String?,
        category: String?,
        like: Int?,
    ) {
        this.id = id
        this.name = name
        this.definition = definition
        this.imagePath = imagePath
        this.category = category
        this.like = like
    }

}