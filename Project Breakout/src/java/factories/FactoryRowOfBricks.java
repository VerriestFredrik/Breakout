/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import domain.Brick;
import domain.Level;
import domain.Level;
import domain.BrickRow;
import domain.BrickRow;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author micha
 */
public class FactoryRowOfBricks{
    private Level level;

    public FactoryRowOfBricks(Level level) {
        this.level = level;
    }
    
    public List<BrickRow> createRowOfBricks(){
        List<BrickRow> rowsOfBricks = new ArrayList();
        FactoryBricks factoryB = new FactoryBricks();
        for (int i = 0; i < level.getMAX_ROWS_BRICKS(); i++) {
            BrickRow rowBricks = new BrickRow(level);
            factoryB.createBricks(rowsOfBricks, rowBricks);
            rowsOfBricks.add(rowBricks);
        }
        return rowsOfBricks;
    }
}
