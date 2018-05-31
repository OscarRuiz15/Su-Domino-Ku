package su.domino.ku.algoritmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import su.domino.ku.modelo.Ficha;
import su.domino.ku.modelo.Nodo;

public class VegasOne {

    private int tablero[][];
    private final int tableroinicial[][];
    int columna[];
    private final ArrayList<Ficha> fichas = new ArrayList<Ficha>();
    private final List<Nodo> nodos = new ArrayList<>();
    private Nodo padre;
    private int recorrido;
    private int x = 0;
    private int y = 0;
    private int fichaspuestas;
    Random aleatorio = new Random(System.currentTimeMillis());
    int posFicha = 0;
    int posOrientacion = 0;
    int orientacion = 0;
    long iteracion = 0;

    public VegasOne(int[][] tablero) {
        System.out.println("\t\t\tVEGAS 1");
        this.tablero = tablero;
        generarFichas();
        tableroinicial = new int[9][9];
        columna = tablero[0].clone();
        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();
        }

    }

    public void algoritmoVegas() {
        Nodo nodito = new Nodo(0, 0, null, 0, 0, 0, false);
        padre = nodito;
        nodos.add(nodito);

        while (!(padre.getId() != 0 && padre.getXsiguiente() == 0 && padre.getYsiguiente() == 0)) {

            recorrido = nodos.size();
            fichaspuestas++;

            for (int i = 0; i < recorrido; i++) {
                if (!nodos.get(i).isExpandido()) {
                    padre = nodos.get(i);
                    padre.setExpandido(true);
                    x = padre.getXsiguiente();
                    y = padre.getYsiguiente();

                    ArrayList<Ficha> fichastemporal = new ArrayList();
                    fichastemporal = (ArrayList<Ficha>) fichas.clone();

                    System.out.println("============================================================");
                    while (fichastemporal.size() > 0) {
                        ArrayList<Integer> orientacionesDisponibles = cargarOrientaciones(x, y);

                        System.out.println("x:" + x + " y:" + y + " Orientaciones disponibles: " + orientacionesDisponibles.size());

                        for (int k = 0; k < orientacionesDisponibles.size(); k++) {
                            System.out.print(orientacionesDisponibles.get(k) + "-");
                        }

                        System.out.println("\n\nFichas temporales disponibles: " + fichastemporal.size());
                        verFichas(fichastemporal);

                        aleatorio = new Random(System.currentTimeMillis());
                        posFicha = aleatorio.nextInt(fichastemporal.size());
                        Ficha fichaAux = fichastemporal.get(posFicha);
                        System.out.println("\n\nFicha elegida al azar: " + fichaAux.getValorA() + ":" + fichaAux.getValorB());

                        if (!esRepetida(fichaAux, padre)) {
                            while (orientacionesDisponibles.size() > 0) {
                                aleatorio = new Random(System.currentTimeMillis());
                                posOrientacion = aleatorio.nextInt(orientacionesDisponibles.size());
                                orientacion = orientacionesDisponibles.get(posOrientacion);
                                System.out.println("-> Orientacion elegida al azar: " + orientacion);
                                agregarFicha(fichaAux, orientacion);
                                orientacionesDisponibles.remove(posOrientacion);
                            }
                        } else {
                            System.out.println("Ficha " + fichaAux.getValorA() + ":" + fichaAux.getValorB() + " ya puesta\n----------------------------------------------");
                        }
                        fichastemporal.remove(fichas.get(posFicha).getId() - 1);
                    }
                    System.out.println("============================================================");
                }
            }
        }

        System.exit(0);

    }

    public ArrayList<Integer> cargarOrientaciones(int x, int y) {
        int fila = x, col = y;
        ArrayList<Integer> orientacionesDisponibles = new ArrayList<Integer>();
        boolean borde = false;

        //Esto sera solamente cuando se encuentre en una esquina desocupada
        if (fila < 9 || col < 9) {
            //Esquina superior izquierda 0,0 Vertical
            if (fila == 0 && col == 0 && tablero[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                orientacionesDisponibles.add(180);
                borde = true;
            }
            //Esquina superior izquierda 0,0 Horizontal
            if (fila == 0 && col == 0 && tablero[fila][col + 1] == 0) {
                orientacionesDisponibles.add(90);
                orientacionesDisponibles.add(270);
                borde = true;
            }
            if (fila == 0 && col == 8 && tablero[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                orientacionesDisponibles.add(180);
                borde = true;
            }
            if (fila == 8 && col == 0 && tablero[fila][col + 1] == 0) {
                orientacionesDisponibles.add(90);
                orientacionesDisponibles.add(270);
                borde = true;
            }
        }

        //Esto es para el resto del tablero
        if (!borde) {
            if (fila < 8 && tablero[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                orientacionesDisponibles.add(180);
            }
            if (col < 8 && tablero[fila][col + 1] == 0) {
                orientacionesDisponibles.add(90);
                orientacionesDisponibles.add(270);
            }
        }
        return orientacionesDisponibles;
    }

    public void verTablero() {
        System.out.println("\nTablero");
        int aux1 = 2;
        int aux2 = 2;

        for (int i = 0; i < tablero.length; i++) {
            aux1 = 2;
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print(tablero[i][j] + " ");
                if (j == aux1) {
                    System.out.print("| ");
                    aux1 += 3;
                }
            }
            if (i == aux2 && i != 8) {
                System.out.println("\n---------------------");
                aux2 += 3;
            } else {
                System.out.println("");
            }
        }
    }

    //Pone los nodos en el tablero recursivamente
    public boolean ponerNodo(Nodo nodo) {
        boolean v = true;
        if (nodo.getId() == 0) {
            verTablero();
            v = true;
        } else if (ponerFicha(nodo.getFicha(), nodo.getX(), nodo.getY(), nodo.getOrientacion())) {
            v = ponerNodo(nodos.get(nodo.getIdPadre()));
        } else {
            v = false;
        }
        return v;
    }

    //Metodo para poner la ficha en el nodo
    public void agregarFicha(Ficha ficha, int orientacion) {
        Nodo nodo = new Nodo(nodos.size(), padre.getId(), ficha, x, y, orientacion, false);
        System.out.println("Iteracion N°: " + iteracion);
        iteracion++;
        System.out.println("Id Padre: " + padre.getId());
        System.out.println("Cambio en el Nodo: " + recorrido);
        System.out.println("Cantidad de Fichas puestas en el nodo: " + fichaspuestas);
        System.out.println("Orientacion " + orientacion + " Ficha " + ficha.getValorA() + ":" + ficha.getValorB());

        if (iteracion == 17039) {
            System.out.println("");
        }
        if (nodo.getId() == 167) {
            System.out.println("");
        }
        if (ponerNodo(nodo)) {

            int siguientes[] = siguienteIteracion();
            nodo.setXsiguiente(siguientes[0]);
            nodo.setYsiguiente(siguientes[1]);
            nodos.add(nodo);
            tablero = tableroinicial.clone();
            for (int i = 0; i < tablero.length; i++) {
                tablero[i] = tableroinicial[i].clone();

            }
            System.gc();
            System.out.println("Guardo en " + x + " " + y + " con orientacion " + orientacion + " la ficha " + ficha.getValorA() + ":" + ficha.getValorB());
        } else {
            System.out.println("NO guardo en " + x + " " + y + " con orientacion " + orientacion + " la ficha " + ficha.getValorA() + ":" + ficha.getValorB());
            for (int i = 0; i < tablero.length; i++) {
                tablero[i] = tableroinicial[i].clone();

            }
            System.gc();
        }
        System.out.println("----------------------------------------------");

    }

    //Metodo para poner la ficha en el tablero con su orientacion
    public boolean ponerFicha(Ficha ficha, int x, int y, int orientacion) {

        switch (orientacion) {
            case 0:
                if (validarColumna(ficha.getValorA(), y) && validarColumna(ficha.getValorB(), y) && validarFila(ficha.getValorA(), x)
                        && validarFila(ficha.getValorB(), x + 1) && validarCuadro(ficha.getValorA(), x, y) && validarCuadro(ficha.getValorB(), x + 1, y)) {
                    tablero[x][y] = ficha.getValorA();
                    tablero[x + 1][y] = ficha.getValorB();
                    return true;

                }
                break;
            case 90:
                if (validarColumna(ficha.getValorA(), y) && validarColumna(ficha.getValorB(), y + 1) && validarFila(ficha.getValorA(), x)
                        && validarFila(ficha.getValorB(), x) && validarCuadro(ficha.getValorA(), x, y) && validarCuadro(ficha.getValorB(), x, y + 1)) {
                    tablero[x][y] = ficha.getValorA();
                    tablero[x][y + 1] = ficha.getValorB();
                    return true;
                }
                break;

            case 180:
                if (validarColumna(ficha.getValorB(), y) && validarColumna(ficha.getValorA(), y) && validarFila(ficha.getValorB(), x)
                        && validarFila(ficha.getValorA(), x + 1) && validarCuadro(ficha.getValorB(), x, y) && validarCuadro(ficha.getValorA(), x + 1, y)) {
                    tablero[x][y] = ficha.getValorB();
                    tablero[x + 1][y] = ficha.getValorA();
                    return true;
                }
                break;
            case 270:
                if (validarColumna(ficha.getValorB(), y) && validarColumna(ficha.getValorA(), y + 1) && validarFila(ficha.getValorB(), x)
                        && validarFila(ficha.getValorA(), x) && validarCuadro(ficha.getValorB(), x, y) && validarCuadro(ficha.getValorA(), x, y + 1)) {
                    tablero[x][y] = ficha.getValorB();
                    tablero[x][y + 1] = ficha.getValorA();
                    return true;
                }
                break;
            default:

                break;
        }
        return false;
    }

    public boolean esRepetida(Ficha ficha, Nodo nodo) {
        boolean v;
        if (nodo.getId() == 2) {
            System.out.println("");
        }
        if (nodo.getIdPadre() == 2) {
            System.out.println("");
        }
        if (nodo.getId() == 0) {
            v = false;
        } else if (nodo.getFicha().getId() == ficha.getId()) {
            v = true;
        } else {
            v = esRepetida(ficha, nodos.get(nodo.getIdPadre()));
        }
        return v;
    }

    public boolean validarFila(int valor, int x) {
        boolean esValida = true;

        for (int i = 0; (i < 9) && esValida; i++) {
            esValida = tablero[x][i] != valor;
        }

        return esValida;
    }

    public boolean validarColumna(int valor, int y) {
        boolean esValida = true;

        for (int i = 0; (i < 9) && esValida; i++) {
            esValida = tablero[i][y] != valor;
        }
        return esValida;
    }

    public boolean validarCuadro(int valor, int fila, int columna) {
        boolean esValido = true;
        int minimo_fila;
        int maximo_fila;
        int minimo_columna;
        int maximo_columna;

        if (fila >= 0 && fila < 3) {
            minimo_fila = 0;
            maximo_fila = 2;
        } else if (fila >= 3 && fila < 6) {
            minimo_fila = 3;
            maximo_fila = 5;
        } else {
            minimo_fila = 6;
            maximo_fila = 8;
        }

        if (columna >= 0 && columna < 3) {
            minimo_columna = 0;
            maximo_columna = 2;
        } else if (columna >= 3 && columna < 6) {
            minimo_columna = 3;
            maximo_columna = 5;
        } else {
            minimo_columna = 6;
            maximo_columna = 8;
        }

        for (int f = minimo_fila; (f <= maximo_fila) && esValido; f++) {
            for (int c = minimo_columna; (c <= maximo_columna) && esValido; c++) {
                esValido = tablero[f][c] != valor;
            }
        }

        return esValido;
    }

    public int[] siguienteIteracion() {
        int casillas[] = new int[2];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 0) {
                    casillas[0] = i;
                    casillas[1] = j;
                    return casillas;

                }

            }

        }
        return casillas;
    }

    public void generarFichas() {
        fichas.clear();
        int cont = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i < j + 1) {
                    int s = j + 2;
                    int z = i + 1;
                    Ficha fichita = new Ficha(cont + 1, z, s);
                    fichas.add(fichita);
                    cont++;
                }
            }
        }
    }

    public void verFichas(ArrayList<Ficha> fichas) {
        for (int i = 0; i < fichas.size(); i++) {
            System.out.print("Id: " + fichas.get(i).getId() + " -> " + fichas.get(i).getValorA() + ":" + fichas.get(i).getValorB() + " - ");
        }
    }
}
