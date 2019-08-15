package com.example.osekeapp;


import androidx.annotation.NonNull;

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

    }


