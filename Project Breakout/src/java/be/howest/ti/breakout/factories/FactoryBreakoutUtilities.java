/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.factories;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author micha
 */
public class FactoryBreakoutUtilities {
    protected List<String> usedColors = new ArrayList<>();
    protected final List<String> colors = new ArrayList<>();
     
    protected String findUnusedColor() {
        for (int i = 0; i < colors.size(); i++) {
            if (usedColors.indexOf(colors.get(i)) < 0) {
                usedColors.add(colors.get(i));
                return colors.get(i);
            }
        }
        return null;
    } 
}
