/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public class EnsaladaSencilla implements ComponenteBase{
    
    private final double precioIndividual = 3.50;

    @Override
    public String getNombre() {
        return "Ensalada Sencilla";
    }

    @Override
    public double getPrecioTotal() {
       return precioIndividual;
    }
    
}
