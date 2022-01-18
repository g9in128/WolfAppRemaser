package com.example.wolfappremaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class OrderSortChar implements Comparator<Character> {

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

class OrderSortSp implements Comparator<Speech> {

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

public class MainActivity extends AppCompatActivity {

    private ListView characters;
    private EditText hostSpeech;
    private TextView entry;
    private LinearLayout selectBtns,playBtns;
    private Button loadBtn,startBtn,stopBtn,pauseBtn;
    private ProgressBar timeLeft;

    private String viewing;
    private Character viewingCharacter;
    private boolean modifyMode;

    private PreferenceHandler handler;
    private Speaker speaker;

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

        modifyMode = true;

        handler = new PreferenceHandler(this);
        speaker = new Speaker(this);


        characters = findViewById(R.id.characters);
        hostSpeech = findViewById(R.id.host_speech);
        entry = findViewById(R.id.entry);
        selectBtns = findViewById(R.id.select_btns);
        playBtns = findViewById(R.id.play_btns);
        loadBtn = findViewById(R.id.load_btn);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        timeLeft = findViewById(R.id.time_left);


        setViewing(handler.getString(PreferenceHandler.SETTING_PREFERENCE,"viewing"));

        pool = new HashMap<>();

        hostSpeech.setOnEditorActionListener((textView, i, keyEvent) -> {
            items.stream().filter(item -> item.getCharacter().equals(viewingCharacter))
                    .forEach(j -> j.getSpeeches().get(j.getCharacter().getOrder()).setSpeech(textView.getText() + ""));
            adapter.notifyDataSetChanged();
            return false;
        });


        loadBtn.setOnClickListener(view -> openSaveLoadPopup("saveload"));

        startBtn.setOnClickListener(view -> openSaveLoadPopup("savestart"));

        pauseBtn.setOnClickListener(view -> {
            if (speaker.isPaused()) {
                pauseBtn.setText("일시정지");
            }else {
                pauseBtn.setText("재생");
            }
            speaker.setPaused(!speaker.isPaused());
        });

        stopBtn.setOnClickListener(view -> speaker.endRead());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speaker.shutdown();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openSaveLoadPopup(String command) {
        Intent intent = new Intent(this,SaveLoadPopupActivity.class);
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
                    items.forEach(item -> handler.setCharacter(name,item));
                }
                if (command.equals("load")) openSaveLoadPopup(command);
                setViewing(name);
            }else if ("start".equals(command)) {
                new ArrayList<>(pool.keySet()).stream().forEach(i -> {
                    if (pool.containsKey(Character.DOPPELGANGER) && i.getDopDirect() == Character.ANOTHER_ROUND) {
                        String order = i.getOrder();
                        Character dop = Character.getCharacter(order.contains(" ") ? order + "~" : order + " ~");
                        pool.put(dop,handler.getCharacter(viewing,dop));
                    }
                });
                if (pool.containsKey(Character.ALPHA_WOLF) || pool.containsKey(Character.MYSTIC_WOLF)) {
                    pool.put(Character.WEREWOLF,handler.getCharacter(viewing,Character.WEREWOLF));
                }
                setModifyMode(false);
                speaker.startRead(new ArrayList<>(pool.values()));
            }else {
                name = data.getStringExtra("load");
                setViewing(name);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViewing(String view) {
        characterList = new ArrayList<>(Arrays.asList(Character.originalCharacters));
        characterList.addAll(Arrays.asList(Character.daybreakCharacters));
        characterList.sort(new OrderSortChar());
        viewing = view;
        handler.setString(PreferenceHandler.SETTING_PREFERENCE,"viewing",viewing);
        items = characterList.stream().map(character -> handler.getCharacter(view,character)).collect(Collectors.toList());
        adapter = new CharacterAdapter(this,R.layout.listview_item,items);
        characters.setAdapter(adapter);
        setViewingCharacter(null);
    }

    @SuppressLint("StringFormatMatches")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setEntry() {
        int num = Math.max(pool.values().stream().mapToInt(item -> item.getCount()).sum() - 3 , 0);
        entry.setText(getString(R.string.entry_text,num));
    }

    String getViewing() {
        return viewing;
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
    void setViewingCharacter(Character item) {
        viewingCharacter = item;
        if (item == null) hostSpeech.setText("");
        else hostSpeech.setText(handler.getCharacter(viewing,item).getSpeeches().get(viewingCharacter.getOrder()).getSpeech());
    }

    void setScript(String str) {
        if (!modifyMode) {
            hostSpeech.setText(str);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setModifyMode(boolean bool) {
        if (bool) {
            selectBtns.setVisibility(View.VISIBLE);
            playBtns.setVisibility(View.GONE);
        }else {
            selectBtns.setVisibility(View.GONE);
            playBtns.setVisibility(View.VISIBLE);
        }
        setViewingCharacter(null);
        hostSpeech.setEnabled(bool);
        modifyMode = bool;
        adapter.notifyDataSetChanged();
    }

    boolean isModifyMode() {
        return modifyMode;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setTimeLeft(long timeLeft, int waitingTime) {
        this.timeLeft.setProgress((int) (timeLeft / waitingTime),true);
    }
}