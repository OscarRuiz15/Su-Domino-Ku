//Oscar Alexander Ruiz Palacio 201667600
//Andres Felipe Medina Tascon  201667602
//Andres Felipe Gonzalez Rojas 201759599
package su.domino.ku.algoritmos;

import java.util.ArrayList;
import java.util.List;
import su.domino.ku.modelo.Ficha;
import su.domino.ku.modelo.Nodo;

public class BusquedaAmplitud {
    //Tablero 
    private int tablero[][];
    //Tablero auxiliar
    private final int tableroinicial[][];
    //Lista de Fichas
    private final List<Ficha> fichas;
    //Lista de nodos
    private final List<Nodo> nodos = new ArrayList<>();
    //Nodo padre
    private Nodo padre;
    //Cantidad de nodos a recorrer por nivel de profundidad
    private int recorrido;
    //Posicion x,y libres en el nodo padre 
    private int x = 0;
    private int y = 0;
    //Cantidad de fichas puestas en el nodo
    private int fichaspuestas;
    //Numero de iteraciones
    long iteracion = 0;
    //Validaciones
    private Validaciones v;
    //Metodo constructor para realizar la busqueda por amplitud
    public BusquedaAmplitud(int[][] tablero, List<Ficha> fichas) {
        this.tablero = tablero;
        this.fichas = fichas;
        tableroinicial = new int[9][9];
        
        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();
        }

    }
    //Realizar la busqueda por amplitud
    //Crea el nodo raiz con el tablero inicial
    //Se detiene cuando las posiciones libres de un nodo sean 0,0 y no sea la raiz
    //Busca las orientaciones disponibles en el x,y libres del nodo
    //Busca las fichas que no esten repetidas en el nodo para ponerlas en el mismo
    //Intenta Poner las fichas en la posicion x,y con sus orientaciones disponibles
    public void busquedaAmplitud() {
         v=new Validaciones(tablero, nodos);
        Nodo nodito = new Nodo(0, 0, null, 0, 0, 0, false);
        padre = nodito;
        int c[] = v.siguienteIteracion();
        nodito.setXsiguiente(c[0]);
        nodito.setYsiguiente(c[1]);
        nodos.add(nodito);

        while (!(padre.getId()!=0 && padre.getXsiguiente()==0&& padre.getYsiguiente()==0)) {

            recorrido = nodos.size();
            fichaspuestas++;
            int cont = 0;
           

            for (int i = 0; i < recorrido; i++) {
                if (!nodos.get(i).isExpandido()) {
                    
                    padre = nodos.get(i);
                    padre.setExpandido(true);
                    x = padre.getXsiguiente();
                    y = padre.getYsiguiente();
                    ArrayList<Integer> orientacionesDisponibles = v.cargarOrientaciones(x, y);
                    for (int k = 0; k < orientacionesDisponibles.size(); k++) {
                        System.out.print(orientacionesDisponibles.get(k) + "-");
                    }
                    System.out.println("x:" + x + " y:" + y + " Orientaciones disponibles: " + orientacionesDisponibles.size());

                    System.out.println("\n-----------------------------");

                    //Poner cada ficha en las orientaciones disponibles de la posicion actual
                    for (int k = 0; k < fichas.size(); k++) {
                        v=new Validaciones(tablero, nodos);
                        if (!v.esRepetida(k + 1, padre)) {

                            cont = 0;
                            //Cuando la orientacion es 0 grados
                            
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 0) {
                                cont++;
                                agregarFicha(k, 0);

                            }
                            //Cuando la orientacion es 180 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 180) {
                                cont++;
                                agregarFicha(k, 180);
                            }
                            //Cuando la orientacion es 90 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 90) {
                                cont++;
                                agregarFicha(k, 90);
                            }

                            //Cuando la orientacion es 270 grados
                            if (cont < orientacionesDisponibles.size() && orientacionesDisponibles.get(cont) == 270) {
                                cont++;
                                agregarFicha(k, 270);
                            }

                        }
                    }
                }
            }
        }

    }

   
   
    //Verifica si se puede poner la ficha en el nodo
    //Si puede poner la ficha en el nodo padre crea el nodo
    public void agregarFicha(int k, int orientacion) {
        v=new Validaciones(tablero, nodos);
        Nodo nodo = new Nodo(nodos.size(), padre.getId(), fichas.get(k), x, y, orientacion, false);
        System.out.println("Iteracion N°: " + iteracion);
        iteracion++;
        System.out.println("Id Padre: " + padre.getId());
        System.out.println("Cambio en el Nodo: " + recorrido);
        System.out.println("Cantidad de Fichas puestas en el nodo: " + fichaspuestas);
        System.out.println("Orientacion " + orientacion + " Ficha " + fichas.get(k).getValorA() + ":" + fichas.get(k).getValorB());

     
        if (v.ponerNodo(nodo)) {
            v.verTablero();
            int siguientes[] = v.siguienteIteracion();
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

    
    //Verifica si una ficha esta ´puesta en el nodo
    //Retorna verdadero si ya existe en el arbol
//    public boolean esRepetida(int idficha, Nodo nodo) {
//        boolean v;
//        if (nodo.getId() == 2) {
//            System.out.println("");
//        }
//        if (nodo.getIdPadre() == 2) {
//            System.out.println("");
//        }
//        if (nodo.getId() == 0) {
//            v = false;
//        } else if (nodo.getFicha().getId() == idficha) {
//            v = true;
//        } else {
//            v = esRepetida(idficha, nodos.get(nodo.getIdPadre()));
//        }
//        return v;
//    }
// 
//    //Metodo que retorna la primera posicion x,y que tenga un 0
//    public int[] siguienteIteracion() {
//        int casillas[] = new int[2];
//        for (int i = 0; i < tablero.length; i++) {
//            for (int j = 0; j < tablero[0].length; j++) {
//                if (tablero[i][j] == 0) {
//                    casillas[0] = i;
//                    casillas[1] = j;
//                    return casillas;
//
//                }
//
//            }
//
//        }
//        return casillas;
//    }
}
