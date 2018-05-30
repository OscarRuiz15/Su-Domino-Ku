package su.domino.ku.frames;

import su.domino.ku.modelo.Logica;
import java.util.ArrayList;
import su.domino.ku.CargarArchivo;

public class Tablero extends javax.swing.JFrame {

    public static javax.swing.JTextField campos[][];
    ArrayList<String> datos;
    public static int tablero[][];
    private String linea[];

    public Tablero() {
        initComponents();
        menuAlgoritmo.setVisible(false);
    }

    public void iniciarCampos() {
        panelCampos.removeAll();
        panelCampos.repaint();
        panelCampos.setLayout(new java.awt.GridLayout(9, 9));
        campos = new javax.swing.JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                campos[i][j] = new javax.swing.JTextField();
                panelCampos.add(campos[i][j]);
                campos[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                campos[i][j].setName(i + "-" + j);
                campos[i][j].setFont(new java.awt.Font("Dialog", 1, 16));
                campos[i][j].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                campos[i][j].setEditable(false);
            }
            validate();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCampos = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuCargar = new javax.swing.JMenuItem();
        menuAlgoritmo = new javax.swing.JMenu();
        menuAmplitud = new javax.swing.JMenuItem();
        menuVegas1 = new javax.swing.JMenuItem();
        menuVegas2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Su-Domino-Ku");

        panelCampos.setMinimumSize(new java.awt.Dimension(0, 600));
        panelCampos.setPreferredSize(new java.awt.Dimension(699, 0));

        javax.swing.GroupLayout panelCamposLayout = new javax.swing.GroupLayout(panelCampos);
        panelCampos.setLayout(panelCamposLayout);
        panelCamposLayout.setHorizontalGroup(
            panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelCamposLayout.setVerticalGroup(
            panelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        menuArchivo.setText("Archivo");

        menuCargar.setText("Cargar");
        menuCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCargarActionPerformed(evt);
            }
        });
        menuArchivo.add(menuCargar);

        menuBar.add(menuArchivo);

        menuAlgoritmo.setText("Algoritmo");

        menuAmplitud.setText("Busqueda por Amplitud");
        menuAmplitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAmplitudActionPerformed(evt);
            }
        });
        menuAlgoritmo.add(menuAmplitud);

        menuVegas1.setText("Vegas Tipo 1");
        menuVegas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVegas1ActionPerformed(evt);
            }
        });
        menuAlgoritmo.add(menuVegas1);

        menuVegas2.setText("Vegas Tipo 2");
        menuVegas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVegas2ActionPerformed(evt);
            }
        });
        menuAlgoritmo.add(menuVegas2);

        menuBar.add(menuAlgoritmo);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCargarActionPerformed
        CargarArchivo ca = new CargarArchivo();
        datos = ca.CargarArchivo();

        if (datos.size() > 0) {
            menuAlgoritmo.setVisible(true);
            int x = 0, y = 1;
            tablero = new int[9][9];

            for (int i = 0; i < 9; i++) {
                linea = datos.get(i).split(" ");
                for (int j = 0; j < linea.length; j++) {
                    tablero[i][j] = Integer.parseInt(linea[j]);
                }
            }

            iniciarCampos();
            mostrarTablero();
        }
    }//GEN-LAST:event_menuCargarActionPerformed

    private void menuAmplitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAmplitudActionPerformed
        Logica logic = new Logica(1);
    }//GEN-LAST:event_menuAmplitudActionPerformed

    private void menuVegas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVegas1ActionPerformed
        Logica logic = new Logica(2);
    }//GEN-LAST:event_menuVegas1ActionPerformed

    private void menuVegas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVegas2ActionPerformed
        Logica logic = new Logica(3);
    }//GEN-LAST:event_menuVegas2ActionPerformed
    public void mostrarTablero() {
        int aux1 = 2;
        int aux2 = 2;

        for (int i = 0; i < tablero.length; i++) {
            aux1 = 2;
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] != 0) {
                    campos[i][j].setText("" + tablero[i][j]);
                    campos[i][j].setBackground(new java.awt.Color(132, 190, 229));
                }
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu menuAlgoritmo;
    private javax.swing.JMenuItem menuAmplitud;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuCargar;
    private javax.swing.JMenuItem menuVegas1;
    private javax.swing.JMenuItem menuVegas2;
    private javax.swing.JPanel panelCampos;
    // End of variables declaration//GEN-END:variables
}
