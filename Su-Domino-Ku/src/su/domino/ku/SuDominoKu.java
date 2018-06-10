//Oscar Alexander Ruiz Palacio 201667600
//Andres Felipe Medina Tascon  201667602
//Andres Felipe Gonzalez Rojas 201759599

package su.domino.ku;

import su.domino.ku.frames.Tablero;
import java.awt.Dimension;
import java.awt.Toolkit;

public class SuDominoKu {

    public SuDominoKu() {

    }

    public static void main(String[] args) {
        Tablero tablerito = new Tablero();
        tablerito.setDefaultCloseOperation(tablerito.EXIT_ON_CLOSE);
        tablerito.setResizable(false);
        tablerito.setLocationRelativeTo(null);
        tablerito.setVisible(true);
    }
}
