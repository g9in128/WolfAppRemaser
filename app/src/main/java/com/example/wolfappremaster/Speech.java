package com.example.wolfappremaster;

public class Speech {

    private String speech;
    private String order;
    private int waitingTime;

    public Speech(String speech, String order) {
        this.speech = speech;
        this.order = order;
        this.waitingTime = waitingTime;
    }

    public Speech(String speech, String order, Format.FormatFunc format) {
        this.speech = speech;
        this.order = order;
        this.waitingTime = waitingTime;
        Format.add(order,format);
    }

    @Override
    public String toString() {
        return "Speech{" +
                "speech='" + speech + '\'' +
                ", order='" + order + '\'' +
                '}';
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
