package com.example.arshit_emotilog.ui.EmojiDashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.arshit_emotilog.Emoji;
import com.example.arshit_emotilog.EmojiLog;
import com.example.arshit_emotilog.R;

import java.util.HashMap;

public class EmojiPage extends Fragment {

    private GridLayout emojiGridLayout;
    private LinearLayout checkboxContainer;
    private final HashMap<String, CheckBox> emojiCheckBoxes = new HashMap<>();

    private static final HashMap<String, Boolean> emojiEnabledState = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.emoji_page, container, false);

        emojiGridLayout = view.findViewById(R.id.emojiGridLayout);
        checkboxContainer = view.findViewById(R.id.checkboxContainer);

        setupCheckBoxes();
        updateEmojiDisplay();

        return view;
    }

    private void setupCheckBoxes() {
        Emoji[] allEmojis = Emoji.getAllEmojiObjects();

        for (Emoji emoji : allEmojis) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(emoji.getSymbol() + " " + emoji.getName());
            checkBox.setTextColor(Color.WHITE);

            // Load state from in-memory map, default to true if not present
            boolean isChecked = emojiEnabledState.getOrDefault(emoji.getSymbol(), true);
            checkBox.setChecked(isChecked);

            checkBox.setOnCheckedChangeListener((buttonView, isCheckedNow) -> {
                // Save the state to in-memory map
                emojiEnabledState.put(emoji.getSymbol(), isCheckedNow);
                updateEmojiDisplay();
            });

            emojiCheckBoxes.put(emoji.getSymbol(), checkBox);
            checkboxContainer.addView(checkBox);
        }
    }

    // Display only checked emojis in the top grid
    private void updateEmojiDisplay() {
        emojiGridLayout.removeAllViews();

        Emoji[] allEmojis = Emoji.getAllEmojiObjects();
        for (Emoji emoji : allEmojis) {
            CheckBox checkBox = emojiCheckBoxes.get(emoji.getSymbol());
            if (checkBox != null && checkBox.isChecked()) {
                emojiGridLayout.addView(createEmojiButton(emoji));
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private Button createEmojiButton(Emoji emoji) {
        Button button = new Button(getContext());
        button.setText(emoji.getSymbol() + "\n" + emoji.getName());
        button.setTextSize(18);
        button.getBackground().setTint(Color.parseColor("#26282A"));
        button.setTextColor(Color.WHITE);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(8, 8, 8, 8);
        button.setLayoutParams(params);
        button.setMinHeight(200);

        button.setOnClickListener(v -> {
            button.setBackgroundColor(Color.parseColor("#DDDDDD"));
            new android.os.Handler().postDelayed(
                    () -> button.setBackgroundColor(Color.parseColor("#26282A")),
                    150
            );

            EmojiLog log = new EmojiLog(emoji.getSymbol(), emoji.getName(), System.currentTimeMillis());
            EmojiLog.addLog(log);
        });

        return button;
    }

    public static void resetEmojiStates() {
        emojiEnabledState.clear();
    }
}