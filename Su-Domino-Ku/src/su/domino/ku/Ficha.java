
package su.domino.ku;

class Ficha {
    private int id; //identificador
    private int valorA; //valor primera mitad de la pieza
    private int valorB; //valor segunda mitad de la pieza
    private int orientacion; //0°, 90°, 180°, 270°
    
    public Ficha(){       
    }

    public Ficha(int id, int valorA, int valorB, int orientacion) {
        this.id = id;
        this.valorA = valorA;
        this.valorB = valorB;
        this.orientacion = orientacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValorA() {
        return valorA;
    }

    public void setValorA(int valorA) {
        this.valorA = valorA;
    }

    public int getValorB() {
        return valorB;
    }

    public void setValorB(int valorB) {
        this.valorB = valorB;
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }
    
    
}
