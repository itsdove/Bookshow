package com.example.bookshow;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.example.bookshow.data.Book;
import com.example.bookshow.data.DataPcakage;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DataPcakage dataPcakage=new DataPcakage();
    List<Book> bookList;
    Adapter adapter;
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
        launcherAdd.launch(intent);
        return true;
    }
    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if(resultCode== RESULT_OK){
                if(null==data)return;
                Book book = new Book(data.getStringExtra("name"), R.drawable.book_3);
                bookList.add(book);
                adapter.notifyItemChanged(mSelectPosition);

            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookList=dataPcakage.dataPcakage(MainActivity.this);
        recyclerView = findViewById(R.id.recycle_view_books);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(bookList);
        recyclerView.setAdapter(adapter);
    }



}