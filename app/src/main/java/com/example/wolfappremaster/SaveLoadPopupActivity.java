package com.example.wolfappremaster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SaveLoadPopupActivity extends Activity {

    private TextView title;
    private EditText name;
    private Spinner names;
    private Button save_btn;
    private Button close_btn;
    private Button delete_preference;
    private PreferenceHandler handler;
    private List<String> preferences;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.save_load_popup);

        title = findViewById(R.id.popup_title);
        name = findViewById(R.id.save_name);
        names = findViewById(R.id.save_names);
        save_btn = findViewById(R.id.save_btn);
        close_btn = findViewById(R.id.close_btn);
        delete_preference = findViewById(R.id.delete_preference);

        handler = new PreferenceHandler(this);

        String command = getIntent().getStringExtra("command");

        if (command.contains("save")) {
            title.setText(R.string.save_popup_title);
            save_btn.setText("예");
            close_btn.setText("아니요");
        }else {
            title.setText(R.string.load_popup_title);
            save_btn.setText("확인");
            close_btn.setText("취소");
        }

        preferences = handler.getPreferences();
        preferences = new ArrayList<>(preferences.subList(1,preferences.size()));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,preferences);

        names.setAdapter(adapter);

        names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name.setText((String) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        names.setSelection(preferences.indexOf(getIntent().getStringExtra("viewing")));

        name.setFilters(new InputFilter[]{(charSequence, i, i1, spanned, i2, i3) ->
            Pattern.compile("^[a-zA-Z0-9]+$").matcher(charSequence).matches() ? charSequence : ""
        });

        save_btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            if (command.contains("save")) {
                intent.putExtra("command",command.replace("save",""));
                intent.putExtra("saveAs",name.getText() + "");
            }else if (command.contains("load")){
                intent.putExtra("load",name.getText() + "");
            }
            setResult(RESULT_OK,intent);
            finish();
        });

        close_btn.setOnClickListener(v -> {
            if (command.contains("save")) {
                Intent intent = new Intent();
                intent.putExtra("command",command.replace("save",""));
                setResult(RESULT_OK,intent);
            }
            finish();
        });

        delete_preference.setOnClickListener(view -> {
            String name = this.name.getText() + "";
            new AlertDialog.Builder(this).setTitle("삭제").setMessage(name + "을(를) 삭제하시겠습니까?")
                    .setPositiveButton("예", (dialogInterface, i) -> {
                        handler.removePreference(name);
                        preferences.remove(name);
                        adapter.notifyDataSetChanged();
                        this.name.setText((String) names.getSelectedItem());
                    }).setNegativeButton("아니요",(dialogInterface, i) -> {}).show();
        });

    }
}
