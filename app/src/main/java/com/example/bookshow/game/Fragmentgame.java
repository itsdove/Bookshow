package com.example.bookshow.game;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookshow.R;

public class Fragmentgame  extends Fragment {
        public Fragmentgame() {
            // Required empty public constructor
        }
        public static Fragmentgame newInstance() {
            Fragmentgame fragment = new Fragmentgame();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_game, container, false);
        }
    }

