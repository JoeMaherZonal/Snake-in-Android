package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SavedTextPreferences {

    private static final String PREF_SAVE_TEXT = "scores";

    public static void setStoredText(Context context, String text){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_SAVE_TEXT, text);
        editor.apply();
    }

    public static String getStoredText(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String text = sharedPreferences.getString(PREF_SAVE_TEXT, null);
        return text;
    }

    public void updateHighScores(Context context, Score score){
        ArrayList<Score> scores = getHighScores(context);
        checkForNoEntries(context, scores);
        evaulateIfHighScore(context, scores, score);
    }

    public void evaulateIfHighScore(Context context, ArrayList<Score> scores, Score newScore){
        for(int i = 0; i < scores.size(); i++){
            if(newScore.getScore() > scores.get(i).getScore()){
                scores.add(newScore);
                orderHighScores(scores);
                scores.remove(3);
                break;
            }
        }
        saveHighScore(context, scores);
    }

    public void saveHighScore(Context context, ArrayList<Score> scores){
        Gson mGson = new Gson();
        String json = mGson.toJson(scores);
        setStoredText(context, json);
    }

    public ArrayList<Score> getHighScores(Context context){
        Gson mGson = new Gson();
        String currentScores = getStoredText(context);
        String testString = getStoredText(context);
        ArrayList<Score> scores = new ArrayList<Score>();
        Type type = new TypeToken<ArrayList<Score>>(){}.getType();
        scores =  mGson.fromJson(currentScores, (java.lang.reflect.Type) type);
        orderHighScores(scores);
        return scores;
    }

    public void orderHighScores(ArrayList<Score> scores){
        //I know this ugly...but it works..
        Collections.sort(scores, new Comparator<Score>() {
            @Override public int compare(Score p1, Score p2) {
                return p2.getScore() - p1.getScore();
            }});
    }

    public void checkForNoEntries(Context context, ArrayList<Score> scores){
        Gson mGson = new Gson();
        ArrayList<Score> blankScores = new ArrayList<Score>();

        if(scores == null){
            for(int i = 0; i < 3; i++){
                blankScores.add(new Score("A player has no name", i));
            }
            String json = mGson.toJson(blankScores);
            setStoredText(context, json);
        }
    }

}
