/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.data;

import be.howest.ti.breakout.domain.powerUps.AllPowerUps;
import be.howest.ti.breakout.domain.powerUps.PowerUpOrDown;

/**
 *
 * @author Henri
 */
public interface PowerUpOrDownRepository {
    public AllPowerUps getAllPowerUpsAndDowns();
    public PowerUpOrDown getPowerUpOrDownWithName(String name);
    public PowerUpOrDown getPowerUpOrDownWithId(int id);
    public void addPowerUpOrDown(PowerUpOrDown p);
    public void deletePowerUpOrDown(PowerUpOrDown p);
}
