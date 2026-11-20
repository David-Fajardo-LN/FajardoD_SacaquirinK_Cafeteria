/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public class CafeSencillo implements ComponenteBase{
    
    private final double precioIndividual = 1.50;

    @Override
    public String getNombre() {
        return "Cafe Sencillo";
    }

    @Override
    public double getPrecioTotal() {
        return precioIndividual;
    }
    
}
