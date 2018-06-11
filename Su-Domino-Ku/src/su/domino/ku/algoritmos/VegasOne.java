//Oscar Alexander Ruiz Palacio 201667600
//Andres Felipe Medina Tascon  201667602
//Andres Felipe Gonzalez Rojas 201759599
package su.domino.ku.algoritmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import su.domino.ku.frames.Tablero;
import su.domino.ku.modelo.Ficha;
import su.domino.ku.modelo.Nodo;

public class VegasOne {

    Tablero tablerito;
    //Tablero 
    private int tablero[][];
    //Tablero auxiliar
    private final int tableroinicial[][];
    //Lista de Fichas
    private ArrayList<Ficha> fichas=new ArrayList<>();
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
    //Aleatorio 
    Random aleatorio = new Random(System.currentTimeMillis());
    int posFicha = 0;
    int posOrientacion = 0;
    int orientacion = 0;
    

    public VegasOne(int[][] tablero) {
        System.out.println("\t\t\tVEGAS 1");
        this.tablero = tablero;
        generarFichas();
        tableroinicial = new int[9][9];
        
        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();
        }

        verFichas(fichas);

    }
    
    //Realizar la busqueda por amplitud
    //Crea el nodo raiz con el tablero inicial
    //Se detiene cuando las posiciones libres de un nodo sean 0,0 y no sea la raiz
    //Busca las orientaciones disponibles en el x,y libres del nodo
    //Busca las fichas que no esten repetidas en el nodo para ponerlas en el mismo
    //Intenta Poner las fichas aleatorias en la posicion x,y con sus orientaciones disponibles de forma aleatoria

    public int[][] algoritmoVegas() {
        v=new Validaciones(tablero, nodos);
        Nodo nodito = new Nodo(0, 0, null, 0, 0, 0, false);
        padre = nodito;
        int c[] = v.siguienteIteracion();
        
        nodito.setXsiguiente(c[0]);
        nodito.setYsiguiente(c[1]);
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
                    //SE CREA UNA COPIA DE LAS FICHAS PARA RECORRERLAS Y ELEGIRLAS DE MANERA ALEATORIA
                    fichastemporal = (ArrayList<Ficha>) fichas.clone();

                    System.out.println("============================================================");
                    while (fichastemporal.size() > 0) {
                        ArrayList<Integer> orientacionesDisponibles = v.cargarOrientaciones(x, y);

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
                        v=new Validaciones(tablero, nodos);
                        if (!v.esRepetida(fichaAux.getId(), padre)) {
                             //SE RECORRE LAS ORIENTACIONES DISPONIBLES Y SE ELIGEN DE MANERA ALEATORIA
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
        return tablero;
        //System.exit(0);
    }
    
    //Verifica si se puede poner la ficha en el nodo
    //Si puede poner la ficha en el nodo padre crea el nodo
    public void agregarFicha(Ficha ficha, int orientacion) {
        v=new Validaciones(tablero, nodos);
        Nodo nodo = new Nodo(nodos.size(), padre.getId(), ficha, x, y, orientacion, false);
        System.out.println("Iteracion N°: " + iteracion);
        iteracion++;
        System.out.println("Id Padre: " + padre.getId());
        System.out.println("Cambio en el Nodo: " + recorrido);
        System.out.println("Cantidad de Fichas puestas en el nodo: " + fichaspuestas);
        System.out.println("Orientacion " + orientacion + " Ficha " + ficha.getValorA() + ":" + ficha.getValorB());

       
        if (v.ponerNodo(nodo)) {
            v.verTablero();
            int siguientes[] = v.siguienteIteracion();
            nodo.setXsiguiente(siguientes[0]);
            nodo.setYsiguiente(siguientes[1]);
            nodos.add(nodo);
            if (siguientes[0] ==siguientes[1] &&!(siguientes[0] == 0 && siguientes[1] == 0)) {
                for (int i = 0; i < tablero.length; i++) {
                    tablero[i] = tableroinicial[i].clone();

                }
                System.gc();
            }
            
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

   
//Genera las fichas de acuerdo a las disponibles a usar en el tablero cargado
    //Estas fichas se cargan por defecto desde el archivo de texto

    public void generarFichas() {
        fichas.clear();
        fichas = tablerito.fichas;
    }

    //Metodo para imprimir las fichas
    public void verFichas(ArrayList<Ficha> fichas) {
        for (int i = 0; i < fichas.size(); i++) {
            System.out.print("Id: " + fichas.get(i).getId() + " -> " + fichas.get(i).getValorA() + ":" + fichas.get(i).getValorB() + " - ");
        }
    }
}
