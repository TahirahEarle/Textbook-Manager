package com.tahirahearle.textbookmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var add_button: FloatingActionButton
    private lateinit var empty_imageview: ImageView
    private lateinit var no_data: TextView
    private lateinit var myDB: MyDatabaseHelper

    private lateinit var book_id: ArrayList<Int>
    private lateinit var book_title: ArrayList<String>
    private lateinit var book_author: ArrayList<String>
    private lateinit var book_isbn: ArrayList<String>
    private lateinit var book_course: ArrayList<String>
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        add_button = findViewById(R.id.add_button)
        empty_imageview = findViewById(R.id.empty_imageview)
        no_data = findViewById(R.id.no_data)

        // Start Add Textbook Activity
        add_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        // Initialize database helper
        myDB = MyDatabaseHelper(this)
        book_id = ArrayList()
        book_title = ArrayList()
        book_author = ArrayList()
        book_isbn = ArrayList()
        book_course = ArrayList()

        storeDataInArrays()

        // Set adapter to recyclerview
        customAdapter = CustomAdapter(this, this, book_id, book_title, book_author, book_isbn, book_course)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            recreate()
        }
    }

    // store data insert into database as array
    private fun storeDataInArrays() {
        val cursor = myDB.readAllData()
        if(cursor?.count == 0){
            // when the a data is insert disable the empty image visibility
            empty_imageview.visibility = View.VISIBLE
            no_data.visibility = View.VISIBLE
        }else{
            while (cursor?.moveToNext() == true) {
                book_id.add(cursor.getString(0)?.toInt() ?: 0)
                book_isbn.add(cursor.getString(1))
                book_author.add(cursor.getString(2))
                book_title.add(cursor.getString(3))
                book_course.add(cursor.getString(4))
            }

            empty_imageview.visibility = View.GONE
            no_data.visibility = View.GONE
        }
    }

    // Display menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Select option from menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_all){
            confirmDialog()
        }
        return super.onOptionsItemSelected(item)
    }


    // Delete all dialogue
    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all your Textbooks")
        builder.setPositiveButton("Yes") { dialogInterface, i ->
            val myDB = MyDatabaseHelper(this)
            myDB.deleteAllData()
            //Refresh Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface, i -> }
        builder.create().show()
    }
}
