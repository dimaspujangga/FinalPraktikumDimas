/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybirdfp;

/**
 *
 * @author dimas
 */
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {

    public static final int Pipe_DELAY = 100;

    private Boolean paused;

    private int pauseDelay;
    private int restartDelay;
    private int PipeDelay;

    private Ostrich Ostrich;
    private ArrayList<Pipe> Pipe;
    private Keyboard keyboard;

    public int score;
    public Boolean gameover;
    public Boolean started;

    public Game() {
        keyboard = Keyboard.getInstance();
        restart();
    }

    public void restart() {
        paused = false;
        started = false;
        gameover = false;

        score = 0;
        pauseDelay = 0;
        restartDelay = 0;
        PipeDelay = 0;

        Ostrich = new Ostrich();
        Pipe = new ArrayList<Pipe>();
    }

    public void update() {
        watchForStart();

        if (!started)
            return;

        watchForPause();
        watchForReset();

        if (paused)
            return;

        Ostrich.update();

        if (gameover)
            return;

        movePipes();
        checkForCollisions();
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<Render>();
        renders.add(new Render(0, 0, "lib/background.jpg"));
        for (Pipe pipe : Pipe)
            renders.add(pipe.getRender());
        renders.add(new Render(0, 0, "lib/desert.png"));
        renders.add(Ostrich.getRender());
        return renders;
    }

    private void watchForStart() {
        if (!started && keyboard.isDown(KeyEvent.VK_SPACE)) {
            started = true;
        }
    }

    private void watchForPause() {
        if (pauseDelay > 0)
            pauseDelay--;

        if (keyboard.isDown(KeyEvent.VK_P) && pauseDelay <= 0) {
            paused = !paused;
            pauseDelay = 10;
        }
    }

    private void watchForReset() {
        if (restartDelay > 0)
            restartDelay--;

        if (keyboard.isDown(KeyEvent.VK_R) && restartDelay <= 0) {
            restart();
            restartDelay = 10;
            return;
        }
    }

    private void movePipes() {
        PipeDelay--;

        if (PipeDelay < 0) {
            PipeDelay = Pipe_DELAY;
            Pipe northPipe = null;
            Pipe southPipe = null;

            // Look for pipe off the screen
            for (Pipe pipe : Pipe) {
                if (pipe.x - pipe.width < 0) {
                    if (northPipe == null) {
                        northPipe = pipe;
                    } else if (southPipe == null) {
                        southPipe = pipe;
                        break;
                    }
                }
            }

            if (northPipe == null) {
                Pipe pipe = new Pipe("north");
                Pipe.add(pipe);
                northPipe = pipe;
            } else {
                northPipe.reset();
            }

            if (southPipe == null) {
                Pipe pipe = new Pipe("south");
                Pipe.add(pipe);
                southPipe = pipe;
            } else {
                southPipe.reset();
            }

            northPipe.y = southPipe.y + southPipe.height + 175;
        }

        for (Pipe pipe : Pipe) {
            pipe.update();
        }
    }

    private void checkForCollisions() {

        for (Pipe pipe : Pipe) {
            if (pipe.collides(Ostrich.x, Ostrich.y, Ostrich.width, Ostrich.height)) {
                gameover = true;
                Ostrich.dead = true;
            } else if (pipe.x == Ostrich.x && pipe.orientation.equalsIgnoreCase("south")) {
                score++;
            }
        }

        // Ground + Bird collision
        if (Ostrich.y + Ostrich.height > App.HEIGHT - 80) {
            gameover = true;
            Ostrich.y = App.HEIGHT - 80 - Ostrich.height;
        }
    }
}
