package com.example.wolfappremaster;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

public class CharacterAdapter extends ArrayAdapter<CharacterItem> {

    private final List<CharacterItem> items;
    private final MainActivity context;

    public CharacterAdapter(@NonNull Context context, int resource, @NonNull List<CharacterItem> objects) {
        super(context, resource, objects);
        this.items = objects;
        this.context = (MainActivity) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_item,null);
        }
        CharacterItem item = items.get(position);
        Character character = item.getCharacter();

        TextView tv = v.findViewById(R.id.character_name);
        Button btn = v.findViewById(R.id.count_btn);
        String text = character.getOrder().equals("") ? character.getText() : character.getOrder().replace(" ","") + "." + character.getText();

        tv.setText(text);
        btn.setText(item.getCount() > 0 ? item.getCount() + "" : "");

        btn.setOnClickListener(view -> {
            if (item.getCount() >= item.getCharacter().getMaxCount()) item.setCount(0);
            else item.setCount(item.getCount() + 1);
            Log.d("string",item.getCount() + "");
            if (item.getCount() > 0) {
                btn.setText(item.getCount() + "");
                context.addPool(item);
            }else {
                btn.setText("");
                context.removePool(item);
            }
        });

        v.setOnClickListener(view -> {
            context.setViewingCharacter(item);
        });
        return v;
    }
}
