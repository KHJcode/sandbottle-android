package com.khjcode.sandbottle

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT id, title, date from Post")
    fun loadPostList(): List<PostItemTuple>

    @Query("SELECT * from Post WHERE id = :id")
    fun loadPostDetail(id: Int): Post

    @Query("SELECT COUNT(*) from Post")
    fun loadCountOfPosts(): Int

    @Insert
    fun create(post: Post)

    @Query("DELETE FROM Post WHERE id = :id")
    fun deletePostById(id: Int)
}