package su.domino.ku;

public class Nodo {
    private int id;
    private int idPadre;
    private Ficha ficha;
    private int x;
    private int y;
    private int orientacion; //0°, 90°, 180°, 270°
    private int xsiguiente;
    private int ysiguiente;
    private boolean expandido;

    

    public Nodo(int id, int idPadre, Ficha ficha, int x, int y,int orientacion, boolean expandido) {
        this.id = id;
        this.idPadre = idPadre;
        this.ficha = ficha;
        this.x = x;
        this.y = y;
        this.expandido = expandido;
        this.orientacion=orientacion;
        
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
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

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXsiguiente() {
        return xsiguiente;
    }

    public void setXsiguiente(int xsiguiente) {
        this.xsiguiente = xsiguiente;
    }

    public int getYsiguiente() {
        return ysiguiente;
    }

    public void setYsiguiente(int ysiguiente) {
        this.ysiguiente = ysiguiente;
    }

    

    public boolean isExpandido() {
        return expandido;
    }

    public void setExpandido(boolean expandido) {
        this.expandido = expandido;
    }
    
}
