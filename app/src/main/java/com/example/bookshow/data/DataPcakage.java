package com.example.bookshow.data;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class DataPcakage {
    List<Book> bookList=new LinkedList<>();

    public List<Book> dataPcakage(Context context) {
        ObjectInputStream objectinputStream=null;
        File file=new File("data");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectinputStream=new ObjectInputStream(context.openFileInput("data"));
                  bookList=(LinkedList<Book>)objectinputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(objectinputStream!=null)
                objectinputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bookList;
    }

    public List<Book> getlist() {
        return bookList;
    }


    public void save(Context context,List<Book> bookList) {
        this.bookList = bookList;
        ObjectOutputStream objectOutputStream=null;
        try {
            objectOutputStream=new ObjectOutputStream(context.openFileOutput("data",Context.MODE_PRIVATE));
            objectOutputStream.writeObject(bookList);


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {if(objectOutputStream!=null)
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
