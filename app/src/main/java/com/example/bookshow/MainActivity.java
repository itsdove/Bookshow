package com.example.bookshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Book book1=new Book("软件项目管理案例教程（第4版）", R.drawable.book_2);
    Book book2=new Book("创新工程实践", R.drawable.book_no_name);
    Book book3=new Book("信息安全数学基础（第2版）", R.drawable.book_1);
    List<Book> bookList=new LinkedList<>();
    Adapter adapter=new Adapter(bookList);
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        recyclerView=findViewById(R.id.recycle_view_books);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);





    }
}