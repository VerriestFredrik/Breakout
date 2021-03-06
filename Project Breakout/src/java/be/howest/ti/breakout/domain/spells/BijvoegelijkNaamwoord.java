/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.domain.spells;

import be.howest.ti.breakout.domain.game.Level;
import be.howest.ti.breakout.domain.effects.Effect;
import be.howest.ti.breakout.domain.game.Player;

/**
 *
 * @author micha
 */
public final class BijvoegelijkNaamwoord extends Woord{
    private final Effect effect;
    
    public BijvoegelijkNaamwoord(String naam, int amountOfDamage, Effect effect) {
        super(naam, amountOfDamage);
        this.effect = effect;
    }
    
    public void setEntetiesOfEffect(Level level, Player player){
        effect.setPlayerPallet(level.getPlayerPallet(player));
        effect.setBallActivatedEffect(effect.getPlayerPallet().getLastBallTouched());
        effect.setLevelOfEffect(level);
        effect.setPlayerActivatedEffect(player);
    }
    
    public void cast(){
        effect.setActive();
    }
    
    public String combineName(String combinedName){
        return getNaam() + " " + combinedName;
    }
    
    public int combineDamage(int totalDamageSoFar){
        return totalDamageSoFar += getAmountOfDamage();
    }
    
    public Effect getEffect(){
        return effect;
    }

    @Override
    public String toString() {
        return "BijvoegelijkNaamwoord{" + "effect=" + effect.toString() + '}';
    }
  
}
