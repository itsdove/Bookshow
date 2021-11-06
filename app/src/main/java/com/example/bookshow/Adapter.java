package com.example.bookshow;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshow.data.Book;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    List<Book> bookList ;
    private Context mcontext;

    public Adapter(List<Book> dataList) {
        this.bookList = dataList;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Book book= bookList.get(position);
        holder.imageView.setImageResource(book.getImgid());
        holder.textView.setText(book.getTitle());

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
            MenuItem menuItem1 = menu.add(Menu.NONE, 1, 1, "修改");
            menuItem1.setOnMenuItemClickListener(this);
            MenuItem menuItem2 = menu.add(Menu.NONE, 2, 2, "删除");
            menuItem2.setOnMenuItemClickListener(this);
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem){
            int position=getAdapterPosition();
            switch (menuItem.getItemId()){
                case 1:
                    View dialagueView= LayoutInflater.from(mcontext).inflate(R.layout.dialogview,null);
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(mcontext);
                    alertDialog.setView(dialagueView);
                    EditText editName=dialagueView.findViewById(R.id.de1);
                    editName.setText(bookList.get(position).getTitle());
                    alertDialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Book book=new Book(editName.getText().toString(),bookList.get(position).getImgid());
                            bookList.set(position,book);
                            Adapter.this.notifyItemChanged(position);
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

            }
            return true;
        }
    }
}
