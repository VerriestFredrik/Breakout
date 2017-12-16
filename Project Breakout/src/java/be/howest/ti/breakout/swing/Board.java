/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.breakout.swing;

import be.howest.ti.breakout.domain.Ball;
import be.howest.ti.breakout.domain.Brick;
import be.howest.ti.breakout.domain.BrickRow;
import be.howest.ti.breakout.domain.game.Game;
import be.howest.ti.breakout.domain.game.GameDifficulty;
import be.howest.ti.breakout.domain.game.Level;
import be.howest.ti.breakout.domain.game.MultiPlayerGame;
import be.howest.ti.breakout.domain.Pallet;
import be.howest.ti.breakout.domain.game.SinglePlayerGame;
import be.howest.ti.breakout.domain.game.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import be.howest.ti.breakout.domain.effects.Effect;
import be.howest.ti.breakout.domain.effects.EffectStatus;
import be.howest.ti.breakout.domain.powerUps.PowerUpOrDown;
import be.howest.ti.breakout.domain.spells.Spell;
import be.howest.ti.breakout.domain.spells.SpellStatus;

/**
 *
 * @author micha
 */
public class Board extends JPanel{
    User me = new User(1, "coolboi", "blabla", "hipitiehoppitie", 99, "pepe");
    User me2 = new User(2, "coolboi", "blabla", "hipitiehoppitie", 99, "pepe");
    User me3 = new User(3, "coolboi", "blabla", "hipitiehoppitie", 99, "pepe");
    User me4 = new User(4, "coolboi", "blabla", "hipitiehoppitie", 99, "pepe");
    List<GameDifficulty> difficulties;
    private Game game;
    private Level level;
    private ScheduleLevelTasker s;

    public Board() {
        this.difficulties = new ArrayList<>(Arrays.asList(new GameDifficulty("Easy", 0.2f), new GameDifficulty("Medium", -0.2f), new GameDifficulty("Hard", -0.4f)));
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        addKeyListener(new TEdaper());
        setFocusable(true);
        setDoubleBuffered(true);
        showDifficulties();
    }
    
    public void showDifficulties(){
        String[] options = new String[difficulties.size()];
        for (int i = 0; i < difficulties.size(); i++) {
            options[i] = difficulties.get(i).getName();
        }
        int response = JOptionPane.showOptionDialog(null, "Choose a Difficulty", "difficulties",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[0]);
        
        List<User> users = new ArrayList<>(Arrays.asList(me, me2, me3, me4));
        game = new SinglePlayerGame(me, 1000, 1000, difficulties.get(response));
        level = game.getLevelPlayedRightNow();
         s = new ScheduleLevelTasker(level, this);
         showSpellChoices();
    }
    
    public void showSpellChoices(){
        List<Spell> spells = level.getAllSpells();
        String[] options = new String[spells.size()];
        for (int i = 0; i < spells.size(); i++) {
            options[i] = spells.get(i).getName();
        }
        int response = JOptionPane.showOptionDialog(null, "Choose a Spell", "Spells",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[0]);
        level.setUserSpell(me, level.getAllSpells().get(response));
        level.startLevel(s);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (!game.isGameOver()) {
            drawObjects(g2d);
            drawTexts(g2d);
            if(level.isCompleted()){
                level = game.getLevelPlayedRightNow();
                s = new ScheduleLevelTasker(level, this);
                showSpellChoices();
            }
        } else {
            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
        Level level = game.getLevelPlayedRightNow();
        g2d.setColor(Color.BLUE);
        for (Pallet pallet : level.getPallets()) {
            g2d.fillRect(pallet.getX(), pallet.getY(), pallet.getLength(), pallet.getHeight());
        }
        g2d.setColor(Color.BLACK);
        for (Ball ball : level.getBalls()) {
            g2d.fillOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
        }
        
        for (BrickRow rowsOfBrick : level.getRowsOfBricks()) {
            for (Brick brick : rowsOfBrick.getBricksOnRow()) {
                g2d.setColor(Color.RED);
                g2d.fillRect(brick.getX(), brick.getY(), brick.getLength(), brick.getHeight());
                g2d.setColor(Color.BLACK);
                g2d.drawRect(brick.getX(), brick.getY(), brick.getLength(), brick.getHeight());
                g2d.drawString(""+brick.getHits(), (brick.getX() + (brick.getLength() / 2)), (brick.getY() + (brick.getHeight() / 2)));
            }
        }
        for (PowerUpOrDown powerUp : level.getPowerUpsShownOnScreen()) {
            g2d.drawRect(powerUp.getBoundaries().getX(), powerUp.getBoundaries().getY(), powerUp.getBoundaries().getLength(), powerUp.getBoundaries().getHeight());
        }
    }
    
    private void drawTexts(Graphics2D g2d) {
        Font font = new Font("Verdana", Font.BOLD, 18);
        String levelNumber = "Level " + game.getLevels().size();
        String lives = "Lives x " + game.getLives();
        String score = "Score " + game.getPlayers().get(0).getScore();
        String scoreTotal = "Total Score " + game.getScore();
        String powerup = "PowerUp Active: ";
        for (PowerUpOrDown powerUp : level.getAllActivePowerUps()) {
            powerup += powerUp.toString();
        }
        String spell = "effects of spell active: ";
        for (Effect effect :  level.getAllSpellsInGame().get(me).getSpellEffects()) {
            if(effect.isActivated() == EffectStatus.RUNNING){
                spell += effect.toString() + ", ";
            }
        }
        String spellCooldown = "Cooldown " + level.getAllSpellsInGame().get(me).getCooldown();
        

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(levelNumber, 5, 20);
        g2d.drawString(lives, 5, 50);
        g2d.drawString(score, 5, 80);
        g2d.drawString(scoreTotal, 5, 110);
        g2d.drawString(powerup, 100, 20);
        g2d.drawString(spell, 100, 50);
        if(level.getAllSpellsInGame().get(me).isActivated() == SpellStatus.COOLDOWN){
            g2d.drawString(spellCooldown, 100, 80);
        }
    }
    
    private void gameFinished(Graphics2D g2d) {
        Font font = new Font("Verdana", Font.BOLD, 18);
        String gameover = "Game over";
        String scoreThisLevel = "Your Score this Level " + game.getLevels().get(game.getLevels().size() - 1).getScore();
        String totalScore = "Your Total Score " + game.getScore();
        String thankyou = "Thank you for playing!";

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(gameover, 410, 450);
        g2d.drawString(scoreThisLevel, 340, 480);
        g2d.drawString(totalScore, 365, 510);
        g2d.drawString(thankyou, 360, 540);
    }

    private class TAdapter extends KeyAdapter {
        
        @Override
        public void keyReleased(KeyEvent e) {
            level.getPallets().get(0).keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            level.getPallets().get(0).keyPressed(e);
        }
    }
    
    private class TEdaper extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            Spell spell = level.getSpellByUser(me); 
            spell.keyPressed(e); 
        }
    }

}
