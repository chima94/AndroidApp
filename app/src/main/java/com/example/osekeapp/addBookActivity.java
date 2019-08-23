package com.example.osekeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class addBookActivity extends AppCompatActivity {
    private EditText author;
    private EditText title;
    private EditText isbn;
    private Spinner category_name;

    public void addBookButton(View view){
        bookClass book = new bookClass();
        book.setAuthor(author.getText().toString());
        book.setTitle(title.getText().toString());
        book.setIsbn(isbn.getText().toString());
        book.setCategory_name(category_name.getSelectedItem().toString());
        new firebaseHelperClass().AddBook(book, new firebaseHelperClass.DataStatus() {
            @Override
            public void DataIsLoaded(List<bookClass> books, List<String> key) {

            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(addBookActivity.this, "book has been inserted successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }



    public void backButton(View view){
        finish(); return;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        author = findViewById(R.id.add_author);
        title = findViewById(R.id.add_title);
        isbn = findViewById(R.id.add_isbn);
        category_name = findViewById(R.id.spinner_category);
    }
}
