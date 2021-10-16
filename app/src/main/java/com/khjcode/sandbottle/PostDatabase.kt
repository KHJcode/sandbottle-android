package com.khjcode.sandbottle

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        private var instance: PostDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PostDatabase? {
            if (instance == null) {
                synchronized(PostDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        "post-database"
                    )
                        .build()
                }
            }
            return instance
        }
    }
}