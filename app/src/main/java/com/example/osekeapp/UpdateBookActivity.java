package com.example.osekeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class UpdateBookActivity extends AppCompatActivity {


    private EditText author;
    private EditText title;
    private EditText isbn;
    private Spinner category_name;
    String key;

    String iAuthor;
    String iTitle;
    String iIsbn;
    String iCategory_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        iAuthor = intent.getStringExtra("author");
        iTitle = intent.getStringExtra("title");
        iIsbn = intent.getStringExtra("isbn");
        iCategory_name = intent.getStringExtra("category_name");

        author = findViewById(R.id.add_author);
        title = findViewById(R.id.add_title);
        isbn = findViewById(R.id.add_isbn);
        category_name = findViewById(R.id.spinner_category);
        category_name.setSelection(getSpinnerItem(category_name, iCategory_name));

        //to set the values to the editText
        author.setText(iAuthor);
        title.setText(iTitle);
        isbn.setText(iIsbn);
        Log.i("Category", iCategory_name);







    }

    public void UpdateBookButton(View view){
        bookClass book = new bookClass();
        book.setAuthor(author.getText().toString());
        book.setTitle(title.getText().toString());
        book.setIsbn(isbn.getText().toString());
        book.setCategory_name(category_name.getSelectedItem().toString());

        new firebaseHelperClass().UpdateBook(key, book, new firebaseHelperClass.DataStatus() {
            @Override
            public void DataIsLoaded(List<bookClass> books, List<String> key) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {
                Toast.makeText(UpdateBookActivity.this, "book is updated!", Toast.LENGTH_SHORT).show();
                author.setText(null);
                title.setText(null);
                isbn.setText(null);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    private int getSpinnerItem(Spinner spinner, String item){
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).equals(item)){
                index = i;
            }
        }
        return index;
    }
}
