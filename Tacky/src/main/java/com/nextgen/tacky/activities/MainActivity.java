package com.nextgen.tacky.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.nextgen.tacky.R;
import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.basic.Tacky;
import com.nextgen.tacky.db.LocalDatabase;

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
