
package su.domino.ku;

class Ficha {
    private int id; //identificador
    private int valorA; //valor primera mitad de la pieza
    private int valorB; //valor segunda mitad de la pieza
    
    
    public Ficha(){       
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
