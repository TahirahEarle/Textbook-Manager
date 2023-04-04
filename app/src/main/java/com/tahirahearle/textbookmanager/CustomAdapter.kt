package com.tahirahearle.textbookmanager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter (private val activity: Activity, private val context: Context,

    private val book_id: ArrayList<Int>,
    private val book_isbn: ArrayList<String>,
    private val book_title: ArrayList<String>,
    private val book_author: ArrayList<String>,
    private val book_course: ArrayList<String>,

) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.book_id_txt.text = book_id[position].toString()
        holder.book_isbn_txt.text = book_isbn[position]
        holder.book_author_txt.text = book_author[position]
        holder.book_title_txt.text = book_title[position]
        holder.book_course_txt.text = book_course[position]

        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", book_id[position].toString())
            intent.putExtra("isbn", book_isbn[position])
            intent.putExtra("author", book_author[position])
            intent.putExtra("title", book_title[position])
            intent.putExtra("course", book_course[position])
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return book_id.size
    }

    // Set row to RecyclerView
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var book_id_txt: TextView = itemView.findViewById(R.id.book_id_txt)
        var book_isbn_txt: TextView = itemView.findViewById(R.id.book_isbn_txt)
        var book_title_txt: TextView = itemView.findViewById(R.id.book_title_txt)
        var book_author_txt: TextView = itemView.findViewById(R.id.book_author_txt)
        var book_course_txt: TextView = itemView.findViewById(R.id.book_course_txt)
        var mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

        init {
            //Animate Recyclerview
            val translate_anim: Animation = AnimationUtils.loadAnimation(context, R.anim.translate_anim)
            mainLayout.animation = translate_anim
        }
    }
}

