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
            webView.loadUrl("http://animation.khjcode.repl.co/?colors=${getColorString(score.toInt())}&score=$score")
        }

        backspaceButton.setOnClickListener {
            finish()
        }
    }

    private fun getColorString(
        score: Int
    ): String {
        val colorBox = arrayListOf(
            "1abc9c",
            "2ecc71",
            "3498db",
            "e74c3c",
            "f1c40f",
            "2c3e50",
            "e67e22",
            "f39c12",
            "8e44ad"
        )
        val range = (0..8)
        var colorString = colorBox[range.random()]
        for (i in 1 until score) {
            colorString = colorString.plus(",${colorBox[range.random()]}")
        }
        return colorString
    }
}