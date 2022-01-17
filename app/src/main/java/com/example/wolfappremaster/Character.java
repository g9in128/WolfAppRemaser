package com.example.wolfappremaster;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Character {



    private static Resources system;


    public static void initResource(Context context) {
        system = context.getResources();

        Speech WOLF_SPEECH =
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
                });

        DOPPELGANGER.initCharacter(system.getString(R.string.doppelganger),
                new Speech(system.getString(R.string.speech_doppelganger), "1", (speech, characters) -> {
                String str = characters.stream().filter(character -> character.dopDirect == DOP_DIRECT).map(character -> character.text).collect(Collectors.joining(","));
                return speech.replace("{}",str);
                }));

        WEREWOLF.initCharacter(system.getString(R.string.werewolf),WOLF_SPEECH);

        MINION.initCharacter(system.getString(R.string.minion),
                new Speech(system.getString(R.string.speech_minion),"3"),
                    new Speech(system.getString(R.string.werewolf_hands_down),"3 ~~",2));

        DOP_MINION.initCharacter(system.getString(R.string.dop_minion),
                new Speech(system.getString(R.string.speech_dop_minion),"3 ~"));

        MASON.initCharacter(system.getString(R.string.mason),
                new Speech(system.getString(R.string.speech_mason),"4",(speech, characters) ->{
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
        SEER.initCharacter(system.getString(R.string.seer),
                new Speech(system.getString(R.string.speech_seer),"5"));

        ROBBER.initCharacter(system.getString(R.string.robber),
                new Speech(system.getString(R.string.speech_robber),"6"));

        TROUBLEMAKER.initCharacter(system.getString(R.string.troublemaker),
                new Speech(system.getString(R.string.speech_troublemaker),"7"));

        DRUNK.initCharacter(system.getString(R.string.drunk),
                new Speech(system.getString(R.string.speech_drunk),"8"));

        INSOMNIAC.initCharacter(system.getString(R.string.insomniac),
                new Speech(system.getString(R.string.speech_insomniac),"9"));

        DOP_INSOMNIAC.initCharacter(system.getString(R.string.dop_insomniac),
                new Speech(system.getString(R.string.speech_dop_insomniac),"9 ~"));

        TANNER.initCharacter(system.getString(R.string.tanner),
                new Speech(system.getString(R.string.speech_tanner),""));

        HUNTER.initCharacter(system.getString(R.string.hunter),
                new Speech(system.getString(R.string.speech_hunter),""));

        VILLAGER.initCharacter(system.getString(R.string.villager),
                new Speech(system.getString(R.string.speech_villager),""));



        SENTINEL.initCharacter(system.getString(R.string.sentinel),
                new Speech(system.getString(R.string.speech_sentinel),"0"));

        ALPHA_WOLF.initCharacter(system.getString(R.string.alpha_wolf),WOLF_SPEECH
            ,new Speech(system.getString(R.string.speech_alpha_wolf),"2 B"));

        MYSTIC_WOLF.initCharacter(system.getString(R.string.mystic_wolf),WOLF_SPEECH
                ,new Speech(system.getString(R.string.speech_mystic_wolf),"2 C"));

        APPRENTICE_SEER.initCharacter(system.getString(R.string.apprentice_seer),
                    new Speech(system.getString(R.string.speech_apprentice_seer),"5 B"));

        PARANORMAL_INVESTIGATOR.initCharacter(system.getString(R.string.paranormal_investigator),
                new Speech(system.getString(R.string.speech_paranormal_investigator),"5 C"));

        WITCH.initCharacter(system.getString(R.string.witch),
                new Speech(system.getString(R.string.speech_witch),"6 B"));

        VILLAGE_IDIOT.initCharacter(system.getString(R.string.village_idiot),
                    new Speech(system.getString(R.string.speech_village_idiot),"7 B"));

        REVEALER.initCharacter(system.getString(R.string.revealer),
                new Speech(system.getString(R.string.speech_revealer),"10"));

        DOP_REVEALER.initCharacter(system.getString(R.string.dop_revealer),
                    new Speech(system.getString(R.string.speech_dop_revealer),"10 ~"));

        CURATOR.initCharacter(system.getString(R.string.curator),
                new Speech(system.getString(R.string.speech_curator),"11"));

        DOP_CURATOR.initCharacter(system.getString(R.string.dop_curator),
                    new Speech(system.getString(R.string.speech_dop_curator),"11 ~"));

        BODYGUARD.initCharacter(system.getString(R.string.bodyguard),
                new Speech(system.getString(R.string.speech_bodyguard),""));
    }



    public static final int NOTHING = 0,DOP_DIRECT = 4,ANOTHER_ROUND = 8;

    public static final Character DOPPELGANGER = new Character("doppelganger");

    public static final Character WEREWOLF = new Character("werewolf");

    public static final Character MINION = new Character("minion",ANOTHER_ROUND);

    public static final Character DOP_MINION = new Character("dop_minion",NOTHING,0);

    public static final Character MASON = new Character("mason",NOTHING,2);

    public static final Character SEER = new Character("seer",DOP_DIRECT);

    public static final Character ROBBER =new Character("robber",DOP_DIRECT);

    public static final Character TROUBLEMAKER = new Character("troublemaker",DOP_DIRECT,1,7);

    public static final Character DRUNK = new Character("drunk",DOP_DIRECT);

    public static final Character INSOMNIAC = new Character("insomniac",ANOTHER_ROUND);

    public static final Character DOP_INSOMNIAC = new Character("dop_insomniac",NOTHING,0);

    public static final Character TANNER = new Character("tanner");

    public static final Character HUNTER = new Character("hunter");

    public static final Character VILLAGER = new Character("villager",NOTHING,3);






    public static final Character SENTINEL = new Character("sentinel",DOP_DIRECT);

    public static final Character ALPHA_WOLF = new Character("alpha_wolf",DOP_DIRECT);

    public static final Character MYSTIC_WOLF = new Character("mystic_wolf",DOP_DIRECT);

    public static final Character APPRENTICE_SEER = new Character("apprentice_seer",DOP_DIRECT);

    public static final Character PARANORMAL_INVESTIGATOR = new Character("paranormal_investigator",DOP_DIRECT);

    public static final Character WITCH = new Character("witch",DOP_DIRECT);

    public static final Character VILLAGE_IDIOT = new Character("village_idiot",DOP_DIRECT,1,10);

    public static final Character REVEALER = new Character("revealer",ANOTHER_ROUND);

    public static final Character DOP_REVEALER = new Character("dop_revealer",NOTHING,0);

    public static final Character CURATOR = new Character("curator",ANOTHER_ROUND);

    public static final Character DOP_CURATOR = new Character("dop_curator",NOTHING,0);
            
    public static final Character BODYGUARD = new Character("bodyguard");

    public static final Character[] originalCharacters =
            {DOPPELGANGER,WEREWOLF,MINION,DOP_MINION,MASON,SEER,ROBBER,TROUBLEMAKER,DRUNK,
                    INSOMNIAC,DOP_INSOMNIAC,TANNER,HUNTER,VILLAGER};

    public static final Character[] daybreakCharacters =
            {SENTINEL,ALPHA_WOLF,MYSTIC_WOLF,APPRENTICE_SEER,PARANORMAL_INVESTIGATOR,
                    WITCH,VILLAGE_IDIOT,REVEALER,DOP_REVEALER,CURATOR,DOP_CURATOR,BODYGUARD};

    public static Character getCharacter(String order) {
        List<Character> list = new ArrayList<>(Arrays.asList(originalCharacters));
        list.addAll(Arrays.asList(daybreakCharacters));
        for (Character i : list) {
            if (i.getOrder().equals(order)) return i;
        }return null;
    }



    private final String name;
    private String text,order;
    private final int dopDirect;
    private final int maxCount;
    private final int waitingTime;

    private final HashMap<String,Speech> speeches;

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", order='" + order + '\'' +
                ", dopDirect=" + dopDirect +
                ", maxCount=" + maxCount +
                "=" + waitingTime +
                ", speeches=" + speeches +
                '}';
    }

    public Character(String name) {
        this(name,0,1,5);
    }

    public Character(String name,int dopDirect){
        this(name,dopDirect,1,5);
    }

    public Character(String name,int dopDirect,int maxCount) {
        this(name,dopDirect,maxCount,5);
    }

    public Character(String name,int dopDirect, int maxCount, int waitingTime) {
        this.name = name;
        this.dopDirect = dopDirect;
        this.maxCount = maxCount;
        this.waitingTime = waitingTime;
        this.speeches = new HashMap<>();
    }

    private void initCharacter(String text,Speech... speeches) {
        if (!text.contains(".")) {
            order =  "";
            this.text = text;
        }
        else {
            order =  text.split("\\.")[0];
            this.text = text.split("\\.")[1];
        }
        for(Speech i : speeches) {
            this.speeches.put(i.getOrder(),i);
        }
        if(!this.speeches.isEmpty()) {
            String str = this.text;
            char lastchar = text.charAt(text.length() - 1);
            if (lastchar > 0xAC00 && lastchar < 0xD7A3) {
                str += (lastchar - 0xAC00) % 28 > 0 ? "은" : "는";
            }
            String order = this.order;
            if (!order.contains(" "))  order += " ";
            this.speeches.put(order + ":",new Speech(system.getString(R.string.close_eyes).replace("{}",str),order + ":",2));
        }
    }


    public String getOrder() {
        return order;
    }

    public int getDopDirect() {
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
