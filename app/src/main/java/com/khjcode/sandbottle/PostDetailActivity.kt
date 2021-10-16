package com.khjcode.sandbottle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.*

class PostDetailActivity : AppCompatActivity() {
    private lateinit var db: PostDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        db = PostDatabase.getInstance(applicationContext)!!

        val backspaceButton = findViewById<ImageButton>(R.id.backspace_button)
        val postTitle = findViewById<TextView>(R.id.post_detail_post_title_textview)
        val postContents = findViewById<TextView>(R.id.post_detail_post_contents_textview)
        val deletePostButton = findViewById<Button>(R.id.post_detail_post_delete_button)
        val postId: Int = intent.getSerializableExtra("id") as Int
        CoroutineScope(Dispatchers.Main).async {
            val post = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                db.postDao().loadPostDetail(postId)
            }
            postTitle.text = post.title
            postContents.text = post.contents
        }

        backspaceButton.setOnClickListener {
            finish()
        }

        deletePostButton.setOnClickListener {
            deletePostById(postId)
        }
    }

    private fun deletePostById(postId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            db.postDao().deletePostById(postId)
        }
        finish()
    }
}