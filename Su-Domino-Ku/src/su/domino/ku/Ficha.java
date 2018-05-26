package su.domino.ku;

class Ficha implements Cloneable{

    private int id; //identificador
    private int valorA; //valor primera mitad de la pieza
    private int valorB; //valor segunda mitad de la pieza

     @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Ficha() {
    }

    public Ficha(int id, int valorA, int valorB) {
        this.id = id;
        this.valorA = valorA;
        this.valorB = valorB;

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

}
