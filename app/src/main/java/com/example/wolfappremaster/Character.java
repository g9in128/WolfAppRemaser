package com.example.wolfappremaster;

import android.content.res.Resources;

public class Character {

    private static Character newCharacter(String name,String text) {
        return newCharacter(name,text,1,5);
    }

    private static Character newCharacter(String name,String text,int maxCount) {
        return newCharacter(name,text,maxCount,5);
    }

    private static Character newCharacter(String name,String text,int maxCount,int waitingTime) {
        String order;
        if (!name.contains(".")) order =  null;
        else order =  name.split(".")[0];
        return new Character(name,text,order,maxCount, waitingTime);
    }


    private static Resources system = Resources.getSystem();

    public static final Character DOPPELGANGER =
            newCharacter("doppelganger",system.getString(R.string.doppelganger));

    public static final Character WEREWOLF =
            newCharacter("werewolf",system.getString(R.string.werewolf));

    public static final Character MINION =
            newCharacter("minion",system.getString(R.string.minion));

    public static final Character MASON =
            newCharacter("mason",system.getString(R.string.mason),2);


    public static final Character SEER =
            newCharacter("seer",system.getString(R.string.seer));

    public static final Character ROBBER =
            newCharacter("robber",system.getString(R.string.robber));

    public static final Character TROUBLEMAKER =
            newCharacter("troublemaker",system.getString(R.string.troublemaker),1,8);

    public static final Character DRUNK =
            newCharacter("drunk",system.getString(R.string.drunk));

    public static final Character INSOMNIAC =
            newCharacter("insomniac",system.getString(R.string.insomniac));

    public static final Character TANNER =
            newCharacter("tanner",system.getString(R.string.tanner));

    public static final Character HUNTER =
            newCharacter("hunter",system.getString(R.string.hunter));

    public static final Character VILLAGER =
            newCharacter("villager",system.getString(R.string.villager),3);





    public static final Character SENTINEL =
            newCharacter("sentinel",system.getString(R.string.sentinel));

    public static final Character ALPHA_WOLF =
            newCharacter("alpha_wolf",system.getString(R.string.alpha_wolf));

    public static final Character MYSTIC_WOLF =
            newCharacter("mystic_wolf",system.getString(R.string.mystic_wolf));

    public static final Character APPRENTICE_WEER =
            newCharacter("apprentice_weer",system.getString(R.string.apprentice_weer));

    public static final Character PARANORMAL_INVESTIGATOR =
            newCharacter("paranormal_investigator",system.getString(R.string.paranormal_investigator));

    public static final Character WITCH =
            newCharacter("witch",system.getString(R.string.witch));

    public static final Character VILLAGE_IDIOT =
            newCharacter("village_idiot",system.getString(R.string.village_idiot),1,10);

    public static final Character REVEALER =
            newCharacter("revealer",system.getString(R.string.revealer));

    public static final Character CURATOR =
            newCharacter("curator",system.getString(R.string.curator));

    public static final Character BODYGUARD =
            newCharacter("bodyguard",system.getString(R.string.bodyguard));

    public static final Character[] originalCharacters =
            {DOPPELGANGER,WEREWOLF,MINION,MASON,SEER,ROBBER,TROUBLEMAKER,DRUNK,
                    INSOMNIAC,TANNER,HUNTER,VILLAGER};

    public static final Character[] daybreakCharacters =
            {SENTINEL,ALPHA_WOLF,MYSTIC_WOLF,APPRENTICE_WEER,PARANORMAL_INVESTIGATOR,
                    WITCH,VILLAGE_IDIOT,REVEALER,CURATOR,BODYGUARD};

    private final String name,text,order;
    private final int maxCount,waitingTime;

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

    public Character(String name,String text, String order, int maxCount, int waitingTime) {
        this.name = name;
        this.text = text;
        this.order = order;
        this.maxCount = maxCount;
        this.waitingTime = waitingTime;
    }

    public String getOrder() {
        return order;
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
}
