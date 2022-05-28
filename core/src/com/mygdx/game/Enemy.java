package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Creates and handles enemies
 *
 * @author Shivam Maji, Aaron Shih, Suvan Amruth
 * @version Launch
 */
public class Enemy {

    public Rectangle body;
    public int speed;

    /**
     * Default enemy constructor. Constructs an enemy with a random speed between 80
     * and 180
     */
    public Enemy() {
        body = new Rectangle();
        speed = MathUtils.random(80, 280);
    }

    /**
     * Returns body
     * 
     * @return Rectangle body
     */
    public Rectangle getBody() {
        return body;
    }

    /**
     * Moves the enemy
     */
    public void move() {

        if (body.y > 1080 - (60 * MathUtils.random(1, 3))) {
            body.y -= Gdx.graphics.getDeltaTime() * 30;
        }

        if (body.x <= 670 - body.width && body.x >= 0) {
            body.x += Gdx.graphics.getDeltaTime() * speed;
        }

        if (body.x >= 670 - body.width || body.x < 0) {
            speed = -1 * speed;
            body.x += Gdx.graphics.getDeltaTime() * speed;
        }
    }

}
