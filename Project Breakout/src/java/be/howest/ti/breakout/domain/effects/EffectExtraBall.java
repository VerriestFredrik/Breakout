/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.domain.effects;

import be.howest.ti.breakout.domain.Ball;
import be.howest.ti.breakout.domain.game.User;

/**
 *
 * @author micha
 */
public final class EffectExtraBall extends Effect {
 
    public EffectExtraBall(String name, int duration) {
        super(name, duration);
    }

    @Override
    public void activate() {
        //int amountOfBallsInLevel = (int) Math.ceil((double) getLevelOfEffect().getAantalSpelers() / 2);
        //if (!(getLevelOfEffect().getBalls().size() >= amountOfBallsInLevel + 1)) {
        if(!hasUserAlreadyActivatedThisEffect()){
            setRunning();
            getLevelOfEffect().createExtraBall(this);
            System.out.println("activated");
        } else {
            setDone();
        }
    }

    @Override
    public void deActivate() {
        System.out.println("deactivated double trouble");
        getLevelOfEffect().getBalls().remove(getLevelOfEffect().getBalls().size() - 1);
        //getThisLevel().decrementLife();
        setDone();
    }

    @Override
    public String toString() {
        return super.toString() + " double-trouble";
    }
    
    public boolean hasUserAlreadyActivatedThisEffect(){
        for (Ball ball : getLevelOfEffect().getBalls()) {
            User u = ball.giveUserActivatedSpecialBall();
            if(u != null){
                if(u.getUserId() == getUserActivatedEffect().getUserId()){
                    return true;
                }
            }
        }
        return false;
    }

}