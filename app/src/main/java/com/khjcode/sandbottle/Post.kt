package com.khjcode.sandbottle

import androidx.room.Entity
import androidx.room.PrimaryKey

data class PostItemTuple(
    val title: String,
    val date: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Post(
    val title: String,
    val contents: String,
    val date: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}