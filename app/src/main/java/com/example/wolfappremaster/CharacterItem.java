package com.example.wolfappremaster;

public class CharacterItem {

    private String character,speech;
    private int count,waitingTime;

    public CharacterItem(String character, String speech, int count, int waitingTime) {
        this.character = character;
        this.speech = speech;
        this.count = count;
        this.waitingTime = waitingTime;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
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
}
