package com.example.bookshow;


import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    List<Book> bookList ;


    public Adapter(List<Book> dataList) {
        this.bookList = dataList;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Book book= bookList.get(position);
        holder.imageView.setImageResource(book.imgid);
        holder.textView.setText(book.Title);

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
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem){
            int position=getAdapterPosition();
            switch (menuItem.getItemId()){
                case 1:
                    bookList.add(position,new Book(textView.getText().toString(),R.drawable.book_1));
                    Adapter.this.notifyItemInserted(position);
                    break;
                case 2:
                    bookList.remove(position);
                    Adapter.this.notifyItemRemoved(position);
            }
            return true;
        }
    }}