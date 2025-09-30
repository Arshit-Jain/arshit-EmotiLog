package com.example.arshit_emotilog;

public class Emoji {
    private final String symbol;
    private final String name;
    private final String color; // Color for UI styling

    // Constructor
    public Emoji(String symbol, String name, String color) {
        this.symbol = symbol;
        this.name = name;
        this.color = color;
    }

    // Getters only
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    // All emoji
    public static Emoji[] getDefaultEmojis() {
        return new Emoji[]{
                new Emoji("😊", "Happy", "#FFD700"),      // Gold
                new Emoji("😢", "Sad", "#4169E1"),        // Royal Blue
                new Emoji("😠", "Angry", "#DC143C"),      // Crimson
                new Emoji("😰", "Anxious", "#9370DB"),    // Medium Purple
                new Emoji("😍", "Love", "#FF1493"),       // Deep Pink
                new Emoji("😴", "Tired", "#708090"),      // Slate Gray
                new Emoji("🎉", "Excited", "#FF6347"),     // Tomato
                new Emoji("😭", "Crying", "#1E90FF")      // Dodger Blue
        };
    }

    // Get emoji count
    public static int getEmojiCount() {
        return getDefaultEmojis().length;
    }
}