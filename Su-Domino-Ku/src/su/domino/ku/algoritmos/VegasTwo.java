//Oscar Alexander Ruiz Palacio 201667600
//Andres Felipe Medina Tascon  201667602
//Andres Felipe Gonzalez Rojas 201759599
package su.domino.ku.algoritmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import su.domino.ku.frames.Tablero;
import su.domino.ku.modelo.Ficha;

public class VegasTwo {

    Tablero tablerito = new Tablero();
    //Tablero
    private int tablero[][];
    //Tablero Auxiliar
    private final int tableroinicial[][];
    //Lista de fichas auxiliar
    private ArrayList<Ficha> fichasaux = new ArrayList<Ficha>();
    //Lista de fichas
    private ArrayList<Ficha> fichas;
    //Condicion de parada
    private boolean termino;
    //Varible para saber  si se repitio el algoritmo
    private boolean repite;
    //Posiciones x,y
    private int y = 0, x = 0;
    Random aleatorio = new Random(System.currentTimeMillis());
    //Numero de fichas puestas en un intento
    private int numfichaspuestas = 0;
    //Numero maximo de fichas puestas de todos lo intentos
    private int maxfichaspuestas = 0;
    //Numero de fracasos
    private int fracasos = 0;
    //Numero de fichas puestas en todo el algoritmo
    private int numfichaspuestastotal = 0;
    //Validaciones
    Validaciones v;

    //Constructor que recibe un tablero
    public VegasTwo(int[][] tablero) {
        System.out.println("-----------COMIENZO-----------");
        this.tablero = tablero;
        generarFichas();
        fichas = (ArrayList<Ficha>) fichasaux.clone();

        tableroinicial = new int[9][9];

        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();

        }
        v = new Validaciones(tablero, null);
        termino = false;
        repite = false;
        v.verTablero();
    }

    //Metodo que ejecuta el algoritmo
    //Hasta que la condicion de parada no sea verdadero
    //Si repite restaura el tablero, fichas usadas y numero de fichas puestas
    //Busca las posiciones x,y que tengan menos posibilidades de colocar un numero segun las reglas del sudoku
    //Si no encuentra ningun numero repite el algoritmo
    //En caso de encontrarlos busca los numeros faltantes en la casilla x,y
    //Elige al azar uno de los numeros faltantes
    //Se buscan las orientaciones disponibles en x,y
    //Se elige una de las orientaciones disponibles al azar
    //Se verifican los numeros disponibles en esa orientacion
    //Si no encuentra ningun numero repite el algoritmo
    //En caso contrario eligen las fichas que tengan el numero eligido al azar y los numeros encontrados en la orientacion
    //Si no existe ninguna ficha disponible con estas condiciones repite el algoritmo
    //Se elige una de estas fichas al azar
    //Se pone la ficha
    //Se quita la ficha de las que estan disponibles
    //Si no existen fichas disponibles la condicion de parada es verdadero
    public int[][] algoritmoVegas() {
        while (!termino) {
            if (repite) {
                for (int i = 0; i < tablero.length; i++) {
                    tablero[i] = this.tableroinicial[i].clone();

                }
                fichas = (ArrayList<Ficha>) fichasaux.clone();
                repite = false;
                numfichaspuestas = 0;
                fracasos++;
                System.out.println("Falle :'c");

            }
            int c[] = encontrarFaltantes();
            x = c[0];
            y = c[1];
            if (c[2] == 0) {
                System.out.println("No se puede colocar ningun numero en " + x + "," + y);
                repite = true;
            } else {
                List<Integer> faltantes = numerosFaltantes(x, y);
                if (faltantes.isEmpty()) {
                    System.out.println("No encontro numeros faltantes");
                    repite = true;
                } else {
                    Random r = new Random();
                    int numero = (int) (r.nextDouble() * c[2]);
                    List<Integer[]> posiciones = posicionesDisponibles(x, y);
                    r = new Random();
                    int random = (int) (r.nextDouble() * posiciones.size());
                    if (posiciones.isEmpty()) {
                        System.out.println("No encontro posicion");
                        repite = true;
                    } else {
                        int x1 = posiciones.get(random)[0];
                        int y1 = posiciones.get(random)[1];
                        List<Integer> faltantesSig = numerosFaltantes(x1, y1);

                        List<Ficha> posibles = fichasPosibles(faltantes.get(numero), faltantesSig);
                        if (posibles.isEmpty()) {
                            System.out.println("No encontro ficha");
                            repite = true;
                        } else {

                            ponerFicha(faltantes.get(numero), x, y, x1, y1, posibles);
                            v = new Validaciones(tablero, null);
                            v.verTablero();
                        }
                    }

                    if (fichas.isEmpty()) {
                        termino = true;
                    }
                }
            }
            System.out.println("Maximo de fichas puestas: " + maxfichaspuestas);
            System.out.println("Maximo de casillas ocupadas: " + (maxfichaspuestas * 2));
            System.out.println("Fracasos: " + fracasos);
            System.out.println("");
        }
        return tablero;
    }

    //Metodo que busca la posicion x,y con menor cantidad de numeros posibles
    //Se detiene en 0 o en 1 ya que, 1 es la menor cantidad de numeros posibles
    //y 0 porque significa que no hay numeros posibles
    public int[] encontrarFaltantes() {
        int casillas[] = new int[3];
        int cantidad = 10;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 0) {
                    v = new Validaciones(tablero, null);
                    System.out.print("Faltan en " + i + " , " + j + ": ");
                    for (int k = 0; k < 9; k++) {
                        if (v.validarColumna(k + 1, j) && v.validarFila(k + 1, i) && v.validarCuadro(k + 1, i, j)) {
                            System.out.print(k + 1);
                            System.out.print(", ");

                            casillas[2]++;
                        }

                    }
                    System.out.println("");
                    if (casillas[2] < cantidad) {
                        casillas[0] = i;
                        casillas[1] = j;
                        cantidad = casillas[2];
                        casillas[2] = 0;
                    } else {
                        casillas[2] = 0;
                    }
                    if (cantidad == 1) {
                        break;
                    }
                    if (cantidad == 0) {
                        break;

                    }
                }
            }
            if (cantidad == 1) {
                break;
            }
            if (cantidad == 0) {
                break;

            }
        }
        casillas[2] = cantidad;
        return casillas;
    }

    //Busca los numeros faltantes en una posicion x,y
    public List<Integer> numerosFaltantes(int x, int y) {
        List<Integer> numeros = new ArrayList<>();
        v = new Validaciones(tablero, null);
        for (int i = 0; i < 9; i++) {
            if (v.validarColumna(i + 1, y) && v.validarFila(i + 1, x) && v.validarCuadro(i + 1, x, y)) {
                numeros.add(i + 1);
            }

        }
        return numeros;
    }

    //Metodo que busca las fichas posibles de acuerdo a un numero y una lista de numeros posibles
    public List<Ficha> fichasPosibles(int valor, List<Integer> posibles) {
        List<Ficha> fichasposibles = new ArrayList<>();
        for (int i = 0; i < fichas.size(); i++) {
            for (int j = 0; j < posibles.size(); j++) {
                if ((fichas.get(i).getValorA() == valor && fichas.get(i).getValorB() == posibles.get(j))
                        || (fichas.get(i).getValorB() == valor && fichas.get(i).getValorA() == posibles.get(j))) {
                    fichasposibles.add(fichas.get(i));
                }

            }

        }
        return fichasposibles;
    }

    //Metodo que pone la ficha en una posicion (x,y);(x1,y1)
    public void ponerFicha(int valor, int x, int y, int x1, int y1, List<Ficha> ficha) {
        Random r = new Random();

        int valorB;
        int random = (int) (r.nextDouble() * ficha.size());
        Ficha elegida = ficha.get(random);
        if (elegida.getValorA() == valor) {
            valorB = elegida.getValorB();
        } else {
            valorB = elegida.getValorA();
        }
        tablero[x][y] = valor;
        tablero[x1][y1] = valorB;
        numfichaspuestas++;
        numfichaspuestastotal++;
        if (numfichaspuestas > maxfichaspuestas) {
            maxfichaspuestas = numfichaspuestas;
        }
        Ficha f = fichasaux.get(elegida.getId() - 1);
        fichas.remove(f);

        System.out.println("Se pone la ficha: " + f.getId() + ", Valor A: " + f.getValorA() + ", Valor B: " + f.getValorB());
        System.out.println("Se pone en " + x + "," + y + ": " + valor);
        System.out.println("Se pone en " + x1 + "," + y1 + ": " + valorB);
        System.out.println("Fichas puestas: " + numfichaspuestas + " de " + fichasaux.size());
        System.out.println("Casillas ocupadas: " + (numfichaspuestas * 2));
        System.out.println("Casillas restantes: " + (fichas.size() * 2));
        System.out.println("Fichas puestas en total: " + numfichaspuestastotal);
        if (fracasos != 0) {
            System.out.println("Fichas puestas en promedio por intento: " + (numfichaspuestastotal / fracasos));
            System.out.println("Casillas llenadas en promedio por intento: " + ((numfichaspuestastotal / fracasos) * 2));
        }

    }

    //Metodo que muestra las orientaciones disponibles a partir de un x,y
    public List<Integer[]> posicionesDisponibles(int x, int y) {

        List<Integer[]> lista = new ArrayList<>();
        if (x + 1 < 9 && tablero[x + 1][y] == 0) {
            Integer p[] = {x + 1, y};
            lista.add(p);

        }
        if (y + 1 < 9 && tablero[x][y + 1] == 0) {
            Integer p[] = {x, y + 1};
            lista.add(p);

        }
        if (x - 1 >= 0 && tablero[x - 1][y] == 0) {
            Integer p[] = {x - 1, y};
            lista.add(p);

        }
        if (y - 1 >= 0 && tablero[x][y - 1] == 0) {
            Integer p[] = {x, y - 1};
            lista.add(p);


        }
        return lista;
    }

    //Metodo que valida un valor determinado en una posicion x,y
    public Integer[] validarValor(int x, int y, int valor) {
        Integer posiciones[] = new Integer[3];
        v = new Validaciones(tablero, null);
        if (v.validarColumna(valor, y) && v.validarFila(valor, x) && v.validarCuadro(valor, x, y)) {
            posiciones[0] = x;
            posiciones[1] = y;
            posiciones[2] = valor;
        } else {
            System.out.println("No se pudo poner la ficha :(");
            posiciones[0] = 0;
            posiciones[1] = 0;
            posiciones[2] = 0;
            repite = true;
        }
        return posiciones;
    }

    //Metodo para imprimir las fichas
    public void verFichas(ArrayList<Ficha> fichas) {
        for (int i = 0; i < fichas.size(); i++) {
            System.out.print("Id: " + fichas.get(i).getId() + " " + fichas.get(i).getValorA() + ":" + fichas.get(i).getValorB() + " - ");
        }
    }

    public void generarFichas() {
        fichasaux.clear();
        /*int cont = 0;
         for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
         if (i < j + 1) {
         int s = j + 2;
         int z = i + 1;
         Ficha fichita = new Ficha(cont + 1, z, s);
         fichasaux.add(fichita);
         cont++;
         }
         }
         }*/
        fichasaux = tablerito.fichas;
    }
}
