package su.domino.ku.algoritmos;

import java.util.ArrayList;
import java.util.List;
import su.domino.ku.modelo.Ficha;
import su.domino.ku.modelo.Nodo;

public class Validaciones {

    private final int[][] tablero;
    private final List<Nodo> nodos;
    public Validaciones(int[][] tablero,List<Nodo> nodos) {
        this.tablero=tablero;
        this.nodos=nodos;
    }
//   Verifica si es posible  poner un valor en el tablero en una fila determinada
//    Retorna verdadero si el valor en la fila no existe
    public boolean validarFila(int valor, int x) {
        boolean esValida = true;

        for (int i = 0; (i < 9) && esValida; i++) {
            esValida = tablero[x][i] != valor;
        }

        return esValida;
    }
//    Verifica si es posible  poner un valor en el tablero en una columna determinada
//    Retorna verdadero si el valor en la columna no existe
    
    public boolean validarColumna(int valor, int y) {
        boolean esValida = true;

        for (int i = 0; (i < 9) && esValida; i++) {
            esValida = tablero[i][y] != valor;
        }
        return esValida;
    }
//    Verifica si es posible poner un valor en el tablero en un cuadrado 3x3 con una fila y columna determinada
//    Retorna verdadero si el valor en el cuadrado no existe
    
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
    ///////////////////////////////////////////////////////////////////////////////////
    //Metodo para buscar las orientaciones disponibles en un x,y determinado
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
    
    ///////////////////////////////////////////////////////
    //Metodo para imprimir el tablero
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
    ////////////////////////////////////////////////////////
    
    //Metodo que verifica si se puede poner una ficha en el nodo padre de acuerdo a las reglase del sudoku
    //Retorna verdadero en caso de que pueda, y falso si no es posible
    public boolean ponerNodo(Nodo nodo) {
        boolean v = true;
        if (nodo.getId() == 0) {
            
            v = true;
        } else if (ponerFicha(nodo.getFicha(), nodo.getX(), nodo.getY(), nodo.getOrientacion())) {
            v = ponerNodo(nodos.get(nodo.getIdPadre()));
        } else {
            v = false;
        }
        return v;
    }
    
    //Metodo para verificar si puede poner la ficha en el tablero con su orientacion
    //Retorna verdadero en caso que le sea posible, falso en caso contrario
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
    
    //Verifica si una ficha esta ´puesta en el nodo
    //Retorna verdadero si ya existe en el arbol
    public boolean esRepetida(int idficha, Nodo nodo) {
        boolean v;
        if (nodo.getId() == 2) {
            System.out.println("");
        }
        if (nodo.getIdPadre() == 2) {
            System.out.println("");
        }
        if (nodo.getId() == 0) {
            v = false;
        } else if (nodo.getFicha().getId() == idficha) {
            v = true;
        } else {
            v = esRepetida(idficha, nodos.get(nodo.getIdPadre()));
        }
        return v;
    }
 
    //Metodo que retorna la primera posicion x,y que tenga un 0
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
}
