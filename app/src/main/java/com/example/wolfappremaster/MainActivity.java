package com.example.wolfappremaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ListView characters;
    private EditText hostSpeech;
    private TextView entry;
    private LinearLayout selectBtns,playBtns;
    private Button loadBtn,startBtn,stopBtn,pauseBtn;

    private String viewing;

    private PreferenceHandler handler;

    private List<CharacterItem> items;
    private List<Character> characterList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViewing(String view) {
        characterList = Arrays.asList(Character.originalCharacters);
        characterList.addAll(Arrays.asList(Character.daybreakCharacters));
        viewing = view;
        handler.setString(PreferenceHandler.SETTING_PREFERENCE,"viewing",viewing);
        items = characterList.stream().map(character -> {
            CharacterItem item = handler.getCharacter(viewing,character.getName());
            if (item == null) item = new CharacterItem(character,character.getWaitingTime());
            return item;
        }).collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new PreferenceHandler(this);

        characters = findViewById(R.id.characters);
        hostSpeech = findViewById(R.id.host_speech);
        entry = findViewById(R.id.entry);
        selectBtns = findViewById(R.id.select_btns);
        playBtns = findViewById(R.id.play_btns);
        loadBtn = findViewById(R.id.load_btn);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        pauseBtn = findViewById(R.id.pause_btn);

        setViewing(handler.getString(PreferenceHandler.SETTING_PREFERENCE,"viewing"));

    }
}