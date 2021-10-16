package com.khjcode.sandbottle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SandBottleActivity : AppCompatActivity() {
    private lateinit var db: PostDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sand_bottle)

        db = PostDatabase.getInstance(applicationContext)!!

        val backspaceButton = findViewById<ImageButton>(R.id.backspace_button)
        val scoreTextView = findViewById<TextView>(R.id.sand_bottle_score_textview)
        val webView = findViewById<WebView>(R.id.sand_bottle_web_view)

        CoroutineScope(Dispatchers.Main).launch {
            val score = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                db.postDao().loadCountOfPosts()
            }.toString()
            scoreTextView.text = score
            webView.loadUrl("http://animation.khjcode.repl.co/?colors=f1c40f,f1c40f,f1c40f,f1c40f,f1c40f&score=$score")
        }

        backspaceButton.setOnClickListener {
            finish()
        }
    }
}