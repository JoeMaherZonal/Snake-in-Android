package uk.co.joemaher.projects.snake;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class MainMenu extends Activity {
    android.widget.Button startGameBtn;
    android.widget.Button highScoreBtn;
    android.widget.Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_menu);

        startGameBtn = (android.widget.Button)findViewById(R.id.new_game_btn);
        highScoreBtn = (android.widget.Button)findViewById(R.id.high_scores_btn);
        settingsBtn = (android.widget.Button)findViewById(R.id.settings_btn);

        startGameBtn.setX(20);
        startGameBtn.setY(400);

        highScoreBtn.setX(20);
        highScoreBtn.setY(410);

        settingsBtn.setX(20);
        settingsBtn.setY(420);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final EditText edittext = new EditText(getBaseContext());
            edittext.setTextColor(Color.parseColor("#000000"));
            new AlertDialog.Builder(MainMenu.this, R.style.PopUpTheme)
                .setTitle("What's your name?")
                .setView(edittext)
                .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    String name = edittext.getText().toString();
                    Intent intent = new Intent(MainMenu.this, Game.class);
                    intent.putExtra("playerName", name);
                    startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //return
                }
            }).show();
            }
        });

        highScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, HighScores.class);
                startActivity(intent);
            }
        });


        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //maybe...
            }
        });
    }

}

