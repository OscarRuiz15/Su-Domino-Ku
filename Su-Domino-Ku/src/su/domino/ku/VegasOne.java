package su.domino.ku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VegasOne {

    private int tablero[][];
    private final int tableroinicial[][];
    int columna[];
    private final ArrayList<Ficha> fichas=new ArrayList<Ficha>();
    private int y = 0, x = 0;
    Random aleatorio = new Random(System.currentTimeMillis());

    public VegasOne(int[][] tablero) {
        System.out.println("-----------COMIENZO-----------");
        this.tablero = tablero;
        //crearFichas();
        generarFichas();
        
        tableroinicial = new int[9][9];
        columna = tablero[0].clone();
        for (int i = 0; i < tablero.length; i++) {
            tableroinicial[i] = this.tablero[i].clone();

        }
        verTablero();
    }

    public void algoritmoVegas() {
        Ficha fichaAzar = new Ficha();
        int coordenadas[];
        boolean fichaValida = false;
        int orientacion = 0;
        int posFicha = 0;
        int posOrientacion = 0;
        boolean pusoFicha = false;
        boolean termino = false;

        while (fichas.size() > 0 && !termino) {
            System.out.println("------------------------------------------------");
            System.out.println("Fichas disponibles: " + fichas.size());
            verFichas(fichas);

            coordenadas = elegirCoordenadas();
            x = coordenadas[0];
            y = coordenadas[1];

            System.out.println("\nCoordenadas -> x:" + x + " y:" + y);

            pusoFicha = false;
            ArrayList<Ficha> fichastemporal = new ArrayList();
            fichastemporal = (ArrayList<Ficha>) fichas.clone();

            while (fichastemporal.size() > 0 && !pusoFicha && !termino) {
                ArrayList<Integer> orientacionesDisponibles = cargarOrientaciones(x, y);

                if (orientacionesDisponibles.size() == 0) {
                    System.out.println("No se encontraron orientaciones disponibles, no hay solucion");
                    termino = true;
                } else {
                    termino = false;
                    fichaValida = false;
                    System.out.println("\nOrientaciones disponibles: " + orientacionesDisponibles.size() + "");

                    for (int k = 0; k < orientacionesDisponibles.size(); k++) {
                        System.out.print(orientacionesDisponibles.get(k) + " ");
                    }

                    System.out.println("");

                    posFicha = elegirFichaAzar(fichastemporal);
                    fichaAzar = fichastemporal.get(posFicha);
                    System.out.println("\nFichas temporales disponibles: " + fichastemporal.size());
                    verFichas(fichastemporal);
                    System.out.println("\nFicha elegida al azar: " + fichaAzar.getValorA() + ":" + fichaAzar.getValorB());

                    while (orientacionesDisponibles.size() > 0 && !pusoFicha) {
                        aleatorio = new Random(System.currentTimeMillis());
                        posOrientacion = aleatorio.nextInt(orientacionesDisponibles.size());
                        orientacion = orientacionesDisponibles.get(posOrientacion);
                        System.out.println("-> Orientacion elegida al azar: " + orientacion);

                        fichaValida = validarFicha(fichaAzar, x, y, orientacion);
                        if (fichaValida) {
                            System.out.println("-> Ficha valida, se procede a poner en el tablero");

                            ponerFichaTablero(orientacion, x, y, fichaAzar);

                            for (int i = 0; i < fichas.size(); i++) {
                                if(fichas.get(i).getId()==fichastemporal.get(posFicha).getId()){
                                    fichas.remove(i);
                                }
                            }
                            
                            verTablero();
                            pusoFicha = true;
                        } else {
                            System.out.println("-> Ficha no valida en orientacion " + orientacion);
                            orientacionesDisponibles.remove(posOrientacion);
                            pusoFicha = false;
                        }
                    }

                    if (orientacionesDisponibles.size() == 0) {
                        fichastemporal.remove(posFicha);
                    }
                    if (fichastemporal.size() == 0) {
                        System.out.println("No se encuentra solucion");
                        termino = true;
                    }
                }
            }

            if (fichas.size() == 0) {
                termino = true;
                System.out.println("Encontro solucion");
                System.exit(0);
            }
        }

    }

    public int[] elegirCoordenadas() {
        boolean pos = false;
        int x = 0;
        int y = 0;

        for (int i = 0; i < tableroinicial.length && !pos; i++) {
            for (int j = 0; j < tableroinicial[0].length && !pos; j++) {
                if (tableroinicial[i][j] == 0) {
                    x = i;
                    y = j;
                    pos = true;
                    break;
                }
            }
        }

        int coordenadas[] = {x, y};

        return coordenadas;
    }

    public int elegirFichaAzar(ArrayList<Ficha> fichastemporal) {
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
            if (fila == 0 && col == 0 && tableroinicial[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                orientacionesDisponibles.add(180);
                borde = true;
            }
            //Esquina superior izquierda 0,0 Horizontal
            if (fila == 0 && col == 0 && tableroinicial[fila][col + 1] == 0) {
                orientacionesDisponibles.add(90);
                orientacionesDisponibles.add(270);
                borde = true;
            }
            if (fila == 0 && col == 8 && tableroinicial[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                orientacionesDisponibles.add(180);
                borde = true;
            }
            if (fila == 8 && col == 0 && tableroinicial[fila][col + 1] == 0) {
                orientacionesDisponibles.add(90);
                orientacionesDisponibles.add(270);
                borde = true;
            }
        }

        //Esto es para el resto del tablero
        if (!borde) {
            if (fila < 8 && tableroinicial[fila + 1][col] == 0) {
                orientacionesDisponibles.add(0);
                orientacionesDisponibles.add(180);
            }
            if (col < 8 && tableroinicial[fila][col + 1] == 0) {
                orientacionesDisponibles.add(90);
                orientacionesDisponibles.add(270);
            }
        }
        return orientacionesDisponibles;
    }

    public boolean validarFila(int valor, int x) {
        boolean esValida = true;

        for (int i = 0; (i < 9) && esValida; i++) {
            esValida = tableroinicial[x][i] != valor;
        }

        return esValida;
    }

    public boolean validarColumna(int valor, int y) {
        boolean esValida = true;

        for (int i = 0; (i < 9) && esValida; i++) {
            esValida = tableroinicial[i][y] != valor;
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
                esValido = tableroinicial[f][c] != valor;
            }
        }

        return esValido;
    }

    public boolean validarFicha(Ficha ficha, int x, int y, int orientacion) {
        switch (orientacion) {
            case 0:
                if (validarColumna(ficha.getValorA(), y) && validarColumna(ficha.getValorB(), y) && validarFila(ficha.getValorA(), x)
                        && validarFila(ficha.getValorB(), x + 1) && validarCuadro(ficha.getValorA(), x, y) && validarCuadro(ficha.getValorB(), x + 1, y)) {
                    tableroinicial[x][y] = ficha.getValorA();
                    tableroinicial[x + 1][y] = ficha.getValorB();
                    return true;

                }
                break;
            case 90:
                if (validarColumna(ficha.getValorA(), y) && validarColumna(ficha.getValorB(), y + 1) && validarFila(ficha.getValorA(), x)
                        && validarFila(ficha.getValorB(), x) && validarCuadro(ficha.getValorA(), x, y) && validarCuadro(ficha.getValorB(), x, y + 1)) {
                    tableroinicial[x][y] = ficha.getValorA();
                    tableroinicial[x][y + 1] = ficha.getValorB();
                    return true;
                }
                break;

            case 180:
                if (validarColumna(ficha.getValorB(), y) && validarColumna(ficha.getValorA(), y) && validarFila(ficha.getValorB(), x)
                        && validarFila(ficha.getValorA(), x + 1) && validarCuadro(ficha.getValorB(), x, y) && validarCuadro(ficha.getValorA(), x + 1, y)) {
                    tableroinicial[x][y] = ficha.getValorB();
                    tableroinicial[x + 1][y] = ficha.getValorA();
                    return true;
                }
                break;
            case 270:
                if (validarColumna(ficha.getValorB(), y) && validarColumna(ficha.getValorA(), y + 1) && validarFila(ficha.getValorB(), x)
                        && validarFila(ficha.getValorA(), x) && validarCuadro(ficha.getValorB(), x, y) && validarCuadro(ficha.getValorA(), x, y + 1)) {
                    tableroinicial[x][y] = ficha.getValorB();
                    tableroinicial[x][y + 1] = ficha.getValorA();
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

    public void ponerFichaTablero(int orientacion, int x, int y, Ficha fichaAzar) {
        if (orientacion == 0) {
            tableroinicial[x][y] = fichaAzar.getValorA();
            tableroinicial[x + 1][y] = fichaAzar.getValorB();
        }
        if (orientacion == 180) {
            tableroinicial[x][y] = fichaAzar.getValorB();
            tableroinicial[x + 1][y] = fichaAzar.getValorA();
        }
        if (orientacion == 90) {
            tableroinicial[x][y] = fichaAzar.getValorA();
            tableroinicial[x][y + 1] = fichaAzar.getValorB();
        }
        if (orientacion == 270) {
            tableroinicial[x][y] = fichaAzar.getValorB();
            tableroinicial[x][y + 1] = fichaAzar.getValorA();
        }
    }

    private void crearFichas() {
        fichas.clear();
        int cont=1;
        for (int i = 1; i < 9; i++) {
            for (int j = i + 1; j < 10; j++) {
                if ((i == 3 && j == 8) || (i == 4 && j == 6) || (i == 3 && j == 5) || (i == 1 && j == 9) || (i == 2 && j == 6) || (i == 1 && j == 3) || (i == 1 && j == 2)
                        || (i == 4 && j == 8) || (i == 2 && j == 7) || (i == 2 && j == 8) || (i == 5 && j == 6) || (i == 6 && j == 9) || (i == 3 && j == 7) || (i == 1 && j == 6)
                        || (i == 7 && j == 9) || (i == 1 && j == 7) || (i == 3 && j == 4) || (i == 5 && j == 8) || (i == 4 && j == 5) || (i == 5 && j == 9) || (i == 3 && j == 6) || (i == 7 && j == 8)) {

                } else {
                    Ficha nuevaPieza = new Ficha(cont,i, j);
                    fichas.add(nuevaPieza);
                    cont++;
                }
            }
        }
    }
    
    public void verFichas(ArrayList<Ficha> fichas){
        for (int i = 0; i < fichas.size(); i++) {
            System.out.print("Id: " + fichas.get(i).getId() + " " + fichas.get(i).getValorA() + ":" + fichas.get(i).getValorB()+" - ");
        }
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
}
