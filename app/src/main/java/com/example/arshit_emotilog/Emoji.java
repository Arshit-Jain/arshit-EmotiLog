package com.example.arshit_emotilog;

import java.util.HashMap;

// this class helps in create emojis and it's objects
public class Emoji {
    private final String symbol;
    private final String name;

    // Store all potential emojis
    private static final HashMap<String, String> potentialEmojis = new HashMap<>();

    // loading default emojis
    static {
        potentialEmojis.put("😊", "Happy");
        potentialEmojis.put("😢", "Sad");
        potentialEmojis.put("😠", "Angry");
        potentialEmojis.put("😴", "Tired");
        potentialEmojis.put("😭", "Crying");
        potentialEmojis.put("🤔", "Thinking");
        potentialEmojis.put("🥳", "Celebrating");
        potentialEmojis.put("😜", "Playful");
    }

    // constructor
    public Emoji(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    // getters
    public String getSymbol() { return symbol; }
    public String getName() { return name; }


    // getting all emojis as HashMap
    public static HashMap<String, String> getAllEmojis() {
        return new HashMap<>(potentialEmojis);
    }


    // getting all emojis as objects
    public static Emoji[] getAllEmojiObjects() {
        HashMap<String, String> all = getAllEmojis();
        Emoji[] result = new Emoji[all.size()];
        int index = 0;
        for (String symbol : all.keySet()) {
            result[index++] = new Emoji(symbol, all.get(symbol));
        }
        return result;
    }
}