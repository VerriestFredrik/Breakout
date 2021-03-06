/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.domain.effects;

import be.howest.ti.breakout.domain.fieldeffects.Web;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Fredr
 */
public class EffectWebs extends Effect{
    List<Web> websCreated = new ArrayList<>();

    public EffectWebs(String name, String description, int duration) {
        super(name, description, duration);
    }

    @Override
    public void activate() {
        setRunning();
        Random generator = new Random();

        int randomX = generator.nextInt(((getLevelOfEffect().getGameWidth() - 100) - 100) + 1) + 100;
        int randomY = generator.nextInt(((getLevelOfEffect().getGameHeight() - 100) - 100) + 1) + 100;
        int randomRadius = generator.nextInt((100 - 50) + 1) + 50;
        
        Web newWeb = new Web(LevelOfEffect, randomX, randomY, randomRadius);
        websCreated.add(newWeb);
        LevelOfEffect.addShapeToFieldEffectShapes(newWeb);
    }

    @Override
    public void deActivate() {
        for (Web web : websCreated) {
            web.removeYourselfNow();
        }
        setDone();
    }
    
}
