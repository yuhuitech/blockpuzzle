package de.mwvb.blockpuzzle.view;

/**
 * Kästchen ausfüllen Strategie
 */
public interface MatrixGet { // TODO nicht der beste Name

    /**
     * @return IBlockDrawer für ein nicht leeres Kästchen
     */
    IBlockDrawer get(int x, int y);
}
