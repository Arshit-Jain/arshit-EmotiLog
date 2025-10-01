package com.example.arshit_emotilog.ui.notifications;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.arshit_emotilog.EmojiLog;
import com.example.arshit_emotilog.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private TextView dateText;
    private TextView totalCountText;
    private LinearLayout summaryContainer;
    private Button selectDateButton;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        dateText = view.findViewById(R.id.dateText);
        totalCountText = view.findViewById(R.id.totalCountText);
        summaryContainer = view.findViewById(R.id.summaryContainer);
        selectDateButton = view.findViewById(R.id.selectDateButton);

        // Set today's date as default
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        selectedDate = sdf.format(Calendar.getInstance().getTime());

        selectDateButton.setOnClickListener(v -> showDatePicker());

        loadSummary();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSummary();
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                    selectedDate = sdf.format(selectedCalendar.getTime());

                    loadSummary();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void loadSummary() {
        // Update date display
        SimpleDateFormat todaySdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String today = todaySdf.format(Calendar.getInstance().getTime());

        if (selectedDate.equals(today)) {
            dateText.setText("Today - " + selectedDate);
        } else {
            dateText.setText(selectedDate);
        }

        // Get logs for selected date
        ArrayList<EmojiLog> logsForDate = EmojiLog.getLogsByDate(selectedDate);

        // Calculate total count
        totalCountText.setText(String.valueOf(logsForDate.size()));

        // Calculate emotion counts
        HashMap<String, EmotionCount> emotionCounts = new HashMap<>();

        for (EmojiLog log : logsForDate) {
            String emojiName = log.getEmojiName();
            String emojiSymbol = log.getEmojiSymbol();

            if (emotionCounts.containsKey(emojiName)) {
                emotionCounts.get(emojiName).count++;
            } else {
                emotionCounts.put(emojiName, new EmotionCount(emojiSymbol, emojiName, 1));
            }
        }

        // Display emotion breakdown
        displayEmotionBreakdown(emotionCounts);
    }

    private void displayEmotionBreakdown(HashMap<String, EmotionCount> emotionCounts) {
        summaryContainer.removeAllViews();

        if (emotionCounts.isEmpty()) {
            TextView noDataText = new TextView(getContext());
            noDataText.setText("No emotions logged for this date");
            noDataText.setTextSize(16);
            noDataText.setTextColor(getResources().getColor(android.R.color.darker_gray));
            noDataText.setPadding(16, 32, 16, 16);
            summaryContainer.addView(noDataText);
            return;
        }

        // Convert to list and sort by count (highest to lowest)
        ArrayList<EmotionCount> sortedEmotions = new ArrayList<>(emotionCounts.values());
        sortedEmotions.sort((e1, e2) -> Integer.compare(e2.count, e1.count));

        // Display sorted emotions
        for (EmotionCount emotionCount : sortedEmotions) {
            View summaryItem = createSummaryItemView(emotionCount);
            summaryContainer.addView(summaryItem);
        }
    }

    private View createSummaryItemView(EmotionCount emotionCount) {
        View summaryItem = LayoutInflater.from(getContext()).inflate(R.layout.summary_item, summaryContainer, false);

        TextView emojiSymbol = summaryItem.findViewById(R.id.summaryEmojiSymbol);
        TextView emojiName = summaryItem.findViewById(R.id.summaryEmojiName);
        TextView emojiCountText = summaryItem.findViewById(R.id.summaryEmojiCount);

        emojiSymbol.setText(emotionCount.symbol);
        emojiName.setText(emotionCount.name);
        emojiCountText.setText(String.valueOf(emotionCount.count));

        return summaryItem;
    }

    // Helper class to store emotion count data
    private static class EmotionCount {
        String symbol;
        String name;
        int count;

        EmotionCount(String symbol, String name, int count) {
            this.symbol = symbol;
            this.name = name;
            this.count = count;
        }
    }
}