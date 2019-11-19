package com.gonzalocl.boardgamestatistics.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gonzalocl.boardgamestatistics.R;

public class SelectionActivity extends AppCompatActivity {


    static void start(Context context) {
        Intent intent = new Intent(context, SelectionActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
    }
}
