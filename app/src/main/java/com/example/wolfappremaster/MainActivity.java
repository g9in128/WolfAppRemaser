package com.example.wolfappremaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
        characterList = new ArrayList<>(Arrays.asList(Character.originalCharacters));
        characterList.addAll(Arrays.asList(Character.daybreakCharacters));
        characterList.sort(Comparator.comparing(Character::getOrder));
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
        getSupportActionBar().hide();
        Character.initResource(this);
//        Log.d("string",String.format("%x ,%x", R.string.doppelganger,R.string.speech_doppelganger));

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

        characters.setAdapter(new CharacterAdapter(this,R.layout.listview_item,items));

    }
}