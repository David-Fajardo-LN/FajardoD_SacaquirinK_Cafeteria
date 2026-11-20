/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public class SalsaCesar extends IngredienteAdicional{
    
    public SalsaCesar(ComponenteBase componente) {
        super(componente);
    }

    @Override
    public double getPrecioIndividual() {
        return 1;
    }

    @Override
    public String getNombre() {
        return "Salsa Cesar";
    }
    
        
}
