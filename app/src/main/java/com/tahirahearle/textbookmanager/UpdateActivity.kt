package com.tahirahearle.textbookmanager


import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UpdateActivity : AppCompatActivity() {

    private lateinit var isbnInput: EditText
    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var courseInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private var id: String = ""
    private var isbn: String = ""
    private var title: String = ""
    private var author: String = ""
    private var course: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        isbnInput = findViewById(R.id.isbn_input2)
        titleInput = findViewById(R.id.title_input2)
        authorInput = findViewById(R.id.author_input2)
        courseInput = findViewById(R.id.course_input2)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)

        getAndSetIntentData()

        // Set actionbar title after getAndSetIntentData method
        supportActionBar?.title = title

        // Activity that takes place when the update button is clicked
        updateButton.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            isbn = isbnInput.text.toString().trim()
            title = titleInput.text.toString().trim()
            author = authorInput.text.toString().trim()
            course = courseInput.text.toString().trim()
            myDB.updateData(id, title, author, isbn, course)
        }

        // Activity that takes place when the delete button is clicked
        deleteButton.setOnClickListener { confirmDialog() }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("isbn") && intent.hasExtra("author") && intent.hasExtra("course")) {
            // Getting Data from Intent
            id = intent.getStringExtra("id").toString()
            isbn = intent.getStringExtra("isbn").toString()
            title = intent.getStringExtra("title").toString()
            author = intent.getStringExtra("author").toString()
            course = intent.getStringExtra("course").toString()

            // Setting Intent Data
            titleInput.setText(title)
            isbnInput.setText(isbn)
            authorInput.setText(author)
            courseInput.setText(course)
            Log.d("setData", " $title $isbn  $author $course")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }


    // delete dialogue
    private fun confirmDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete $title ?")
            .setMessage("Are you sure you want to delete $title ?")
            .setPositiveButton("Yes") { _, _ ->
                val myDB = MyDatabaseHelper(this)
                myDB.deleteOneRow(id)
                finish()
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }
}
