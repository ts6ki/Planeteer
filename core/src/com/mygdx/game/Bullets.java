
package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

/**
 * Bullets used by both enemies and the player
 *
 * @author Shivam Maji, Aaron Shih, Suvan Amruth
 * @version Launch
 */
public class Bullets {
    private Rectangle bullet;

    public Bullets(float x, float y) {
        bullet = new Rectangle();
        bullet.width = 3;
        bullet.height = 15;
        bullet.setX(x);
        bullet.setY(y);
    }

    /**
     * Returns width
     * 
     * @return float
     */
    public float getWidth() {
        return bullet.getWidth();
    }

    /**
     * Returns height
     * 
     * @return float
     */
    public float getHeight() {
        return bullet.getHeight();
    }

    /**
     * Returns x coordinate
     * @return float
     */
    public float getX() {
        return bullet.getX();
    }

    /**
     * Returns y coordinate
     * 
     * @return float
     */
    public float getY() {
        return bullet.getY();
    }

    /**
     * Check collision for Enemies
     * 
     * @param e Enemy you want to check
     * @return boolean True if it collides false if not
     */
    public boolean isCollide(Enemy e) {
        if (bullet != null) {
            return bullet.overlaps(e.getBody());
        }
        return false;
    }

    /**
     * Check collision for Astroids
     * 
     * @param e Rectangle you want to check
     * @return boolean True if it collides false if not
     */
    public boolean isCollide(Rectangle e) {
        if (bullet != null) {
            return bullet.overlaps(e);
        }
        return false;
    }

    /**
     * Checks collision for Bullets
     * @param e Bullets you want to check
     * @return boolean True if it collides false if not
     */
    public boolean isCollide(Bullets e) {
        if (bullet != null) {
            if (bullet.x - e.getX() == 0 || bullet.y - e.getY() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns bullet
     * 
     * @return Rectangle
     */
    public Rectangle getBullet() {
        return bullet;
    }

    /**
     * Removes the bullet
     */
    public void remove() {
        bullet = null;
    }

    /**
     * Sets y coordinate
     * 
     * @param f
     */
    public void setY(float f) {
        bullet.y = f;
    }

    /**
     * Makes the bullet move
     */
    public void update() {
        bullet.setY(bullet.getY() - 500 * Gdx.graphics.getDeltaTime());
    }

}
