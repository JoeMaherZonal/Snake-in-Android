package uk.co.joemaher.projects.snake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 22/08/2016.
 */
public class HighScores extends Activity {
    android.widget.Button mainMenuBtn;
    TextView firstHighScore;
    TextView secondHighScore;
    TextView thirdHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.high_scores_activity);

        SavedTextPreferences savedHighScores = new SavedTextPreferences();
        ArrayList<Score> highScores = savedHighScores.getHighScores(getBaseContext());

        firstHighScore = (TextView)findViewById(R.id.first_highscore_txtview);
        secondHighScore = (TextView)findViewById(R.id.second_highscore_txtview);
        thirdHighScore = (TextView)findViewById(R.id.third_highscore_txtview);
        mainMenuBtn = (android.widget.Button)findViewById(R.id.main_menu_btn);

        mainMenuBtn.setX(440);
        mainMenuBtn.setY(200);

        firstHighScore.setTextSize(60);
        firstHighScore.setX(170);
        firstHighScore.setY(240);

        secondHighScore.setTextSize(60);
        secondHighScore.setX(170);
        secondHighScore.setY(240);

        thirdHighScore.setTextSize(60);
        thirdHighScore.setX(170);
        thirdHighScore.setY(240);



        if(highScores.size() > 0) {
            firstHighScore.setText(highScores.get(0).prettyPrint());
        }

        if(highScores.size() > 1) {
            secondHighScore.setText(highScores.get(1).prettyPrint());
        }

        if(highScores.size() > 2) {
            thirdHighScore.setText(highScores.get(2).prettyPrint());
        }

        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScores.this, MainMenu.class);
                startActivity(intent);
            }
        });

    }

}
