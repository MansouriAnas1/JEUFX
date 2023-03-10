package com.example.demo;


import javafx.scene.image.Image;

import static com.example.demo.SpaceInvaders.HEIGHT;
import static com.example.demo.SpaceInvaders.score;

public class Bomb extends Rocket {
        int SPEED = (score/5)+2;

    public Bomb(int posX, int posY, int size, Image img) {
        super(posX, posY, size, img);
    }


    public void update(){
            super.update();
            if(!exploding && !destoryed) posY += (score/5)+2;
            if(posY>HEIGHT) destoryed = true;
        }
    }

