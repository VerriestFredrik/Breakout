/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.data;

import java.util.List;
import be.howest.ti.breakout.domain.effects.Effect;

/**
 *
 * @author micha
 */
public interface EffectRepository {
    public List<Effect> getAllEffects();
    public Effect getEffect(int effectID);
    public List<Effect> getEffectOfPowerUp(int powerUpID);
}
