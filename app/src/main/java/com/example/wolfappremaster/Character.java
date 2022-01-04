package com.example.wolfappremaster;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Character {

    private static Resources system;

    public static void initResource(Context context) {
        system = context.getResources();

        DOPPELGANGER.initCharacter(system.getString(R.string.doppelganger),new Speech(system.getString(R.string.speech_doppelganger),
                    "1", (speech, characters) -> {
                String str = characters.stream().filter(character -> character.dopDirect).map(character -> character.text).collect(Collectors.joining(","));
                return speech.replace("{}",str);
        }));

        WEREWOLF.initCharacter(system.getString(R.string.werewolf),new Speech(system.getString(R.string.speech_werewolf),"2",(speech, characters) ->{
                if (!characters.contains(DOPPELGANGER)) {
                    String str = "";
                    boolean add = true;
                    for (char i : speech.toCharArray()) {
                        if (i == '{'&& add) add = false;
                        if (add) str += i;
                        if (i == '}'&& !add) add = true;
                    }
                    return str;
                }
                return speech.replace("{","").replace("}","");
        }));
        MINION.initCharacter(system.getString(R.string.minion),new Speech(system.getString(R.string.speech_minion),"3"),
                    new Speech(system.getString(R.string.hands_down),"3~~"));

        DOP_MINION.initCharacter(system.getString(R.string.dop_minion),new Speech(system.getString(R.string.speech_dop_minion),"3~"));

        MASON.initCharacter(system.getString(R.string.mason),new Speech(system.getString(R.string.speech_mason),"4",(speech, characters) ->{
                if (!characters.contains(DOPPELGANGER)) {
                    String str = "";
                    boolean add = true;
                    for (char i : speech.toCharArray()) {
                        if (i == '{'&& add) add = false;
                        if (add) str += i;
                        if (i == '}'&& !add) add = true;
                    }
                    return str;
                }
                return speech.replace("{","").replace("}","");
            }));
        SEER.initCharacter(system.getString(R.string.seer),new Speech(system.getString(R.string.speech_seer),"5"));

        ROBBER.initCharacter(system.getString(R.string.robber),new Speech(system.getString(R.string.speech_robber),"6"));

        TROUBLEMAKER.initCharacter(system.getString(R.string.troublemaker),
                    new Speech(system.getString(R.string.speech_troublemaker),"7"));

        DRUNK.initCharacter(system.getString(R.string.drunk),new Speech(system.getString(R.string.speech_drunk),"8"));

        INSOMNIAC.initCharacter(system.getString(R.string.insomniac),new Speech(system.getString(R.string.speech_insomniac),"9"));

        DOP_INSOMNIAC.initCharacter(system.getString(R.string.dop_insomniac),
                    new Speech(system.getString(R.string.speech_dop_insomniac),"9~"));

        TANNER.initCharacter(system.getString(R.string.tanner));

        HUNTER.initCharacter(system.getString(R.string.hunter));

        VILLAGER.initCharacter(system.getString(R.string.villager));

        SENTINEL.initCharacter(system.getString(R.string.sentinel),new Speech(system.getString(R.string.speech_sentinel),"0"));

        ALPHA_WOLF.initCharacter(system.getString(R.string.alpha_wolf),
                    new Speech(system.getString(R.string.speech_werewolf),"2",(speech, characters) ->{
                if (!characters.contains(DOPPELGANGER)) {
                    String str = "";
                    boolean add = true;
                    for (char i : speech.toCharArray()) {
                        if (i == '{'&& add) add = false;
                        if (add) str += i;
                        if (i == '}'&& !add) add = true;
                    }
                    return str;
                }
                return speech.replace("{","").replace("}","");
            }),new Speech(system.getString(R.string.speech_alpha_wolf),"2B"));

        MYSTIC_WOLF.initCharacter(system.getString(R.string.mystic_wolf),
                    new Speech(system.getString(R.string.speech_werewolf),"2",(speech, characters) ->{
                if (!characters.contains(DOPPELGANGER)) {
                    String str = "";
                    boolean add = true;
                    for (char i : speech.toCharArray()) {
                        if (i == '{'&& add) add = false;
                        if (add) str += i;
                        if (i == '}'&& !add) add = true;
                    }
                    return str;
                }
                return speech.replace("{","").replace("}","");
            }),new Speech(system.getString(R.string.speech_mystic_wolf),"2C"));

        APPRENTICE_SEER.initCharacter(system.getString(R.string.apprentice_seer),
                    new Speech(system.getString(R.string.speech_apprentice_seer),"5B"));

        PARANORMAL_INVESTIGATOR.initCharacter(system.getString(R.string.paranormal_investigator),new Speech(system.getString(R.string.speech_paranormal_investigator),"5C"));

        WITCH.initCharacter(system.getString(R.string.witch),new Speech(system.getString(R.string.speech_witch),"6B"));

        VILLAGE_IDIOT.initCharacter(system.getString(R.string.village_idiot),
                    new Speech(system.getString(R.string.speech_village_idiot),"7B"));

        REVEALER.initCharacter(system.getString(R.string.revealer),new Speech(system.getString(R.string.speech_revealer),"10"));

        DOP_REVEALER.initCharacter(system.getString(R.string.dop_revealer),
                    new Speech(system.getString(R.string.speech_revealer),"10~"));

        CURATOR.initCharacter(system.getString(R.string.curator),new Speech(system.getString(R.string.speech_curator),"11"));

        DOP_CURATOR.initCharacter(system.getString(R.string.dop_curator),
                    new Speech(system.getString(R.string.speech_dop_curator),"11~"));

        BODYGUARD.initCharacter(system.getString(R.string.bodyguard));



    }

    public static final Character DOPPELGANGER = new Character("doppelganger");

    public static final Character WEREWOLF = new Character("werewolf");

    public static final Character MINION = new Character("minion");

    public static final Character DOP_MINION = new Character("dop_minion",false,0);

    public static final Character MASON = new Character("mason",false,2);


    public static final Character SEER = new Character("seer",true);

    public static final Character ROBBER =new Character("robber",true);

    public static final Character TROUBLEMAKER = new Character("troublemaker",true);


    public static final Character DRUNK = new Character("drunk",true);

    public static final Character INSOMNIAC = new Character("insomniac");


    public static final Character DOP_INSOMNIAC = new Character("dop_insomniac",false,0);


    public static final Character TANNER = new Character("tanner");


    public static final Character HUNTER = new Character("hunter");


    public static final Character VILLAGER = new Character("villager");






    public static final Character SENTINEL = new Character("sentinel",true);


    public static final Character ALPHA_WOLF = new Character("alpha_wolf");


    public static final Character MYSTIC_WOLF = new Character("mystic_wolf",true);


    public static final Character APPRENTICE_SEER = new Character("apprentice_seer",true);

    public static final Character PARANORMAL_INVESTIGATOR = new Character("paranormal_investigator",true);

    public static final Character WITCH = new Character("witch",true);

    public static final Character VILLAGE_IDIOT = new Character("village_idiot",true);

    public static final Character REVEALER = new Character("revealer");

    public static final Character DOP_REVEALER = new Character("dop_revealer",false,0);

    public static final Character CURATOR = new Character("curator");

    public static final Character DOP_CURATOR = new Character("dop_curator");
            
    public static final Character BODYGUARD = new Character("bodyguard");

    public static final Character[] originalCharacters =
            {DOPPELGANGER,WEREWOLF,MINION,DOP_MINION,MASON,SEER,ROBBER,TROUBLEMAKER,DRUNK,
                    INSOMNIAC,DOP_INSOMNIAC,TANNER,HUNTER,VILLAGER};

    public static final Character[] daybreakCharacters =
            {SENTINEL,ALPHA_WOLF,MYSTIC_WOLF,APPRENTICE_SEER,PARANORMAL_INVESTIGATOR,
                    WITCH,VILLAGE_IDIOT,REVEALER,DOP_REVEALER,CURATOR,DOP_CURATOR,BODYGUARD};



    private final String name;
    private String text,order;
    private final boolean dopDirect;
    private final int maxCount;
    private final int waitingTime;

    private final HashMap<String,Speech> speeches;

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", order='" + order + '\'' +
                ", maxCount=" + maxCount +
                ", waitingTime=" + waitingTime +
                '}';
    }

    public Character(String name) {
        this(name,false,1,5);
    }

    public Character(String name,boolean dopDirect){
        this(name,dopDirect,1,5);
    }

    public Character(String name,boolean dopDirect,int maxCount) {
        this(name,dopDirect,maxCount,5);
    }

    public Character(String name,boolean dopDirect, int maxCount, int waitingTime) {
        this.name = name;
        this.dopDirect = dopDirect;
        this.maxCount = maxCount;
        this.waitingTime = waitingTime;
        this.speeches = new HashMap<>();
    }

    private void initCharacter(String text,Speech... speeches) {
        Log.d("string",text.split("\\.").length + "");
        if (!text.contains(".")) {
            order =  "";
            this.text = text;
        }
        else {
            order =  text.split("\\.")[0];
            this.text = text.split("\\.")[1];
        }
        for(Speech i : this.speeches.values()) {
            this.speeches.put(i.getOrder(),i);
        }
        if(!this.speeches.isEmpty()) {
            char lastchar = text.charAt(text.length() - 1);
            if (lastchar > 0xAC00 && lastchar < 0xD7A3) {
                text += (lastchar - 0xAC00) % 28 > 0 ? "은" : "는";
            }
            this.speeches.put(order + ":",new Speech(system.getString(R.string.close_eye).replace("{}",text),order + ":"));
        }
    }


    public String getOrder() {
        return order;
    }

    public boolean isDopDirect() {
        return dopDirect;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public HashMap<String, Speech> getSpeeches() {
        return new HashMap<>(speeches);
    }
}
