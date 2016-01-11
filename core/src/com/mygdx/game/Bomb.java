package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/**
 * Created by dinnatruong on 16-01-10.
 */
public class Bomb {
    Sprite[] spBomb;
    SpriteBatch spriteBatch;
    int j, nSpeed, nIndex;
    boolean isExploded;
    float x, y;
    public Array<Explosion> arExplosions;

    Bomb(TextureAtlas taBombExplode, float fX, float fY, int nCurrentIndex, Sprite sprChar) {
        x = fX;
        y = fY;
        nIndex = nCurrentIndex;
        spriteBatch = new SpriteBatch();
        isExploded = false;
        spBomb = new Sprite[16];
        j = 0;
        nSpeed = 0;

        //Set position of bomb based on the direction of character, 0=up 1=down 2=left 3=right
        if (nIndex == 0) {
            y += sprChar.getHeight();
        } else if (nIndex == 1) {
            y -= sprChar.getHeight();
        } else if (nIndex == 2) {
            x -= sprChar.getWidth() + 10;
        } else if (nIndex == 3) {
            x += sprChar.getWidth() + 10;
        }

        //Loop through regions of the TextureAtlas and assign each to an index of the array
        for(int a = 0; a < 16; a++) {
            spBomb[a] = new Sprite(taBombExplode.findRegion("frame_"+ a));
            spBomb[a].setSize(25, 25);
            spBomb[a].setOrigin(spBomb[a].getHeight() / 2, spBomb[a].getWidth() / 2);
            spBomb[a].setPosition(x, y);
        }
        arExplosions = new Array<Explosion>();
    }

    //https://github.com/sfaci/jbombermanx.git
    public void explode() {
        Explosion explosion = new Explosion(x+4, y);
        arExplosions.add(explosion);
    }

    public void render () {
        spriteBatch.begin();
        spBomb[j].draw(spriteBatch);
        nSpeed++;   //nSpeed changes the time interval at which the sprites are drawn
        if (nSpeed%8 == 0) {
            isExploded = isExploded(j);
            if (j > 6) {
                explode();
                for (Explosion explosion : arExplosions) {
                    explosion.render();
                    System.out.println("Exploded");
                }
            }
            j++;
        }
        spriteBatch.end();
    }

    //Check if index is at the end of the animation
    public boolean isExploded(int j) {
        if (j == 15) {
            return true;
        }
        return false;
    }
}