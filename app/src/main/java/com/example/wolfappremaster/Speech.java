package com.example.wolfappremaster;

import java.util.List;

public class Speech {

    public interface Format {
        String format(String speech, List<Character> characters);
    }

    private String speech;
    private String order;
    private Format format;

    public Speech(String speech, String order) {
        this(speech,order,((speech1, characters) -> speech1));
    }

    public Speech(String speech, String order,Format format) {
        this.speech = speech;
        this.order = order;
        this.format = format;
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
}
