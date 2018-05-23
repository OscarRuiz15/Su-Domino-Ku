package su.domino.ku;

import java.util.ArrayList;
import java.util.List;

public class BusquedaAmplitud {

    private int tablero[][];
    private final int tableroinicial[][];
    int columna[];
    private final List<Ficha> fichas;
    private final List<Nodo> nodos = new ArrayList<>();
    private Nodo padre;
    private int recorrido;
    private int x = 0;
    private int y = 0;
    private int fichaspuestas;
    
    long iteracion = 0;

    public BusquedaAmplitud(int[][] tablero, List<Ficha> fichas) {
        this.tablero = tablero;
        this.fichas = fichas;
        tableroinicial = new int[9][9];
        columna = tablero[0].clone();
        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();

        }

    }

    public void busquedaAmplitud() {

        Nodo nodito = new Nodo(0, 0, null, 0, 0, 0, false);
        padre = nodito;
        nodos.add(nodito);

        //boolean wait;
        ///////////Validar que algun numero de la ficha a usar no se encuentre en la fila o columna de todo el tablero :c
        //wait = false;
        //Si la posicion actual del tablero esta libre, buscara las orientaciones disponibles
        //Llamado a metodo para buscar las orientaciones disponibles segun la posicion actual      
        while (/*orientacionesDisponibles.size() > 0 && !wait &&*/y < 9) {

            recorrido = nodos.size();
            fichaspuestas++;
            int cont = 0;

            for (int i = 0; i < recorrido; i++) {
                if (!nodos.get(i).isExpandido()) {
                    padre = nodos.get(i);
                    padre.setExpandido(true);
                    x = padre.getXsiguiente();
                    y = padre.getYsiguiente();
                    ArrayList<Integer> orientacionesDisponibles = cargarOrientaciones(x, y);
                    for (int k = 0; k < orientacionesDisponibles.size(); k++) {
                        System.out.print(orientacionesDisponibles.get(k) + "-");
                    }
                    System.out.println("x:" + x + " y:" + y + " Orientaciones disponibles: " + orientacionesDisponibles.size());

                    //Imprimir orientaciones disponibles para la posicion actual
                    System.out.println("\n-----------------------------");

                    //Poner cada ficha en las orientaciones disponibles de la posicion actual
                    for (int k = 0; k < fichas.size(); k++) {

                        if (!esRepetida(k+1, padre)) {

                            cont = 0;
                            //Cuando la orientacion es 0 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 0) {
//                                tablero[x][y] = fichas.get(k).getValorA();
//                                tablero[x + 1][y] = fichas.get(k).getValorB();
                                cont++;
//                                verTablero();
                                agregarFicha(k, 0);

                            }
                            //Cuando la orientacion es 180 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 180) {
//                                tablero[x][y] = fichas.get(k).getValorB();
//                                tablero[x + 1][y] = fichas.get(k).getValorA();
                                cont++;
//                                verTablero();
                                agregarFicha(k, 180);
                            }
                            //Cuando la orientacion es 90 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 90) {
//                                tablero[x][y] = fichas.get(k).getValorA();
//                                tablero[x][y + 1] = fichas.get(k).getValorB();
                                cont++;
////                                verTablero();
                                agregarFicha(k, 90);
                            }
                            
                            //Cuando la orientacion es 270 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 270) {
//                                tablero[x][y] = fichas.get(k).getValorB();
//                                tablero[x][y + 1] = fichas.get(k).getValorA();
                                cont++;
//                                verTablero();
                                agregarFicha(k, 270);
                            }

                        }
                    }
                }
            }

            /*while (tablero[x][y] != 0 || y < 9) {
             x++;
             if (x == 9) {
             x = 0;
             y++;
             }
             }*/
//                        wait = true;
        }

    }

    public ArrayList<Integer> cargarOrientaciones(int x, int y) {
        int fila = x, col = y;//primero se verifican los limites del tablero 
        //System.out.println("Fila " + fila + " Columna " + col);
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
            //Esquina inferior derecha 8,8
//            if (fila == 8 && col == 8 && tablero[fila][col - 1] == 0) {
//                orientacionesDisponibles.add(90);
//                borde = true;
//            }
//            if (fila == 8 && col == 8 && tablero[fila - 1][col] == 0) {
//                orientacionesDisponibles.add(180);
//                borde = true;
//            }
            //Esquina superior derecha 0,8
//            if (fila == 0 && col == 8 && tablero[fila][col - 1] == 0) {
//                orientacionesDisponibles.add(90);
//                borde = true;
//            }
            if (fila == 0 && col == 8 && tablero[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                orientacionesDisponibles.add(180);
                borde = true;
            }
            //Esquina inferior izquierda 8,0
//////            if (fila == 8 && col == 0 && tablero[fila - 1][col] == 0) {
//////                orientacionesDisponibles.add(180);
//////                borde = true;
//////            }
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
//            if (col > 0 && tablero[fila][col - 1] == 0) {
//                orientacionesDisponibles.add(90);
//            }
//            if (fila > 0 && tablero[fila - 1][col] == 0) {
//                orientacionesDisponibles.add(180);
//            }
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
            v = true; //            tablero = tableroinicial.clone();
        } else if (ponerFicha(nodo.getFicha(), nodo.getX(), nodo.getY(), nodo.getOrientacion())) {
            v = ponerNodo(nodos.get(nodo.getIdPadre()));
        } else {
            v = false;
        }
        return v;
    }

    //Metodo para poner la ficha en el nodo
    public void agregarFicha(int k, int orientacion) {
        Nodo nodo = new Nodo(nodos.size(), padre.getId(), fichas.get(k), x, y, orientacion, false);

//        boolean cuadroLibre = validarCuadro(fichas.get(k), x, y);
//        boolean filaLibre = validarFila(fichas.get(k), x, orientacion);
//        boolean columnaLibre = validarColumna(fichas.get(k), y, orientacion);
        System.out.println("Iteracion N°: " + iteracion);
        iteracion++;
        System.out.println("Cambio en el Nodo: " + recorrido);
        System.out.println("Cantidad de Fichas puestas en el nodo: " +fichaspuestas);
        System.out.println("Id Padre: " + padre.getId());
        System.out.println("Orientacion " + orientacion + " Ficha " + fichas.get(k).getValorA() + ":" + fichas.get(k).getValorB());
//        System.out.println("Validar Cuadro: " + cuadroLibre);
//        System.out.println("Validar Fila: " + filaLibre);
        //System.out.println("Validar Columna: " + columnaLibre);

        if (iteracion == 17039) {
            System.out.println("");
        }
        if (nodo.getId() == 167) {
            System.out.println("");
        }
        if (ponerNodo(nodo)/*cuadroLibre && filaLibre && columnaLibre*/) {

            int siguientes[] = siguienteIteracion();
            nodo.setXsiguiente(siguientes[0]);
            nodo.setYsiguiente(siguientes[1]);
            nodos.add(nodo);
            tablero = tableroinicial.clone();
            for (int i = 0; i < tablero.length; i++) {
                tablero[i] = tableroinicial[i].clone();

            }
            System.gc();
            System.out.println("Guardo en " + x + " " + y + " con orientacion " + orientacion + " la ficha " + fichas.get(k).getValorA() + ":" + fichas.get(k).getValorB());
        } else {
            System.out.println("NO guardo en " + x + " " + y + " con orientacion " + orientacion + " la ficha " + fichas.get(k).getValorA() + ":" + fichas.get(k).getValorB());
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

    public boolean esRepetida(int idficha, Nodo nodo) {
        boolean v;
        if (nodo.getId()==2) {
            System.out.println("");
        }
        if (nodo.getIdPadre()==2) {
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
}
