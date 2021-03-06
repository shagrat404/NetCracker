package lec14.miner;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lec14.miner.component.Mob;
import lec14.miner.component.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("res/1.png")));
    private Player player = new Player(imageView);

    private Label scoreLabel = new Label("Score: " + player.getScoreBonus());
    private Label scoreMobsLabel = new Label("Score mobs: " + player.getScoreMobs());

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private HashMap<MouseButton, Boolean> keysMouse = new HashMap<>();

    public static ArrayList<Rectangle> bonuses = new ArrayList<>();
    public static ArrayList<Mob> mobs = new ArrayList<>();
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static Pane root = new Pane();

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public void addKey(KeyCode keyCode, boolean flag) {
        this.keys.put(keyCode, flag);
    }

    public void addKey(MouseButton keyCode, boolean flag) {
        this.keysMouse.put(keyCode, flag);
    }

    public void createMobs() {
        int random = (int)Math.floor(Math.random()*500);
        int x = (int)Math.floor(Math.random()*550+20);
        int y = (int)Math.floor(Math.random()*550+20);
        if (random == 5) {
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("res/soldat13.png")));
            Mob mob = new Mob(imageView);
            mob.setPlayer(this.player);
            mob.setTranslateX(x);
            mob.setTranslateY(y);
            mobs.add(mob);
            root.getChildren().add(mob);
        }
    }

    public void bonus() {
        int random = (int)Math.floor(Math.random()*200);
        int x = (int)Math.floor(Math.random()*550+20);
        int y = (int)Math.floor(Math.random()*550+20);
        if (random == 5) {
            Rectangle rect = new Rectangle(20,20, Color.RED);
            rect.setX(x);
            rect.setY(y);
            bonuses.add(rect);
            root.getChildren().add(rect);
        }
    }

    public void update() {
        if (isPressed(KeyCode.UP)) {
            player.animation.play();
            player.animation.setOffsetY(96);
            player.moveY(-2);
        } else if (isPressed(KeyCode.DOWN)) {
            player.animation.play();
            player.animation.setOffsetY(0);
            player.moveY(2);
        } else if (isPressed(KeyCode.RIGHT)) {
            player.animation.play();
            player.animation.setOffsetY(64);
            player.moveX(2);
        } else if (isPressed(KeyCode.LEFT)) {
            player.animation.play();
            player.animation.setOffsetY(32);
            player.moveX(-2);
        } else {
            player.animation.stop();
        }
        if (isPressed(KeyCode.SPACE) || isClicked(MouseButton.PRIMARY)) {
            player.shooting();
        }
        scoreLabel.setText("Score: " + player.getScoreBonus());
        scoreMobsLabel.setText("Score mobs: " + player.getScoreMobs());

        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }

    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public boolean isClicked(MouseButton mouseButton) {
        return keysMouse.getOrDefault(mouseButton, false);
    }

    public Parent createScene() {
        root.setPrefSize(WIDTH,HEIGHT);
        scoreMobsLabel.setTranslateY(15);
        root.getChildren().add(player);
        root.getChildren().add(scoreLabel);
        root.getChildren().add(scoreMobsLabel);
        return root;
    }
}
