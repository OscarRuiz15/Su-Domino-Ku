package su.domino.ku.algoritmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import su.domino.ku.modelo.Ficha;

public class VegasTwo {

    private int tablero[][];
    private final int tableroinicial[][];
    int columna[];
    private final ArrayList<Ficha> fichasaux = new ArrayList<Ficha>();
    private ArrayList<Ficha> fichas;
    private boolean termino;
    private boolean repite;
    private int y = 0, x = 0;
    Random aleatorio = new Random(System.currentTimeMillis());

    public VegasTwo(int[][] tablero) {
        System.out.println("-----------COMIENZO-----------");
        this.tablero = tablero;
        //crearFichas();
        generarFichas();
        fichas = (ArrayList<Ficha>) fichasaux.clone();

        tableroinicial = new int[9][9];
        columna = tablero[0].clone();
        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();

        }
        termino = false;
        repite = false;
        verTablero();
    }

    public void algoritmoVegas() {
        while (!termino) {
            if (repite) {
                for (int i = 0; i < tablero.length; i++) {
                    tablero[i] = this.tableroinicial[i].clone();

                }
                fichas=(ArrayList<Ficha>) fichasaux.clone();
                repite=false;
            }
            int c[] = encontrarFaltantes();
            List<Integer> faltantes = numerosFaltantes(c[0], c[1]);
            Random r = new Random();
            int numero = (int) (r.nextDouble() * c[2]);
            List<Ficha> posibles = fichasPosibles(faltantes.get(numero));
            ponerFicha(faltantes.get(numero), c[0], c[1], posibles);
            verTablero();
            if (fichas.isEmpty()) {
                termino=true;
            }

        }

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

    public int[] encontrarFaltantes() {
        int casillas[] = new int[3];
        int cantidad = 10;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 0) {

                    System.out.print("Faltan en " + i + " , " + j + ": ");
                    for (int k = 0; k < 9; k++) {
                        if (validarColumna(k + 1, j) && validarFila(k + 1, i) && validarCuadro(k + 1, i, j)) {
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
                }
            }
            if (cantidad == 1) {
                break;
            }
        }
        casillas[2] = cantidad;
        return casillas;
    }

    public List<Integer> numerosFaltantes(int x, int y) {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (validarColumna(i + 1, y) && validarFila(i + 1, x) && validarCuadro(i + 1, x, y)) {
                numeros.add(i + 1);
            }

        }
        return numeros;
    }

    public List<Ficha> fichasPosibles(int valor) {
        List<Ficha> fichasposibles = new ArrayList<>();
        for (int i = 0; i < fichas.size(); i++) {
            if (fichas.get(i).getValorA() == valor || fichas.get(i).getValorB() == valor) {
                fichasposibles.add(fichas.get(i));
            }

        }
        return fichasposibles;
    }

    public void verTablero() {
        System.out.println("\nTablero");
        int aux1 = 2;
        int aux2 = 2;

        for (int i = 0; i < tableroinicial.length; i++) {
            aux1 = 2;
            for (int j = 0; j < tableroinicial[0].length; j++) {
                System.out.print(tableroinicial[i][j] + " ");
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

    public void ponerFicha(int valor, int x, int y, List<Ficha> ficha) {
        Random r = new Random();
        int random = (int) (r.nextDouble() * ficha.size());
        Ficha elegida = ficha.get(random);
        List<Integer[]> posiciones = posicionesDisponibles(x, y, valor, elegida);
        r = new Random();
        random = (int) (r.nextDouble() * posiciones.size());
        if (posiciones.isEmpty()) {
            System.out.println("No encontro");
            repite=true;
        } else {
            Integer p[] = posiciones.get(random);
            tablero[x][y] = valor;
            tablero[p[0]][p[1]] = p[2];
            Ficha f = fichasaux.get(elegida.getId() - 1);
            fichas.remove(f);
        }
    }

    public List<Integer[]> posicionesDisponibles(int x, int y, int valor, Ficha ficha) {

        List<Integer[]> lista = new ArrayList<>();
        int valorfichaA = ficha.getValorA();
        int valorfichaB = ficha.getValorB();
        if (x + 1 < 9 && tablero[x + 1][y] == 0) {
            if (valorfichaA == valor) {
                Integer p[] = validarValor(x + 1, y, valorfichaB);
                if (p[2] != 0) {
                    lista.add(p);
                }
            } else {
                Integer p[] = validarValor(x + 1, y, valorfichaA);
                if (p[2] != 0) {
                    lista.add(p);
                }
            }
        }
        if (y + 1 < 9 && tablero[x][y + 1] == 0) {
            if (valorfichaA == valor) {
                Integer p[] = validarValor(x, y + 1, valorfichaB);
                if (p[2] != 0) {
                    lista.add(p);
                }
            } else {
                Integer p[] = validarValor(x, y + 1, valorfichaA);
                if (p[2] != 0) {
                    lista.add(p);
                }
            }
        }
        if (x - 1 >= 0 && tablero[x - 1][y] == 0) {
            if (valorfichaA == valor) {
                Integer p[] = validarValor(x - 1, y, valorfichaB);
                if (p[2] != 0) {
                    lista.add(p);
                }
            } else {
                Integer p[] = validarValor(x - 1, y, valorfichaA);
                if (p[2] != 0) {
                    lista.add(p);
                }
            }
        }
        if (y - 1 >= 0 && tablero[x][y - 1] == 0) {
            if (valorfichaA == valor) {
                Integer p[] = validarValor(x, y - 1, valorfichaB);
                if (p[2] != 0) {
                    lista.add(p);
                }
            } else {
                Integer p[] = validarValor(x, y - 1, valorfichaA);
                if (p[2] != 0) {
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    public Integer[] validarValor(int x, int y, int valor) {
        Integer posiciones[] = new Integer[3];
        if (validarColumna(valor, y) && validarFila(valor, x) && validarCuadro(valor, x, y)) {
            posiciones[0] = x;
            posiciones[1] = y;
            posiciones[2] = valor;
        } else {
            System.out.println("No se pudo poner la ficha :(");
            posiciones[0] = 0;
            posiciones[1] = 0;
            posiciones[2] = 0;
            repite=true;
        }
        return posiciones;
    }

    private void crearFichas() {
        fichasaux.clear();
        int cont = 1;
        for (int i = 1; i < 9; i++) {
            for (int j = i + 1; j < 10; j++) {
                if ((i == 3 && j == 8) || (i == 4 && j == 6) || (i == 3 && j == 5) || (i == 1 && j == 9) || (i == 2 && j == 6) || (i == 1 && j == 3) || (i == 1 && j == 2)
                        || (i == 4 && j == 8) || (i == 2 && j == 7) || (i == 2 && j == 8) || (i == 5 && j == 6) || (i == 6 && j == 9) || (i == 3 && j == 7) || (i == 1 && j == 6)
                        || (i == 7 && j == 9) || (i == 1 && j == 7) || (i == 3 && j == 4) || (i == 5 && j == 8) || (i == 4 && j == 5) || (i == 5 && j == 9) || (i == 3 && j == 6) || (i == 7 && j == 8)) {

                } else {
                    Ficha nuevaPieza = new Ficha(cont, i, j);
                    fichasaux.add(nuevaPieza);
                    cont++;
                }
            }
        }
    }

    public void verFichas(ArrayList<Ficha> fichas) {
        for (int i = 0; i < fichas.size(); i++) {
            System.out.print("Id: " + fichas.get(i).getId() + " " + fichas.get(i).getValorA() + ":" + fichas.get(i).getValorB() + " - ");
        }
    }

    public void generarFichas() {
        fichasaux.clear();
        int cont = 0;
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
        }
    }
}
