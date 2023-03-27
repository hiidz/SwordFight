package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.R;
import com.example.swordfight.Vector2;
import com.example.swordfight.graphics.OrbAnimator;
import com.example.swordfight.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class BossOrb extends GameObject {

    private Context context;
    private int color;
    private Player player;
    private Enemy enemy;
    private ConcurrentLinkedQueue<Projectile> projectiles;
    private BlockingQueue<Projectile> projectileQueue;

    private OrbAnimator orbAnimator;

    List<Float> usedAngles = new ArrayList<>();

    float minAngleDistance = 0.2f; // Define the minimum distance between angles (radians)

    public BossOrb(Context context, int color, float positionX, float positionY, float radius, Player player, Enemy enemy) {
        super(context, positionX, positionY, 0, radius);
        this.context = context;
        this.color = color;
        this.player = player;
        this.projectiles = new ConcurrentLinkedQueue<>();
        this.projectileQueue = new LinkedBlockingQueue<>();
        this.enemy = enemy;
        this.orbAnimator = new OrbAnimator(new SpriteSheet(context, R.drawable.orb_spritesheet).getOrbSpriteArray(5, 1));

        startProducerThread();
        startConsumerThread();
    }

    private void startProducerThread() {
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    int sleepTime = random.nextInt(600) + 100;
                    Thread.sleep(sleepTime);
                    if (projectileQueue.size() < 5) {
                        Projectile p = new Projectile();
                        float radius = 150; // You can adjust the radius to your desired value
                        float angle = generateUniqueAngle(random, usedAngles, minAngleDistance);
                        float offsetX = (float) Math.cos(angle) * radius;
                        float offsetY = (float) Math.sin(angle) * radius - 120;
                        p.setSlotOffset(new Vector2(offsetX, offsetY));
                        p.setPosition(new Vector2(getPositionX() + offsetX, getPositionY() + offsetY));
                        projectileQueue.put(p);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private float generateUniqueAngle(Random random, List<Float> usedAngles, float minAngleDistance) {
        if (usedAngles.size() >= 10) { // Change the value based on your requirements
            usedAngles.clear();
        }

        float newAngle;
        do {
            newAngle = random.nextFloat() * 2 * (float) Math.PI; // Random angle between 0 and 2π
        } while (isAngleTooClose(newAngle, usedAngles, minAngleDistance));

        usedAngles.add(newAngle);
        return newAngle;
    }

    private boolean isAngleTooClose(float angle, List<Float> usedAngles, float minAngleDistance) {
        for (float usedAngle : usedAngles) {
            if (Math.abs(usedAngle - angle) < minAngleDistance || Math.abs(usedAngle - angle - 2 * (float) Math.PI) < minAngleDistance || Math.abs(usedAngle - angle + 2 * (float) Math.PI) < minAngleDistance) {
                return true;
            }
        }
        return false;
    }

    private void startConsumerThread() {
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    int sleepTime = random.nextInt(700) + 200;
                    Thread.sleep(sleepTime);
                    if (!projectileQueue.isEmpty()) {
                        if (projectileQueue.size() == 5) {
                            // Perform a spread shot
                            Vector2 direction1 = player.getPosition().subtract(getPosition()).normalize().rotate(30); // rotate the direction by 30 degrees
                            Vector2 direction2 = player.getPosition().subtract(getPosition()).normalize().rotate(-30); // rotate the direction by -30 degrees
                            Vector2 direction3 = player.getPosition().subtract(getPosition()).normalize().rotate(60); // rotate the direction by 60 degrees
                            Vector2 direction4 = player.getPosition().subtract(getPosition()).normalize().rotate(-60); // rotate the direction by -60 degrees
                            Vector2 direction5 = player.getPosition().subtract(getPosition()).normalize(); // original direction

                            projectileQueue.take();
                            projectiles.add(new Projectile(context, color, getPositionX(), getPositionY(), direction1, 10, 50));
                            projectileQueue.take();
                            projectiles.add(new Projectile(context, color, getPositionX(), getPositionY(), direction2, 10, 50));
                            projectileQueue.take();
                            projectiles.add(new Projectile(context, color, getPositionX(), getPositionY(), direction3, 10, 50));
                            projectileQueue.take();
                            projectiles.add(new Projectile(context, color, getPositionX(), getPositionY(), direction4, 10, 50));
                            projectileQueue.take();
                            projectiles.add(new Projectile(context, color, getPositionX(), getPositionY(), direction5, 10, 50));
                        } else {
                            Vector2 direction = player.getPosition().subtract(getPosition()).normalize();
                            Projectile p = projectileQueue.take();
                            projectiles.add(new Projectile(context, color, p.getPositionX(), p.getPositionY(), direction, 10, 50));
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {

//        canvas.drawCircle((float) gameDisplay.gameToDisplayCoordinatesX(position.getX()),
//                (float) gameDisplay.gameToDisplayCoordinatesY(position.getY()),
//                (float) radius,
//                paint);

        // Draw the Orbs floating around the enemy
        List<Projectile> projectileList = new ArrayList<>(projectileQueue); //temp list to store the projectiles in the queue
        int numCircles = projectileList.size();
        for (int i = 0; i < numCircles; i++) {
            orbAnimator.draw(canvas, gameDisplay, projectileList.get(i));
        }

        // Draw the projectiles
        for (Projectile projectile : projectiles) {
            projectile.draw(canvas, gameDisplay, orbAnimator);
        }

//        canvas.drawText("Projectile Queue Size: " + projectileQueue.size(), (float) gameDisplay.gameToDisplayCoordinatesX(position.getX()), (float) gameDisplay.gameToDisplayCoordinatesY(position.getY()), paint);
    }

    @Override
    public void update() {
        setPosition(enemy.getPosition()); //update the position of the boss orb to the position of the enemy

        for (Projectile projectile : projectileQueue) {
            Vector2 offset = projectile.getSlotOffset();
            projectile.setPosition(enemy.getPosition().add(offset));
        }

        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();

            // Remove the projectile if it's off the screen
            if (projectile.getPositionX() < 0 || projectile.getPositionX() > 5000
                    || projectile.getPositionY() < 0 || projectile.getPositionY() > 2400) {
                iterator.remove();
            } else if (isColliding(projectile, player)) {
                player.setDamageDealt(projectile.getDamage());
                iterator.remove();
            }
        }
    }
}
