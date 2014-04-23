package com.nextgen.tacky.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nextgen.tacky.R;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createTacky(View view){
        Intent intent = new Intent(this, CreateTacky.class);
        startActivity(intent);
    }

    public void loadTacky(View view){
        Intent intent = new Intent(this, LoadSavedTackys.class);
        startActivity(intent);
    }

    public void deleteTacky(View view){
        Intent intent = new Intent(this, DeleteTacky.class);
        startActivity(intent);
    }
}
