package com.example.bookshow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookshow.data.Book;
import com.example.bookshow.data.DataPcakage;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    DataPcakage dataPcakage=new DataPcakage();
    List<Book> bookList;
    static  Adapter adapter;
    RecyclerView recyclerView;
    private int mSelectPosition;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dataPcakage.save(MainActivity.this,bookList);
    }

    @Override
    public void finish() {
        super.finish();
        dataPcakage.save(MainActivity.this,bookList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        startActivityForResult(intent,1);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                Book book = new Book(data.getStringExtra("name"), R.drawable.book_no_name);
                bookList.add(book);
                adapter.notifyItemChanged(mSelectPosition);
                break;
            case RESULT_CANCELED:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookList=dataPcakage.dataPcakage(MainActivity.this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view_books);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(bookList);
        recyclerView.setAdapter(adapter);
    }



}