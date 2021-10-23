package com.example.bookshow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Book book1=new Book("软件项目管理案例教程（第4版）", R.drawable.book_2);
    Book book2=new Book("创新工程实践", R.drawable.book_no_name);
    Book book3=new Book("信息安全数学基础（第2版）", R.drawable.book_1);
    List<Book> bookList=new LinkedList<>();
    Adapter adapter;
    RecyclerView recyclerView;
    private Object MenuItem;
    private int mSelectPosition;

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        startActivityForResult(intent,1);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Book book = new Book(data.getStringExtra("name"), R.drawable.book_no_name);
        bookList.add(book);
        adapter.notifyItemChanged(mSelectPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        recyclerView = findViewById(R.id.recycle_view_books);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(bookList);
        recyclerView.setAdapter(adapter);

    }
}