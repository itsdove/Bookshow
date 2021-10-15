package com.example.bookshow;

public class Book {
    int imgid;
    String Title;

    public Book(String s, int book_1) {
        Title=s;
        imgid=book_1;
    }

    int getCoverResourceId(){
        return this.imgid;
    }

    String getTitle(){
        return this.Title;
    }
}
