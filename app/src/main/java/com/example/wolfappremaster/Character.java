package com.example.wolfappremaster;

import android.content.res.Resources;
import android.provider.Settings;

public class Character {

    private static Character newCharacter(String name) {
        String order;
        if (!name.contains(".")) order =  null;
        order =  name.split(".")[0];
        return new Character(name,order,1);
    }

    private static Character newCharacter(String name,int maxCount) {
        String order;
        if (!name.contains(".")) order =  null;
        order =  name.split(".")[0];
        return new Character(name,order,maxCount);
    }


    private static Resources system = Resources.getSystem();

    public static final Character DOPPELGANGER =
            newCharacter(system.getString(R.string.doppelganger));

    public static final Character WEREWOLF =
            newCharacter(system.getString(R.string.werewolf));

    public static final Character MINION =
            newCharacter(system.getString(R.string.minion));

    public static final Character MASON =
            newCharacter(system.getString(R.string.mason),2);

    public static final Character SEER =
            newCharacter(system.getString(R.string.seer));

    public static final Character ROBBER =
            newCharacter(system.getString(R.string.robber));

    public static final Character TROUBLEMAKER =
            newCharacter(system.getString(R.string.troublemaker));

    public static final Character DRUNK =
            newCharacter(system.getString(R.string.drunk));

    public static final Character INSOMNIAC =
            newCharacter(system.getString(R.string.insomniac));

    public static final Character TANNER =
            newCharacter(system.getString(R.string.tanner));

    public static final Character HUNTER =
            newCharacter(system.getString(R.string.hunter));

    public static final Character VILLAGER =
            newCharacter(system.getString(R.string.villager),3);





    public static final Character SENTINEL =
            newCharacter(system.getString(R.string.sentinel));

    public static final Character ALPHA_WOLF =
            newCharacter(system.getString(R.string.alpha_wolf));

    public static final Character MYSTIC_WOLF =
            newCharacter(system.getString(R.string.mystic_wolf));

    public static final Character APPRENTICE_WEER =
            newCharacter(system.getString(R.string.apprentice_weer));

    public static final Character PARANORMAL_INVESTIGATOR =
            newCharacter(system.getString(R.string.paranormal_investigator));

    public static final Character WITCH =
            newCharacter(system.getString(R.string.witch));

    public static final Character VILLAGE_IDIOT =
            newCharacter(system.getString(R.string.village_idiot));

    public static final Character REVEALER =
            newCharacter(system.getString(R.string.revealer));

    public static final Character CURATOR =
            newCharacter(system.getString(R.string.curator));

    public static final Character BODYGUARD =
            newCharacter(system.getString(R.string.bodyguard));

    public static final Character[] originalCharacters =
            {DOPPELGANGER,WEREWOLF,MINION,MASON,SEER,ROBBER,TROUBLEMAKER,DRUNK,
                    INSOMNIAC,TANNER,VILLAGER};

    public static final Character[] daybreakCharacters =
            {SENTINEL,ALPHA_WOLF,MYSTIC_WOLF,APPRENTICE_WEER,PARANORMAL_INVESTIGATOR,
                    WITCH,VILLAGE_IDIOT,REVEALER,CURATOR,BODYGUARD};

    private final String name,order;
    private final int maxCount;

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", order='" + order + '\'' +
                ", maxCount=" + maxCount +
                '}';
    }

    public String getOrder() {
        return order;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public Character(String name, String order, int maxCount) {
        this.name = name;
        this.order = order;
        this.maxCount = maxCount;
    }

    public String getName() {
        return name;
    }
}
