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


    public class Adapter extends RecyclerView.Adapter{
        List<Book> bookList ;

        public Adapter(List<Book> dataList) {
            this.bookList = dataList;
        }

        @Override
        public RecyclerView.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
            return new MainActivity.Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder holders= (ViewHolder)holder;
            Book book= bookList.get(position);
            holders.imageView.setImageResource(book.getImgid());
            holders.textView.setText(book.getTitle());
        }


        @Override
        public int getItemCount() {
            return bookList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener,View.OnCreateContextMenuListener {

            ImageView imageView;
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image_view_book_cover);
                textView = itemView.findViewById(R.id.text_view_book_title);
                itemView.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem menuItem1 = menu.add(Menu.NONE, 1, 1, "add");
                menuItem1.setOnMenuItemClickListener(this);
                MenuItem menuItem2 = menu.add(Menu.NONE, 2, 2, "DELETE");
                menuItem2.setOnMenuItemClickListener(this);
                MenuItem menuItem3 = menu.add(Menu.NONE, 3, 3, "new");
                menuItem3.setOnMenuItemClickListener(this);
            }


            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                int position=getAdapterPosition();
                switch (menuItem.getItemId()){
                    case 1:
                        View dialagueView= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogview,null);
                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setView(dialagueView);

                         alertDialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText editName=dialagueView.findViewById(R.id.de1);
                                Log.d("m",editName.getText().toString());
                                bookList.add(position,new Book(editName.getText().toString(),R.drawable.book_1));
                                Adapter.this.notifyItemInserted(position);
                            }
                        });
                        alertDialog.setCancelable(false).setNegativeButton ("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialog.create().show();


                        break;
                    case 2:
                        bookList.remove(position);
                       Adapter.this.notifyItemRemoved(position);
                       break;
                    case 3:
                        Intent intent1=new Intent(MainActivity.this,SecondActivity.class);
                        intent1.putExtra("name",textView.getText().toString());
                        startActivityForResult(intent1,2);
                        break;
                }
                return true;
            }
        }}
}