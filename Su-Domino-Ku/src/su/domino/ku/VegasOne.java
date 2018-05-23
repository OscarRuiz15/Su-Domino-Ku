package su.domino.ku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VegasOne {

    private int tablero[][];
    private final int tableroinicial[][];
    int columna[];
    private final List<Ficha> fichas;
    private List<Ficha> fichastemporal;
    private int y = 0, x = 0;
    Random aleatorio = new Random(System.currentTimeMillis());

    public VegasOne(int[][] tablero, List<Ficha> fichas) {
        this.tablero = tablero;
        this.fichas = fichas;
        tableroinicial = new int[9][9];
        columna = tablero[0].clone();
        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();

        }
        fichastemporal = fichas;
    }

    public void algoritmoVegas() {
        Ficha fichaAzar;
        int coordenadas[] = generarCoordenadasAzar();
        x = coordenadas[0];
        y = coordenadas[1];
        boolean fichaValida = false;
        int orientacion = 0;
        int posFicha = 0;

        //Si el tablero en las posiciones al azar es 0 es porque puede ponerse una ficha
        if (tablero[x][y] == 0) {
            //Imprimir las coordenadas aleatorias
            System.out.println("Coordenadas aleatorias -> x:" + x + " y:" + y);

            //Carga las orientaciones disponibles en la coordenada
            ArrayList<Integer> orientacionesDisponibles = cargarOrientaciones(x, y);

            //Si el tamaño de las orientaciones es 0, no se puede poner nada
            if (orientacionesDisponibles.size() == 0) {
                System.out.println("No se encontraron orientaciones disponibles");
            } else {//Hay orientaciones disponibles para poner la ficha
                System.out.println("Orientaciones disponibles: " + orientacionesDisponibles.size() + "\n");

                //Imprimir las orientaciones disponibles
                for (int k = 0; k < orientacionesDisponibles.size(); k++) {
                    System.out.print(orientacionesDisponibles.get(k) + "-");
                }

                //Elegir una posicion al azar de una ficha
                posFicha = elegirFichaAzar();

                //Elegir la ficha dependiendo de la posicion obtenida
                fichaAzar = fichastemporal.get(posFicha);

                //Imprimir la ficha elegida
                System.out.println("\nFicha elegida al azar: " + fichaAzar.getValorA() + ":" + fichaAzar.getValorB());

                //Numero aleatorio para seleccionar una orientacion
                aleatorio = new Random(System.currentTimeMillis());

                //Orientacion obtenida al azar
                orientacion = aleatorio.nextInt(orientacionesDisponibles.size());
                orientacion = orientacionesDisponibles.get(orientacion);
                //Imprimir orientacion
                System.out.println("Orientacion elegida al azar: " + orientacion);

                //Validar que la ficha se pueda poner en las coordenadas segun la orientacion
                fichaValida = validarFicha(fichaAzar, x, y, orientacion);

                //Si la ficha es valida y se puede poner
                if (fichaValida) {
                    System.out.println("Ficha valida, se procede a poner en el tablero");
                    //Si la orientacion obtenida es 0 grados
                    if (orientacion == 0) {
                        tablero[x][y] = fichaAzar.getValorA();
                        tablero[x + 1][y] = fichaAzar.getValorB();
                    }
                    //Si la orientacion obtenida es 180 grados
                    if (orientacion == 180) {
                        tablero[x][y] = fichaAzar.getValorB();
                        tablero[x + 1][y] = fichaAzar.getValorA();
                    }
                    //Si la orientacion obtenida es 90 grados
                    if (orientacion == 90) {
                        tablero[x][y] = fichaAzar.getValorA();
                        tablero[x][y + 1] = fichaAzar.getValorB();
                    }

                    //Si la orientacion obtenida es 270 grados
                    if (orientacion == 270) {
                        tablero[x][y] = fichaAzar.getValorB();
                        tablero[x][y + 1] = fichaAzar.getValorA();
                    }

                    //Cuiando se pone la ficha, se remueve de la lista de fichas
                    fichastemporal.remove(posFicha);
                    verTablero();
                } else {//La ficha no se puede poner en el tablero
                    System.out.println("Ficha no valida");
                }
            }
        }

    }

    public int[] generarCoordenadasAzar() {
        aleatorio = new Random(System.currentTimeMillis());
        int x = aleatorio.nextInt(9);
        int y = aleatorio.nextInt(9);
        int coordenadas[] = {x, y};
        aleatorio.setSeed(System.currentTimeMillis());

        return coordenadas;

    }

    public int elegirFichaAzar() {
        aleatorio = new Random(System.currentTimeMillis());
        int pos = aleatorio.nextInt(fichastemporal.size());
        //Ficha fichita = fichastemporal.get(pos);
        //fichastemporal.remove(pos);
        aleatorio.setSeed(System.currentTimeMillis());

        return pos;
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

    public boolean validarFicha(Ficha ficha, int x, int y, int orientacion) {
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
