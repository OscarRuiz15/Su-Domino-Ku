package su.domino.ku.modelo;

import su.domino.ku.algoritmos.VegasOne;
import su.domino.ku.algoritmos.VegasTwo;
import su.domino.ku.algoritmos.BusquedaAmplitud;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import su.domino.ku.frames.Tablero;

public class Logica {

    private List<String> secuencia;
    private int tablero[][];

    static Tablero tablerito = new Tablero();
    private List<Ficha> fichas;

    Calendar calendario = Calendar.getInstance();
    int horaInicio, minutosInicio, segundosInicio, horaFin, minutosFin, segundosFin, milisegundosInicio, milisegundosFin;
    long tiempo, tiempoF;

    public Logica(int op) {
        //generarFichas();
        inicializarTablero();
        inicializarFichas();
        verTodo();

        tiempo = System.currentTimeMillis();
        calendario = Calendar.getInstance();
        horaInicio = calendario.get(Calendar.HOUR_OF_DAY);
        minutosInicio = calendario.get(Calendar.MINUTE);
        segundosInicio = calendario.get(Calendar.SECOND);
        milisegundosInicio = calendario.get(Calendar.MILLISECOND);

        if (op == 1) {
            busquedaAmplitud();
        } else if (op == 2) {
            vegasOne();
        } else if (op == 3) {
            vegasTwo();
        }

        tiempoF = System.currentTimeMillis();
        calendario = Calendar.getInstance();
        horaFin = calendario.get(Calendar.HOUR_OF_DAY);
        minutosFin = calendario.get(Calendar.MINUTE);
        segundosFin = calendario.get(Calendar.SECOND);
        milisegundosFin = calendario.get(Calendar.MILLISECOND);

        System.out.println("Comienzo:\t" + horaInicio + ":" + minutosInicio + ":" + segundosInicio + ":" + milisegundosInicio);
        System.out.println("Fin:\t\t" + horaFin + ":" + minutosFin + ":" + segundosFin + ":" + milisegundosFin);
        System.out.println("H:M:S:M " + ((tiempoF - tiempo) / 3600000) + ":" + ((tiempoF - tiempo) / 60000) + ":" + (tiempoF - tiempo) / 1000 + ":" + (tiempoF - tiempo));

        System.exit(0);

    }

    public void generarFichas() {
        fichas = new ArrayList<>();
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

    public void inicializarTablero() {
        tablero = tablerito.tablero;
    }

    public void inicializarFichas() {
        fichas = tablerito.fichas;
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
        BusquedaAmplitud ba = new BusquedaAmplitud(tablero, fichas);
        ba.busquedaAmplitud();
    }

    public void vegasOne() {
        VegasOne vegas = new VegasOne(tablero);
        vegas.algoritmoVegas();
    }

    private void vegasTwo() {
        VegasTwo vt = new VegasTwo(tablero);
        vt.algoritmoVegas();
    }

}
