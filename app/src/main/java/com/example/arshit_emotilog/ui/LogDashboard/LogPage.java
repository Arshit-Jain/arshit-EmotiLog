package com.example.arshit_emotilog.ui.LogDashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.arshit_emotilog.EmojiLog;
import com.example.arshit_emotilog.R;
import java.util.ArrayList;

// Note: I implemented majority of the logic but I wanted this page to look like logcat.
// I used ChatGPT's help to achieve that UI.
// creates log page
public class LogPage extends Fragment {

    // UI component
    private LinearLayout logsContainer;

    @Nullable
    @Override
    // on create view to display logs
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_page, container, false);
        logsContainer = view.findViewById(R.id.logsContainer);
        displayLogs();
        return view;
    }

    // displaying logs through calling createLogView function for data
    private void displayLogs() {
        logsContainer.removeAllViews();
        ArrayList<EmojiLog> logs = EmojiLog.getAllLogs();

        for (EmojiLog log : logs) {
            View logView = createLogView(log);
            logsContainer.addView(logView);
        }
    }

    // getting data to display logs
    @SuppressLint("SetTextI18n")
    private View createLogView(EmojiLog log) {
        View logItem = LayoutInflater.from(getContext()).inflate(R.layout.log_item, logsContainer, false);
        TextView emojiSymbolText = logItem.findViewById(R.id.emojiSymbolText);
        TextView emojiNameText = logItem.findViewById(R.id.emojiNameText);
        TextView timestampText = logItem.findViewById(R.id.timestampText);

        emojiSymbolText.setText(log.getEmojiSymbol());
        emojiNameText.setText(log.getEmojiName().toUpperCase());
        timestampText.setText("logged_at: " + log.getFormattedDateTime());

        return logItem;
    }
}