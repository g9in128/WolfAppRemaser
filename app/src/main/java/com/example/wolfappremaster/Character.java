package com.example.wolfappremaster;

import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Character {

    private static Character newCharacter(String name,String text,Speech... speeches) {
        return newCharacter(name,text,false,1,5,speeches);
    }

    private static Character newCharacter(String name,String text, boolean dopDirect,Speech... speeches) {
        return newCharacter(name, text, dopDirect,1,5,speeches);
    }

    private static Character newCharacter(String name,String text,int maxCount,Speech... speeches) {
        return newCharacter(name,text,false,maxCount,5,speeches);
    }

    private static Character newCharacter(String name,String text,int maxCount,int waitingTime,Speech... speeches) {
        return newCharacter(name,text,false,maxCount,waitingTime,speeches);
    }

    private static Character newCharacter(String name,String text,boolean dopDirect,int maxCount,int waitingTime,Speech... speeches) {
        String order;
        if (!name.contains(".")) order =  "";
        else {
            order =  name.split(".")[0];
            text = name.split(".")[1];
        }
        return new Character(name,text,order,dopDirect,maxCount,waitingTime,speeches);
    }




    private static Resources system = Resources.getSystem();

    public static final Character DOPPELGANGER =
            newCharacter("doppelganger",system.getString(R.string.doppelganger),new Speech(system.getString(R.string.speech_doppelganger),"1", (speech, characters) -> {
                String str = characters.stream().filter(character -> character.dopDirect).map(character -> character.text).collect(Collectors.joining(","));
                return speech.replace("{}",str);
            }));

    public static final Character WEREWOLF =
            newCharacter("werewolf",system.getString(R.string.werewolf),new Speech(system.getString(R.string.speech_werewolf),"2",(speech, characters) ->{
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

    public static final Character MINION =
            newCharacter("minion",system.getString(R.string.minion),new Speech(system.getString(R.string.speech_minion),"3"),new Speech(system.getString(R.string.hands_down),"3~~"));

    public static final Character DOP_MINION =
            newCharacter("dop_minion",system.getString(R.string.dop_minion),0,new Speech(system.getString(R.string.speech_dop_minion),"3~"));



    public static final Character MASON =
            newCharacter("mason",system.getString(R.string.mason),2,new Speech(system.getString(R.string.speech_mason),"4",(speech, characters) ->{
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


    public static final Character SEER =
            newCharacter("seer",system.getString(R.string.seer),true,new Speech(system.getString(R.string.speech_seer),"5"));

    public static final Character ROBBER =
            newCharacter("robber",system.getString(R.string.robber),true,new Speech(system.getString(R.string.speech_robber),"6"));

    public static final Character TROUBLEMAKER =
            newCharacter("troublemaker",system.getString(R.string.troublemaker),true,1,8,new Speech(system.getString(R.string.speech_troublemaker),"7"));

    public static final Character DRUNK =
            newCharacter("drunk",system.getString(R.string.drunk),true,new Speech(system.getString(R.string.speech_drunk),"8"));

    public static final Character INSOMNIAC =
            newCharacter("insomniac",system.getString(R.string.insomniac),new Speech(system.getString(R.string.speech_insomniac),"9"));

    public static final Character DOP_INSOMNIAC =
            newCharacter("dop_insomniac",system.getString(R.string.dop_insomniac),0,new Speech(system.getString(R.string.speech_dop_insomniac),"9~"));

    public static final Character TANNER =
            newCharacter("tanner",system.getString(R.string.tanner));

    public static final Character HUNTER =
            newCharacter("hunter",system.getString(R.string.hunter));

    public static final Character VILLAGER =
            newCharacter("villager",system.getString(R.string.villager),3);





    public static final Character SENTINEL =
            newCharacter("sentinel",system.getString(R.string.sentinel),true,new Speech(system.getString(R.string.speech_sentinel),"0"));

    public static final Character ALPHA_WOLF =
            newCharacter("alpha_wolf",system.getString(R.string.alpha_wolf),true,new Speech(system.getString(R.string.speech_werewolf),"2",(speech, characters) ->{
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

    public static final Character MYSTIC_WOLF =
            newCharacter("mystic_wolf",system.getString(R.string.mystic_wolf),true,new Speech(system.getString(R.string.speech_werewolf),"2",(speech, characters) ->{
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

    public static final Character APPRENTICE_SEER =
            newCharacter("apprentice_seer",system.getString(R.string.apprentice_seer),true,new Speech(system.getString(R.string.speech_apprentice_seer),"5B"));

    public static final Character PARANORMAL_INVESTIGATOR =
            newCharacter("paranormal_investigator",system.getString(R.string.paranormal_investigator),true,new Speech(system.getString(R.string.speech_paranormal_investigator),"5C"));

    public static final Character WITCH =
            newCharacter("witch",system.getString(R.string.witch),true,new Speech(system.getString(R.string.speech_witch),"6B"));

    public static final Character VILLAGE_IDIOT =
            newCharacter("village_idiot",system.getString(R.string.village_idiot),true,1,10,new Speech(system.getString(R.string.speech_village_idiot),"7B"));

    public static final Character REVEALER =
            newCharacter("revealer",system.getString(R.string.revealer),new Speech(system.getString(R.string.speech_revealer),"10"));

    public static final Character DOP_REVEALER =
            newCharacter("dop_revealer",system.getString(R.string.dop_revealer),0,new Speech(system.getString(R.string.speech_revealer),"10~"));

    public static final Character CURATOR =
            newCharacter("curator",system.getString(R.string.curator),new Speech(system.getString(R.string.speech_curator),"11"));

    public static final Character DOP_CURATOR =
            newCharacter("dop_curator", system.getString(R.string.dop_curator),0,new Speech(system.getString(R.string.speech_dop_curator),"11~"));

    public static final Character BODYGUARD =
            newCharacter("bodyguard",system.getString(R.string.bodyguard));

    public static final Character[] originalCharacters =
            {DOPPELGANGER,WEREWOLF,MINION,DOP_MINION,MASON,SEER,ROBBER,TROUBLEMAKER,DRUNK,
                    INSOMNIAC,DOP_INSOMNIAC,TANNER,HUNTER,VILLAGER};

    public static final Character[] daybreakCharacters =
            {SENTINEL,ALPHA_WOLF,MYSTIC_WOLF,APPRENTICE_SEER,PARANORMAL_INVESTIGATOR,
                    WITCH,VILLAGE_IDIOT,REVEALER,DOP_REVEALER,CURATOR,DOP_CURATOR,BODYGUARD};



    private final String name,text,order;
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

    public Character(String name,String text, String order,boolean dopDirect, int maxCount, int waitingTime,Speech... speeches) {
        this.name = name;
        this.text = text;
        this.order = order;
        this.dopDirect = dopDirect;
        this.maxCount = maxCount;
        this.waitingTime = waitingTime;
        this.speeches = new HashMap<>();
        for(Speech i : speeches) {
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
