package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by user on 23/08/2016.
 */
public interface Edible {

    void draw(Canvas canvas);

    double adjustSpeed();

    int adjustSnakeSize();

    void adjustGame(Context context, Snake snake);
}
