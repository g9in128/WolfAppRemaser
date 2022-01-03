package com.example.wolfappremaster;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class CharacterItem {

    private final Character character;
    private int count,waitingTime;
    private final HashMap<String,Speech> speeches;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CharacterItem(Character character, int waitingTime, Speech... speeches) {
        this.character = character;
        this.count = 0;
        this.waitingTime = waitingTime;
        this.speeches = character.getSpeeches();
        for(Speech i : speeches) {
            this.speeches.put(i.getOrder(),i);
        }
    }

    public Character getCharacter() {
        return character;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public HashMap<String, Speech> getSpeeches() {
        return speeches;
    }
}
