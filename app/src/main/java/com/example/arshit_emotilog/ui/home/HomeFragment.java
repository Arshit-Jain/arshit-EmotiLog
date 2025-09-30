package com.example.arshit_emotilog.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// Import your Emoji class
import com.example.arshit_emotilog.Emoji;
import com.example.arshit_emotilog.R;

public class HomeFragment extends Fragment {

    private GridLayout emojiGridLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        emojiGridLayout = view.findViewById(R.id.emojiGridLayout);

        setupEmojiButtons();

        return view;
    }

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

        // Set button text (emoji + name)
        button.setText(emoji.getSymbol() + "\n" + emoji.getName());
        button.setTextSize(18);

        // Set button styling with rounded corners
        button.setBackgroundResource(R.drawable.rounded_button);
        button.getBackground().setTint(Color.parseColor(emoji.getColor()));
        button.setTextColor(Color.WHITE);

        // Set layout parameters for grid (2 columns)
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(8, 8, 8, 8);
        button.setLayoutParams(params);

        // Set minimum height for nice boxes
        button.setMinHeight(200);

        // Click listener - just show a toast for now
        button.setOnClickListener(v ->
                Toast.makeText(getContext(), "You selected: " + emoji.getName(), Toast.LENGTH_SHORT).show()
        );

        return button;
    }
}