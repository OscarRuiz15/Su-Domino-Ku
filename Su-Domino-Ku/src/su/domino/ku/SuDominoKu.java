/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.domino.ku;

import java.util.ArrayList;
import java.util.List;

public class SuDominoKu {

    private List<String>secuencia;
    private final List<String>fichas=generarFichas();
    private int tablero[][]=inicializarTablero();
    

    public SuDominoKu() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j]==0) {
                    if (j+1<9&&tablero[i][j+1]==0) {
                        
                    }
                }
                
            }
            
        }
    }
    public static void main(String[] args) {
        SuDominoKu sudominoku = new SuDominoKu();
    }
    
    public List<String> generarFichas(){
        List<String> fichas=new ArrayList<>();
        int cont=0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i<j+1) {
                    int s=j+2;
                    int z=i+1;
                    String ficha=cont+";"+z+";"+s+";"+"no uso";
                    fichas.add(ficha);
                    cont++;
                                    }
                
            }
            
        }
        return fichas;
    }
    public int[][]inicializarTablero(){
        int tablero[][]=new int[9][9];
        tablero[0][1]=7;
        tablero[0][2]=2;
        tablero[0][8]=5;
        tablero[2][4]=1;
        tablero[2][5]=8;
        tablero[3][8]=3;
        tablero[4][4]=6;
        tablero[8][0]=9;
        tablero[8][3]=4;
        return tablero;
    }
    
}

