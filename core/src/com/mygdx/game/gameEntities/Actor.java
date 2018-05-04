package com.mygdx.game.gameEntities;

/**
 * Created by danielli on 4/19/18.
 */

public interface Actor {
    public void update(float deltaTime);

    public boolean shouldRemove();

    public void setRemove(boolean remove);
}
