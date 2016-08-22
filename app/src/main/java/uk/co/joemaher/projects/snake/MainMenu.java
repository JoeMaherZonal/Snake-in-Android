package uk.co.joemaher.projects.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by user on 20/08/2016.
 */
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
//        setContentView(new GameController(this));

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
                Intent intent = new Intent(MainMenu.this, Game.class);
                startActivity(intent);
            }
        });
    }

}

