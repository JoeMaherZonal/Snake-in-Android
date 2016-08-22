package uk.co.joemaher.projects.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by user on 20/08/2016.
 */
public class GameOver extends Activity {
    android.widget.Button mainMenuBtn;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_over);

        scoreView = (TextView)findViewById(R.id.game_over_score);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String score = extras.getString("score");
        scoreView.setText(score);

        mainMenuBtn = (android.widget.Button)findViewById(R.id.return_to_main_btn);


        mainMenuBtn.setX(20);
        mainMenuBtn.setY(450);
        scoreView.setX(20);
        scoreView.setY(455);


        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, MainMenu.class);
                startActivity(intent);
            }
        });

    }

}