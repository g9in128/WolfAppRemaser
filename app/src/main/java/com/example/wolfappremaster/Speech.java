package com.example.wolfappremaster;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Speech {

    private String speech;
    private String order;
    private int waitingTime;

    public Speech(String speech, String order) {
        this(speech,order,5);
    }

    public Speech(String speech, String order, Format.FormatFunc format) {
        this(speech,order,5,format);
    }

    public Speech(String speech, String order,int waitingTime) {
        this.speech = speech;
        this.order = order;
        this.waitingTime = waitingTime;
    }

    public Speech(String speech, String order,int waitingTime, Format.FormatFunc format) {
        this(speech, order, waitingTime);
        Format.add(order,format);
    }

    @NonNull
    @Override
    protected Object clone() {
        return new Speech(speech,order,waitingTime);
    }

    @Override
    public String toString() {
        return "Speech{" +
                "speech='" + speech + '\'' +
                ", order='" + order + '\'' +
                ", waitingTime=" + waitingTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
//        Log.d("string","other : " + o.toString());
//        Log.d("string","this :" + toString());
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speech speech1 = (Speech) o;
        return waitingTime == speech1.waitingTime && Objects.equals(speech, speech1.speech) && Objects.equals(order, speech1.order);
    }

    public String getSpeech() {
        return speech;
    }


    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
