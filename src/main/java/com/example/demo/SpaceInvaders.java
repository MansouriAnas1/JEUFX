package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.stream.IntStream;

public class SpaceInvaders extends Application {

    static int score;
    private double mouseX;
    public static final Random RAND = new Random();
    public static final int PLAYER_SIZE = 60;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    static final Image PLAYER_IMG = new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket.png");
    static final Image EXPLOSION_IMG = new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\explosion.png");

    static final int EXPLOSION_W = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_H = 128;
    static final int EXPLOSION_STEPS = 15;

    static final Image[] BOMBS_IMG =
            {
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png"),
                    new Image("file:E:\\ENSET\\S3\\JAVA\\FXGame\\rocket2.png")
            };

    final int MAX_BOMBS = 10, MAX_SHOTS = MAX_BOMBS * 2;
    boolean gameOver = false;
    public static GraphicsContext gc;
    Rocket player;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> bombs;

    Bomb newBomb() {

        return new Bomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[RAND.nextInt(BOMBS_IMG.length)]);
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    private void setup() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        bombs = new ArrayList<>();
        player = new Rocket(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;
        IntStream.range(0, MAX_BOMBS).mapToObj(i -> this.newBomb()).forEach(bombs::add);

    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);

        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over\n Your Score is : " + score + " \n Click to play again", WIDTH / 2, HEIGHT / 2.5);
        }
        univ.forEach(Universe::draw);

        player.update();
        player.draw();
        player.posX = (int) mouseX;

        bombs.stream().peek(Rocket::update).peek(Rocket::draw).forEach(e -> {
            if (player.collide(e) && !player.exploding) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw(score);
            for (Bomb bomb : bombs) {
                if (shot.colide(bomb) && !bomb.exploding) {
                    score++;
                    bomb.explode();
                    shot.toRemove = true;
                }

            }
        }
        //add new bomb if less that 10 available
        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (bombs.get(i).destoryed) bombs.set(i, newBomb());
        }

        gameOver = player.destoryed;
        if (RAND.nextInt(10) > 2) univ.add(new Universe());

        for (int i = 0; i < univ.size(); i++) {
            if (univ.get(i).posY > HEIGHT) univ.remove(i);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        canvas.setOnMouseClicked(e -> {
            if (shots.size() < MAX_SHOTS) shots.add(player.shoot());
            if (gameOver) {
                gameOver = false;
                setup();
            }

        });
        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space Invaders");
        stage.show();
    }


}
