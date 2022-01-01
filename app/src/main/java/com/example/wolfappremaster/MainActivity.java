package com.example.wolfappremaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView characters;
    EditText hostSpeech;
    TextView entry;
    LinearLayout selectBtns,playBtns;
    Button loadBtn,startBtn,stopBtn,pauseBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        characters = findViewById(R.id.characters);
        hostSpeech = findViewById(R.id.host_speech);
        entry = findViewById(R.id.entry);
        selectBtns = findViewById(R.id.play_btns);
        playBtns = findViewById(R.id.play_btns);
        loadBtn = findViewById(R.id.load_btn);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        pauseBtn = findViewById(R.id.pause_btn);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}