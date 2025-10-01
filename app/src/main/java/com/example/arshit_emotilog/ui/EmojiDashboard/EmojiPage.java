package com.example.arshit_emotilog.ui.EmojiDashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// Import your Emoji class
import com.example.arshit_emotilog.Emoji;
import com.example.arshit_emotilog.EmojiLog;
import com.example.arshit_emotilog.R;


public class EmojiPage extends Fragment {

    private GridLayout emojiGridLayout;

    @Nullable
    @Override
    // on create view to display emojis
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emoji_page, container, false);
        emojiGridLayout = view.findViewById(R.id.emojiGridLayout);
        setupEmojiButtons();
        return view;
    }

    // using grid layout to display emojis
    private void setupEmojiButtons() {
        Emoji[] emojis = Emoji.getDefaultEmojis();

        for (Emoji emoji : emojis) {
            Button emojiButton = createEmojiButton(emoji);
            emojiGridLayout.addView(emojiButton);
        }
    }

    @SuppressLint("SetTextI18n")
    private Button createEmojiButton(Emoji emoji) {
        Button button = new Button(getContext());

        // Setting button properties
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

        // With help of AI, added change color when clicked
        button.setOnClickListener(v -> {
            button.setBackgroundColor(Color.parseColor("#DDDDDD"));
            new android.os.Handler().postDelayed(
                    () -> button.setBackgroundColor(Color.parseColor("#26282A")),
                    150
            );

            // Logging
            EmojiLog log = new EmojiLog(emoji.getSymbol(), emoji.getName(), System.currentTimeMillis());
            EmojiLog.addLog(log);
        });

        return button;
    }
}