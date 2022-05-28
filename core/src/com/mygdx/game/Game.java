package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Core game class. Renders everything.
 *
 * @author Shivam Maji, Aaron Shih, Suvan Amruth
 * @version Launch
 */
public class Game extends ApplicationAdapter {
   private Texture asteroidImage;
   private Texture shipImage;
   private Sound enemySound;
   private Music bgMusic;
   private SpriteBatch batch;
   private OrthographicCamera camera;
   private Rectangle ship;
   private LinkedList<Rectangle> asteroids;
   private ArrayList<Enemy> enemyArray;
   private long lastDropTime;
   private Texture enemyImage;
   private Texture bulletImage;
   private Texture enemyBulletImage;
   private long lastEnemySpawnTime;
   private Texture bgTexture;
   private Texture healthImage;
   private Texture winScreen;
   private Texture gameOver;
   private ArrayList<Bullets> bulletArr;
   private ArrayList<Bullets> enemyBulletArr;
   private int enemyCap;
   private int health;
   private long lastBulletTime;
   private long lastEnemyBulletTime;
   private Texture emptyHeart;
   private int score;
   private BitmapFont font;
   private Color blue;
   private Sound playerShoot;
   /**
    * Overrides create() method in ApplicationAdapter
    * 
    * Initializes all sprites, initalizes boot functions
    */
   @Override
   public void create() {
      // load the images for the droplet and the bucket, 64x64 pixels each
      asteroidImage = new Texture(Gdx.files.internal("asteroid/asteroid.png"));
      shipImage = new Texture(Gdx.files.internal("player/ship.png"));
      enemyImage = new Texture(Gdx.files.internal("enemy/enemy.png"));
      bulletImage = new Texture(Gdx.files.internal("player/bullet.png"));
      healthImage = new Texture(Gdx.files.internal("player/heart.png"));
      emptyHeart = new Texture(Gdx.files.internal("player/emptyHeart.png"));
      gameOver = new Texture(Gdx.files.internal("game/gameOver.jpg"));
      enemyBulletImage = new Texture(Gdx.files.internal("enemy/enemyBullet.png"));
      winScreen = new Texture(Gdx.files.internal("game/winScreen.png"));
      // load the drop sound effect and the rain background "music"
      //enemySound = Gdx.audio.newSound(Gdx.files.internal("music/drop.wav"));
      playerShoot = Gdx.audio.newSound(Gdx.files.internal("music/pewpew.mp3"));
      bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/bgMusic.wav"));
      font = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
      Color myColor = new Color(255, 255, 255, 255);
      font.setColor(myColor);
      enemyCap = 6;
      score = 0;

      // start the playback of the background music immediately
      bgMusic.setLooping(true);
      bgMusic.play();

      bgTexture = new Texture(Gdx.files.internal("game/bg.png"));

      // create the camera and the SpriteBatch
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 720, 1080);
      batch = new SpriteBatch();

      // create a Rectangle to logically represent the bucket
      ship = new Rectangle();
      ship.x = 328; // center the bucket horizontally
      ship.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
      ship.width = 30;
      ship.height = 30;

      // create the raindrops array and spawn the first raindrop
      asteroids = new LinkedList<Rectangle>();
      enemyArray = new ArrayList<>();
      bulletArr = new ArrayList<>();
      enemyBulletArr = new ArrayList<>();
      spawnAsteroid();
      spawnEnemy();

      blue = new Color(84, 17, 66, 1);

      // health is initialized
      health = 3;
   }

   /**
    * Spawns asteroids
    */
   private void spawnAsteroid() {
      Rectangle asteroid = new Rectangle();
      asteroid.x = MathUtils.random(0, 1080 - asteroid.width);
      asteroid.y = 1080;
      asteroid.width = 30;
      asteroid.height = 30;
      asteroids.add(asteroid);
      lastDropTime = TimeUtils.nanoTime();
   }

   /**
    * Returns asteroid at the 0th indec
    * 
    * @return Rectangle Asteroid at index 0
    */
   public Object getAsteroid() {
      return asteroids.get(0);
   }

   /**
    * Spawns enemy
    */
   private void spawnEnemy() {
      Enemy enemy = new Enemy();
      enemy.getBody().x = MathUtils.random(100, 600);
      enemy.getBody().y = 1060;
      enemy.getBody().width = 36;
      enemy.getBody().height = 36;
      enemyArray.add(enemy);
      enemyArray.trimToSize();

      lastEnemySpawnTime = TimeUtils.nanoTime();
   }

   /**
    * Makes enemy shoot bullets
    */
   private void spawnEnemyBullets() {
      for (Enemy enemy : enemyArray) {
         Bullets bullet = new Bullets(enemy.body.getX(), enemy.body.getY());
         enemyBulletArr.add(bullet);
         lastEnemyBulletTime = TimeUtils.nanoTime();
      }

   }

   /**
    * Spawns player bullets
    */
   private void spawnBullet() {
      Bullets bullet = new Bullets(ship.x, ship.y);
      Bullets bullet2 = new Bullets(ship.x + ship.width - 10, ship.y);
      bulletArr.add(bullet);
      bulletArr.add(bullet2);
      lastBulletTime = TimeUtils.nanoTime();
   }

   /**
    * Returns ship's x coordinate
    * 
    * @return float x coordinate
    */
   public float getShipX() {
      return ship.getX();
   }

   /**
    * Returns ship's y coordinate
    * 
    * @return float y coordinate
    */
   public float getShipY() {
      return ship.getY();
   }

   /**
    * Processes user input for controls
    */
   public void processUserInput() {
      if (health > 0) {
         if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
            ship.x -= 250 * Gdx.graphics.getDeltaTime();
         if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
            ship.x += 250 * Gdx.graphics.getDeltaTime();
         if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
            ship.y -= 200 * Gdx.graphics.getDeltaTime();
         if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
            ship.y += 200 * Gdx.graphics.getDeltaTime();
         if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            if (TimeUtils.nanoTime() - lastBulletTime > 250000000) {
               playerShoot.play();
               spawnBullet();
            }
         }
      }

   }

   /**
    * Draws and updates hearts
    * 
    * Draws score at the end of the match
    */
   private void drawHearts() {
      if (health >= 3) {
         batch.draw(healthImage, 10, 30, 39, 33);
         batch.draw(healthImage, 59, 30, 39, 33);
         batch.draw(healthImage, 59 + 49, 30, 39, 33);
      } else if (health == 2) {
         batch.draw(healthImage, 10, 30, 39, 33);
         batch.draw(healthImage, 59, 30, 39, 33);
         batch.draw(emptyHeart, 59 + 49, 30, 39, 33);
      } else if (health == 1) {
         batch.draw(healthImage, 10, 30, 39, 33);
         batch.draw(emptyHeart, 59, 30, 39, 33);
         batch.draw(emptyHeart, 59 + 49, 30, 39, 33);
      } else if (health <= 0) {
         batch.draw(emptyHeart, 10, 30, 39, 33);
         batch.draw(emptyHeart, 59, 30, 39, 33);
         batch.draw(emptyHeart, 59 + 49, 30, 39, 33);
         //batch.draw(gameOver, 0, 0, 735, 1200);
         //font.draw(batch, "Total Score: " + Integer.toString(score), 210, 50);
      }
   }

   /**
    * Draws sprites
    */
   private void drawSprites() {
      batch.draw(bgTexture, (float) (0 - (double) Gdx.graphics.getWidth() / 4), 0);
      batch.draw(shipImage, ship.x, ship.y, ship.width, ship.height);
      for (int i = 0; i < asteroids.size(); i++) {
         batch.draw(asteroidImage, asteroids.get(i).x, asteroids.get(i).y, asteroids.get(i).width,
               asteroids.get(i).height);
      }

      for (Bullets b : bulletArr) {
         batch.draw(bulletImage, b.getX(), b.getY(), b.getWidth(), b.getHeight());
      }

      for (Enemy e : enemyArray) {
         batch.draw(enemyImage, e.getBody().getX(), e.getBody().getY() - 60, e.getBody().width, e.getBody().height);
         for (Bullets b : enemyBulletArr) {
            batch.draw(enemyBulletImage, b.getX() + (e.getBody().width / 2 - b.getWidth() / 2), b.getY() - 60,
                  b.getWidth(),
                  b.getHeight());
         }
      }
   }

   /**
    * Moves asteroids. If an asteroid collides with a player, add camera shake
    */
   private void asteroidMovement() {
      if (health > 0) {
         for (Iterator<Rectangle> iter = asteroids.iterator(); iter.hasNext();) {
            Rectangle asteroid = iter.next();
            asteroid.y -= MathUtils.random(100, 500) * Gdx.graphics.getDeltaTime();
            if (asteroid.y + 64 < 0)
               iter.remove();
            if (asteroid.overlaps(ship)) {
               Rumble.rumble(3, .8f);
               //enemySound.play();
               iter.remove();
               health--;
            }
            for (int j = 0; j < bulletArr.size(); j++) {
               if (bulletArr.get(j).isCollide(asteroid)) {
                  bulletArr.remove(j);
               }
            }
            if (Rumble.getRumbleTimeLeft() > 0) {
               Rumble.tick(Gdx.graphics.getDeltaTime());
               camera.translate(Rumble.getPos());
            }
            if (Rumble.getRumbleTimeLeft() <= 0) {
               camera.position.set(720 / 2f, 1080 / 2f, 0);
            }
         }
      }
   }

   /**
    * Moves the enemies left and right
    */
   private void enemyMovement() {
      for (Iterator<Enemy> i = enemyArray.iterator(); i.hasNext();) {
         Enemy e = i.next();
         e.move();

         for (int j = 0; j < bulletArr.size(); j++) {
            if (bulletArr.get(j).isCollide(e)) {
               bulletArr.remove(j);
            }
         }
      }
   }

   /**
    * Moves bullets and checks for collisions
    */
   public void bulletMovement() {
      for (Iterator<Bullets> i = bulletArr.iterator(); i.hasNext();) {
         Bullets bullet = i.next();
         bullet.setY(bullet.getY() + 500 * Gdx.graphics.getDeltaTime());
         if (bullet.getY() - ship.height / 2 < 0) {
            i.remove();
            continue;
         }
         if (bullet.getY() > 1200) {
            i.remove();
            continue;
         }
         for (int j = 0; j < asteroids.size(); j++) {
            if (bullet.isCollide(asteroids.get(j))) {
               i.remove();
               asteroids.remove(j);
               score = score + 5;
               continue;
            }
         }
         for (int j = 0; j < enemyArray.size(); j++) {
            if (bullet.isCollide(enemyArray.get(j))) {
                  i.remove();
               enemyArray.remove(j);
               score = score + 10;
               continue;
            }
         }
         for (int j = 0; j < enemyBulletArr.size(); j++) {
            if (bullet.isCollide(enemyBulletArr.get(j))) {
               enemyBulletArr.remove(j);
               i.remove();
            }
         }
         if (health > 0) {
            font.draw(batch, "Total Score: " + Integer.toString(score), 300, 60);
         }
         if (score >= 1000) {
            batch.draw(winScreen, 0, 0, 735, 1200);
            font.draw(batch, "Total Score: " + Integer.toString(score), 210, 50);
            health = 0;
         }
      }
   }

   /**
    * Moves enemy bullets and checks for colissions
    */
   public void enemyBulletMovement() {
      if (health > 0) {
         for (Iterator<Bullets> e = enemyBulletArr.iterator(); e.hasNext();) {
            Bullets b = e.next();
            b.setY(b.getY() - 500 * Gdx.graphics.getDeltaTime());
            if (b.getY() < 0) {
               e.remove();
            } else if (b.isCollide(ship)) {
               Rumble.rumble(3, .8f);
               health--;
               drawHearts();
               e.remove();
            }
            if (Rumble.getRumbleTimeLeft() > 0) {
               Rumble.tick(Gdx.graphics.getDeltaTime());
               camera.translate(Rumble.getPos());
            }
            if (Rumble.getRumbleTimeLeft() <= 0) {
               camera.position.set(720 / 2f, 1080 / 2f, 0);
            }
         }
      }
   }

   /**
    * Overrides render() method in ApplicationAdapter
    * 
    * Renders everything on the screen.
    */
   @Override
   public void render() {
      // clear the screen with a dark blue color. The
      // arguments to clear are the red, green
      // blue and alpha component in the range [0,1]
      // of the color to be used to clear the screen.
      ScreenUtils.clear(blue, true);

      // tell the camera to update its matrices.
      camera.update();

      // tell the SpriteBatch to render in the
      // coordinate system specified by the camera.
      batch.setProjectionMatrix(camera.combined);

      batch.begin();
      drawSprites();
      drawHearts();

      enemyBulletMovement();

      processUserInput();

      // make sure the ship stays within the screen bounds
      if (ship.x < 0)
         ship.x = 0;
      if (ship.x > 720 - ship.width)
         ship.x = 720 - ship.width;
      if (ship.y < 0)
         ship.y = 0;
      if (ship.y > 1080 - ship.width)
         ship.y = 1080 - ship.height;

      // check if we need to create a new raindrop
      if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
         spawnAsteroid();

      if (TimeUtils.nanoTime() - lastEnemyBulletTime > 1000000000) {
         spawnEnemyBullets();
      }

      if (enemyArray.size() < enemyArray.size() - 3) {
         spawnEnemy();
      }
      if (TimeUtils.nanoTime() - lastEnemySpawnTime > 500000000) {
         if (enemyArray.size() < enemyCap) {
            spawnEnemy();
         }
      }

      bulletMovement();

      asteroidMovement();

      enemyMovement();



      batch.end();
   }

   /**
    * Overrides dispose() method in ApplicationAdapter
    * 
    * Dispose of all the native resources
    */
   @Override
   public void dispose() {
      asteroidImage.dispose();
      shipImage.dispose();
      //enemySound.dispose();
      bgMusic.dispose();
      batch.dispose();
   }
}