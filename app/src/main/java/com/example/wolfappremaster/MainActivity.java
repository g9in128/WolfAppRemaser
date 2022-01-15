package com.example.wolfappremaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private class OrderSortChar implements Comparator<Character> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public int compare(Character character, Character t1) {
            String[] order1 = character.getOrder().split(" "),
                    order2 = t1.getOrder().split(" ");
            String head1 = order1[0],head2 = order2[0];
            if (head1.isEmpty() != head2.isEmpty()) {
                return head1.isEmpty() ? 1 : -1;
            }
            try {
                int num1 = Integer.parseInt(head1), num2 = Integer.parseInt(head2);
                if (num1 != num2) {
                    return num1 - num2;
                }
            }catch (NumberFormatException e) {

            }
            return character.getOrder().compareTo(t1.getOrder());

        }
    }

    private class OrderSortSp implements Comparator<Speech> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public int compare(Speech str1, Speech str2) {
            String[] order1 = str1.getOrder().split(" "),
                    order2 = str2.getOrder().split(" ");
            String head1 = order1[0],head2 = order2[0];
            if (head1.isEmpty() != head2.isEmpty()) {
                return head1.isEmpty() ? 1 : -1;
            }
            try {
                int num1 = Integer.parseInt(head1), num2 = Integer.parseInt(head2);
                if (num1 != num2) {
                    return num1 - num2;
                }
            }catch (NumberFormatException e) {

            }
            return str1.getOrder().compareTo(str2.getOrder());

        }
    }

    private ListView characters;
    private EditText hostSpeech;
    private TextView entry,script;
    private LinearLayout selectBtns,playBtns;
    private Button loadBtn,startBtn,stopBtn,pauseBtn;

    private String viewing;
    private Character viewingCharacter;

    private PreferenceHandler handler;

    private TextToSpeech tts;

    private List<CharacterItem> items;
    private Map<Character,CharacterItem> pool;
    private List<Character> characterList;
    private CharacterAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Character.initResource(this);

        handler = new PreferenceHandler(this);

        characters = findViewById(R.id.characters);
        hostSpeech = findViewById(R.id.host_speech);
        entry = findViewById(R.id.entry);
        script = findViewById(R.id.script);
        selectBtns = findViewById(R.id.select_btns);
        playBtns = findViewById(R.id.play_btns);
        loadBtn = findViewById(R.id.load_btn);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        pauseBtn = findViewById(R.id.pause_btn);

        setViewing(handler.getString(PreferenceHandler.SETTING_PREFERENCE,"viewing"));

        tts = new TextToSpeech(this,i -> {
            if (i == TextToSpeech.SUCCESS) {
                int res = tts.setLanguage(Locale.KOREAN);
                if (res < 0) Log.d("string","한글 x");
            }
        });

        pool = new HashMap<>();

        hostSpeech.setOnEditorActionListener((textView, i, keyEvent) -> {
            items.stream().filter(item -> item.getCharacter().equals(viewingCharacter))
                    .forEach(j -> j.getSpeeches().get(j.getCharacter().getOrder()).setSpeech(textView.getText() + ""));
            adapter.notifyDataSetChanged();
            return false;
        });

        loadBtn.setOnClickListener(view -> openSaveLoadPopup("saveload"));

        startBtn.setOnClickListener(view -> openSaveLoadPopup("savestart"));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openSaveLoadPopup(String command) {
        Intent intent = new Intent(this,SaveLoadPopupActivity.class);
        intent.putExtra("preferences",handler.getPreferences().toArray(new String[0]));
        intent.putExtra("viewing",viewing);
        intent.putExtra("command",command);
        startActivityForResult(intent,1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String command = data.getStringExtra("command");
            String name;
            if (command != null && (command.isEmpty() || command.equals("load"))) {
                name = data.getStringExtra("saveAs");
                if (name != null) {
                    handler.addPreference(name);
                    items.forEach(item -> {
                        Character character = item.getCharacter();
                        Speech sp1,sp2;
                        sp1 = character.getSpeeches().get(character.getOrder());
                        sp2 = item.getSpeeches().get(character.getOrder());
                        if (!sp1.equals(sp2))handler.setSpeech(name, item.getCharacter().getName(),sp2);
                    });
                }
                if (command.equals("load")) openSaveLoadPopup(command);
                setViewing(name);
            }else if ("start".equals(command)) {
                startRead();
            }else {
                name = data.getStringExtra("load");
                setViewing(name);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startRead() {
        selectBtns.setVisibility(View.GONE);
        playBtns.setVisibility(View.VISIBLE);
        hostSpeech.setVisibility(View.GONE);
        script.setVisibility(View.VISIBLE);
        hostSpeech.setEnabled(false);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                selectBtns.setVisibility(View.VISIBLE);
                playBtns.setVisibility(View.GONE);
                hostSpeech.setVisibility(View.VISIBLE);
                script.setVisibility(View.GONE);
            }
        };

        HashMap<String,Speech> speeches = new HashMap<>();

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {}

            @Override
            public void onDone(String s) {
                if ("end".equals(s)) handler.sendMessage(handler.obtainMessage());
            }

            @Override
            public void onError(String s) {}
        });

        boolean dop = pool.containsKey(Character.DOPPELGANGER);
        pool.values().forEach(item -> {
            speeches.putAll(item.getSpeeches());
            if(dop && item.getCharacter().getDopDirect() == Character.ANOTHER_ROUND) {
                speeches.putAll(items.get(items.indexOf(item.getCharacter()) + 1).getSpeeches());
            }
        });
        ArrayList<Speech> speechArrayList = new ArrayList<>(speeches.values());
        speechArrayList.sort(new OrderSortSp());
        tts.speak(getString(R.string.close_eyes_all),TextToSpeech.QUEUE_ADD,null,"start");
        tts.playSilentUtterance(1000l,TextToSpeech.QUEUE_ADD,"wait/start");

        speechArrayList.forEach(s -> {
            tts.speak(Format.get(s.getOrder()).format(s.getSpeech(),new ArrayList<>(pool.keySet())),TextToSpeech.QUEUE_ADD,null,s.getOrder());
            tts.playSilentUtterance(s.getWaitingTime() * 1000l,TextToSpeech.QUEUE_ADD,"wait/" + s.getOrder());
        });

        tts.speak(getString(R.string.open_eyes_all),TextToSpeech.QUEUE_ADD,null,"end");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViewing(String view) {
        characterList = new ArrayList<>(Arrays.asList(Character.originalCharacters));
        characterList.addAll(Arrays.asList(Character.daybreakCharacters));
        characterList.sort(new OrderSortChar());
        Log.d("string",characterList.toString());
        viewing = view;
        handler.setString(PreferenceHandler.SETTING_PREFERENCE,"viewing",viewing);
        items = characterList.stream().map(character -> {
            CharacterItem item = new CharacterItem(character,character.getWaitingTime());
            Speech main = handler.getSpeech(view,character.getName()),charSpeech = character.getSpeeches().get(character.getOrder());
            if (main != null && !charSpeech.equals(main))
                item.getSpeeches().put(character.getOrder(),main);
            return item;
        }).collect(Collectors.toList());
        adapter = new CharacterAdapter(this,R.layout.listview_item,items);
        characters.setAdapter(adapter);
        hostSpeech.setText("");
    }

    @SuppressLint("StringFormatMatches")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setEntry() {
        int num = Math.max(pool.values().stream().mapToInt(item -> item.getCount()).sum() - 3 , 0);
        entry.setText(getString(R.string.entry_text,num));
    }

    Character getViewingCharacter() {
        return viewingCharacter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void addPool(CharacterItem item) {
        pool.put(item.getCharacter(),item);
        setEntry();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void removePool(CharacterItem item) {
        pool.remove(item.getCharacter());
        setEntry();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setViewingCharacter(CharacterItem item) {
        viewingCharacter = item.getCharacter();
        hostSpeech.setText(item.getSpeeches().get(viewingCharacter.getOrder()).getSpeech());
    }
}