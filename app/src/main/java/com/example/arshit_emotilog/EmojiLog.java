package com.example.arshit_emotilog;

import java.util.ArrayList;

public class EmojiLog {
    private final String emojiSymbol;
    private final String emojiName;
    private final long timestamp;

    // Static ArrayList to store all logs
    private static final ArrayList<EmojiLog> allLogs = new ArrayList<>();

    // Constructor
    public EmojiLog(String emojiSymbol, String emojiName, long timestamp) {
        this.emojiSymbol = emojiSymbol;
        this.emojiName = emojiName;
        this.timestamp = timestamp;
    }

    // Getters
    public String getEmojiSymbol() {
        return emojiSymbol;
    }

    public String getEmojiName() {
        return emojiName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Get formatted date and time
    public String getFormattedDateTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy hh:mm a", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date(timestamp));
    }

    // Get formatted date only
    public String getFormattedDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date(timestamp));
    }

    // Static method to add a log (adds at the beginning for latest first)
    public static void addLog(EmojiLog log) {
        allLogs.add(0, log);  // Add at index 0 (beginning of list)
    }

    // Static method to get all logs
    public static ArrayList<EmojiLog> getAllLogs() {
        return allLogs;
    }

    // Static method to get logs by date
    public static ArrayList<EmojiLog> getLogsByDate(String date) {
        ArrayList<EmojiLog> filteredLogs = new ArrayList<>();
        for (EmojiLog log : allLogs) {
            if (log.getFormattedDate().equals(date)) {
                filteredLogs.add(log);
            }
        }
        return filteredLogs;
    }
}