package com.example.arshit_emotilog;

// create emotion count object
public class EmotionCount {
    private final String symbol;
    private final String name;
    public int count;

    // constructor
    public EmotionCount(String symbol, String name, int count) {
        this.symbol = symbol;
        this.name = name;
        this.count = count;
    }

    // getters
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
