package com.example.osekeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Adapter adapter;
    ArrayList<String> author = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> category_name = new ArrayList<>();
    ArrayList<String> isbn = new ArrayList<>();
    ArrayList<String>mKey = new ArrayList<>();


    //add book menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_book:
                Intent intent = new Intent(this, addBookActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //populate_book array method
    public void PopulateArray(List<bookClass> pBooks, List<String>key){
        for(int i = 0; i < pBooks.size(); i++){
            author.add(pBooks.get(i).getAuthor());
            title.add(pBooks.get(i).getTitle());
            category_name.add(pBooks.get(i).getCategory_name());
            isbn.add(pBooks.get(i).getIsbn());
            mKey.add(key.get(i));
        }
        adapter = new Adapter(this, author, title, category_name, isbn);
        listView.setAdapter(adapter);
    }


    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UpdateBookActivity.class);
                intent.putExtra("key", mKey.get(i));
                intent.putExtra("author", author.get(i));
                intent.putExtra("title", title.get(i));
                intent.putExtra("isbn", isbn.get(i));
                intent.putExtra("category_name", category_name.get(i));
                startActivity(intent);
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deletBook(mKey.get(i));
                return true;

            }

        });



        new firebaseHelperClass().readBooks(new firebaseHelperClass.DataStatus() {
            @Override
            public void DataIsLoaded(List<bookClass> books, List<String> key) {
                author.clear();
                title.clear();
                category_name.clear();
                isbn.clear();
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                PopulateArray(books, key);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }

    public void deletBook(String key){
        new firebaseHelperClass().DeleteBook(key, new firebaseHelperClass.DataStatus() {
            @Override
            public void DataIsLoaded(List<bookClass> books, List<String> key) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {
                Toast.makeText(MainActivity.this, "book has been delected!", Toast.LENGTH_SHORT).show();
            }
        });
    }
















    //listAdapter class
    public class Adapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> mAuthor, mTitle, mCategory_name, mIsbn;

        Adapter(Context c, ArrayList<String> author, ArrayList<String> title, ArrayList<String> category_name, ArrayList<String>Isbn) {
            super(c, R.layout.row, R.id.add_title, title);
            this.context = c;
            this.mAuthor = author;
            this.mCategory_name = category_name;
            this.mTitle = title;
            this.mIsbn = Isbn;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView title = row.findViewById(R.id.add_title);
            TextView author = row.findViewById(R.id.add_author);
            TextView category_name = row.findViewById(R.id.category_name);
            TextView isbn = row.findViewById(R.id.isbn);

            title.setText(mAuthor.get(position));
            author.setText(mTitle.get(position));
            category_name.setText(mCategory_name.get(position));
            isbn.setText(mIsbn.get(position));

            return row;
        }
    }
}
