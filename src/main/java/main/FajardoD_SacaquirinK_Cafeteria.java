/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import controlador.Controlador;
import vista.SeleccionPedidoPanel;

/**
 *
 * @author User
 */
public class FajardoD_SacaquirinK_Cafeteria {

    public static void main(String[] args) {
          
        java.awt.EventQueue.invokeLater(() -> {
            SeleccionPedidoPanel ventana = new SeleccionPedidoPanel();
            new Controlador(ventana);      // conecta botones y tabla con el modelo
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}

