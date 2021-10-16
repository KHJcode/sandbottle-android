package com.khjcode.sandbottle

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class WritePostActivity : AppCompatActivity() {
    private lateinit var db: PostDatabase
    private lateinit var postTitle: EditText
    private lateinit var postContents: EditText
    private lateinit var toast: Toast

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        db = PostDatabase.getInstance(applicationContext)!!

        val backspaceButton = findViewById<ImageButton>(R.id.backspace_button)
        val submitPostButton = findViewById<Button>(R.id.write_post_submit_post_button)
        postTitle = findViewById(R.id.write_post_post_title_edittext)
        postContents = findViewById(R.id.write_post_post_contents_edittext)
        toast = Toast.makeText(applicationContext, "기록 생성 성공!", Toast.LENGTH_SHORT)

        backspaceButton.setOnClickListener {
            finish()
        }

        submitPostButton.setOnClickListener {
            addPost()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addPost() {
        val title = postTitle.text.toString()
        val contents = postContents.text.toString()
        val date = LocalDate.now().toString()

        CoroutineScope(Dispatchers.IO).launch {
            db.postDao().create(Post(title, contents, date))
        }
        toast.show()
        finish()
    }
}