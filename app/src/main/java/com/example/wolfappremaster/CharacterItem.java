package com.example.wolfappremaster;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class CharacterItem {

    private transient Character character;
    private int count,waitingTime;
    private HashMap<String,Speech> speeches;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CharacterItem(Character character, int waitingTime, Speech... speeches) {
        this.character = character;
        this.count = 0;
        this.waitingTime = waitingTime;
        this.speeches = new HashMap<>();
        character.getSpeeches().values().forEach(speech -> {
            this.speeches.put(speech.getOrder(), (Speech) speech.clone());
        });
        for(Speech i : speeches) {
            if (i.getOrder().equals(getCharacter().getOrder())) {
                i.setWaitingTime(waitingTime);
            }
            this.speeches.put(i.getOrder(),i);
        }
    }

    @Override
    public String toString() {
        return "CharacterItem{" +
                "character=" + character +
                ", count=" + count +
                ", waitingTime=" + waitingTime +
                ", speeches=" + speeches +
                '}';
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
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

    public void setSpeeches(HashMap<String, Speech> speeches) {
        this.speeches = speeches;
        Log.d("string",toString());
    }
}
