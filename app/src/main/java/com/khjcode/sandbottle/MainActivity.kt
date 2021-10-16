package com.khjcode.sandbottle

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var db: PostDatabase
    private lateinit var postRecyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = PostDatabase.getInstance(applicationContext)!!

        val addPostButton = findViewById<FloatingActionButton>(R.id.main_add_post_button)
        val sandBottleButton = findViewById<ImageButton>(R.id.main_sand_bottle_image_button)
        val writePostIntent = Intent(this, WritePostActivity::class.java)
        val sandBottleIntent = Intent(this, SandBottleActivity::class.java)
        postRecyclerView = findViewById(R.id.main_post_recyclerview)

        addPostButton.setOnClickListener {
            startActivity(writePostIntent)
        }

        sandBottleButton.setOnClickListener {
            startActivity(sandBottleIntent)
        }
    }

    override fun onStart() {
        super.onStart()

        refreshPostList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshPostList() {
        CoroutineScope(Dispatchers.Main).launch {
            val posts = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                db.postDao().loadPostList()
            }
            postAdapter = PostAdapter(this@MainActivity, posts)
            postAdapter.notifyDataSetChanged()
            postRecyclerView.adapter = postAdapter
            postRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            postRecyclerView.setHasFixedSize(true)
        }
    }
}