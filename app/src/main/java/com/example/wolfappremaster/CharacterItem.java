package com.example.wolfappremaster;

import java.util.HashMap;

public class CharacterItem {

    private Character character;
    private String order;
    private int count,waitingTime;
    private HashMap<String,Speech> speeches;

    public CharacterItem(Character character, String order, int count, int waitingTime, Speech... speeches) {
        this.character = character;
        this.order = order;
        this.count = count;
        this.waitingTime = waitingTime;
        this.speeches = new HashMap<>();
        for(Speech i : speeches) {
            this.speeches.put(i.getOrder(),i);
        }
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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
