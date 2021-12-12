package com.example.bookshow.ts;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookshow.R;
import com.example.bookshow.ts.data.Book;
import com.example.bookshow.ts.data.DataPcakage;
import java.util.List;

public class Fragmentts extends Fragment {
    DataPcakage dataPcakage=new DataPcakage();
    public Adapter adapter;
    public List<Book> bookList;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ts,container);
        bookList=dataPcakage.dataPcakage(getContext());
        recyclerView = view.findViewById(R.id.recycle_view_books);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(bookList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
