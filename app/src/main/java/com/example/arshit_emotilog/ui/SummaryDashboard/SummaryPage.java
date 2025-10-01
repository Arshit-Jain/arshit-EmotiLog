package com.example.arshit_emotilog.ui.SummaryDashboard;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.arshit_emotilog.EmojiLog;
import com.example.arshit_emotilog.EmotionCount;
import com.example.arshit_emotilog.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

//Fragment for summary page, manages date and calculate counts
public class SummaryPage extends Fragment {

    private TextView dateText;
    private TextView totalCountTodayText;
    private LinearLayout summaryContainer;
    private Button selectDateButton;
    private String selectedDate;

    @Nullable
    @Override
    // on create view to display summary
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summary_page, container, false);

        dateText = view.findViewById(R.id.dateText);
        totalCountTodayText = view.findViewById(R.id.totalCountTodayText);
        summaryContainer = view.findViewById(R.id.summaryContainer);
        selectDateButton = view.findViewById(R.id.selectDateButton);

        // setting today's date as default
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        selectedDate = sdf.format(Calendar.getInstance().getTime());
        selectDateButton.setOnClickListener(v -> showDatePicker());

        loadSummary();

        return view;
    }

    // asked LLM's to help me with this function
    // function to pick date
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

    // showing details of selected date
    private void loadSummary() {
        dateText.setText(selectedDate);

        // getting logs for selected date
        ArrayList<EmojiLog> logsForDate = EmojiLog.getLogsByDate(selectedDate);
        totalCountTodayText.setText(String.valueOf(logsForDate.size()));

        // calculating emotion counts
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

        // displaying
        displayEmotionBreakdown(emotionCounts);
    }

    // this displays all the emotions
    private void displayEmotionBreakdown(HashMap<String, EmotionCount> emotionCounts) {
        summaryContainer.removeAllViews();

        // no emotions logged
        if (emotionCounts.isEmpty()) {
            TextView noDataText = new TextView(getContext());
            noDataText.setText("No emotions logged for this date");
            noDataText.setTextSize(16);
            noDataText.setTextColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
            noDataText.setPadding(16, 32, 16, 16);
            summaryContainer.addView(noDataText);
            return;
        }

        // convert to list and sorting by count
        ArrayList<EmotionCount> sortedEmotions = new ArrayList<>(emotionCounts.values());
        sortedEmotions.sort((e1, e2) -> Integer.compare(e2.count, e1.count));

        // displaying emotions
        for (EmotionCount emotionCount : sortedEmotions) {
            View summaryItem = createSummaryItemView(emotionCount);
            summaryContainer.addView(summaryItem);
        }
    }

    // creating summary item
    private View createSummaryItemView(EmotionCount emotionCount) {
        View summaryItem = LayoutInflater.from(getContext()).inflate(R.layout.summary_item, summaryContainer, false);

        TextView emojiSymbol = summaryItem.findViewById(R.id.summaryEmojiSymbol);
        TextView emojiName = summaryItem.findViewById(R.id.summaryEmojiName);
        TextView emojiCountText = summaryItem.findViewById(R.id.summaryEmojiCount);

        emojiSymbol.setText(emotionCount.getSymbol() + " ");
        emojiName.setText(emotionCount.getName() + " ");
        emojiCountText.setText(emotionCount.getCount() + " ");

        return summaryItem;
    }
}