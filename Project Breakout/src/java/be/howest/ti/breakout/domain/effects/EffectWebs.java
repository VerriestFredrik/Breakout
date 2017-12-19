/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.domain.effects;

import be.howest.ti.breakout.domain.fieldeffects.Web;
import java.util.Random;

/**
 *
 * @author Fredr
 */
public class EffectWebs extends Effect{

    public EffectWebs(String name, int duration) {
        super(name, duration);
    }

    @Override
    public void activate() {
        System.out.println("activated webs");
        setRunning();
        Random generator = new Random();
        //((max-min) + 1) + min
        int randomX = generator.nextInt(((getLevelOfEffect().getGameWidth() - 100) - 100) + 1) + 100;
        int randomY = generator.nextInt(((getLevelOfEffect().getGameHeight() - 100) - 100) + 1) + 100;
        int randomRadius = generator.nextInt((100 - 50) + 1) + 50;
        System.out.println(randomRadius);
        getLevelOfEffect().addShapeToFieldEffectShapes(new Web(getLevelOfEffect(), randomX, randomY, randomRadius));
    }

    @Override
    public void deActivate() {
        System.out.println("deactivated webs");
        setDone();
    }
    
}
