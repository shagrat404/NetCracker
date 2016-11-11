package lec14.miner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Character extends Pane {
    private ImageView imageView;
    private int count = 3;
    private int columns = 3;
    private int offsetX = 0;
    private int offsetY = 0;
    private int width = 32;
    private int height = 32;
    private int score = 0;
    Rectangle removeRect = null;
    SpriteAnimation animation;

    public Character(ImageView imageView) {
        this.imageView = imageView;
        this.imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView,Duration.millis(200),count,columns,offsetX,offsetY,width,height);
        getChildren().addAll(imageView);
    }

    public void moveX(int x) {
        boolean right = x > 0;
        for(int i = 0; i < Math.abs(x); i++) {
            for (Mob mob : Game.mobs) {
                if (this.getBoundsInParent().intersects(mob.getBoundsInParent())) {
                    if (right) {
                        this.setTranslateX(this.getTranslateX() - 1);
                        return;
                    } else {
                        this.setTranslateX(this.getTranslateX() + 1);
                        return;
                    }
                }
            }
            if (right) this.setTranslateX(this.getTranslateX() + 1);
            else this.setTranslateX(this.getTranslateX() - 1);
            isBonuseEat();
            if (getTranslateX() < 0) {
                setTranslateX(0);
            }
            if (getTranslateX() > 600 - width) {
                setTranslateX(600 - width);
            }
        }
    }

    public void moveY(int y) {
        boolean moveDown = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            for (Mob mob : Game.mobs) {
                if (this.getBoundsInParent().intersects(mob.getBoundsInParent())) {
                    if (moveDown) {
                        this.setTranslateY(this.getTranslateY() - 1);
                        return;
                    } else {
                        this.setTranslateY(this.getTranslateY() + 1);
                        return;
                    }
                }
            }
            if (moveDown) this.setTranslateY(this.getTranslateY() + 1);
            else this.setTranslateY(this.getTranslateY() - 1);
            isBonuseEat();
            if (getTranslateY() < 0) {
                setTranslateY(0);
            }
            if (getTranslateY() > 600 - width) {
                setTranslateY(600 - width);
            }
        }
    }

    public void isBonuseEat() {
        Game.bonuses.forEach((rect) -> {
            if (this.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                removeRect = rect;
                score++;
//                System.out.println(score);
            }
        });
        Game.bonuses.remove(removeRect);
        Game.root.getChildren().remove(removeRect);
    }

    public int getScore() {
        return score;
    }
}