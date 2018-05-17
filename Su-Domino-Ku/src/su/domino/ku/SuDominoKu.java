package su.domino.ku;

public class SuDominoKu {
    
    public SuDominoKu() {
        
        /*for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] == 0) {
                    if (j + 1 < 9 && tablero[i][j + 1] == 0) {

                    }
                }
            }
        }*/
    }

    public static void main(String[] args) {
        Tablero tablerito=new Tablero();
        tablerito.setVisible(true);
        tablerito.setDefaultCloseOperation(tablerito.EXIT_ON_CLOSE);
        tablerito.setResizable(false);
    }
}
