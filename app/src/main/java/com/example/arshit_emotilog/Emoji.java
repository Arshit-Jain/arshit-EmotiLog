package com.example.arshit_emotilog;

public class Emoji {
    private final String symbol;
    private final String name;


    // Constructor
    public Emoji(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    // Getters only
    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }

    // All emoji
    public static Emoji[] getDefaultEmojis() {
        return new Emoji[]{
                new Emoji("😊", "Happy"),
                new Emoji("😢", "Sad"),
                new Emoji("😠", "Angry"),
                new Emoji("😰", "Anxious"),
                new Emoji("😍", "Love"),
                new Emoji("😴", "Tired"),
                new Emoji("🎉", "Excited"),
                new Emoji("😭", "Crying")
        };
    }

    // Get emoji count
    public static int getEmojiCount() {
        return getDefaultEmojis().length;
    }
}