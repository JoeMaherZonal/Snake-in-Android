package uk.co.joemaher.projects.snake;

/**
 * Created by user on 22/08/2016.
 */
public class Score {
    private String name;
    private int score;

    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public String prettyPrint(){
        String prettyString = " " + getName() + "  " + getScore();
        return prettyString;
    }
}
