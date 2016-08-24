package uk.co.joemaher.projects.snake;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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
        startGameBtn.setY(getHeightPerc(0.50));

        highScoreBtn.setY(getHeightPerc(0.63));

        settingsBtn.setY(getHeightPerc(0.76));

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

    public float getWidthPerc(double perc){
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        double widthPerc = size.x * perc;
        return (float) widthPerc;
    }

    public float getHeightPerc(double perc){
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        double heightPerc = size.y * perc;
        return (float) heightPerc;
    }

}

