package com.example.wolfappremaster;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Speaker {


    private Context context;
    private TextToSpeech tts;
    private boolean paused;
    private boolean playing;
    private List<Speech> speeches;
    private List<Character> characters;
    private CountDownTimer timer;

    public Speaker(Context context) {
        this.context = context;
        paused = playing = false;
        tts = new TextToSpeech(context, i -> {
            Log.d("string",i + "");
            if (i == TextToSpeech.SUCCESS) {
                int res = tts.setLanguage(Locale.KOREAN);
                if (res < 0) Log.d("string", "한글 x");
            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                Log.d("string",s);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDone(String s) {
                Speech now = speeches.isEmpty() ? null : speeches.get(0);
                switch (s) {
                    case "wait":
                        if (paused) break;
                        if (now == null) tts.speak(context.getString(R.string.open_eyes_all),TextToSpeech.QUEUE_ADD,null,"end");
                        else {
                            String str = Format.get(now.getOrder()).format(now.getSpeech(),characters);
                            tts.speak(str,TextToSpeech.QUEUE_ADD,null,"sound");
                            Character character = Character.getCharacter(now.getOrder());
                            ((MainActivity)context).setScript(str);
                        }
                        break;
                    case "sound":
                        assert now != null;
                        tts.playSilentUtterance(now.getWaitingTime() * 1000l,TextToSpeech.QUEUE_ADD,"wait");
                        speeches.remove(0);
                        MainActivity main = ((MainActivity)context);
                        main.runOnUiThread(() -> timer = new CountDownTimer(now.getWaitingTime() * 1000l,100) {
                            @Override
                            public void onTick(long l) {
                                main.setTimeLeft(l,now.getWaitingTime());
                            }
                            @Override
                            public void onFinish() {
                                main.setTimeLeft(0l,1);
                            }
                        }.start());
                        break;
                    case "end":
                        endRead();
                        break;
                    default:
                }
            }

            @Override
            public void onError(String s) {}
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void endRead() {
        tts.stop();
        characters = null;
        speeches = null;
        if (timer != null) timer.cancel();
        timer = null;
        playing = false;
        MainActivity main = (MainActivity) context;
        main.setTimeLeft(0l,1);
        main.runOnUiThread(() -> {
            main.setModifyMode(true);
        });
    }

    public void shutdown() {
        tts.shutdown();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startRead(List<CharacterItem> list) {
        HashMap<String,Speech> map = new HashMap<>();
        characters = new ArrayList<>();
        list.forEach(item -> {
            map.putAll(item.getSpeeches());
            characters.add(item.getCharacter());
        });
        speeches = new ArrayList<>(map.values());
        speeches.sort(new OrderSortSp());
        playing = true;
        tts.speak(context.getString(R.string.close_eyes_all),TextToSpeech.QUEUE_ADD,null,"start");
        tts.playSilentUtterance(1000l,TextToSpeech.QUEUE_ADD,"wait");
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPaused(boolean paused) {
        if(!tts.isSpeaking() && this.paused && !paused) {
            Speech now = speeches.isEmpty() ? null : speeches.get(0);
            if (now == null) tts.speak(context.getString(R.string.open_eyes_all),TextToSpeech.QUEUE_ADD,null,"end");
            else {
                String str = Format.get(now.getOrder()).format(now.getSpeech(),characters);
                tts.speak(str,TextToSpeech.QUEUE_ADD,null,"sound");
            }
        }
        this.paused = paused;
    }
}
