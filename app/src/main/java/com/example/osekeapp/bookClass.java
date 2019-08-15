package com.example.osekeapp;

public class bookClass {

        private String author;
        private String title;
        private String category_name;
        private String isbn;

        public String getAuthor() {
            return author;
        }

        public bookClass() {
        }

        public bookClass(String author, String title, String category_name, String isbn) {
            this.author = author;
            this.title = title;
            this.category_name = category_name;
            this.isbn = isbn;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
    }


