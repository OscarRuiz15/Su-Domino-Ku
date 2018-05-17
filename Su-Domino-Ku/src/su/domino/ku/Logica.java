package su.domino.ku;

import java.util.ArrayList;
import java.util.List;

public class Logica {

    private List<String> secuencia;
    private int tablero[][];
    static Tablero tablerito = new Tablero();
    private List<Ficha> fichas;

    public Logica() {
        generarFichas();
        inicializarTablero();
        verTodo();
        busquedaAmplitud();
    }

    public void generarFichas() {
        fichas = new ArrayList<>();
        int cont = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i < j + 1) {
                    int s = j + 2;
                    int z = i + 1;
                    Ficha fichita = new Ficha(cont + 1, z, s, 0);
                    /*String ficha = cont + ";" + z + ";" + s + ";" + "no uso";
                     System.out.println(ficha);*/
                    fichas.add(fichita);
                    cont++;
                }
            }
        }
    }

    public void inicializarTablero() {
        tablero = tablerito.tablero;
    }

    public void verTodo() {
        System.out.println("Fichas");
        for (int i = 0; i < fichas.size(); i++) {
            System.out.println("Id: " + fichas.get(i).getId() + " Valor A: " + fichas.get(i).getValorA() + " Valor B: " + fichas.get(i).getValorB());
        }

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

    public void busquedaAmplitud() {
        List<Nodo> nodos = new ArrayList<>();
        Nodo nodito = new Nodo(0, 0, tablero, true);
        nodos.add(nodito);
        int idNodo = 1;
        boolean wait = false;

        
        ///////////Validar que algun numero de la ficha a usar no se encuentre en la fila o columna de todo el tablero :c
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {

                wait = false;

                //Si la posicion actual del tablero esta libre, buscara las orientaciones disponibles
                if (tablero[i][j] == 0) {
                    //Llamado a metodo para buscar las orientaciones disponibles segun la posicion actual      
                    ArrayList<Integer> orientacionesDisponibles = cargarOrientaciones(i, j);
                    System.out.println("x:" + i + " y:" + j + " Orientaciones disponibles: " + orientacionesDisponibles.size());

                    //Imprimir orientaciones disponibles para la posicion actual
                    for (int k = 0; k < orientacionesDisponibles.size(); k++) {
                        System.out.print(orientacionesDisponibles.get(k) + "-");
                    }

                    while (orientacionesDisponibles.size() > 0 && !wait) {
                        int cont = 0;

                        //Poner cada ficha en las orientaciones disponibles de la posicion actual
                        for (int k = 0; k < fichas.size(); k++) {
                            cont=0;
                            //Cuando la orientacion es 0 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 0) {
                                tablero[i][j] = fichas.get(k).getValorA();
                                tablero[i + 1][j] = fichas.get(k).getValorB();
                                cont++;
                                verTablero();
                            }
                            //Cuando la orientacion es 90 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 90) {
                                tablero[i][j] = fichas.get(k).getValorA();
                                tablero[i][j - 1] = fichas.get(k).getValorB();
                                cont++;
                                verTablero();
                            }
                            //Cuando la orientacion es 180 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 180) {
                                tablero[i][j] = fichas.get(k).getValorA();
                                tablero[i - 1][j] = fichas.get(k).getValorB();
                                cont++;
                                verTablero();
                            }
                            //Cuando la orientacion es 270 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 270) {
                                tablero[i][j] = fichas.get(k).getValorA();
                                tablero[i][j + 1] = fichas.get(k).getValorB();
                                cont++;
                                verTablero();
                            }
                        }
                        wait = true;
                    }
                }
            }

        }
    }

    public ArrayList<Integer> cargarOrientaciones(int x, int y) {
        int fila = x, col = y;//primero se verifican los limites del tablero 
        System.out.println("Fila " + fila + " Columna " + col);
        ArrayList<Integer> orientacionesDisponibles = new ArrayList<Integer>();
        boolean borde = false;
        
        //Esto sera solamente cuando se encuentre en una esquina desocupada
        if (fila < 9 || col < 9) {
            //Esquina superior izquierda 0,0
            if (fila == 0 && col == 0 && tablero[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                borde = true;
            }
            if (fila == 0 && col == 0 && tablero[fila][col + 1] == 0) {
                orientacionesDisponibles.add(270);
                borde = true;
            }
            //Esquina inferior derecha 8,8
            if (fila == 8 && col == 8 && tablero[fila][col - 1] == 0) {
                orientacionesDisponibles.add(90);
                borde = true;
            }
            if (fila == 8 && col == 8 && tablero[fila - 1][col] == 0) {
                orientacionesDisponibles.add(180);
                borde = true;
            }
            //Esquina superior derecha 0,8
            if (fila == 0 && col == 8 && tablero[fila][col - 1] == 0) {
                orientacionesDisponibles.add(90);
                borde = true;
            }
            if (fila == 0 && col == 8 && tablero[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                borde = true;
            }
            //Esquina inferior izquierda 8,0
            if (fila == 8 && col == 0 && tablero[fila - 1][col] == 0) {
                orientacionesDisponibles.add(180);
                borde = true;
            }
            if (fila == 8 && col == 0 && tablero[fila][col + 1] == 0) {
                orientacionesDisponibles.add(270);
                borde = true;
            }
        }

        //Esto es para el resto del tablero
        if (!borde) {
            if (fila < 8 && tablero[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
            }
            if (col > 0 && tablero[fila][col - 1] == 0) {
                orientacionesDisponibles.add(90);
            }
            if (fila > 0 && tablero[fila - 1][col] == 0) {
                orientacionesDisponibles.add(180);
            }
            if (col < 8 && tablero[fila][col + 1] == 0) {
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
}
