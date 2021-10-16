package com.khjcode.sandbottle

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val context: Context, private val posts: List<PostItemTuple>) :
    RecyclerView.Adapter<PostAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(posts[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val postTitle = itemView?.findViewById<TextView>(R.id.item_post_title)
        private val postDate = itemView?.findViewById<TextView>(R.id.item_post_date)

        fun bind(post: PostItemTuple) {
            postTitle?.text = post.title
            postDate?.text = post.date

            itemView.setOnClickListener {
                Intent(context, PostDetailActivity::class.java).apply {
                    putExtra("id", post.id)
                }.run { context.startActivity(this) }
            }
        }
    }
}