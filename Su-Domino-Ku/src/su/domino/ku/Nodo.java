package su.domino.ku;

public class Nodo {
    private int id;
    private int idPadre;
    private int tablero[][];
    private boolean expandido;

    public Nodo(int id, int idPadre, int[][] tablero, boolean expandido) {
        this.id = id;
        this.idPadre = idPadre;
        this.tablero = tablero;
        this.expandido = expandido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public boolean isExpandido() {
        return expandido;
    }

    public void setExpandido(boolean expandido) {
        this.expandido = expandido;
    }
    
}
