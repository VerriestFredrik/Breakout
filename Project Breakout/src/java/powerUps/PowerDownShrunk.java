/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerUps;

import domain.Ball;
import domain.Brick;
import domain.Level;
import domain.Pallet;
import java.util.Timer;

/**
 *
 * @author micha
 */
public class PowerDownShrunk extends PowerUpOrDown{
    private Pallet palletOfUser;
    private Timer t;

    public PowerDownShrunk(Level level, Brick theBrick, String name, int duration, String description) {
        super(level, theBrick, name, duration, description);
    }

//    @Override
//    public void show() {
//       brick.getLevel().getPowerUpsShownOnScreen().add(this); 
//    }

    @Override
    public void activate() {
        palletOfUser = level.getUserPallet(ballActivatedPower.getLastUserThatTouchedMe());
        
//        level.getPowerUpsShownOnScreen().remove(this);
//        level.resetPowerUps();
//        level.setPowerUpActive(this);
        
        setRunning();

        palletOfUser.setLength((int) (palletOfUser.getLength() - (palletOfUser.getLength() * 0.2)));
        
        System.out.println("activated");
        
        t = new Timer();
        t.schedule(new TimerTaskPower(this), 0, 1000);
    }

    @Override
    public void deActivate() {
        System.out.println("deactivated");
        t.cancel();
        palletOfUser.setLength((int) (palletOfUser.getLength() + (palletOfUser.getLength() * 0.2)));
        level.setPowerUpActive(new NoPower());
    }
    
}
