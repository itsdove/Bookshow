package com.example.bookshow.ts.data;

import java.io.Serializable;

public class Book implements Serializable {
    int imgid;
    String Title;

    public Book(String s, int book_1) {
        Title=s;
        imgid=book_1;
    }




    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public  String getTitle(){
        return this.Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
