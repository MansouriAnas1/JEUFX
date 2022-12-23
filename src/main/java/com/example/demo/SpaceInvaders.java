package com.example.demo;

import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class SpaceInvaders extends Application {

    public static final  Random RAND  = new Random();
    public static final  int PLAYER_SIZE  = 60;
    public static final  int WIDTH  = 800;
    public static final  int HEIGHT  = 600;


    static final Image PLAYER_IMG = new Image();
    static final Image EXPLOSION_IMG = new Image();


    static final int EXPLOSION_W = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_H = 128;
    static final int EXPLOSION_STEPS = 15;

    static final Image BOMBS_IMG[]{
        new Image();
        new Image();
        new Image();
        new Image();
        new Image();
        new Image();
        new Image();
        new Image();
        new Image();
        new Image();
    };
final int MAX_BOMBS = 10, MAX_SHOTS = MAX_BOMBS * 2;
boolean gameOver=false;
public static GraphicsContext gc;
Rocket player;
List<Shot> shots;
List<Universe> univ;
List<Bomb>Bombs;



    @Override
    public void start(Stage stage) throws Exception {

    }
}
