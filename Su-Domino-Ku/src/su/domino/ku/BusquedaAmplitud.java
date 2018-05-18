package su.domino.ku;

import java.util.ArrayList;
import java.util.List;

public class BusquedaAmplitud {

    private int tablero[][];
    private final int tableroinicial[][];
    private final List<Ficha> fichas;
    private List<Nodo> nodos = new ArrayList<>();
    private Nodo padre;
    private int recorrido;
    private int x = 0;
    private int y = 0;

    public BusquedaAmplitud(int[][] tablero, List<Ficha> fichas) {
        this.tablero = tablero;
        this.fichas = fichas;
        tableroinicial = tablero.clone();

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

            if (tablero[x][y] == 0) {
                ArrayList<Integer> orientacionesDisponibles = cargarOrientaciones(x, y);
                System.out.println("x:" + x + " y:" + y + " Orientaciones disponibles: " + orientacionesDisponibles.size());

                //Imprimir orientaciones disponibles para la posicion actual
                for (int k = 0; k < orientacionesDisponibles.size(); k++) {
                    System.out.print(orientacionesDisponibles.get(k) + "-");
                }
                System.out.println("\n-----------------------------");
                recorrido = nodos.size();
                int cont = 0;

                for (int i = 0; i < recorrido; i++) {
                    if (!nodos.get(i).isExpandido()) {
                        padre = nodos.get(i);

                        //Poner cada ficha en las orientaciones disponibles de la posicion actual
                        for (int k = 0; k < fichas.size(); k++) {
                            if (!esRepetida(i, padre)) {

                                cont = 0;
                                //Cuando la orientacion es 0 grados
                                if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 0) {
//                                tablero[x][y] = fichas.get(k).getValorA();
//                                tablero[x + 1][y] = fichas.get(k).getValorB();
                                    cont++;
//                                verTablero();
                                    agregarFicha(k, 0);

                                }
                                //Cuando la orientacion es 90 grados
                                if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 90) {
//                                tablero[x][y] = fichas.get(k).getValorA();
//                                tablero[x][y + 1] = fichas.get(k).getValorB();
                                    cont++;
////                                verTablero();
                                    agregarFicha(k, 90);
                                }
                                //Cuando la orientacion es 180 grados
                                if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 180) {
//                                tablero[x][y] = fichas.get(k).getValorB();
//                                tablero[x + 1][y] = fichas.get(k).getValorA();
                                    cont++;
//                                verTablero();
                                    agregarFicha(k, 180);
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
            }
            y++;
            if (y == 9) {
                x++;
                y = 0;
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
                orientacionesDisponibles.add(270);
                orientacionesDisponibles.add(90);
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
                orientacionesDisponibles.add(270);
                orientacionesDisponibles.add(90);
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
    public void ponerNodo(Nodo nodo) {
        if (nodo.getIdPadre() == 0) {
            verTablero();
            tablero = tableroinicial.clone();
        } else {
            ponerFicha(nodo.getFicha(), nodo.getX(), nodo.getY(), nodo.getOrientacion());
            ponerNodo(nodos.get(nodo.getIdPadre()));
            verTablero();
        }
    }

    //Metodo para poner la ficha en el nodo
    public void agregarFicha(int k, int orientacion) {
        boolean filaLibre = validarFila(fichas.get(k), x, orientacion);
        boolean columnaLibre = validarColumna(fichas.get(k), y, orientacion);
        System.out.println("Orientacion "+orientacion+" Ficha "+fichas.get(k).getValorA()+":"+fichas.get(k).getValorB());
        System.out.println("Validar Fila: " + filaLibre);
        //System.out.println("Validar Columna: " + columnaLibre);
        
        if (filaLibre /*&& columnaLibre*/) {
            Nodo nodo = new Nodo(nodos.size(), padre.getId(), fichas.get(k), x, y, orientacion, false);
            nodos.add(nodo);
            System.out.println("Guardo en "+x+" "+y+" con orientacion "+orientacion+" la ficha "+fichas.get(k).getValorA()+":"+fichas.get(k).getValorB());
        }else{
            System.out.println("NO guardo en "+x+" "+y+" con orientacion "+orientacion+" la ficha "+fichas.get(k).getValorA()+":"+fichas.get(k).getValorB());
        }
        System.out.println("----------------------------------------------");
        
    }

    //Metodo para poner la ficha en el tablero con su orientacion
    public void ponerFicha(Ficha ficha, int x, int y, int orientacion) {
        switch (orientacion) {
            case 0:
                tablero[x][y] = ficha.getValorA();
                tablero[x + 1][y] = ficha.getValorB();
                break;
            case 90:
                tablero[x][y] = ficha.getValorA();
                tablero[x][y + 1] = ficha.getValorB();
                break;
            case 180:
                tablero[x][y] = ficha.getValorB();
                tablero[x + 1][y] = ficha.getValorA();
                break;
            case 270:
                tablero[x][y] = ficha.getValorB();
                tablero[x][y + 1] = ficha.getValorA();
                break;
            default:
                break;
        }
    }

    public boolean esRepetida(int idficha, Nodo nodo) {
        if (nodo.getIdPadre() == 0) {
            return false;
        }
        if (nodo.getFicha().getId() == idficha) {
            return true;
        } else {
            esRepetida(idficha, nodos.get(nodo.getIdPadre()));
        }
        return false;
    }

    public boolean validarFila(Ficha ficha, int x, int orientacion) {
        boolean esValida = true;
        int valorA=0;
        int valorB=0;
        
        if(orientacion==0){
            valorA=ficha.getValorA();
            valorB=ficha.getValorB();
        }
        else if(orientacion==180){
            valorA=ficha.getValorB();
            valorB=ficha.getValorA();
        }
        
        for (int y = 0; (y < 9) && esValida; y++) {
            esValida=tablero[x][y]!=valorA && tablero[x+1][y]!=valorB;
        }
        
        return esValida;
    }

    public boolean validarColumna(Ficha ficha, int y, int orientacion) {
        boolean esValida = true;
        int valorA=0;
        int valorB = 0;
        
        if(orientacion==90){
            valorA=ficha.getValorA();
            valorB=ficha.getValorB();
        }else if(orientacion==270){
            valorA=ficha.getValorA();
            valorB=ficha.getValorB();
        }
        
        for (int x = 0; (x < 9) && esValida; x++) {
            esValida=tablero[x][y]!=valorA && tablero[x][y+1]!=valorB;
        }
        return esValida;
    }
}
