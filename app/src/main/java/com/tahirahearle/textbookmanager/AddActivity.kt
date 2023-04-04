package com.tahirahearle.textbookmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddActivity : AppCompatActivity() {

    private lateinit var ISBN_input: EditText
    private lateinit var title_input: EditText
    private lateinit var Course_input: EditText
    private lateinit var Author_input: EditText
    private lateinit var add_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        ISBN_input = findViewById(R.id.ISBN_input)
        title_input = findViewById(R.id.title_input)
        Author_input = findViewById(R.id.Author_input)
        Course_input = findViewById(R.id.Course_input)
        add_button = findViewById(R.id.add_button)

        add_button.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            myDB.addBook(
                ISBN_input.text.toString().trim(),
                title_input.text.toString().trim(),
                Author_input.text.toString().trim(),
                Course_input.text.toString().trim()
            )
        }
    }
}