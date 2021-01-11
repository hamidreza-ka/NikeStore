package com.example.nikestore.feature.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.data.Comment

class CommentAdapter(val showAll : Boolean = false) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments = ArrayList<Comment>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commentTitleTv = itemView.findViewById<TextView>(R.id.commentTitleTv)
        val commentDateTv = itemView.findViewById<TextView>(R.id.commentDateTv)
        val commentAuthorTv = itemView.findViewById<TextView>(R.id.commentAuthorTv)
        val commentContentTv = itemView.findViewById<TextView>(R.id.commentContentTv)

        fun bindComment(comment: Comment) {

            commentTitleTv.text = comment.title
            commentDateTv.text = comment.date
            commentAuthorTv.text = comment.author.email
            commentContentTv.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_comment, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }

    override fun getItemCount(): Int = if (comments.size > 3 && !showAll) 3 else comments.size
}