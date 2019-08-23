package com.example.osekeapp;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

    public class firebaseHelperClass {


        private FirebaseDatabase mdatabase;
        private DatabaseReference databaseReference;
        private List<bookClass> books = new ArrayList<>();


        public firebaseHelperClass(){
            mdatabase  = FirebaseDatabase.getInstance();
            databaseReference = mdatabase.getReference("books");

        }

        public interface DataStatus{
            void DataIsLoaded( List<bookClass>books, List<String>key);
            void DataIsInserted();
            void DataIsUpdated();
            void DataIsDeleted();
        }



        public void readBooks(final DataStatus dataStatus){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    books.clear();
                    List<String>keys = new ArrayList<>();
                    for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                        keys.add(keyNode.getKey());
                        bookClass book = keyNode.getValue(bookClass.class);
                        books.add(book);
                    }
                    dataStatus.DataIsLoaded(books, keys);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public void AddBook(bookClass book, final DataStatus dataStatus){
            String key = databaseReference.push().getKey();
            databaseReference.child(key).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dataStatus.DataIsInserted();
                }
            });
        }

        public void UpdateBook(String key, bookClass book, final DataStatus dataStatus){
            databaseReference.child(key).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dataStatus.DataIsUpdated();
                }
            });
        }


        public void DeleteBook(String key, final DataStatus dataStatus){
            databaseReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dataStatus.DataIsDeleted();
                }
            });
        }

    }


