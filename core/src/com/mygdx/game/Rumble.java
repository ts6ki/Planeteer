package com.mygdx.game;

import java.util.Random;
import com.badlogic.gdx.math.Vector3;

/**
 * Camera shake effect after hitting stuff
 *
 * @author Shivam Maji, Aaron Shih, Suvan Amruth
 * @version Launch
 */
public class Rumble {
    private static float time = 0;
    private static float currentTime = 0;
    private static float power = 0;
    private static float currentPower = 0;
    private static Random random;
    private static Vector3 pos = new Vector3();

    /**
     * Constructor
     * 
     * Not meant to be used
     */
    private Rumble() {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param rumblePower
     * @param rumbleLength
     */
    public static void rumble(float rumblePower, float rumbleLength) {
        random = new Random();
        power = rumblePower;
        time = rumbleLength;
        currentTime = 0;
    }

    /**
     * @param delta
     * @return Vector3
     */
    public static Vector3 tick(float delta) {
        if (currentTime <= time) {
            currentPower = power * ((time - currentTime) / time);

            pos.x = (random.nextFloat() - 0.5f) * 3 * currentPower;
            pos.y = (random.nextFloat() - 0.5f) * 3 * currentPower;

            currentTime += delta;
        } else {
            time = 0;
        }
        return pos;
    }

    /**
     * @return float
     */
    public static float getRumbleTimeLeft() {
        return time;
    }

    /**
     * @return Vector3
     */
    public static Vector3 getPos() {
        return pos;
    }
}