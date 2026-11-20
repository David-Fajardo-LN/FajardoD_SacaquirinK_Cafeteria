/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public class CremaBatida extends IngredienteAdicional{ 
    public CremaBatida(ComponenteBase componente) {
        super(componente);
    }
    
    @Override
    public double getPrecioIndividual(){
        return 0.75;
    }
    
    @Override
    public String getNombre() {
        return "Crema Batida"; 
    }  
}
