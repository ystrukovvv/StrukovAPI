package com.example.strukov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.strukov.Add.AddPlayerActivity;
import com.example.strukov.Get.PlayersActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonAdd = findViewById(R.id.buttonAddPlayers);
        Button buttonPlayers = findViewById(R.id.buttonPlayers);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSender = new Intent(MainActivity.this, AddPlayerActivity.class);
                startActivity(intentSender);
            }
        });

        buttonPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSender = new Intent(MainActivity.this, PlayersActivity.class);
                startActivity(intentSender);
            }
        });
    }
}
