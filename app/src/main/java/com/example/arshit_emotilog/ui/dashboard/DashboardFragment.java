package com.example.arshit_emotilog.ui.dashboard;

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

public class DashboardFragment extends Fragment {

    private LinearLayout logsContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        logsContainer = view.findViewById(R.id.logsContainer);

        displayLogs();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayLogs();
    }

    private void displayLogs() {
        logsContainer.removeAllViews();

        ArrayList<EmojiLog> logs = EmojiLog.getAllLogs();

        for (EmojiLog log : logs) {
            View logView = createLogView(log);
            logsContainer.addView(logView);
        }
    }

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