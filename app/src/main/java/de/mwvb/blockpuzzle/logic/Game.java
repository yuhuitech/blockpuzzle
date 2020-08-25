package de.mwvb.blockpuzzle.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.mwvb.blockpuzzle.MainActivity;
import de.mwvb.blockpuzzle.logic.spielstein.*;

public class Game {
    public static final int blocks = 10;
    private static final Random rand = new Random(System.currentTimeMillis());
    private final MainActivity view;
    private final Spielfeld spielfeld = new Spielfeld(blocks);
    private final List<Spielstein> teile = new ArrayList<>();
    private int punkte = 0;
    private boolean gameOver = false;
    private boolean drehen = false;
    // TODO Bisher höchste Punktzahl persistieren.
    // TODO Wenn eine Exception auf Handy auftritt, muss ich das irgendwie mitbekommen.
    // TODO Drag Schatten anzeigen
    // TODO Sound: Game over
    // TODO Anwendung wird "minimiert": Daten behalten

    // Spielaufbau ----

    public Game(MainActivity activity) {
        view = activity;

        teile.add(new Teil1());
        teile.add(new Teil2());
        teile.add(new Teil2().rotateToRight());
        teile.add(new Teil3());
        teile.add(new Teil3().rotateToRight());
        teile.add(new Teil4());
        teile.add(new Teil4().rotateToRight());
        teile.add(new Teil5());
        teile.add(new Teil5().rotateToRight());
        teile.add(new TeilEcke2());
        teile.add(new TeilEcke2().rotateToRight());
        teile.add(new TeilEcke2().rotateToRight().rotateToRight());
        teile.add(new TeilEcke2().rotateToLeft());
        teile.add(new TeilEcke3());
        teile.add(new TeilEcke3().rotateToRight());
        teile.add(new TeilEcke3().rotateToRight().rotateToRight());
        teile.add(new TeilEcke3().rotateToLeft());
        teile.add(new Teil2x2());
        teile.add(new Teil3x3());
    }

    // Neues Spiel ----

    public void newGame() {
        gameOver = false;
        punkte = 0;
        spielfeld.clear();

        view.updatePunkte();
        view.drawSpielfeld();
        vorschlag();
        view.setTeil(-1, null);
        // TODO Drehmodus deaktivieren
    }

    /** 3 neue zufällige Spielsteine anzeigen */
    private void vorschlag() {
        view.setTeil(1, createZufallsteil(teile));
        view.setTeil(2, createZufallsteil(teile));
        view.setTeil(3, createZufallsteil(teile));
    }

    private Spielstein createZufallsteil(List<Spielstein> teile) {
        int index = rand.nextInt(teile.size());
        return teile.get(index);
    }

    // Spielaktionen ----

    /** Drop Aktion für Spielfeld oder Parking */
    public void dispatch(boolean targetIsParking, int index, Spielstein teil, float x, float y) {
        if (gameOver) {
            return;
        }
        boolean ret;
        if (targetIsParking) {
            ret = parke(index, teil);
        } else {
            ret = platziere(index, teil, x, y);
        }
        if (ret) {
            if (view.getTeil(1) == null && view.getTeil(2) == null && view.getTeil(3) == null) {
                vorschlag();
            }
            checkGame();
        } else {
            view.gehtNicht();
        }
    }

    /** Drop Aktion für Parking Area */
    private boolean parke(int index, Spielstein teil) {
        if (index != -1 && view.getTeil(-1) == null) { // es geht wenn Source 1,2,3 und Parking frei
            view.setTeil(-1, view.getTeil(index)); // Parking belegen
            view.setTeil(index, null); // Source leeren
            return true;
        }
        return false;
    }

    /**
     * Drop Aktion für Spielfeld
     * @return true wenn Teil platziert wurde, false wenn dies nicht möglich ist
     */
    private boolean platziere(int index, Spielstein teil, float x, float y) {
        final int br = 60;
        QPosition pos = new QPosition((int) x / br, (int) y / br - 2 - (teil.getMaxY() - teil.getMinY()));
        boolean ret = spielfeld.match(teil, pos);
        if (ret) {
            spielfeld.platziere(teil, pos);
            view.drawSpielfeld();
            view.setTeil(index, null);

            // Gibt es gefüllte Rows?
            FilledRows f = spielfeld.getFilledRows();

            // Punktzahl erhöhen
            punkte += teil.getPunkte() + 10 * f.getTreffer();
            view.updatePunkte();

            view.clearRows(f); // Wird erst wenige Millisekunden später fertig!
            spielfeld.clearRows(f);
        }
        return ret;
    }

    private void checkGame() {
        // es muss ein Teil noch rein gehen
        boolean a = moveImpossible(1);
        boolean b = moveImpossible(2);
        boolean c = moveImpossible(3);
        boolean d = moveImpossible(-1);
        debug("checkGame: " + a + ", " + b + ", " + c + "; " + d);
        if (a && b && c && d && view.getTeil(-1) != null) {
            gameOver = true;
            view.updatePunkte();
            view.drawSpielfeld(); // wenn parke die letzte Aktion war
        }
    }

    public boolean moveImpossible(int index) {
        Spielstein teil = view.getTeil(index);
        if (teil == null) {
            debug("moveImpossible("+index+") -> true weil Teil ist leer");
            return true; // Teil ist leer
        }
        for (int x = 0; x < blocks; x++) {
            for (int y = 0; y < blocks; y++) {
                if (spielfeld.match(teil, new QPosition(x, y))) {
                    view.grey(index, false);
                    debug("moveImpossible("+index+") -> false, d.h. Move ist möglich");
                    return false; // Teil passt rein
                }
            }
        }
        view.grey(index, true);
        debug("moveImpossible("+index+") -> true, d.h. Teil passt nicht rein");
        return true; // Teil passt nirgendwo rein
    }

    public int getPunkte() {
        return punkte;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int get(int x, int y) {
        return spielfeld.get(x, y);
    }

    public boolean toggleDrehmodus() {
        drehen = !drehen;
        return drehen;
    }

    private void debug(String msg) {
        System.out.println(msg);
    }
}
